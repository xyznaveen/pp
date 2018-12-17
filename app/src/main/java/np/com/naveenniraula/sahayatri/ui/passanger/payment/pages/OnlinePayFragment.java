package np.com.naveenniraula.sahayatri.ui.passanger.payment.pages;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import np.com.naveenniraula.sahayatri.R;

public class OnlinePayFragment extends OpayBaseFragment {

    private OnlinePayViewModel mViewModel;

    public static OnlinePayFragment newInstance() {
        return new OnlinePayFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.online_pay_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(OnlinePayViewModel.class);
        // TODO: Use the ViewModel
    }

}
