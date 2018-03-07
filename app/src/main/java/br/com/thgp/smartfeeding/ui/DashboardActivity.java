package br.com.thgp.smartfeeding.ui;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import br.com.thgp.smartfeeding.R;
import br.com.thgp.smartfeeding.model.FeederData;
import br.com.thgp.smartfeeding.model.FeederDevice;
import br.com.thgp.smartfeeding.services.IKnotServiceConnection;
import br.com.thgp.smartfeeding.services.KnotIntegrationService;
import br.com.thgp.smartfeeding.services.OnDataChangedListener;
import br.com.thgp.smartfeeding.services.ServiceBinder;
import br.com.thgp.smartfeeding.util.Logger;
import br.com.thgp.smartfeeding.util.PreferenceUtil;
import br.com.thgp.smartfeeding.util.TypePreferenceEnum;
import br.com.thgp.smartfeeding.util.Util;
import br.org.cesar.knot.lib.connection.FacadeConnection;
import br.org.cesar.knot.lib.exception.InvalidDeviceOwnerStateException;
import br.org.cesar.knot.lib.exception.KnotException;
import br.org.cesar.knot.lib.model.KnotList;

public class DashboardActivity extends AppCompatActivity implements OnDataChangedListener, ServiceConnection {

    private static ProgressDialog mProgressDialog = null;
    private IKnotServiceConnection mKnotServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        new SyncData().execute();

        PreferenceUtil.setPreferenceValue(PreferenceUtil.KEY_UUID, Util.DEFAULT_UUID, TypePreferenceEnum.String);
        PreferenceUtil.setPreferenceValue(PreferenceUtil.KEY_TOKEN, Util.DEFAULT_TOKEN, TypePreferenceEnum.String);
        PreferenceUtil.setPreferenceValue(PreferenceUtil.KEY_END_POINT, Util.KNOT_URL, TypePreferenceEnum.String);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, new FeederFragment()).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent it = new Intent(this, KnotIntegrationService.class);
        startService(it);
        bindService(it, this, BIND_AUTO_CREATE);
    }


    @Override
    protected void onPause() {
        super.onPause();
        unbindService(this);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_feed:
                    transaction.replace(R.id.container, new FeederFragment()).commit();
                    return true;
                case R.id.navigation_devices:
                    transaction.replace(R.id.container, new DevicesFragment()).commit();
                    return true;
                case R.id.navigation_settings:
                    transaction.replace(R.id.container, new SettingsFragment()).commit();
                    return true;
            }
            return false;
        }
    };

    public class SyncData extends AsyncTask<Object, Object , List<FeederDevice>> {
        @Override
        protected void onPreExecute() {
            mProgressDialog = Util.createSimpleProgressDialog("Wait", "Processing...", DashboardActivity.this);
        }

        @Override
        protected List<FeederDevice> doInBackground(Object[] objects) {
            KnotList<FeederDevice> list = new KnotList<>(FeederDevice.class);
            try {

                FacadeConnection.getInstance().setupHttp(PreferenceUtil.getInstance().getEndPoint(),
                        (String) PreferenceUtil.getPreferenceValue(PreferenceUtil.KEY_UUID, TypePreferenceEnum.String),
                        (String) PreferenceUtil.getPreferenceValue(PreferenceUtil.KEY_TOKEN, TypePreferenceEnum.String));

                Util.DevicesList = FacadeConnection.getInstance().httpGetDeviceList(list);

                Logger.d("Size = " + Util.DevicesList.size());
            } catch (KnotException e) {
                Logger.d("KnotExecption");
                e.getMessage();
                e.printStackTrace();
            } catch (InvalidDeviceOwnerStateException e) {
                Logger.d("InvalidDeviceOwnerStateException");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<FeederDevice> list) {
            mProgressDialog.dismiss();
            super.onPostExecute(list);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(R.string.reset_preferences);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case 0:
                PreferenceUtil.reset();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataChanged(List<FeederData> deviceData) {
        for (FeederData data: deviceData){
            if (data != null) {
                switch (data.getCurrentValue()){
                    case Util.NotificationPetEating:
                        Util.createSimpleNotification(this, "Pet is eating",
                                "Your pet just started eating.", DashboardActivity.class);
                        break;
                    case Util.NotificationRemainingDaysEndMeal:
                        CreateRemainingDaysNotification();
                        break;

                    case Util.NotificationMealsRemaining:
                        CreateMealsRemainingNotification();
                        break;
                }
            }
        }
    }

    private void CreateMealsRemainingNotification() {
        Float amountByMeal = (Float) PreferenceUtil.getPreferenceValue(
                PreferenceUtil.Preference_Amount_Automatic, TypePreferenceEnum.Float);

        Float  amountStock = (Float) PreferenceUtil.getPreferenceValue(
                PreferenceUtil.Preference_Amount_Stock, TypePreferenceEnum.Float);

        Util.createSimpleNotification(this, "Check out your stock",
                 String.format("Hey! Your pet have %.0f meals remaining", amountStock / amountByMeal), DashboardActivity.class);
    }

    private void CreateRemainingDaysNotification() {
        Float amountByMeal = (Float) PreferenceUtil.getPreferenceValue(
                PreferenceUtil.Preference_Amount_Automatic, TypePreferenceEnum.Float);
        Float amountStock = (Float) PreferenceUtil.getPreferenceValue(
                PreferenceUtil.Preference_Amount_Stock, TypePreferenceEnum.Float);

        Float amountRemaining = amountStock / amountByMeal;
        //TODO Get this information from SharedPreferences
        Integer mealByDay = 3;

        Util.createSimpleNotification(this, "Check out your stock",
                String.format("Hey! Your pet have %.0f day(s) of food remaining", amountRemaining/mealByDay), DashboardActivity.class);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mKnotServiceConnection = ((ServiceBinder) service).getService();

        mKnotServiceConnection.subscribe(getIntent().getStringExtra(Util.EXTRA_DEVICE_UUID), DashboardActivity.this);

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mKnotServiceConnection = null;
    }

}
