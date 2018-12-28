package np.com.naveenniraula.sahayatri.ui.owner.booking;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import np.com.naveenniraula.sahayatri.data.model.Vehicle;

public class BookingStatusViewModel extends ViewModel {

    private MutableLiveData<List<Vehicle>> vehicleMutableLiveData;
    FirebaseAuth mAuth;

    public MutableLiveData<List<Vehicle>> observeVehicle() {

        if (vehicleMutableLiveData == null) {
            vehicleMutableLiveData = new MutableLiveData<>();
        }

        return vehicleMutableLiveData;
    }

    public void fetchVehicle(String operationMode) {

        if (mAuth == null) {

            mAuth = FirebaseAuth.getInstance();
        }

        DatabaseReference dbRef =
                FirebaseDatabase.getInstance().getReference("VehicleList");
        dbRef.child(operationMode)
                .orderByChild("vehicleOwnerKey")
                .equalTo(mAuth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        List<Vehicle> temp = new ArrayList<>();
                        for (DataSnapshot ds :
                                dataSnapshot.getChildren()) {

                            temp.add(ds.getValue(Vehicle.class));
                        }
                        vehicleMutableLiveData.postValue(temp);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

}
