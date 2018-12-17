package np.com.naveenniraula.sahayatri.ui.passanger.payment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import np.com.naveenniraula.sahayatri.R;

public class PaymentDialog extends DialogFragment {

    public static PaymentDialog newInstance() {

        Bundle args = new Bundle();

        PaymentDialog fragment = new PaymentDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Context context = getContext();
        if (context == null) {
            return super.onCreateDialog(savedInstanceState);
        }

        LayoutInflater layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.dialog_payment, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        return alertDialog;
    }
}
