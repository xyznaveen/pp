package np.com.naveenniraula.sahayatri.ui.owner.vehicles.add;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.data.model.Vehicle;
import np.com.naveenniraula.sahayatri.ui.login.LoginFragment;
import np.com.naveenniraula.sahayatri.ui.owner.BaseFragment;
import np.com.naveenniraula.sahayatri.util.InputHelper;
import np.com.naveenniraula.sahayatri.util.MessageHelper;
import np.com.naveenniraula.sahayatri.util.PreferenceUtil;
import np.com.naveenniraula.sahayatri.util.validation.Rectify;

public class AddVehicleFragment extends BaseFragment {

    public static final String DAY_BUS = "Day";
    public static final String NIGHT_BUS = "Night";
    private static final String EMPTY = "";

    private AddVehicleViewModel mViewModel;
    private Vehicle model;

    public static AddVehicleFragment newInstance() {
        return new AddVehicleFragment();
    }

    public static AddVehicleFragment newInstance(Vehicle model) {
        AddVehicleFragment fragment = new AddVehicleFragment();
        fragment.model = model;
        return fragment;
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

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.child("VehicleList")
                .child("Day")
                .child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Log.i("BQ7CH72", "Databe");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        attachListeners();

        if (model != null) {
            fillEditData();
        }
    }

    private void fillEditData() {

        View view = getView();
        if (view == null) {
            return;
        }

        TextInputLayout model = view.findViewById(R.id.mvfModel);
        model.getEditText().setText(this.model.getModel());

        TextInputLayout reg = view.findViewById(R.id.mvfRegistrationNumber);
        reg.getEditText().setText(this.model.getRegistrationNumber());

        TextInputLayout seatCount = view.findViewById(R.id.mvfTotalSeatCount);
        seatCount.getEditText().setText(String.valueOf(this.model.getTotalSeatCount()));

        TextInputLayout crewCount = view.findViewById(R.id.mvfCrewCount);
        crewCount.getEditText().setText(String.valueOf(this.model.getTotalCrewCount()));

        RadioGroup busType = view.findViewById(R.id.mvfBustType);
        boolean isNight = this.model.getOperationMode().equalsIgnoreCase("night");

        busType.check(isNight ? R.id.mvfbtNight : R.id.mvfbtDay);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        changeTitle(R.string.title_owner_vehicles);
        mViewModel = ViewModelProviders.of(this).get(AddVehicleViewModel.class);
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

                            clearInput();
                            MessageHelper.regularSnack(this, "Vehicle has been added.");
                            return;
                        }

                        MessageHelper.showErrorSnack(this, "Something went wrong while adding vehicle.");
                    });
        }

    }

    private void clearInput() {

        if (getView() == null) {
            return;
        }

        ConstraintLayout constraintLayout = getView().findViewById(R.id.mvfRoot);
        TextInputLayout inputItem;

        for (int i = 0; i < constraintLayout.getChildCount(); i++) {

            if (constraintLayout.getChildAt(i) instanceof TextInputLayout) {

                inputItem = (TextInputLayout) constraintLayout.getChildAt(i);

                if (inputItem.getEditText() != null) {

                    inputItem.getEditText().setText(EMPTY);
                }
            }

        }
    }

    private Vehicle prepareModel() {

        if (getView() == null) return null;

        PreferenceUtil preferenceUtil = new PreferenceUtil(getView().getContext());


        Vehicle vehicle = new Vehicle();
        TextInputLayout model = getView().findViewById(R.id.mvfModel);
        TextInputLayout reg = getView().findViewById(R.id.mvfRegistrationNumber);
        TextInputLayout seatCount = getView().findViewById(R.id.mvfTotalSeatCount);
        TextInputLayout crewCount = getView().findViewById(R.id.mvfCrewCount);

        RadioGroup busType = getView().findViewById(R.id.mvfBustType);

        vehicle.setModel(InputHelper.getString(model));
        vehicle.setRegistrationNumber(InputHelper.getStringAllCaps(reg));
        vehicle.setTotalSeatCount(Integer.parseInt(InputHelper.getString(seatCount)));
        vehicle.setTotalCrewCount(Integer.parseInt(InputHelper.getString(crewCount)));
        vehicle.setOperationMode(
                busType.getCheckedRadioButtonId() == R.id.mvfbtNight
                        ? NIGHT_BUS
                        : DAY_BUS
        );
        vehicle.setOwnerName(preferenceUtil.getString(LoginFragment.USER_NAME));

        if (this.model != null) {
            vehicle.setKey(this.model.getKey());
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
        vehicle.setVehicleOwnerKey(auth.getUid());
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
