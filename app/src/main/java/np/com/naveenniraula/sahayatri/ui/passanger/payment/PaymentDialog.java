package np.com.naveenniraula.sahayatri.ui.passanger.payment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.pages.FirstFragment;
import np.com.naveenniraula.sahayatri.ui.passanger.payment.adapter.OpayAdapter;
import np.com.naveenniraula.sahayatri.ui.passanger.payment.pages.OnlinePayFragment;
import np.com.naveenniraula.sahayatri.ui.passanger.payment.pages.OnlinePaymentLoginFragment;

public class PaymentDialog extends DialogFragment {

    public static PaymentDialog newInstance() {

        Bundle args = new Bundle();

        PaymentDialog fragment = new PaymentDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Theme_MaterialComponents_Dialog);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = (int) (metrics.widthPixels * 0.95);
        int screenHeight = (int) (metrics.heightPixels * 0.95);

        getDialog().getWindow().setLayout(screenWidth, screenHeight);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
    }

    private void initViews(View view) {

        if (view == null) {
            return;
        }

        OpayAdapter adapter = new OpayAdapter(getChildFragmentManager());
        adapter.addFragment(OnlinePaymentLoginFragment.newInstance());
        adapter.addFragment(OnlinePayFragment.newInstance());

        ViewPager viewPager = view.findViewById(R.id.dpContainer);
        viewPager.setAdapter(adapter);
    }
}
