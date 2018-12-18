package np.com.naveenniraula.sahayatri.ui.owner.vehicles.garage;

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

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.data.model.Vehicle;
import np.com.naveenniraula.sahayatri.ui.owner.BaseFragment;
import np.com.naveenniraula.sahayatri.ui.owner.vehicles.garage.adapter.GarageAdapter;

public class GarageFragment extends BaseFragment {

    private GarageViewModel mViewModel;

    public static GarageFragment newInstance() {
        return new GarageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.vehicle_detail_fragment, container, false);
    }

    private GarageAdapter garageAdapter;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView totalVehicle;
    private RadioGroup radioGroup;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        changeTitle(R.string.title_garage);

        garageAdapter = new GarageAdapter();

        progressBar = view.findViewById(R.id.vdfProgressBar);
        radioGroup = view.findViewById(R.id.vdfRadioGroup);
        totalVehicle = view.findViewById(R.id.vdfVehicleCount);
        recyclerView = view.findViewById(R.id.vdfVehicles);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(garageAdapter);

        garageAdapter.setOnVehiicleSelectedListener(position -> {
            Vehicle model = garageAdapter.getItemAt(position);
            showBottomSheet();
        });

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {

            if (checkedId == R.id.vdfRgDay) {

                // call day vehicles
                mViewModel.fetchVehicles("Day");
            } else {

                // call night buses
                mViewModel.fetchVehicles("Night");
            }
        });
    }

    private void showBottomSheet() {
        GarageBottomSheetFragment garageBottomSheetFragment = new GarageBottomSheetFragment();
        garageBottomSheetFragment.show(getChildFragmentManager(), "bottom_sheet");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(GarageViewModel.class);

        mViewModel.observeVehicleUpdate().observe(this, vehicles -> {

            if (vehicles == null) {
                return;
            }

            totalVehicle.setText(String.valueOf(vehicles.size()));
            hideProgressBar();
            garageAdapter.setVehicleList(vehicles);
        });

        radioGroup.check(R.id.vdfRgDay);
    }

    private void showProgressBar() {

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStop() {
        super.onStop();

        garageAdapter = null;
    }
}
