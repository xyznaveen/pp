package np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.pages;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.lang.ref.WeakReference;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.data.model.Vehicle;
import np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.BookVehicleFragment;
import np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.adapter.BusSeatAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdFragment extends BasePageFragment {

    public static final String VEHICLE_ID = "LTJ-AVWJxPAVHd9pnW1";

    public static ThirdFragment newInstance(BookVehicleFragment bookVehicleFragment) {

        ThirdFragment fragment = new ThirdFragment();
        fragment.parentWeakReference = new WeakReference<>(bookVehicleFragment);
        return fragment;
    }

    public ThirdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupBusSeat();

        registerListeners();
    }

    private BusSeatAdapter adapter;

    private void setupBusSeat() {

        View view = getView();
        if (view == null) {
            return;
        }

        final int COL = 6;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), COL);

        RecyclerView recyclerView = view.findViewById(R.id.fsSeatList);
        recyclerView.setLayoutManager(gridLayoutManager);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        Query query  = FirebaseDatabase.getInstance().getReference().child("vehicles")
                .equalTo("Edited 20123")
                .orderByPriority();
        FirebaseRecyclerOptions<Vehicle> options =
                new FirebaseRecyclerOptions.Builder<Vehicle>()
                        .setQuery(query, Vehicle.class)
                        .build();
        adapter = new BusSeatAdapter(options, view.getContext());
        recyclerView.setAdapter(adapter);
    }

    private void registerListeners() {

        View view = getView();

        if (view == null) {
            return;
        }

        Button next = view.findViewById(R.id.fsNext);
        next.setOnClickListener(v -> nextPage());
    }

    @Override
    public void onStart() {
        super.onStart();

        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.stopListening();
        }
    }
}
