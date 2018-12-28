package np.com.naveenniraula.sahayatri.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

public class PermissionUtil {

    public interface PermissionListener {

        // ask for permission
        void onPermissionRequest();

        // callback when permission is denied by user
        void onPermissionDenied();

        // when permission is disabled after granted
        void onPermissionDisabled();

        // when permission is granted
        void onPermissionGranted();
    }

    /**
     * Checks whether permissions are required for this device or not.
     *
     * @return true if device requires permission else false
     */
    public static boolean isPermissionRequired() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * Checks whether requested permission is already granted.
     *
     * @param context    the activity which is invoking this method
     * @param permission the required permission
     * @return true if permission is not granted else flase
     */
    public static boolean isPermissionGranted(final Context context, final String permission) {

        int result = ActivityCompat.checkSelfPermission(context, permission);
        return isPermissionRequired() && result != PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Helper to ask for permission based on passed permission.
     *
     * @param context
     * @param permission
     * @param permissionListener
     */
    public static void askForPermission(final Context context, final String permission,
                                        PermissionListener permissionListener) {

        if (isPermissionGranted(context, permission)) {
            if (isPermissionRequired()
                    && ((Activity) context).shouldShowRequestPermissionRationale(permission)) {
                // user denied the requested permission previously
                permissionListener.onPermissionDenied();
            } else {
                // the user denied the request for the fragment_first time
                // maybe this is the fragment_first application launch ?
                if (PreferenceUtil.isFirstTimeAskingPermission(context, permission)) {
                    // the permission is being asked for the fragment_first time
                    PreferenceUtil.firstTimeAskingPermission(context, permission, false);
                    permissionListener.onPermissionRequest();
                } else {
                    // the requested permission has been disabled by the user
                    // maybe from the settings menu or (god ?/ who) knows how
                    permissionListener.onPermissionDisabled();
                }
            }
        } else {
            // the requested permission has already been granted
            permissionListener.onPermissionGranted();
        }
    }

    public static void requestPermission(final Context context, final String[] perm, final int reqCode) {
        ActivityCompat.requestPermissions((Activity) context, perm, reqCode);
    }

    public static void requestPermission(final Fragment fragment, final String[] perm, final int reqCode) {
        fragment.requestPermissions(perm, reqCode);
    }

}
