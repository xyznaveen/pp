package np.com.naveenniraula.sahayatri.ui.passanger.profile;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.data.local.UserEntity;
import np.com.naveenniraula.sahayatri.util.MessageHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {

        final ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        mViewModel.observeUserData().observe(this, this::updateProfileDetails);

        mViewModel.fetchUserDetails();
    }

    private void updateProfileDetails(UserEntity userEntity) {

        View view = getView();

        if (view == null || userEntity == null) {
            MessageHelper.showErrorSnack(this,
                    "An unkown error occurred please try again later.");
            return;
        }

        final TextView userName = getView().findViewById(R.id.fpUserName);
        userName.setText(userEntity.getName());

        final TextView email = getView().findViewById(R.id.fpUserEmail);
        email.setText(userEntity.getEmail());

        final TextView userType = getView().findViewById(R.id.fpUserType);
        userType.setText(userEntity.getUserType());

        final TextView userPhonenumber = getView().findViewById(R.id.fpPhoneNumber);
        userPhonenumber.setText(String.valueOf(userEntity.getPhoneNumber()));

        final SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
        final Date formatDate = new Date(userEntity.getRegistrationDate());
        final TextView memberSince = getView().findViewById(R.id.fpMemberSince);
        memberSince.setText(format.format(formatDate));

        final ImageView userImage = getView().findViewById(R.id.fpUserImage);

    }

}
