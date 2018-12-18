package np.com.naveenniraula.sahayatri.ui.owner.booking;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.data.model.Vehicle;
import np.com.naveenniraula.sahayatri.ui.owner.BaseFragment;
import np.com.naveenniraula.sahayatri.ui.owner.booking.adapters.BookingStatusAdapter;
import np.com.naveenniraula.sahayatri.ui.owner.booking.detail.BookingDetailFragment;

public class BookingStatusFragment extends BaseFragment {

    private BookingStatusViewModel mViewModel;

    public static BookingStatusFragment newInstance() {
        return new BookingStatusFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.booking_status_fragment, container, false);
    }


    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView progressBarStatus;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        changeTitle(R.string.title_bookings);

        progressBar = view.findViewById(R.id.bsfProgress);
        progressBarStatus = view.findViewById(R.id.bsfProgressStatus);

        initViews();
    }

    private BookingStatusAdapter adapter;

    private void initViews() {

        View view = getView();

        if (view == null) {
            return;
        }

        recyclerView = view.findViewById(R.id.bsfVehicleList);
        adapter = new BookingStatusAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setHasFixedSize(true);

        RadioGroup radioGroup = view.findViewById(R.id.bsfRbGroup);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {

            showProgress();
            if (checkedId == R.id.bsfDay) {
                mViewModel.fetchVehicle("Day");
            } else {
                mViewModel.fetchVehicle("Night");
            }
        });

    }

    private void showProgress() {

        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        progressBarStatus.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {

        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        progressBarStatus.setVisibility(View.GONE);
    }


    List<Vehicle> vehicleList = new ArrayList<>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BookingStatusViewModel.class);

        mViewModel.observeVehicle().observe(this, value -> {
            hideProgress();
            adapter.setDataSet(value);
        });

        adapter.setOnBusClickListener(position -> {

            String vehicleKey = adapter.getItemAt(position).getKey();
            changeFragment(BookingDetailFragment.newInstance(vehicleKey));
        });
    }

}
