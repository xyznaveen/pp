package np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.pages;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.lang.ref.WeakReference;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.BookVehicleFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends BasePageFragment {

    public static SecondFragment newInstance(BookVehicleFragment bookVehicleFragment) {

        SecondFragment fragment = new SecondFragment();
        fragment.parentWeakReference = new WeakReference<>(bookVehicleFragment);
        return fragment;
    }

    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerListeners(view);
    }

    private void registerListeners(View view) {

        Button next = view.findViewById(R.id.fsNext);
        Button prev = view.findViewById(R.id.fsPrev);

        next.setOnClickListener(v -> nextPage());

        prev.setOnClickListener(v -> previousPage());
    }

}
