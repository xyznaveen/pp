package np.com.naveenniraula.sahayatri.ui.passanger.payment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import np.com.naveenniraula.sahayatri.ui.passanger.payment.pages.OpayBaseFragment;

public class OpayAdapter extends FragmentPagerAdapter {

    private List<OpayBaseFragment> fragments;

    public OpayAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public List<OpayBaseFragment> getFragments() {
        return fragments;
    }

    public void addFragment(OpayBaseFragment fragment) {
        fragments.add(fragment);
    }
}
