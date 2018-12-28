package np.com.naveenniraula.sahayatri.util;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class InputHelper {

    public static boolean isValidName(String s) {
        //TODO check for number input
        return s != null && !s.isEmpty() && s.split(" ").length == 2;
    }

    public static boolean isValidPassword(String s) {
        return s != null && !s.isEmpty() && s.length() >= 6;
    }

    public static boolean isValidPhone(String s) {
        return s != null && !s.isEmpty() && s.length() == 10;
    }

    public static boolean isValidEmail(String str) {

        return Patterns.EMAIL_ADDRESS.matcher(str).matches();
    }

    /**
     * Return the content of text fields.
     *
     * @param view
     * @return
     */
    public static String getString(View view) {

        if (view == null) {
            return "";
        }

        if (MessageHelper.isInstanceOf(view, TextInputLayout.class)) {

            TextInputLayout v = (TextInputLayout) view;
            return v.getEditText() == null ? "" : String.valueOf(v.getEditText().getText());
        } else if (MessageHelper.isInstanceOf(view, TextInputEditText.class)) {

            TextInputEditText v = (TextInputEditText) view;
            return v.getText() == null ? "" : String.valueOf(v.getText());
        } else if (MessageHelper.isInstanceOf(view, EditText.class)) {

            EditText v = (EditText) view;
            return String.valueOf(v.getText());
        } else if (MessageHelper.isInstanceOf(view, TextView.class)) {

            TextView v = (TextView) view;
            return String.valueOf(v.getText());
        }

        return "";
    }

    public static String getStringAllCaps(View view) {

        return getString(view).toUpperCase();
    }

    public static String removeSpace(String inputString) {
        return inputString.replaceAll(" ", "");
    }

}
