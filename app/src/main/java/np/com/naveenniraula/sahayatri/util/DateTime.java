package np.com.naveenniraula.sahayatri.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTime {


    public static String getHumanReadableDate(String inputDate) {
        SimpleDateFormat parser = new SimpleDateFormat("ddMMMyyyy", Locale.ENGLISH);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
        try {
            Date date = parser.parse(inputDate);
            String hrDate = dateFormat.format(date);

            Log.i("BQ7CH72", "HUman readable date :: " + hrDate);

            return hrDate;
        } catch (ParseException ignore) {
        }

        return inputDate;
    }

    public static String getQueryableDate(String inputDate) {
        SimpleDateFormat parser = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMMyyyy", Locale.ENGLISH);
        try {
            Date date = parser.parse(inputDate);
            String qrDate = dateFormat.format(date).toUpperCase();

            Log.i("BQ7CH72", "Query date :: " + qrDate);
            return qrDate;
        } catch (ParseException ignore) {
        }

        return inputDate;
    }
}
