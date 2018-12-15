package np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.pages;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

import np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.BookVehicleFragment;

public abstract class BasePageFragment extends Fragment {

    public static final String SENDER = "SENDER";
    public static final String DATE = "Travel Date";
    public static final String TRAVEL_MODE = "Travel Mode";
    public static final String FROM = "From";
    public static final String TO = "To";

    private PageChangeViewModel viewModel;

    protected Bundle bookingConfiguration;

    protected WeakReference<BookVehicleFragment> parentWeakReference;

    public void nextPage(Bundle data) {
        if (parentWeakReference.get() != null) {
            parentWeakReference.get().nextPage(data);
        }
    }

    public void previousPage() {
        if (parentWeakReference.get() != null) {
            parentWeakReference.get().prevPage();
        }
    }

    public void nextPage(String s) {

        if (parentWeakReference.get() == null) {
            return;
        }

        if (parentWeakReference.get().getViewPager().getAdapter() == null) {
            return;
        }

        int currentPageNumber = parentWeakReference.get().getViewPager().getCurrentItem() + 1;
        int nextPageNumber = currentPageNumber >= parentWeakReference.get().getViewPager().getAdapter().getCount()
                ? parentWeakReference.get().getViewPager().getAdapter().getCount()
                : currentPageNumber;

        changePage(nextPageNumber);
    }

    public void previousPage(String s) {

        if (parentWeakReference.get() == null) {
            return;
        }

        if (parentWeakReference.get().getViewPager().getAdapter() == null) {
            return;
        }

        int currentPageNumber = parentWeakReference.get().getViewPager().getCurrentItem() - 1;
        int prevPageNumber = currentPageNumber < 0 ? 0 : currentPageNumber;
        changePage(prevPageNumber);
    }

    private void changePage(int currentPageNumber) {
        parentWeakReference.get().getViewPager().setCurrentItem(currentPageNumber);
    }

    public void setData(Bundle bundle) {
        bookingConfiguration = bundle;
    }

    public Bundle getBookingConfiguration() {
        return bookingConfiguration;
    }
}
