package np.com.naveenniraula.sahayatri.util;

import android.graphics.Color;

public class Constants {

    public static final CharSequence STR_EMPTY = "";
    public static final String EMPTY_STRING = "";
    public static final CharSequence ERR_INVALID_INPUT = "please enter a valid value.";

    enum ValidationTypes {
        EMAIL, PASSWORD, NAME,
    }

    public static final String PREFS_FILE_NAME = "app_pref";
    public static final int REQUEST_LOGIN_PERMS = 14001;

    public static final int BOOKED_COLOR = Color.parseColor("#B71C1C");
    public static final int SELECTED_COLOR = Color.parseColor("#1565c0");
    public static final int SEAT_COLOR = Color.parseColor("#008577");
    public static final int DISABLED_COLOR = Color.parseColor("#FFFFFF");

}
