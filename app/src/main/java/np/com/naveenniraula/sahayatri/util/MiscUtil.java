package np.com.naveenniraula.sahayatri.util;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import np.com.naveenniraula.sahayatri.R;

public class MiscUtil {

    /**
     * Compares the two passed class if they both are same.
     *
     * @param view  the view which requires validation
     * @param clazz the class to compare against them.
     * @param <T>   could be any view or object.
     * @return true if it is an instance else false
     */
    public static <T> boolean isInstanceOf(View view, Class<?> clazz) {

        return view != null && clazz.isInstance(view);
    }

    public static void snack(View view, String message) {
        if (!TextUtils.isEmpty(message)) {
            Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimaryDark));
            TextView tv = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text); //snackbar_text
            tv.setTextColor(ContextCompat.getColor(view.getContext(), R.color.colorWhite));
            snackbar.show();
        }
    }

    public static String sha512(@NonNull String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] digest = md.digest(str.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte aDigest : digest) {
            sb.append(Integer.toString((aDigest & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static String md5(String string) throws NoSuchAlgorithmException {
        MessageDigest messageDigest;
        messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(string.getBytes());
        byte[] messageDigestMD5 = messageDigest.digest();
        StringBuilder stringBuffer = new StringBuilder();
        for (byte bytes : messageDigestMD5) {
            stringBuffer.append(String.format("%02x", bytes & 0xff));
        }
        return stringBuffer.toString();
    }

}
