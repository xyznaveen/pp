package np.com.naveenniraula.sahayatri.ui.login;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

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
