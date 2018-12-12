package pt.estig.myfavoriteplaces.prefs;

import android.content.Context;
import android.content.SharedPreferences;


public class PreferencesHelper {
    private static final String PREFS_FILE = "app_prefs";

    // Keys
    public static final String SORTING_PREF = "sorting";
    public static final String FIRST_TIME_PREF = "first_time";

    private static String PLACES_LIST_SORT = "PlaceListSort";

    public static String USERID = "userid";
    public static String USERNAME = "username";




    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor edit(Context context) {
        return getPrefs(context).edit();
    }

    public static void set(Context context, String key, boolean b) {
        edit(context).putBoolean(key, b).apply();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return getPrefs(context).getBoolean(key, defValue);
    }

    public static void set(Context context, String key, String s) {
        edit(context).putString(key, s).apply();
    }

    public static String getString(Context context, String key, String defValue) {
        return getPrefs(context).getString(key, defValue);
    }




    // -----

    public static boolean getFirstTimeRunning(Context context, boolean defValue) {
        return getBoolean(context, "sdas", defValue);
    }

    public static void setContactListSort(Context context, boolean naturalSorting) {
        set(context, PLACES_LIST_SORT, naturalSorting);
    }

    public static boolean getContactListSort(Context context) {
        return getBoolean(context, PLACES_LIST_SORT, true);
    }
}
