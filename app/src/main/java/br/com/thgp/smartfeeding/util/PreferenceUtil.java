package br.com.thgp.smartfeeding.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

import br.com.thgp.smartfeeding.application.App;

/**
 * Created by tiago on 01/03/2018.
 */

public class PreferenceUtil {
    public static final String Preference_Name = "Preference_Name";
    public static final String Preference_Weight = "Preference_Weight";
    public static final String Preference_Genre_Masc = "Preference_Genre_Masc";
    public static final String Preference_Breed = "Preference_Breed";
    public static final String Preference_BirthDate = "Preference_BirthDate";

    public static final String Preference_Amount_Automatic = "Preference_Amount_Automatic";
    public static final String Preference_Amount_Stock = "Preference_AmountStock";
    public static final String Preference_Period = "Preference_Period";

    private static final String PREFERENCES_NAME = "smartfeeding_preference";

    public static final String KEY_END_POINT = "KNOT_URL";
    public static final String KEY_UUID = "USER_UUID";
    public static final String KEY_TOKEN = "USER_TOKEN";

    private static Object lock = new Object();

    private static PreferenceUtil sInstance;

    private PreferenceUtil() {
    }

    /**
     * Gets instance.
     * @return the instance
     */
    public static PreferenceUtil getInstance() {
        synchronized (lock) {
            if (sInstance == null) {
                sInstance = new PreferenceUtil();
            }
            return sInstance;
        }
    }

    /**
     * Reset all sharedPreferences
     */
    public static void reset() {
        getInstance().getPref().edit().clear().apply();
    }

    public String getEndPoint() {
        return getPref().getString(KEY_END_POINT, "");
    }

    private SharedPreferences getPref() {
        final Context context = App.getContext();
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static void setPreferenceValue(String key, Object value, TypePreferenceEnum type){
        SharedPreferences sharedPref = App.getContext().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        switch (type){
            case Int:
                editor.putInt(key, (Integer)value);
                break;
            case Bool:
                editor.putBoolean(key, (boolean)value);
                break;
            case Float:
                editor.putFloat(key,  Float.parseFloat(value.toString()));
                break;
            case String:
                editor.putString(key, (String)value);
                break;
        }
        editor.commit();
    }

    public static Object getPreferenceValue(String key, TypePreferenceEnum type) {
        SharedPreferences sharedPref = App.getContext().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        Object value = null;
        switch (type){
            case Int:
                value = sharedPref.getInt(key, 0);
                break;
            case Bool:
                value = sharedPref.getBoolean(key, false);
                break;
            case Float:
                value = sharedPref.getFloat(key, 0);
                break;
            case String:
                value = sharedPref.getString(key, "");
                break;
        }

        return value;
    }
}
