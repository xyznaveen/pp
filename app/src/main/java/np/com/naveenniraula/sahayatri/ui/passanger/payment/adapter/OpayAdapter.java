package np.com.naveenniraula.sahayatri.ui.passanger.payment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import np.com.naveenniraula.sahayatri.ui.passanger.payment.pages.OpayBaseFragment;

public class OpayAdapter extends FragmentPagerAdapter {

    private List<OpayBaseFragment> fragments;

    public OpayAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    public List<OpayBaseFragment> getFragments() {
        return fragments;
    }

    public void setFragment(OpayBaseFragment fragment) {
        fragments.add(fragment);
    }
}
