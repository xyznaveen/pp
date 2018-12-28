package np.com.naveenniraula.sahayatri.ui.login;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import np.com.naveenniraula.sahayatri.R;

public class ResetPasswordDialog extends DialogFragment {

    private OnButtonClickedListener onButtonClickedListener;

    public static ResetPasswordDialog newInstance() {

        Bundle args = new Bundle();

        ResetPasswordDialog fragment = new ResetPasswordDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_reset_password, null);
        EditText inputEmail = view.findViewById(R.id.drpEmail);

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Reset Password");
        builder.setView(view);
        builder.setPositiveButton("Sent Link", (dialog, which) -> {

            if (onButtonClickedListener != null) {

                String email = inputEmail.getText().toString();
                onButtonClickedListener.onButtonClick(email);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        return dialog;
    }

    public void setOnButtonClickedListener(OnButtonClickedListener onButtonClickedListener) {
        this.onButtonClickedListener = onButtonClickedListener;
    }

    interface OnButtonClickedListener {
        void onButtonClick(String email);
    }

}
