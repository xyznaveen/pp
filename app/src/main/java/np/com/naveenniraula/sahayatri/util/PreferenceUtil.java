package np.com.naveenniraula.sahayatri.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.ref.WeakReference;

import static android.content.Context.MODE_PRIVATE;

public class PreferenceUtil {

    private WeakReference<Context> weakReference;
    private static final String PREF_NAME = "SharedPrefName";
    public static final String PREF_VERIFICATION_ID = "VerificationID";

    public PreferenceUtil(final Context context) {
        weakReference = new WeakReference<>(context);
    }

    public void saveString(String key, String value) {
        SharedPreferences sp = weakReference.get().getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }

    public String getString(String key) {
        SharedPreferences sp = weakReference.get().getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        return sp.getString(key, Constants.STR_EMPTY.toString());
    }

    public static void firstTimeAskingPermission(Context context, String permission, boolean isFirstTime) {
        SharedPreferences sharedPreference =
                context.getSharedPreferences(Constants.PREFS_FILE_NAME, MODE_PRIVATE);
        sharedPreference.edit().putBoolean(permission, isFirstTime).apply();
    }

    public static boolean isFirstTimeAskingPermission(Context context, String permission) {
        return context.getSharedPreferences(Constants.PREFS_FILE_NAME, MODE_PRIVATE)
                .getBoolean(permission, true);
    }

}
