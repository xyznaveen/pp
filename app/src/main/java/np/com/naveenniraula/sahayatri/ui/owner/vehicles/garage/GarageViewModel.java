package np.com.naveenniraula.sahayatri.ui.owner.vehicles.garage;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import np.com.naveenniraula.sahayatri.data.model.Vehicle;
import np.com.naveenniraula.sahayatri.ui.owner.vehicles.garage.adapter.VehicleAdapter;

public class GarageViewModel extends AndroidViewModel {

    private MutableLiveData<List<Vehicle>> vehicleListLiveData;

    public GarageViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Vehicle>> observeVehicleUpdate() {

        if (vehicleListLiveData == null) {

            vehicleListLiveData = new MutableLiveData<>();
        }

        return vehicleListLiveData;
    }

    private VehicleAdapter<Vehicle> va;

    public void fetchVehicles(String operationMode) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("VehicleList");
        dbRef.child(operationMode)
                .orderByChild("vehicleOwnerKey")
                .equalTo(auth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        List<Vehicle> vehicles = new ArrayList<>();

                        for (DataSnapshot ds :
                                dataSnapshot.getChildren()) {

                            vehicles.add(ds.getValue(Vehicle.class));
                        }

                        vehicleListLiveData.postValue(vehicles);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (va != null) {
            va.stopListening();
        }
    }

    public void deleteBus(Vehicle model) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("VehicleList");
        reference.child(model.getOperationMode())
                .child(model.getKey()).removeValue((databaseError, databaseReference) -> {

                });
    }
}
