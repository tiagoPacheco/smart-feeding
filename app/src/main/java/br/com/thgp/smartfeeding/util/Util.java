package br.com.thgp.smartfeeding.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import java.util.List;

import br.com.thgp.smartfeeding.R;
import br.com.thgp.smartfeeding.model.FeederDevice;

/**
 * Created by tiago on 03/03/2018.
 */

public class Util {
    public static final String KNOT_URL = "http://knot-test.cesar.org.br:3000";
    public static final String DEFAULT_UUID = "a9cc3e68-abea-4541-972a-39cf32bd0000";
    public static final String DEFAULT_TOKEN = "097ee5c09e978c3ebc3841c362107de832d50664";
    public static final String EXTRA_DEVICE_UUID = "DEVICE_UUID";
    public static final String EXTRA_CURRENT_VALUE = "CURRENT_VALUE";
    public static FeederDevice CurrentFeederDevice = null;

    public static List<FeederDevice> DevicesList = null;

    public static ProgressDialog createSimpleProgressDialog(String title, String message, Context context) {
        return ProgressDialog.show(context, title, message, true);
    }

    public static boolean isFeederDeviceSetted(Context context) {
        if(CurrentFeederDevice == null){
            Toast.makeText(context, R.string.device_not_setted, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
