package np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.pages;

import android.support.v4.app.Fragment;
import android.util.Log;

import java.lang.ref.WeakReference;

import np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.BookVehicleFragment;

public class BasePageFragment extends Fragment {

    protected WeakReference<BookVehicleFragment> parentWeakReference;

    public void nextPage() {
        if (parentWeakReference.get() != null) {
            parentWeakReference.get().nextPage();
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

        Log.i("BQ7CH72", "Current Page :: " + currentPageNumber);
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

}
