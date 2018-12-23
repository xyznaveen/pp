package np.com.naveenniraula.sahayatri.ui.owner.vehicles.profile;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.data.model.Vehicle;
import np.com.naveenniraula.sahayatri.util.MessageHelper;

public class VehicleProfileFragment extends Fragment {

    private VehicleProfileViewModel mViewModel;

    public static VehicleProfileFragment newInstance() {
        return new VehicleProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.vehicle_profile_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VehicleProfileViewModel.class);

        mViewModel.observeVehicle().observe(this, vehicle -> {

            updateViews(vehicle);

        });
    }

    private void updateViews(Vehicle vehicle) {

        View view = getView();
        if (view == null) {

            MessageHelper.showErrorSnack(this, "An unknown error stopped proper functioning of this application.");
            return;
        }

        final TextView busName = view.findViewById(R.id.vpfVehicleName);

        final TextView regNum = view.findViewById(R.id.vpfRegNum);
        regNum.setText(vehicle.getRegistrationNumber());

        final ImageView vehicleImage = view.findViewById(R.id.vpfImage);

        Picasso.get().load("http://www.mbea.org.np/gallerypics/sized/55566_20150423_131039.jpg")
                .into(vehicleImage);

    }

}
