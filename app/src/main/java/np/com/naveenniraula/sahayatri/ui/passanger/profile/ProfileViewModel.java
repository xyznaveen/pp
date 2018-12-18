package np.com.naveenniraula.sahayatri.ui.passanger.profile;

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

import np.com.naveenniraula.sahayatri.data.local.UserEntity;
import np.com.naveenniraula.sahayatri.ui.login.LoginFragment;
import np.com.naveenniraula.sahayatri.util.PreferenceUtil;

public class ProfileViewModel extends AndroidViewModel {

    private MutableLiveData<UserEntity> userEntityMutableLiveData;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<UserEntity> observeUserData() {

        if (userEntityMutableLiveData == null) {

            userEntityMutableLiveData = new MutableLiveData<>();
        }

        return userEntityMutableLiveData;
    }

    public void fetchUserDetails() {

        PreferenceUtil preferenceUtil = new PreferenceUtil(getApplication());
        FirebaseAuth auth = FirebaseAuth.getInstance();

        DatabaseReference databaseReference =
                FirebaseDatabase.getInstance()
                        .getReference(preferenceUtil.getString(LoginFragment.USER_TYPE));

        databaseReference.child(auth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Log.i("BQ7CH72", "Data snapsoht :: " + dataSnapshot + " path " + databaseReference.getPath());

                        UserEntity userEntity = dataSnapshot.getValue(UserEntity.class);
                        userEntityMutableLiveData.postValue(userEntity);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

}
