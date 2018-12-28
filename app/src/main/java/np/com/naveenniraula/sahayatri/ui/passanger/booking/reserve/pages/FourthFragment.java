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
public class FourthFragment extends BasePageFragment {

    public static FourthFragment newInstance(BookVehicleFragment bookVehicleFragment) {

        FourthFragment fragment = new FourthFragment();
        fragment.parentWeakReference = new WeakReference<>(bookVehicleFragment);
        return fragment;
    }

    public FourthFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fourth, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerListeners();
    }

    private void registerListeners() {

        View view = getView();

        if (view == null) {
            return;
        }

        Button next = view.findViewById(R.id.ftComplete);
        next.setOnClickListener(v -> {


            if (parentWeakReference.get() != null
                    && parentWeakReference.get().getActivity() != null) {

                parentWeakReference.get().getActivity().onBackPressed();
            }
        });
    }

}
