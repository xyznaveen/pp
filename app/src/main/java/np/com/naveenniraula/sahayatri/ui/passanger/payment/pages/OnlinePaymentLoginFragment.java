package np.com.naveenniraula.sahayatri.ui.passanger.payment.pages;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.util.validation.Rectify;

public class OnlinePaymentLoginFragment extends OpayBaseFragment {

    private OnlinePaymentLoginViewModel mViewModel;

    public static OnlinePaymentLoginFragment newInstance() {
        return new OnlinePaymentLoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.online_payment_login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initVIews();
    }

    private void initVIews() {

        View view = getView();

        if (isNull(view)) {
            return;
        }

        Button login = view.findViewById(R.id.oplfLogin);
        login.setOnClickListener(v -> {

            if (isInputValid(view)) {

                authenticate(view);
            }
        });


    }

    private void authenticate(View view) {
        TextInputLayout user = view.findViewById(R.id.oplfUsername);
        TextInputLayout pass = view.findViewById(R.id.oplfPassword);
        EditText userText = user.getEditText();
        EditText passText = pass.getEditText();

        if (userText == null || passText == null) {
            return;
        }

        if (userText.getText().toString().equals("payUser")
                && passText.getText().toString().equals("$ahayatri")) {


        }
    }

    private boolean isInputValid(View view) {

        TextInputLayout user = view.findViewById(R.id.oplfUsername);
        TextInputLayout pass = view.findViewById(R.id.oplfPassword);

        Rectify rectify = new Rectify();
        rectify.basic(user);
        rectify.basic(pass);

        return rectify.validate();
    }

    private boolean isNull(View view) {
        return view == null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(OnlinePaymentLoginViewModel.class);
        // TODO: Use the ViewModel
    }

}
