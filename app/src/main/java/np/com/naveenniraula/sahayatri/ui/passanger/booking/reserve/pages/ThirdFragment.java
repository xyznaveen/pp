package np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.pages;


import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.data.model.BookingModel;
import np.com.naveenniraula.sahayatri.data.model.SeatModel;
import np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.BookVehicleFragment;
import np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.adapter.BusSeatAdapter;

import static np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.pages.SecondFragment.VEHICLE_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdFragment extends BasePageFragment {

    private Bundle bundle = new Bundle();

    private ThirdFragmentViewModel viewModel;


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

    private boolean fetchedOnce = false;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(ThirdFragmentViewModel.class);

        if (parentWeakReference.get() != null) {

            parentWeakReference.get().getViewModel().observeDataChanges()
                    .observe(this, bundle -> {

                        Log.i("BQ7CH72", "ThirdFragment data change observed. \n Bundle contents::: " + bundle);
                        if (bundle != null) {

                            this.bundle = bundle;

                            if (bundle.containsKey(VEHICLE_KEY)
                                    && bundle.containsKey(TRAVEL_MODE)) {
                                startFetchingInfo();
                            }
                        }
                    });
        }

        viewModel.observeVehicleData().observe(this, value -> {

            if (value != null && adapter.getItemCount() == 0) {

                Log.i("BQ7CH72", "Seat Created From Scratch.");
                adapter.add(prepareSeat(value.getTotalSeatCount()));
            }
        });

        viewModel.observeBooking().observe(this, value -> {

            if (value == null) return;

            if (value.size() == 0) {
                // not even a single booking has been done.
                // fetch vehicle seat from vehicle for booking.
                viewModel.fetchVehicleInformation(
                        bundle.getString(VEHICLE_KEY),
                        bundle.getString(FirstFragment.TRAVEL_MODE));
                return;
            }

            if (!fetchedOnce) {

                Log.i("BQ7CH72", "Booking info exists so created from the provided information.");
                adapter.add(prepareSeat(value));
                fetchedOnce = true;
            }
        });
    }

    private List<SeatModel> prepareSeat(List<BookingModel> value) {

        final List<SeatModel> seatModelList = new ArrayList<>();


        for (BookingModel model :
                value) {

            SeatModel seatModel = new SeatModel();

            boolean isBooked = model.getBookMode().equals("Booked");
            seatModel.setAvailable(!isBooked);
            seatModel.setSeatNumber(model.getSeatIdentifier());
            seatModel.setSelected(isBooked);
            seatModel.setAvailable(!isBooked);
            seatModelList.add(seatModel);
        }

        return seatModelList;
    }

    private List<SeatModel> prepareSeat(int totalSeat) {
        List<SeatModel> sm = new ArrayList<>();

        for (int i = 1; i <= totalSeat; i++) {
            sm.add(new SeatModel(String.valueOf(i), true, Color.parseColor("#008577"), false));
        }

        return sm;
    }

    private void startFetchingInfo() {

        String vehicleKey = bundle.getString(VEHICLE_KEY);
        Log.i("BQ7CH72", "Fetching for vehicle :: " + vehicleKey);
        viewModel.fetchBooking(vehicleKey);
    }

    private BusSeatAdapter adapter;

    private void setupBusSeat() {

        View view = getView();
        if (view == null) {
            return;
        }

        final int COL = 5;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), COL, GridLayoutManager.VERTICAL, true);

        RecyclerView recyclerView = view.findViewById(R.id.fsSeats);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new BusSeatAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void registerListeners() {

        View view = getView();

        if (view == null) {
            return;
        }

        Button next = view.findViewById(R.id.fsNext);
        Bundle b = null;
        next.setOnClickListener(v -> {
            // nextPage(b)

            viewModel.saveBookings(prepareForSeatInsertion());

        });
    }

    private List<BookingModel> prepareForSeatInsertion() {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        List<SeatModel> seatModelList = adapter.getSeatModelList();
        List<BookingModel> bookingModelList = new ArrayList<>();

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyy", Locale.US);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Bookings");
        long time = System.currentTimeMillis();

        String parentNode = sdf.format(date).toUpperCase();
        for (SeatModel sm
                : seatModelList) {

            dbRef = dbRef.child(parentNode).push();
            String key = dbRef.getKey();

            BookingModel bm = new BookingModel();
            bm.setBookedOn(time);
            bm.setUserKey(auth.getUid());
            bm.setVehicleKey(bundle.getString(VEHICLE_KEY));
            bm.setBookMode(sm.isSelected() ? "Booked" : "Not Booked");
            bm.setPaymentStatus(sm.isSelected() ? "Paid" : "Unpaid");
            bm.setSeatIdentifier(sm.getSeatNumber());
            bm.setKey(key);

            bookingModelList.add(bm);
        }

        return bookingModelList;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void setData(Bundle bundle) {
        super.setData(bundle);
    }

    public static String getClassName() {
        return ThirdFragment.class.getSimpleName();
    }
}
