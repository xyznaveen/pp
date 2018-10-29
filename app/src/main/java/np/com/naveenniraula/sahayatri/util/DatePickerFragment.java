package np.com.naveenniraula.sahayatri.util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    public interface DateSelectedListener {
        void onDateSet(Calendar selectedDate);
    }

    private DateSelectedListener dateSelectedListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(Calendar.YEAR, datePicker.getYear());
        selectedDate.set(Calendar.MONTH, datePicker.getMonth());
        selectedDate.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());

        // user selected a date, notify every listeners
        dateSelectedListener.onDateSet(selectedDate);
    }

    public void setDateSelectedListener(DateSelectedListener listener) {
        dateSelectedListener = listener;
    }

}
