package br.com.thgp.smartfeeding.services;


import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.List;

import br.com.thgp.smartfeeding.model.FeederData;
import br.com.thgp.smartfeeding.util.PoolingTimer;
import br.org.cesar.knot.lib.connection.FacadeConnection;
import br.org.cesar.knot.lib.exception.InvalidDeviceOwnerStateException;
import br.org.cesar.knot.lib.exception.KnotException;
import br.org.cesar.knot.lib.model.KnotList;
import br.org.cesar.knot.lib.model.KnotQueryData;

/**
 * Created by tiago on 05/03/18.
 */

public class KnotIntegrationService extends Service implements IKnotServiceConnection{

    private OnDataChangedListener mListener;

    private PoolingTimer poolingTimer;

    private String deviceUUID;

    private List<FeederData> deviceData;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ServiceBinder(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    private void syncAndStartPooling() {
        // Force a sync and reeschedule the last pooling
        if (poolingTimer != null) {
            poolingTimer.stopPooling();
            poolingTimer.startPooling();
        } else {
            this.poolingTimer = new PoolingTimer(poolingListener);
            poolingTimer.startPooling();
        }
        // Sync all data of devices
        syncData();
    }

    private void syncData() {
        new SyncDeviceDataTask().execute();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        unsubscribe();
        return super.onUnbind(intent);
    }

    private PoolingTimer.PoolingListener poolingListener = new PoolingTimer.PoolingListener() {
        @Override
        public void onPoolingFinished() {
            syncData();
        }
    };

    @Override
    public void subscribe(String deviceUUID, OnDataChangedListener listener) {
        mListener = listener;

        this.deviceUUID = deviceUUID;

        //start pooling
        syncAndStartPooling();

    }


    private void notifyListener(List<FeederData> deviceData){
        if(mListener != null) {
            mListener.onDataChanged(deviceData);
        }
    }

    @Override
    public void unsubscribe() {
        mListener = null;

        //stop pooling
        if (poolingTimer != null) {
            poolingTimer.stopPooling();
        }

        stopSelf();
    }

    private class SyncDeviceDataTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {

            KnotList<FeederData> listOfData = new KnotList<>(FeederData.class);
            KnotQueryData knotQueryData = new KnotQueryData();

            try {
                deviceData = FacadeConnection.getInstance().httpGetDataList(deviceUUID, knotQueryData,listOfData);

            } catch (KnotException e) {
                e.printStackTrace();
            } catch (InvalidDeviceOwnerStateException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            if (deviceData != null && deviceData.size() > 0) {
                for (FeederData data : deviceData){
                    notifyListener(deviceData);
                }
            }
        }
    }
}
