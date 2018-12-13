package np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.pages.BasePageFragment;

public class NewBookingAdapter extends FragmentPagerAdapter {

    private List<BasePageFragment> fragmentList;

    public NewBookingAdapter(FragmentManager fm) {
        super(fm);

        fragmentList = new ArrayList<>();
    }

    public void addPage(BasePageFragment fragment) {
        fragmentList.add(fragment);
        notifyDataSetChanged();
    }

    public void removeAllPage() {
        fragmentList.clear();
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public BasePageFragment getItem(int pos) {
        return fragmentList.get(pos);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

}
