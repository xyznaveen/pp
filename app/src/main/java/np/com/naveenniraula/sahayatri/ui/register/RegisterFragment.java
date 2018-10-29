package np.com.naveenniraula.sahayatri.ui.register;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import np.com.naveenniraula.sahayatri.BaseFragment;
import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.WelcomeActivity;
import np.com.naveenniraula.sahayatri.data.local.UserEntity;
import np.com.naveenniraula.sahayatri.data.local.ValidationModel;
import np.com.naveenniraula.sahayatri.data.local.ValidationModel.Rule;
import np.com.naveenniraula.sahayatri.util.Constants;
import np.com.naveenniraula.sahayatri.util.ValidationUtil;

public class RegisterFragment extends BaseFragment
        implements View.OnClickListener {

    private static final String TAG = "CALLBACK TAG";
    private RegisterViewModel mViewModel;

    private Button actionVehicleOwner;
    private Button actionPassanger;
    private FirebaseAuth mAuth;

    private UserEntity newUser;

    public static RegisterFragment newInstance() {

        RegisterFragment rf = new RegisterFragment();
        rf.newUser = new UserEntity();
        return rf;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.register_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((WelcomeActivity) getActivity()).changeTitle("Registration");

        // init firebase authentication
        mAuth = FirebaseAuth.getInstance();

        actionVehicleOwner = view.findViewById(R.id.rfVehicleBtn);
        actionPassanger = view.findViewById(R.id.rfPassangerBtn);
        actionVehicleOwner.setOnClickListener(this);
        actionPassanger.setOnClickListener(this);

        Button register = view.findViewById(R.id.rfRegisterBtn);
        register.setOnClickListener((view2) -> {


            register.setEnabled(false);

            // send authentication code to the user
            createAccount(register);
        });

        inactive = getResources().getColor(R.color.colorPrimaryDark);
        active = getResources().getColor(R.color.colorWhite);
        transparent = getResources().getColor(android.R.color.transparent);

        // set a default selected.
        actionVehicleOwner.callOnClick();
    }

    int inactive;
    int active;
    int transparent;

    private void createAccount(Button register) {

        TextInputLayout fullName = getView().findViewById(R.id.rfInputFullName);
        TextInputLayout email = getView().findViewById(R.id.rfInputEmail);
        TextInputLayout password = getView().findViewById(R.id.rfInputPassword);
        TextInputLayout phone = getView().findViewById(R.id.rfPhoneNumber);

        List<ValidationModel> validationModels = new ArrayList<>();
        validationModels.add(new ValidationModel(fullName, getText(R.string.error_name), Rule.NAME));
        validationModels.add(new ValidationModel(email, getText(R.string.error_email), Rule.EMAIL));
        validationModels.add(new ValidationModel(password, getText(R.string.error_password), Rule.PASSWORD));
        validationModels.add(new ValidationModel(phone, getText(R.string.error_phone), Rule.PHONE));

        if (!ValidationUtil.isInputInvalid(validationModels)) {
            UserEntity user = new UserEntity(email.getEditText().getText().toString(),
                    password.getEditText().getText().toString());
            user.setRegistrationDate(System.currentTimeMillis());
            user.setPhoneNumber(phone.getEditText().getText().toString());
            user.setName(fullName.getEditText().getText().toString());

            ProgressBar progressBar = getView().findViewById(R.id.rfProgressBar);
            progressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {

                            // we do not want to store password
                            user.setPassword(null);

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference =
                                    database.getReference(newUser.getUserType()
                                            .equalsIgnoreCase("passanger")
                                            ? Constants.REF_PASSANGER
                                            : Constants.REF_VEHICLE_OWNER);

                            reference.child(mAuth.getUid()).setValue(user);

                            // clear input
                            ValidationUtil.clearFields(validationModels);
                            progressBar.setVisibility(View.GONE);
                            register.setEnabled(true);
                        }
                    });
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
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
                    break;
                }
                default:
            }
            newUser.setUserType(((Button) view).getText().toString());
        }
    }

    private void replaceFragment(Fragment fragment) {

        WelcomeActivity welcomeActivity = ((WelcomeActivity) getActivity());
//        welcomeActivity.getSupportFragmentManager().popBackStackImmediate();
        welcomeActivity.onBackPressed();
        welcomeActivity.replaceFragment(fragment);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                    mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                Toast.makeText(getActivity(), task.getResult().getUser().getPhoneNumber() + " is users logged in phone number.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {

                    Toast.makeText(getActivity(), "An error occurred -> " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                    Toast.makeText(getActivity(), "Code has been sent for retrieval", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCodeAutoRetrievalTimeOut(String s) {

                    Toast.makeText(getActivity(), "COde retrival time has out! -> " + s, Toast.LENGTH_SHORT).show();
                }
            };

    private void forOtpVerification() {

        UserEntity user = new UserEntity();
        PhoneAuthProvider pap = PhoneAuthProvider.getInstance();
        pap.verifyPhoneNumber(
                user.getPhoneNumber(),
                60,
                TimeUnit.SECONDS,
                getActivity(),
                callbacks);
    }

}
