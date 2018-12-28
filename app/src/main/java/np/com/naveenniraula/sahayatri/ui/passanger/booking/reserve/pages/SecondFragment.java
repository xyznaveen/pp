package np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.pages;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.lang.ref.WeakReference;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.data.model.Vehicle;
import np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.BookVehicleFragment;
import np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.adapter.AvailableBusAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends BasePageFragment {

    public static final String VEHICLE_KEY = "Vehicle Key";
    private static final String USER_KEY = "User Key";
    public static final String VEHICLE_NAME = "Vehicle Name";

    private Bundle vehiceInfo = new Bundle();

    public SecondFragment() {
        // Required empty public constructor
    }

    public static BasePageFragment newInstance(BookVehicleFragment bookVehicleFragment) {
        SecondFragment secondFragment = new SecondFragment();
        secondFragment.parentWeakReference = new WeakReference<>(bookVehicleFragment);
        return secondFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.fsProgress);
        recyclerView = view.findViewById(R.id.fsVehicleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        initViews();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (parentWeakReference.get() != null) {

            parentWeakReference.get().getViewModel().observeDataChanges()
                    .observe(this, bundle -> {

                        if (bundle != null
                                && FirstFragment.getClassName().equals(bundle.getString(SENDER))) {

                            progressBar.setVisibility(View.VISIBLE);
                            vehiceInfo = bundle;
                            fetchMatchingVehicles(bundle);
                            return;
                        }
                    });
        }
    }

    private AvailableBusAdapter adapter;

    private void fetchMatchingVehicles(Bundle value) {

        View view = getView();
        if (view == null) return;

        String travelMode = value.getString(BasePageFragment.TRAVEL_MODE);
        if (travelMode == null) return;

        Query query = FirebaseDatabase.getInstance()
                .getReference("VehicleList")
                .child(travelMode);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Vehicle>()
                .setQuery(query, Vehicle.class)
                .build();

        if (adapter == null) {
            adapter = new AvailableBusAdapter(options, view.getContext());
            adapter.startListening();
        }

        recyclerView.setAdapter(adapter);

        adapter.setBusSelectedListener((position, model) -> {

            FirebaseAuth auth = FirebaseAuth.getInstance();
            vehiceInfo = bookingConfiguration;
            vehiceInfo.putString(VEHICLE_KEY, model.getKey());
            vehiceInfo.putString(USER_KEY, auth.getUid());
            vehiceInfo.putString(VEHICLE_NAME, model.getOwnerName());

            adapter.notifyItemChanged(position);
            enableButton();
        });

        adapter.setDataFetchCompleteListener(dataCount -> {
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        });
    }

    private void enableButton() {
        View view = getView();

        if (view == null) {
            return;
        }

        final Button showSeats = view.findViewById(R.id.btnNext);
        showSeats.setEnabled(true);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (adapter != null) {
            adapter.stopListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.stopListening();
        }
    }

    private void initViews() {

        View view = getView();

        if (view == null) {
            return;
        }

        Button showSeats = view.findViewById(R.id.btnNext);
        showSeats.setOnClickListener(v -> {

            if (parentWeakReference.get() != null) {

                // add bundle to view model
                parentWeakReference.get().getViewModel().addData(vehiceInfo);

                vehiceInfo.putString(BasePageFragment.FROM, SecondFragment.getClassName());
                parentWeakReference.get().nextPage(vehiceInfo);
            }
        });
        showSeats.setEnabled(false);
    }

    @Override
    public void setData(Bundle bundle) {
        super.setData(bundle);
    }

    public static String getClassName() {
        return SecondFragment.class.getSimpleName();
    }

}
