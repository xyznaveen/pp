package np.com.naveenniraula.sahayatri.data.local;

import android.view.View;

public class ValidationModel {

    public enum Rule {
        EMAIL, PASSWORD, NAME, PHONE, DOMAIN, IP
    }

    private View view;
    private Rule errorType;
    private CharSequence errorMessage;

    public ValidationModel() {
    }

    public ValidationModel(View view, CharSequence errorMessage, Rule errorType) {
        this.view = view;
        this.errorType = errorType;
        this.errorMessage = errorMessage;
    }

    public ValidationModel(View view, String errorMessage, Rule errorType) {

        this.view = view;
        this.errorType = errorType;
        this.errorMessage = errorMessage;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Rule getErrorType() {
        return errorType;
    }

    public void setErrorType(Rule errorType) {
        this.errorType = errorType;
    }

    public CharSequence getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(CharSequence errorMessage) {
        this.errorMessage = errorMessage;
    }
}
