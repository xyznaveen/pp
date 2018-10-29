package np.com.naveenniraula.sahayatri.ui.login;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import np.com.naveenniraula.sahayatri.BaseFragment;
import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.WelcomeActivity;
import np.com.naveenniraula.sahayatri.data.local.ValidationModel;
import np.com.naveenniraula.sahayatri.ui.owner.OwnerDashboardActivity;
import np.com.naveenniraula.sahayatri.ui.passanger.PassangerDashboardActivity;
import np.com.naveenniraula.sahayatri.ui.register.RegisterFragment;
import np.com.naveenniraula.sahayatri.util.Constants;
import np.com.naveenniraula.sahayatri.util.PermissionUtil;
import np.com.naveenniraula.sahayatri.util.PreferenceUtil;
import np.com.naveenniraula.sahayatri.util.TextUtil;
import np.com.naveenniraula.sahayatri.util.ValidationUtil;

public class LoginFragment extends BaseFragment
        implements View.OnClickListener {

    private static final String USER_TYPE = "userType";
    private LoginViewModel mViewModel;
    private Button actionVehicleOwner;
    private Button actionPassanger;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    final Observer<String> stringObserver = s -> updateText(s);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((WelcomeActivity) getActivity()).changeTitle("Sign in");

        actionVehicleOwner = view.findViewById(R.id.lfActionVehicle);
        actionPassanger = view.findViewById(R.id.lfActionPassanger);

        actionVehicleOwner.setOnClickListener(this);
        actionPassanger.setOnClickListener(this);

        TextView actionRegister = view.findViewById(R.id.lfActionRegister);
        actionRegister.setOnClickListener(view1
                -> {
            ((WelcomeActivity) getActivity()).replaceFragment(RegisterFragment.newInstance());
        });

        Button actionLogin = view.findViewById(R.id.lfActionLogin);
        actionLogin.setOnClickListener((clickedView) -> {

            TextInputLayout email = getView().findViewById(R.id.lfInputUsername);
            TextInputLayout pwd = getView().findViewById(R.id.lfInputPassword);

            List<ValidationModel> list = new ArrayList<>();
            list.add(new ValidationModel(email, getText(R.string.error_email), ValidationModel.Rule.EMAIL));
            list.add(new ValidationModel(pwd, getText(R.string.error_password), ValidationModel.Rule.PASSWORD));

            if (!ValidationUtil.isInputInvalid(list)) {
                ProgressBar progressBar = getView().findViewById(R.id.lfProgressBar);
                progressBar.setVisibility(View.VISIBLE);

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(Objects.requireNonNull(TextUtil.getText(email)),
                        Objects.requireNonNull(TextUtil.getText(pwd))).addOnCompleteListener(task -> {

                    PreferenceUtil pf = new PreferenceUtil(getActivity());
                    pf.saveString(USER_TYPE, mViewModel.fetchString().getValue());

                    progressBar.setVisibility(View.GONE);

                    Intent intent;

                    if (PASSANGER.equals(pf.getString(USER_TYPE))) {

                        intent = new Intent(getActivity(), PassangerDashboardActivity.class);
                        startActivity(intent);
                    } else {

                        intent = new Intent(getActivity(), OwnerDashboardActivity.class);
                        startActivity(intent);
                    }
                    getActivity().finish();
                    getActivity().overridePendingTransition(0, 0);
                });
            }
        });

        askForPermission();
    }

    private void askForPermission() {
        PermissionUtil.askForPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION,
                new PermissionUtil.PermissionListener() {
                    @Override
                    public void onPermissionRequest() {

                        // permissions were requested
                        askThesePermissions(Manifest.permission.ACCESS_FINE_LOCATION);
                    }

                    @Override
                    public void onPermissionDenied() {

                        // permissions were disabled
                        Toast.makeText(getActivity(), "Awww Crap.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDisabled() {

                        // permissions were denined
                        askThesePermissions(Manifest.permission.ACCESS_FINE_LOCATION);
                    }

                    @Override
                    public void onPermissionGranted() {

                        // permission already granted
                    }
                });
    }

    /**
     * Requests for these passed permissions.
     *
     * @param perms
     */
    private void askThesePermissions(String... perms) {

        int permissionSize = perms.length;
        String[] permArr = new String[permissionSize];

        for (int i = 0; i < permissionSize; ++i) {
            permArr[i] = perms[i];
        }

        PermissionUtil.requestPermission(LoginFragment.this,
                permArr, Constants.REQUEST_LOGIN_PERMS);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        // observe for data changes from view model
        mViewModel.fetchString().observe(this, stringObserver);

        // set a default user type selected
        actionVehicleOwner.callOnClick();
    }

    private void updateText(String s) {
        Log.i("MCTag", "updateText: " + s);
    }

    void printDebug(String... strings) {
        ((WelcomeActivity) getActivity()).printDebug(strings);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        permissionsWereDenied(requestCode, permissions, grantResults);
    }

    private void permissionsWereDenied(final int requestCode, final String[] permissions,
                                       final int[] grantResults) {
        switch (requestCode) {
            case Constants.REQUEST_LOGIN_PERMS: {

                int permssionSize = permissions.length;
                for (int i = 0; i < permssionSize; ++i) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                    } else {

                    }
                }
                break;
            }
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    public static final String PASSANGER = "Passanger";
    public static final String VEHICLE_OWNER = "Vehicle Owner";

    @Override
    public void onClick(View view) {

        // check whether the passed view is an instance of Button
        if (view instanceof Button) {
            final int inactive = getResources().getColor(R.color.colorPrimaryDark);
            final int active = getResources().getColor(R.color.colorWhite);
            final int transparent = getResources().getColor(android.R.color.transparent);
            switch (view.getId()) {
                case R.id.lfActionPassanger: {
                    actionVehicleOwner.setTextColor(inactive);
                    actionVehicleOwner.setBackgroundColor(transparent);
                    actionPassanger.setTextColor(active);
                    actionPassanger.setBackgroundColor(inactive);
                    mViewModel.fetchString().setValue(PASSANGER);
                    break;
                }
                case R.id.lfActionVehicle: {
                    actionPassanger.setTextColor(inactive);
                    actionPassanger.setBackgroundColor(transparent);
                    actionVehicleOwner.setTextColor(active);
                    actionVehicleOwner.setBackgroundColor(inactive);
                    mViewModel.fetchString().setValue(VEHICLE_OWNER);
                    break;
                }
                default:
            }
        }
    }
}