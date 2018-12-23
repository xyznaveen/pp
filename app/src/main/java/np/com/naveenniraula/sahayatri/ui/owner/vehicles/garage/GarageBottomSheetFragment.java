package np.com.naveenniraula.sahayatri.ui.owner.vehicles.garage;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.ui.owner.vehicles.profile.VehicleProfileFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class GarageBottomSheetFragment extends BottomSheetDialogFragment {

    public GarageBottomSheetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vd_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn = view.findViewById(R.id.fvdbsDetail);
        btn.setOnClickListener(btnView -> changeFragment(VehicleProfileFragment.newInstance()));
    }

    public void changeFragment(Fragment fragment) {

        if (isGarageFragment() && getParentFragment() != null) {

            ((GarageFragment) getParentFragment())
                    .changeFragment(fragment);
        }

    }

    private boolean isGarageFragment() {
        return getParentFragment() instanceof GarageFragment;
    }

}
