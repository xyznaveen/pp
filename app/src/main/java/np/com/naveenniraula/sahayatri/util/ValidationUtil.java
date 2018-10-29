package np.com.naveenniraula.sahayatri.util;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import java.util.List;

import np.com.naveenniraula.sahayatri.data.local.ValidationModel;

public class ValidationUtil {

    /**
     * Validates input and displays passed error messages. Making (my own) life easier.
     *
     * @return true even when any one input is invalid, false when every required input are provided
     */
    public static boolean isInputInvalid(List<ValidationModel> validationModels) {

        // FALSE because? natural language SEMANTICS
        // the method returns true when inputs are invalid
        // why am I explaining this here?
        boolean isOneInputInvalid = false;
        for (ValidationModel validationModel : validationModels) {

            ValidationModel currentModel = validationModel;

            if (MiscUtil.isInstanceOf(currentModel.getView(), TextInputLayout.class)) {

                TextInputLayout layout = (TextInputLayout) currentModel.getView();
                EditText editText = layout.getEditText();

                // if null validation cannot continue
                if (editText == null && editText.getText() == null) {

                    isOneInputInvalid = true;
                    break;
                }

                if (currentModel.getErrorType() == ValidationModel.Rule.EMAIL) {
                    isOneInputInvalid = !TextUtil.isValidEmail(editText.getText().toString());
                } else if (currentModel.getErrorType() == ValidationModel.Rule.PHONE) {
                    isOneInputInvalid = !TextUtil.isValidPhone(editText.getText().toString());
                } else if (currentModel.getErrorType() == ValidationModel.Rule.PASSWORD) {
                    isOneInputInvalid = !TextUtil.isValidPassword(editText.getText().toString());
                } else if (currentModel.getErrorType() == ValidationModel.Rule.NAME) {
                    isOneInputInvalid = !TextUtil.isValidName(editText.getText().toString());
                }

                updateErrorMessage(layout, isOneInputInvalid ? currentModel.getErrorMessage() : "");
            }
        }

        return isOneInputInvalid;
    }

    public static <T> void clearFields(List<T> list) {

        if (list == null || list.size() <= 0) {
            return;
        }

        if (list.get(0) instanceof ValidationModel) {
            for (T model : list) {
                ((TextInputLayout) ((ValidationModel) model).getView()).getEditText().setText("");
            }
        } else if (list.get(0) instanceof TextInputLayout) {
            for (T view : list) {
                ((TextInputLayout) view).getEditText().setText("");
            }
        }
    }

    private static void updateErrorMessage(TextInputLayout til, CharSequence errorMessage) {

        if (errorMessage != null && errorMessage.toString().isEmpty()) {

            til.setErrorEnabled(false);
            return;
        }

        til.setErrorEnabled(true);
        til.setError(errorMessage);
    }

}
