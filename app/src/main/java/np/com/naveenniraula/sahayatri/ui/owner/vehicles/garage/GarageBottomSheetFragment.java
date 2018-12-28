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
import np.com.naveenniraula.sahayatri.data.model.Vehicle;
import np.com.naveenniraula.sahayatri.ui.owner.vehicles.add.AddVehicleFragment;
import np.com.naveenniraula.sahayatri.ui.owner.vehicles.profile.VehicleProfileFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class GarageBottomSheetFragment extends BottomSheetDialogFragment {

    private OnDeleteClickListener onDeleteClickListener;
    private Vehicle model;

    public GarageBottomSheetFragment() {
        // Required empty public constructor
    }

    public static GarageBottomSheetFragment newInstance(Vehicle model) {
        GarageBottomSheetFragment fragment = new GarageBottomSheetFragment();
        fragment.model = model;
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vd_bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn = view.findViewById(R.id.fvdbsDetail);
        btn.setOnClickListener(btnView -> changeFragment(VehicleProfileFragment.newInstance()));

        Button btnEdit = view.findViewById(R.id.fvdbsEdit);
        btnEdit.setOnClickListener(btnView -> changeFragment(AddVehicleFragment.newInstance(model)));

        Button btnDelete = view.findViewById(R.id.fvdbsDelete);
        btnDelete.setOnClickListener(v -> {
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(model);
                dismiss();
            }
        });
    }

    public void changeFragment(Fragment fragment) {

        if (isGarageFragment() && getParentFragment() != null) {

            ((GarageFragment) getParentFragment())
                    .changeFragment(fragment);
        }

    }

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    private boolean isGarageFragment() {
        return getParentFragment() instanceof GarageFragment;
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(Vehicle model);
    }

}
