package np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.pages;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import np.com.naveenniraula.sahayatri.data.model.BookingModel;
import np.com.naveenniraula.sahayatri.data.model.Vehicle;

public class ThirdFragmentViewModel extends ViewModel {

    private MutableLiveData<List<BookingModel>> bookingLiveData;

    public MutableLiveData<List<BookingModel>> observeBooking() {

        if (bookingLiveData == null) {
            bookingLiveData = new MutableLiveData<>();
        }

        return bookingLiveData;
    }

    public void fetchBooking(@NonNull String vehicleId) {

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyy", Locale.US);

        DatabaseReference dbRef = FirebaseDatabase.getInstance()
                .getReference("Bookings");
        dbRef.child(sdf.format(date).toUpperCase())
                .orderByChild("vehicleKey")
                .equalTo(vehicleId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.i("BQ7CH72", "SNAP VALUE :: " + dataSnapshot);
                        if (bookingLiveData != null) {
                            List<BookingModel> models = new ArrayList<>();
                            for (DataSnapshot dsnap :
                                    dataSnapshot.getChildren()) {

                                models.add(dsnap.getValue(BookingModel.class));
                            }
                            bookingLiveData.postValue(models);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private MutableLiveData<Vehicle> vehicleMutableLiveData;

    public MutableLiveData<Vehicle> observeVehicleData() {
        if (vehicleMutableLiveData == null) {

            vehicleMutableLiveData = new MutableLiveData<>();
        }

        return vehicleMutableLiveData;
    }

    public void fetchVehicleInformation(String vehicleKey, String operationMode) {

        DatabaseReference dbRef = FirebaseDatabase.getInstance()
                .getReference("VehicleList")
                .child(operationMode)
                .child(vehicleKey);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                vehicleMutableLiveData.postValue(dataSnapshot.getValue(Vehicle.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void saveBookings(List<BookingModel> bookingModels) {

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyy", Locale.US);

        String parentNode = sdf.format(date).toUpperCase();
        DatabaseReference dbRef = FirebaseDatabase.getInstance()
                .getReference("Bookings")
                .child(parentNode);

        for (BookingModel bookingModel :
                bookingModels) {

            dbRef.child(bookingModel.getKey()).setValue(bookingModel);
        }

    }
}
