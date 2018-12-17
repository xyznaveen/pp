package np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.pages;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.BookVehicleFragment;
import np.com.naveenniraula.sahayatri.util.validation.Rectify;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends BasePageFragment {

    private static final String NIGHT = "Night";
    private static final String DAY = "Day";
    private Bundle travelOptions = new Bundle();

    public static FirstFragment newInstance(BookVehicleFragment bookVehicleFragment) {

        FirstFragment fragment = new FirstFragment();
        fragment.parentWeakReference = new WeakReference<>(bookVehicleFragment);
        return fragment;
    }

    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    private EditText editText;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editText = view.findViewById(R.id.ffDate);
        editText.setOnClickListener(v -> showDatePicker());

        Button next = view.findViewById(R.id.ffNext);
        next.setOnClickListener(v -> {

            if (isInputValid()) {

                saveOptionsToBundle();
                nextPage(travelOptions);
            }
        });
    }

    private boolean isInputValid() {
        View view = getView();

        if (view == null) {
            return false;
        }

        Rectify rectify = new Rectify();
        EditText date = view.findViewById(R.id.ffDate);
        rectify.basic(date);
        return rectify.validate();
    }

    private void setDate(String date) {
        editText.setText(date);
    }

    private void showDatePicker() {

        if (getContext() == null || getActivity() == null) {

            return;
        }

        Calendar cal = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            travelOptions.putLong(BasePageFragment.DATE, calendar.getTimeInMillis());

            setDate(dayOfMonth + "-" + month + "-" + year);
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        // pre booking only before 2 weeks.
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(21));
        datePickerDialog.show();
    }

    private void saveOptionsToBundle() {

        View view = getView();

        if (view == null) {
            return;
        }

        Spinner from = view.findViewById(R.id.ffFrom);
        Spinner to = view.findViewById(R.id.ffTo);
        RadioGroup travelMode = view.findViewById(R.id.ffTravelMode);

        String strFrom = from.getSelectedItem().toString();
        String strTo = to.getSelectedItem().toString();
        String strTravelMode = travelMode.getCheckedRadioButtonId() == R.id.fftmDay
                ? DAY
                : NIGHT;

        travelOptions.putString(FROM, strFrom);
        travelOptions.putString(TO, strTo);
        travelOptions.putString(TRAVEL_MODE, strTravelMode);
        travelOptions.putString(SENDER, getClassName());

        bookingConfiguration = travelOptions;
    }

    public static String getClassName() {
        return FirstFragment.class.getSimpleName();
    }

}
