package np.com.naveenniraula.sahayatri.ui.passanger;

import android.app.Activity;
import android.support.v4.app.Fragment;

import np.com.naveenniraula.sahayatri.R;

public class BaseFragment extends Fragment {

    public void changeFragment(Fragment fragment) {

        PassangerDashboardActivity activity = null;

        if (getActivity() instanceof PassangerDashboardActivity) {

            activity = (PassangerDashboardActivity) getActivity();
        }

        if (activity == null) return;

        activity.replaceFragment(fragment);
    }

    public void changeTitle(String title) {

        ((PassangerDashboardActivity) getActivity()).changeTitle(title);
    }

}
