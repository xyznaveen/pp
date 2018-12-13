package np.com.naveenniraula.sahayatri.ui.owner.vehicles;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.data.model.Vehicle;
import np.com.naveenniraula.sahayatri.ui.owner.BaseFragment;
import np.com.naveenniraula.sahayatri.util.InputHelper;
import np.com.naveenniraula.sahayatri.util.validation.Rectify;

public class MyVehiclesFragment extends BaseFragment {

    private MyVehiclesViewModel mViewModel;

    public static MyVehiclesFragment newInstance() {
        return new MyVehiclesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_vehicles_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        changeTitle(R.string.title_garage);

        attachListeners();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        changeTitle(R.string.title_owner_vehicles);
        mViewModel = ViewModelProviders.of(this).get(MyVehiclesViewModel.class);
    }

    private void attachListeners() {

        View rootView = getView();

        if (rootView != null) {

            Button btn = rootView.findViewById(R.id.mvfSaveVehicleInfo);
            btn.setOnClickListener(v -> saveDataToFireBase());
        }

    }

    private void saveDataToFireBase() {

        if (isInputValid()) {

            Vehicle vehicle = prepareModel();

            mViewModel.saveVehicle(vehicle)
                    .observe(this, aBoolean -> {

                        if (aBoolean != null && aBoolean) {

                            Toast.makeText(getContext(), "Vehicle added successfully.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Toast.makeText(getContext(), "Could Not Create New Vehicle.", Toast.LENGTH_SHORT).show();

                    });
        }

    }

    private Vehicle prepareModel() {

        if (getView() == null) return null;

        Vehicle vehicle = new Vehicle();
        TextInputLayout model = getView().findViewById(R.id.mvfModel);
        TextInputLayout reg = getView().findViewById(R.id.mvfRegistrationNumber);
        TextInputLayout seatCount = getView().findViewById(R.id.mvfTotalSeatCount);
        TextInputLayout crewCount = getView().findViewById(R.id.mvfCrewCount);

        vehicle.setModel(InputHelper.getString(model));
        vehicle.setRegistrationNumber(InputHelper.getString(reg));
        vehicle.setTotalSeatCount(Integer.parseInt(InputHelper.getString(seatCount)));
        vehicle.setTotalCrewCount(Integer.parseInt(InputHelper.getString(crewCount)));

        return vehicle;
    }

    private boolean isInputValid() {

        if (getView() == null) return false;

        Rectify rectify = new Rectify();
        TextInputLayout model = getView().findViewById(R.id.mvfModel);
        rectify.basic(model);
        TextInputLayout reg = getView().findViewById(R.id.mvfRegistrationNumber);
        rectify.basic(reg);
        TextInputLayout seatCount = getView().findViewById(R.id.mvfTotalSeatCount);
        rectify.basic(seatCount);
        TextInputLayout crewCount = getView().findViewById(R.id.mvfCrewCount);
        rectify.basic(crewCount);
        return rectify.validate();
    }

}
