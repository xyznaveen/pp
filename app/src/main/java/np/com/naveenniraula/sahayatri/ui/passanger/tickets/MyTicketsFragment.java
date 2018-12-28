package np.com.naveenniraula.sahayatri.ui.passanger.tickets;

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
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.data.model.DateModel;
import np.com.naveenniraula.sahayatri.ui.passanger.BaseFragment;

public class MyTicketsFragment extends BaseFragment {

    private MyTicketsViewModel mViewModel;
    private MyTicketsAdapter ticketsAdapter;
    private ArrayAdapter<String> dates;

    public static MyTicketsFragment newInstance() {
        return new MyTicketsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_tickets_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        changeTitle("Booked Tickets");

        // add dates for selection
        Spinner datesView = view.findViewById(R.id.mtfDate);
        dates = new ArrayAdapter<>(view.getContext(), R.layout.item_spinner);
        dates.setDropDownViewResource(android.R.layout
                .simple_selectable_list_item);
        datesView.setAdapter(dates);

        datesView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                FirebaseAuth auth = FirebaseAuth.getInstance();
                TextView selected = (TextView) view;
                mViewModel.fetchBookings(selected.getText().toString(), auth.getUid());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.mtfList);
        ticketsAdapter = new MyTicketsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(ticketsAdapter);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MyTicketsViewModel.class);

        mViewModel.observeDateKey().observe(this, this::updateDates);
        mViewModel.fetchDateKey();

        mViewModel.observeBookings().observe(this, bookingData -> {

            Log.i("BQ7CH72", "Tickets booked by this guy :: " + bookingData.size());

            ticketsAdapter.add(bookingData);
        });

    }

    private void updateDates(List<DateModel> value) {

        for (DateModel dm :
                value) {

            Log.i("BQ7CH72", dm.getKey());
            dates.add(dm.getKey());
        }

        dates.notifyDataSetChanged();

        Log.i("BQ7CH72", dates.getCount() + " total items.");
    }

}
