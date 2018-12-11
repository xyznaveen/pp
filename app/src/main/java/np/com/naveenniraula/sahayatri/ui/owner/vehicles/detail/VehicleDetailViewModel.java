package np.com.naveenniraula.sahayatri.ui.owner.vehicles.detail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import np.com.naveenniraula.sahayatri.data.model.Vehicle;
import np.com.naveenniraula.sahayatri.ui.owner.vehicles.adapter.VehicleAdapter;

public class VehicleDetailViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> fetchStatusLive;

    public VehicleDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> getFetchStatus() {

        fetchDataFromFirebase();

        return fetchStatusLive;
    }

    private VehicleAdapter<Vehicle> va;

    private void fetchDataFromFirebase() {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        Query query = FirebaseDatabase.getInstance().getReference().child("vehicles/" + auth.getUid()).orderByKey();
        FirebaseRecyclerOptions<Vehicle> options =
                new FirebaseRecyclerOptions.Builder<Vehicle>()
                        .setQuery(query, Vehicle.class)
                        .build();
        va = new VehicleAdapter<>(options, getApplication());
        va.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();

                Log.d("BQ7CH72", "FIRED AFTER DATA HAS BEEN INSERTED.");
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
}
