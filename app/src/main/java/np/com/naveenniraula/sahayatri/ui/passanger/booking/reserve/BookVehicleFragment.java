package np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.components.NoSwipeViewPager;
import np.com.naveenniraula.sahayatri.ui.passanger.BaseFragment;
import np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.adapter.NewBookingAdapter;
import np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.pages.FirstFragment;
import np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.pages.FourthFragment;
import np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.pages.PageChangeViewModel;
import np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.pages.SecondFragment;
import np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.pages.ThirdFragment;

public class BookVehicleFragment extends BaseFragment {

    private BookVehicleViewModel mViewModel;
    private PageChangeViewModel viewModel;

    public static BookVehicleFragment newInstance() {
        return new BookVehicleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.book_vehicle_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViewPager();
    }

    private NoSwipeViewPager viewPager;
    private NewBookingAdapter nba;

    private void setupViewPager() {

        View view = getView();
        if (view == null) return;

        setupAdapter();

        viewPager = view.findViewById(R.id.bvfNewBooking);
        viewPager.setAdapter(nba);
        viewPager.setOffscreenPageLimit(0);
    }

    public ViewPager getViewPager() {

        return viewPager;
    }

    public void nextPage(Bundle data) {

        int pageNumber = viewPager.getCurrentItem() + 1 >= nba.getCount()
                ? nba.getCount()
                : viewPager.getCurrentItem() + 1;

        if (data != null) {
            nba.setBundleFor(pageNumber, data);
        }

        viewPager.setCurrentItem(pageNumber, true);
        viewModel.addData(data);
    }

    public void prevPage() {

        int pageNumber = viewPager.getCurrentItem() - 1 < 0
                ? 0
                : viewPager.getCurrentItem() - 1;

        viewPager.setCurrentItem(pageNumber, true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BookVehicleViewModel.class);
        viewModel = ViewModelProviders.of(this).get(PageChangeViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onStop() {
        super.onStop();

        nba.removeAllPage();
    }

    private void setupAdapter() {

        if (nba == null && getActivity() != null) {

            nba = new NewBookingAdapter(getChildFragmentManager());
        }

        nba.removeAllPage();
        nba.addPage(FirstFragment.newInstance(this));
        nba.addPage(SecondFragment.newInstance(this));
        nba.addPage(ThirdFragment.newInstance(this));
        nba.addPage(FourthFragment.newInstance(this));

        if (viewPager != null) {

            viewPager.setCurrentItem(0);
        }

    }

    public PageChangeViewModel getViewModel() {
        return viewModel;
    }
}
