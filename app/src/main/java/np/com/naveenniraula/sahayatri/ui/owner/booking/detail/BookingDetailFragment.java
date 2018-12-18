package np.com.naveenniraula.sahayatri.ui.owner.booking.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.data.model.DateModel;
import np.com.naveenniraula.sahayatri.ui.owner.booking.detail.adapters.BookingDetailAdapter;

public class BookingDetailFragment extends Fragment {

    public static final String VEHICLE_KEY = "Vehicle Key";

    private BookingDetailViewModel mViewModel;

    private Spinner spinner;
    private RecyclerView recyclerView;

    private ProgressBar progressBar;
    private TextView progressBarStatus;

    public static BookingDetailFragment newInstance(String vehicleKey) {

        Bundle bundle = new Bundle();
        bundle.putString(VEHICLE_KEY, vehicleKey);

        BookingDetailFragment fragment = new BookingDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.booking_detail_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
    }

    private BookingDetailAdapter bookingDetailAdapter;

    private void initViews() {
        View view = getView();

        if (view == null) {
            return;
        }

        bookingDetailAdapter = new BookingDetailAdapter();

        spinner = view.findViewById(R.id.bdfDate);
        recyclerView = view.findViewById(R.id.bdfBookingList);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(bookingDetailAdapter);
        progressBar = view.findViewById(R.id.bdfProgressBar);
        progressBarStatus = view.findViewById(R.id.bdfProgressbarStatus);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Bundle args = BookingDetailFragment.this.getArguments();
                if (args != null) {
                    String vehicleKey = args.getString(VEHICLE_KEY);
                    TextView dateView = (TextView) view;
                    showProgressBar();
                    mViewModel.fetchBookings(dateView.getText().toString(), vehicleKey);
                    Log.i("BQ7CH72", "Item selected :: " + dateView.getText().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                hideProgressBar();
            }
        });
    }

    private void hideProgressBar() {
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        progressBarStatus.setVisibility(View.GONE);
    }

    private void showProgressBar() {

        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        progressBarStatus.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BookingDetailViewModel.class);

        mViewModel.observeDateKey().observe(this, value -> {

            if (value == null) {
                return;
            }

            ArrayAdapter<String> stringArrayAdapter =
                    new ArrayAdapter<>(recyclerView.getContext(), R.layout.item_spinner);
            stringArrayAdapter.setNotifyOnChange(true);
            stringArrayAdapter.setDropDownViewResource(android.R.layout
                    .simple_selectable_list_item);
            for (DateModel dm :
                    value) {

                stringArrayAdapter.add(dm.getKey());
            }
            spinner.setAdapter(stringArrayAdapter);
        });
        mViewModel.fetchDateKey();

        mViewModel.observeBookingsLiveData().observe(this, bookingModelList -> {

            hideProgressBar();
            bookingDetailAdapter.setBookingModelList(bookingModelList);

            Log.i("BQ7CH72", "PROGRESS BAR :: " + bookingModelList.size());
        });

    }

}
