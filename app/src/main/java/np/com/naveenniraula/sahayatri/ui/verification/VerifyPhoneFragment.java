package np.com.naveenniraula.sahayatri.ui.verification;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.WelcomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class VerifyPhoneFragment extends Fragment {

    public static VerifyPhoneFragment newInstance() {

        Bundle args = new Bundle();

        VerifyPhoneFragment fragment = new VerifyPhoneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public VerifyPhoneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verify_phone, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        changeTitle("Verification");
    }

    private void changeTitle(final String name) {
        ((WelcomeActivity) getActivity()).changeTitle(name);
    }
}
