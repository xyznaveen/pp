package np.com.naveenniraula.sahayatri.ui.owner.vehicles.add;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import np.com.naveenniraula.sahayatri.data.model.Vehicle;

public class AddVehicleViewModel extends ViewModel {

    private MutableLiveData<String> testLiveData;
    private Disposable dps;

    MutableLiveData<String> fetchData() {

        if (testLiveData == null) {
            testLiveData = new MutableLiveData<>();
        }

        dps = Observable.fromCallable(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ignore) {
            }
            return false;
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result) -> {

                    testLiveData.setValue("Success fetching data.");
                    dps.dispose();
                });

        return testLiveData;
    }

    private DatabaseReference dbRef;
    private MutableLiveData<Boolean> newLiveData;

    MutableLiveData<Boolean> saveVehicle(Vehicle vehicle) {

        if (!vehicle.generateVehicleHash()) {
            newLiveData.postValue(false);
            return newLiveData;
        }

        if (newLiveData == null) {
            newLiveData = new MutableLiveData<>();
        }

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (mAuth.getUid() == null) {
            newLiveData.postValue(false);
            return newLiveData;
        }

        FirebaseDatabase db = FirebaseDatabase.getInstance();

        DatabaseReference currentUserRef = db.getReference()
                .child("VehicleList")
                .child(vehicle.getOperationMode()).push();

        // store key along with value because we will need it later
        vehicle.setKey(currentUserRef.getKey());

        currentUserRef.setValue(vehicle)
                .addOnCompleteListener(task -> {

                    if (task.getException() != null) {

                        Log.i("BQ7CH72", "Found an error :: "
                                + task.getException().getLocalizedMessage());
                    }

                    newLiveData.postValue(task.isSuccessful());
                });

        return newLiveData;
    }


}
