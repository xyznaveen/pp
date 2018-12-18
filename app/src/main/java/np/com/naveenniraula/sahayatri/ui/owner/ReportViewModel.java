package np.com.naveenniraula.sahayatri.ui.owner;

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

public class ReportViewModel extends ViewModel {

    public void fetchVehicleCount() {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("VehicleList");
        dbRef.child("Day")
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
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

}
