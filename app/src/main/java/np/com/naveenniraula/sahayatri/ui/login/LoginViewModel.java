package np.com.naveenniraula.sahayatri.ui.login;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import np.com.naveenniraula.sahayatri.data.local.UserEntity;
import np.com.naveenniraula.sahayatri.util.PreferenceUtil;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<String> mutableLiveData;

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getUserType() {

        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
        }
        return mutableLiveData;
    }

    public void setUserType(String userType) {

        if (mutableLiveData == null) {
            return;
        }

        mutableLiveData.postValue(userType);
    }

    private MutableLiveData<AuthenticationResult> authLiveData;

    public void authenticate(String email, String password) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

            AuthenticationResult authResult = new AuthenticationResult();
            authResult.setAuthStatus(task.isSuccessful());
            if (task.getException() != null) {

                authResult.setErrorMessage(task.getException().getLocalizedMessage());
            }
            authLiveData.postValue(authResult);
        });
    }

    public MutableLiveData<AuthenticationResult> observeAuthenticationChange() {

        if (authLiveData == null) {
            authLiveData = new MutableLiveData<>();
        }

        return authLiveData;
    }

    private MutableLiveData<UserEntity> userDetailLiveData;

    public MutableLiveData<UserEntity> observeUserDetailFetch() {

        if (userDetailLiveData == null) {

            userDetailLiveData = new MutableLiveData<>();
        }

        return userDetailLiveData;
    }

    private ValueEventListener userDetailEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if (dataSnapshot.getValue() != null) {

                PreferenceUtil util = new PreferenceUtil(getApplication());

                UserEntity userEntity = dataSnapshot.getValue(UserEntity.class);
                util.saveString("USER_NAME", userEntity.getName());
                util.saveString("USER_EMAIL", userEntity.getEmail());
                userDetailLiveData.postValue(userEntity);
                return;
            }

            userDetailLiveData.postValue(null);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

            Log.i("BQ7CH72", "Database error : " + databaseError.getDetails());
        }
    };

    public void fetchUserDetail() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String userId = firebaseAuth.getUid();

        if (userId == null || firebaseAuth.getUid() == null) {

            // not authenticated
            userDetailLiveData.postValue(null);
            return;
        }

        String userType = mutableLiveData.getValue();
        if (userType == null) {

            // user type is, and must be, always defined
            userDetailLiveData.postValue(null);
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (String str :
                userType.split(" ")) {

            stringBuilder.append(str);
        }

        userType = stringBuilder.toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference()
                .child(userType).child(firebaseAuth.getUid());
        databaseReference.addListenerForSingleValueEvent(userDetailEventListener);
    }

    static class AuthenticationResult {

        private boolean authStatus;
        private String errorMessage;

        public AuthenticationResult() {
            authStatus = false;
            errorMessage = null;
        }

        public AuthenticationResult(boolean authStatus, String errorMessage) {
            this.authStatus = authStatus;
            this.errorMessage = errorMessage;
        }

        public boolean isAuthStatus() {
            return authStatus;
        }

        public void setAuthStatus(boolean authStatus) {
            this.authStatus = authStatus;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }

}
