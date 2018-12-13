package np.com.naveenniraula.sahayatri.util.validation;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import np.com.naveenniraula.sahayatri.R;

public class Rectify {

    private List<Validator> validatorList;
    private int moveFocusTo = 0;
    private boolean isStisfied = false;

    public Rectify() {
        validatorList = new ArrayList<>();
    }

    /**
     * Add new @{Validator} to list. It will be validated later.
     *
     * @param validator
     */
    public void add(Validator validator) {

        validatorList.add(validator);
    }

    /**
     * Adds validation for checking whether fields are empty
     *
     * @param view the @{View} to be validated
     */
    public void basic(View view) {

        Validator validator = null;

        if (view instanceof TextInputLayout) {

            TextInputLayout textInputLayout = (TextInputLayout) view;
            validator = new ValidatorBuilder(textInputLayout.getContext())
                    .setErrorMessage(R.string.rec_error_required)
                    .setRule(Validator.Rule.NOTEMPTY)
                    .setTextInputLayout(textInputLayout)
                    .build();
        } else if (view instanceof EditText) {

            EditText editText = (EditText) view;
            validator = new ValidatorBuilder(editText.getContext())
                    .setErrorMessage(R.string.rec_error_required)
                    .setRule(Validator.Rule.NOTEMPTY)
                    .setEditText(editText)
                    .build();
        }

        validatorList.add(validator);
    }

    public void phone(View view) {

        Validator validator = null;

        if (view instanceof TextInputLayout) {

            TextInputLayout textInputLayout = (TextInputLayout) view;
            validator = new ValidatorBuilder(textInputLayout.getContext())
                    .setErrorMessage(R.string.rec_error_required)
                    .setRule(Validator.Rule.PHONE)
                    .setTextInputLayout(textInputLayout)
                    .build();
        } else if (view instanceof EditText) {

            EditText editText = (EditText) view;
            validator = new ValidatorBuilder(editText.getContext())
                    .setErrorMessage(R.string.rec_error_required)
                    .setRule(Validator.Rule.PHONE)
                    .setEditText(editText)
                    .build();
        }

        validatorList.add(validator);
    }

    public void decimal(View view) {

        Validator validator = null;

        if (view instanceof TextInputLayout) {

            TextInputLayout textInputLayout = (TextInputLayout) view;
            validator = new ValidatorBuilder(textInputLayout.getContext())
                    .setErrorMessage(R.string.rec_decimal_required)
                    .setRule(Validator.Rule.DECIMAL)
                    .setTextInputLayout(textInputLayout)
                    .build();
        } else if (view instanceof EditText) {

            EditText editText = (EditText) view;
            validator = new ValidatorBuilder(editText.getContext())
                    .setErrorMessage(R.string.rec_decimal_required)
                    .setRule(Validator.Rule.DECIMAL)
                    .setEditText(editText)
                    .build();
        }

        validatorList.add(validator);
    }

    public void email(View view) {

        Validator validator = null;

        if (view instanceof TextInputLayout) {

            TextInputLayout textInputLayout = (TextInputLayout) view;
            validator = new ValidatorBuilder(textInputLayout.getContext())
                    .setErrorMessage(R.string.rec_email_required)
                    .setRule(Validator.Rule.EMAIL)
                    .setTextInputLayout(textInputLayout)
                    .build();
        } else if (view instanceof EditText) {

            EditText editText = (EditText) view;
            validator = new ValidatorBuilder(editText.getContext())
                    .setErrorMessage(R.string.rec_email_required)
                    .setRule(Validator.Rule.EMAIL)
                    .setEditText(editText)
                    .build();
        }

        validatorList.add(validator);
    }

    public void password(View view) {

        Validator validator = null;

        if (view instanceof TextInputLayout) {

            TextInputLayout textInputLayout = (TextInputLayout) view;
            validator = new ValidatorBuilder(textInputLayout.getContext())
                    .setErrorMessage(R.string.rec_password_invalid)
                    .setRule(Validator.Rule.PASSWORD)
                    .setTextInputLayout(textInputLayout)
                    .build();
        } else if (view instanceof EditText) {

            EditText editText = (EditText) view;
            validator = new ValidatorBuilder(editText.getContext())
                    .setErrorMessage(R.string.rec_password_invalid)
                    .setRule(Validator.Rule.PASSWORD)
                    .setEditText(editText)
                    .build();
        }

        validatorList.add(validator);
    }

    /**
     * Iterate through each element which requires validation.
     */
    public boolean validate() {

        boolean isValid = true;
        int index = 0;
        for (Validator validator :
                validatorList) {

            validator.validate();
            isValid = (isValid && validator.isValid());
            if (!validator.isValid() && !isStisfied) {
                isStisfied = true;
            } else {
                moveFocusTo = index + 1;
            }
            index++;
        }

        try {

            validatorList.get(moveFocusTo).focusThis();
        } catch (IndexOutOfBoundsException ignore) {
            // if everything is valid moveFocusTo will be same as the size of validatorList
            // hence leading to this crash

            // move the focus to the fragment_first input
            validatorList.get(0).focusThis();
        }

        return isValid;
    }

    /**
     * Builder class to help build the @{Validator} Instance.
     */
    public static class ValidatorBuilder {

        private WeakReference<Context> contextWeakReference;
        private Validator.Rule rule;
        private TextInputLayout textInputLayout;
        private String errorMessage;
        private EditText editText;

        ValidatorBuilder(Context context) {
            contextWeakReference = new WeakReference<>(context);
        }

        ValidatorBuilder setRule(Validator.Rule rule) {
            this.rule = rule;
            return this;
        }

        ValidatorBuilder setTextInputLayout(TextInputLayout textInputLayout) {
            this.textInputLayout = textInputLayout;
            return this;
        }

        public ValidatorBuilder setEditText(EditText editText) {
            this.editText = editText;
            return this;
        }


        public ValidatorBuilder setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        ValidatorBuilder setErrorMessage(int errorMessageResId) {
            if (contextWeakReference.get() != null) {
                errorMessage = contextWeakReference.get().getString(errorMessageResId);
            }
            return this;
        }

        public Validator build() {

            return textInputLayout == null
                    ? new Validator(editText, errorMessage, rule)
                    : new Validator(textInputLayout, errorMessage, rule);
        }
    }

}
