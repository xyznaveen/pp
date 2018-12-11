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

import java.lang.ref.WeakReference;
import java.util.Calendar;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.BookVehicleFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends BasePageFragment {

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
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        Button next = view.findViewById(R.id.ffNext);
        next.setOnClickListener(v -> nextPage());
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
            setDate(dayOfMonth + "-" + month + "-" + year);
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

}
