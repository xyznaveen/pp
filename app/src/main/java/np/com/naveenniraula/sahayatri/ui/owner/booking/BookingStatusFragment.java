package np.com.naveenniraula.sahayatri.ui.owner.booking;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.ui.owner.BaseFragment;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        changeTitle(R.string.title_bookings);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BookingStatusViewModel.class);
        // TODO: Use the ViewModel
    }

}
