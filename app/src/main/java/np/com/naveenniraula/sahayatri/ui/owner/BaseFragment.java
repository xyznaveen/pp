package np.com.naveenniraula.sahayatri.ui.owner;

import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {

    protected void changeTitle(int titleId) {

        if (getMainActivityInstance() != null) {
            getMainActivityInstance().changeTitle(titleId);
        }
    }

    protected void changeSubTitle(int subtitleId) {

        if (getMainActivityInstance() != null) {
            getMainActivityInstance().changeSubTitle(subtitleId);
        }
    }

    private OwnerDashboardActivity getMainActivityInstance() {

        if (getActivity() != null && getActivity() instanceof OwnerDashboardActivity) {

            return (OwnerDashboardActivity) getActivity();
        }
        return null;
    }

}
