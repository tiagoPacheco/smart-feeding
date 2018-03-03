package br.com.thgp.smartfeeding.ui;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import br.com.thgp.smartfeeding.R;
import br.com.thgp.smartfeeding.model.FeederDevice;
import br.com.thgp.smartfeeding.util.Logger;
import br.com.thgp.smartfeeding.util.PreferenceUtil;
import br.com.thgp.smartfeeding.util.TypePreferenceEnum;
import br.com.thgp.smartfeeding.util.Util;
import br.org.cesar.knot.lib.connection.FacadeConnection;
import br.org.cesar.knot.lib.exception.InvalidDeviceOwnerStateException;
import br.org.cesar.knot.lib.exception.KnotException;
import br.org.cesar.knot.lib.model.KnotList;

public class DashboardActivity extends AppCompatActivity {

    private static ProgressDialog mProgressDialog = null;

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

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, new FeederFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_feed:
                    transaction.replace(R.id.container, new FeederFragment()).commit();
                    return true;
                case R.id.navigation_devices:
                    transaction.replace(R.id.container, new DevicesFragment()).commit();
                    return true;
                case R.id.navigation_settings:
                    getFragmentManager().beginTransaction().replace(R.id.container, new SettingsFragment()).commit();
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

}
