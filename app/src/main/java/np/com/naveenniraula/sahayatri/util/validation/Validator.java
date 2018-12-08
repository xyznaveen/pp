package np.com.naveenniraula.sahayatri.util.validation;

import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class Validator {

    public enum Rule {
        NOTEMPTY, EMAIL, PASSWORD, DECIMAL
    }

    private String errorMessage;
    private TextInputLayout textInputLayout;
    private EditText editText;
    private Rule rule;
    private boolean isInputValid;

    Validator(TextInputLayout textInputLayout, String errorMessage, Rule rule) {
        this.errorMessage = errorMessage;
        this.textInputLayout = textInputLayout;
        this.rule = rule;
        editText = null;
        isInputValid = false;
    }

    Validator(EditText editText, String errorMessage, Rule rule) {
        this.errorMessage = errorMessage;
        this.editText = editText;
        this.rule = rule;
        isInputValid = false;
        textInputLayout = null;
    }

    /**
     * Begins the process of validation.
     * Checks the rule against the input values.
     */
    public void validate() {

        String inputValue = textInputLayout != null
                ? getInputText(textInputLayout)
                : getInputText(editText);
        ValidationCore validationCore = new ValidationCore(rule, inputValue);

        isInputValid = validationCore.check();

        if (textInputLayout != null) {
            toggleTextInputLayoutMessage();
            return;
        }
        toggleEditTextMessage();
    }

    /**
     *
     */
    private void toggleEditTextMessage() {

        if (editText == null) {
            return;
        }

        if (isInputValid) {
            editText.setError(null);
            return;
        }
        editText.setError(errorMessage);
    }

    /**
     * Toggle error message of @{TextInputLayout}
     */
    private void toggleTextInputLayoutMessage() {

        if (textInputLayout == null) {
            return;
        }

        if (isInputValid) {
            textInputLayout.setErrorEnabled(false);
            return;
        }

        textInputLayout.setErrorEnabled(true);
        textInputLayout.setError(errorMessage);
    }

    /**
     * Extract input value from the @{TextInputLayout}
     *
     * @param view
     * @return value contained in this TextInputLayout
     */
    private String getInputText(View view) {

        EditText editText;

        if (view instanceof TextInputLayout) {

            editText = textInputLayout.getEditText();
        } else {

            editText = (EditText) view;
        }

        if (editText != null) {

            return editText.getText().toString();
        }

        return null;
    }

    /**
     * Checks if this @{TextInputLayout} contains valid values.
     * These values are checked against the @{Rule} specified before.
     *
     * @return true if the rule is satisfied.
     */
    public boolean isValid() {
        return isInputValid;
    }

    /**
     * Move the focus to this @{TextInputLayout}
     */
    public void focusThis() {

        if (textInputLayout != null) {
            textInputLayout.requestFocus();
            return;
        }

        editText.requestFocus();
    }
}
