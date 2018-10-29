package np.com.naveenniraula.sahayatri.util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

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

    public static void snack(Context ctx, View view, String message) {
        if (!TextUtils.isEmpty(message)) {
            Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorPrimaryDark));
            TextView tv = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text); //snackbar_text
            tv.setTextColor(ContextCompat.getColor(ctx, R.color.colorWhite));
            snackbar.show();
        }
    }

}
