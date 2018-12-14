package np.com.naveenniraula.sahayatri.ui.register;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import np.com.naveenniraula.sahayatri.data.local.UserEntity;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<Boolean> registrationLiveData;

    public MutableLiveData<Boolean> observeRegistrationStatus() {

        if (registrationLiveData == null) {

            registrationLiveData = new MutableLiveData<>();
        }

        return registrationLiveData;
    }

    public void registerNewUser(UserEntity user) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.addAuthStateListener(firebaseAuth1 -> {

            if (firebaseAuth1.getUid() != null) {
                // we do not want to store password
                user.setPassword("");
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference();
                reference.child(user.getUserType())
                        .child(firebaseAuth1.getUid())
                        .setValue(user).addOnCompleteListener(task1 -> {

                    registrationLiveData.postValue(task1.isSuccessful());
                });
            }

        });
        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnFailureListener(e -> registrationLiveData.postValue(false));
    }

    static class RegisteredUserPojo {

        private boolean isRegistered;
        private String errorMessage;

        public RegisteredUserPojo(boolean isRegistered, String errorMessage) {
            this.isRegistered = isRegistered;
            this.errorMessage = errorMessage;
        }

        public boolean isRegistered() {
            return isRegistered;
        }

        public void setRegistered(boolean registered) {
            isRegistered = registered;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

    }

}
