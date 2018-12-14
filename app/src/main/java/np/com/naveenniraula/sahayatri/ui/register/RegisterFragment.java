package np.com.naveenniraula.sahayatri.ui.register;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import np.com.naveenniraula.sahayatri.BaseFragment;
import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.WelcomeActivity;
import np.com.naveenniraula.sahayatri.data.local.UserEntity;
import np.com.naveenniraula.sahayatri.util.MessageHelper;
import np.com.naveenniraula.sahayatri.util.validation.Rectify;

public class RegisterFragment extends BaseFragment
        implements View.OnClickListener {

    private static final String USER = "Users";
    private static final String USER_TYPE = "Passenger";
    private RegisterViewModel mViewModel;

    private UserEntity newUser;

    private Button actionVehicleOwner;
    private Button actionPassanger;

    private int inactive;
    private int active;
    private int transparent;

    public static RegisterFragment newInstance() {

        RegisterFragment rf = new RegisterFragment();
        rf.newUser = new UserEntity();
        return rf;
    }

    private Button register;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.register_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((WelcomeActivity) getActivity()).changeTitle("Registration");

        actionVehicleOwner = view.findViewById(R.id.rfVehicleBtn);
        actionPassanger = view.findViewById(R.id.rfPassangerBtn);
        actionVehicleOwner.setOnClickListener(this);
        actionPassanger.setOnClickListener(this);

        register = view.findViewById(R.id.rfRegisterBtn);
        register.setOnClickListener((view2) -> {

            createAccount();
        });

        prepareColors();

        // set a default selected.
        actionPassanger.callOnClick();
    }

    private void prepareColors() {

        inactive = getResources().getColor(R.color.colorPrimaryDark);
        active = getResources().getColor(R.color.colorWhite);
        transparent = getResources().getColor(android.R.color.transparent);
    }

    private void createAccount() {

        if (isInputValid()) {

            changeProgressBarVisibility(View.VISIBLE);
            UserEntity userEntity = getUserModel();

            if (userEntity != null) {

                mViewModel.registerNewUser(userEntity);
            }

            return;
        }

        register.setVisibility(View.VISIBLE);
    }

    private UserEntity getUserModel() {

        if (getView() == null) {
            return null;
        }

        TextInputLayout fullName = getView().findViewById(R.id.rfInputFullName);
        TextInputLayout email = getView().findViewById(R.id.rfInputEmail);
        TextInputLayout password = getView().findViewById(R.id.rfInputPassword);
        TextInputLayout phone = getView().findViewById(R.id.rfPhoneNumber);

        UserEntity user = new UserEntity();
        user.setEmail(email.getEditText().getText().toString());
        user.setPassword(password.getEditText().getText().toString());
        user.setName(fullName.getEditText().getText().toString());
        user.setPhoneNumber(phone.getEditText().getText().toString());
        user.setRegistrationDate(System.currentTimeMillis());
        user.setUserType(USER_TYPE);

        return user;
    }

    /**
     * Get all input from the layout and check whether each field is valid.
     *
     * @return true if all input are valid.
     */
    private boolean isInputValid() {

        if (getView() == null) return false;

        TextInputLayout fullName = getView().findViewById(R.id.rfInputFullName);
        TextInputLayout email = getView().findViewById(R.id.rfInputEmail);
        TextInputLayout password = getView().findViewById(R.id.rfInputPassword);
        TextInputLayout phone = getView().findViewById(R.id.rfPhoneNumber);

        Rectify rectify = new Rectify();
        rectify.email(email);
        rectify.password(password);
        rectify.basic(fullName);
        rectify.basic(phone);
        return rectify.validate();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        mViewModel.observeRegistrationStatus().observe(this, aBoolean -> {

            register.setVisibility(View.VISIBLE);
            changeProgressBarVisibility(View.GONE);
            if (aBoolean != null && aBoolean) {
                MessageHelper.regularSnack(this, "User registration success. Please login with same details.");
                clearFields();
                return;
            }
            MessageHelper.showErrorSnack(this, "Apologies! We are unable to process your request.");
        });
    }

    private void clearFields() {

        ConstraintLayout root = getView().findViewById(R.id.rfRoot);
        TextInputLayout child = null;
        for (int i = 0; i < root.getChildCount(); i++) {

            if (root.getChildAt(i) instanceof TextInputLayout) {

                child = (TextInputLayout) root.getChildAt(i);
                child.getEditText().setText("");
            }
        }

    }

    private void changeProgressBarVisibility(int visibility) {
        ProgressBar progressBar = getView().findViewById(R.id.rfProgressBar);
        progressBar.setVisibility(visibility);
    }

    @Override
    public void onClick(View view) {

        // check whether the passed view is an instance of Button
        if (view instanceof Button) {
            switch (view.getId()) {
                case R.id.rfPassangerBtn: {
                    actionVehicleOwner.setTextColor(inactive);
                    actionVehicleOwner.setBackgroundColor(transparent);
                    actionPassanger.setTextColor(active);
                    actionPassanger.setBackgroundColor(inactive);
                    break;
                }
                case R.id.rfVehicleBtn: {

                    actionPassanger.setTextColor(inactive);
                    actionPassanger.setBackgroundColor(transparent);
                    actionVehicleOwner.setTextColor(active);
                    actionVehicleOwner.setBackgroundColor(inactive);

                    stopSignUp();
                    break;
                }
                default:
            }
            newUser.setUserType(((Button) view).getText().toString());
        }
    }

    private void stopSignUp() {

        StringBuilder message = new StringBuilder();
        message.append("We have restricted vehicle owner signup from mobile application. ");
        message.append("It is to prevent spam as this application is premature. ");
        message.append("However, you can contact us via. email and we will sign you up. ");
        message.append("We apologise for any inconvenience.");

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("NOTICE!");
        builder.setMessage(message.toString());
        builder.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());
        AlertDialog ad = builder.create();
        ad.setCancelable(false);
        ad.show();

        actionPassanger.callOnClick();
    }

}
