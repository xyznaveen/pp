package np.com.naveenniraula.sahayatri.ui.owner.transaction;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import np.com.naveenniraula.sahayatri.R;

public class TransactionFragment extends Fragment {

    private TransactionViewModel mViewModel;

    public static TransactionFragment newInstance() {
        return new TransactionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.transaction_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
        // TODO: Use the ViewModel
    }

}
