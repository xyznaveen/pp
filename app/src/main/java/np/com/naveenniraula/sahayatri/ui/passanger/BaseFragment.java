package np.com.naveenniraula.sahayatri.ui.passanger;

import android.app.Activity;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {

    public void changeFragment(Fragment fragment) {

        PassangerDashboardActivity activity = null;

        if (getActivity() instanceof PassangerDashboardActivity) {

            activity = (PassangerDashboardActivity) getActivity();
        }

        if (activity == null) return;

        activity.replaceFragment(fragment);
    }

}
