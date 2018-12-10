package np.com.naveenniraula.sahayatri.ui.owner.vehicles.detail;


import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import np.com.naveenniraula.sahayatri.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VdBottomSheetFragment extends BottomSheetDialogFragment {

    public VdBottomSheetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vd_bottom_sheet, container, false);
    }

}
