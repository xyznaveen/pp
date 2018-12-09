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

            mViewModel.saveVehicle(new Vehicle("MER XAK 210", "BA 2 JA 9822", 100, 3)).observe(this, aBoolean -> {

                if (aBoolean != null && aBoolean) {

                    Toast.makeText(getContext(), "Vehicle added successfully.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(getContext(), "Could Not Create New Vehicle.", Toast.LENGTH_SHORT).show();

            });
        }

    }

    private boolean isInputValid() {

        Rectify rectify = new Rectify();

        TextInputLayout model = getView().findViewById(R.id.mvfModel);
        rectify.basic(model);

        return rectify.validate();
    }

}
