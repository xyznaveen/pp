package np.com.naveenniraula.sahayatri.ui.passanger.dashboard;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import np.com.naveenniraula.sahayatri.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PassangerDashboardFragment extends Fragment {

    public static PassangerDashboardFragment newInstance() {

        PassangerDashboardFragment fragment = new PassangerDashboardFragment();
        return fragment;
    }

    public PassangerDashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

}
