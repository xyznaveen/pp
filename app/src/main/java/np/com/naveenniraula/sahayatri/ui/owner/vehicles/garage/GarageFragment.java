package np.com.naveenniraula.sahayatri.ui.owner.vehicles.garage;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.data.model.Vehicle;
import np.com.naveenniraula.sahayatri.ui.owner.BaseFragment;
import np.com.naveenniraula.sahayatri.ui.owner.vehicles.garage.adapter.VehicleAdapter;

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

    private VehicleAdapter<Vehicle> va;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        changeTitle(R.string.title_garage);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getUid() == null) {
            return;
        }

//        Query query = FirebaseDatabase.getInstance().getReference()
//                .child("VehicleList").child(auth.getUid()).orderByKey();
        Query query = FirebaseDatabase.getInstance().getReference()
                .child("VehicleList")
                .orderByChild("vehicleOwnerKey")
                .equalTo(auth.getUid());

        FirebaseRecyclerOptions<Vehicle> options =
                new FirebaseRecyclerOptions.Builder<Vehicle>()
                        .setQuery(query, Vehicle.class)
                        .build();
        va = new VehicleAdapter<>(options, getContext());
        RecyclerView rv = view.findViewById(R.id.vdfVehicles);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(va);

        // called after data has been fetched
        va.setDataFetchCompleteListener(dataCount -> {
            ProgressBar pb = view.findViewById(R.id.vdfProgressBar);
            pb.setVisibility(View.GONE);

            TextView totalVehicle = view.findViewById(R.id.vdfVehicleCount);
            totalVehicle.setText(String.valueOf(dataCount));

            rv.setVisibility(View.VISIBLE);
        });

        // user selects an item from the list
        va.setOptionListener((position, model) -> {

            GarageBottomSheetFragment garageBottomSheetFragment = new GarageBottomSheetFragment();
            garageBottomSheetFragment.show(getActivity().getSupportFragmentManager(), "bottom_sheet");
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(GarageViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onStart() {
        super.onStart();

        va.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

        va.stopListening();
    }
}