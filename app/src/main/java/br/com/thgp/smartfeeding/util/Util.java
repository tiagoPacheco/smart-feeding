package br.com.thgp.smartfeeding.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
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
    public static final String NotificationMealsRemaining = "NotificationMealsRemaining";
    public static final String NotificationPetEating = "NotificationAnimalEating";
    public static final String NotificationRemainingDaysEndMeal = "NotificationRemainingDaysEndMeal";

    public static List<FeederDevice> DevicesList = null;

    public static final int sNOTIFICATION_ID = 1000;

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

    public static void createSimpleNotification(Context context, String title, String content, Class activityClass) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(content);
        Intent resultIntent = new Intent(context, activityClass);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(activityClass);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        notificationManager.notify(sNOTIFICATION_ID, builder.build());
    }
}
