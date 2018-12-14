package np.com.naveenniraula.sahayatri.ui.login;

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

public class LoginViewModel extends ViewModel {

    private MutableLiveData<String> mutableLiveData;

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

                userDetailLiveData.postValue(dataSnapshot.getValue(UserEntity.class));
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

        if (userId == null) {

            Log.i("BQ7CH72", "User was not authenticated");
            // not authenticated
            userDetailLiveData.postValue(null);
            return;
        }

        String userType = mutableLiveData.getValue();
        if (userType == null) {

            Log.i("BQ7CH72", "User type is not selected.");
            // user type is, and must be, always defined
            userDetailLiveData.postValue(null);
            return;
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child(userType).child(firebaseAuth.getUid());
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
