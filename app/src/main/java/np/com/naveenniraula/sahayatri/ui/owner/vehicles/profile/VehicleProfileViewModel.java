package np.com.naveenniraula.sahayatri.ui.owner.vehicles.profile;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import np.com.naveenniraula.sahayatri.data.model.Vehicle;
import np.com.naveenniraula.sahayatri.ui.login.LoginFragment;
import np.com.naveenniraula.sahayatri.util.InputHelper;
import np.com.naveenniraula.sahayatri.util.PreferenceUtil;

public class VehicleProfileViewModel extends AndroidViewModel {

    private MutableLiveData<Vehicle> vehicleMutableLiveData;

    public VehicleProfileViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Vehicle> observeVehicle() {

        if (vehicleMutableLiveData == null) {

            vehicleMutableLiveData = new MutableLiveData<>();
        }

        return vehicleMutableLiveData;
    }

    public void fetchVehicle() {

        PreferenceUtil preferenceUtil = new PreferenceUtil(getApplication());
        FirebaseAuth auth = FirebaseAuth.getInstance();

        DatabaseReference databaseReference =
                FirebaseDatabase.getInstance()
                        .getReference(InputHelper.removeSpace(preferenceUtil.getString(LoginFragment.USER_TYPE)));

        databaseReference.child(auth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Log.i("BQ7CH72", "Data snapsoht :: " + dataSnapshot + " path " + databaseReference.getPath());

                        Vehicle vehicle = dataSnapshot.getValue(Vehicle.class);
                        vehicleMutableLiveData.postValue(vehicle);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

}
