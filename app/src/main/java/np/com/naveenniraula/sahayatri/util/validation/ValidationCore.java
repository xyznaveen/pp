package np.com.naveenniraula.sahayatri.util.validation;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationCore {

    private static final int DEFAULT_PASSWORD_LENGTH = 8;
    private static final int DEFAULT_PHONE_NUMBER_LENGTH = 10;
    private static final int DEFAULT_PHONE_NUMBER_LENGTH_CC = 13;
    private Validator.Rule rule;
    private String value;

    ValidationCore(Validator.Rule rule, String value) {
        this.rule = rule;
        this.value = value;
    }

    /**
     * Checks the string based on the @{Rule} specified.
     *
     * @return true if the string satisfies the rule.
     */
    public boolean check() {

        switch (rule) {
            case NOTEMPTY: {

                return isNotEmpty(value);
            }
            case EMAIL: {

                return hasValidEmail(value);
            }
            case PASSWORD: {

                return hasValidPassword(value);
            }
            case DECIMAL: {

                return hasValidDecimal(value);
            }
            case PHONE: {

                return hasValidPhoneNumber(value);
            }
            default: {

                return false;
            }
        }
    }

    private boolean hasValidPhoneNumber(String value) {

        if (isNotEmpty(value)) {
            try {
                Long.parseLong(value);
                return value.length() == DEFAULT_PHONE_NUMBER_LENGTH
                        || value.length() == DEFAULT_PHONE_NUMBER_LENGTH_CC;
            } catch (NumberFormatException exception) {
                return false;
            }
        }

        return false;
    }

    private boolean hasValidDecimal(String value) {

        if (!isNotEmpty(value)) {

            return false;
        }

        try {

            Double.parseDouble(value);
        } catch (NumberFormatException ignore) {

            return false;
        }

        return true;
    }

    /**
     * Checks if the passed string is a valid password.
     *
     * @param value the string to be checked.
     * @return true if it is a valid password.
     */
    private boolean hasValidPassword(String value) {

        return isNotEmpty(value)
                && value.length() >= DEFAULT_PASSWORD_LENGTH;
        //&& hasSpecialAlphaNumericCharacter(value);
    }

    /**
     * Checks if the provided string contains both uppercase and lowercase, numeric and
     * special characters.
     *
     * @param value the value to be checked.
     * @return true if the password contains special-alpha-numeric characters.
     */
    private boolean hasSpecialAlphaNumericCharacter(String value) {

        Pattern letter = Pattern.compile("[a-zA-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

        Matcher hasLetter = letter.matcher(value);
        Matcher hasDigit = digit.matcher(value);
        Matcher hasSpecial = special.matcher(value);

        return hasLetter.find() && hasDigit.find() && hasSpecial.find();
    }

    /**
     * Checks whether the provided email is valid.
     *
     * @param value the string to be evaluated.
     * @return true if passed value is valid email.
     */
    private boolean hasValidEmail(String value) {

        if (isNotEmpty(value)) {

            return Patterns.EMAIL_ADDRESS.matcher(value).matches();
        }

        return false;
    }

    /**
     * Checks if the provided string is not null or is not empty.
     * Whitespace characters are also taken into account when evaluating.
     *
     * @param str the string to be checked.
     * @return true if the input has valid value, else false.
     */
    private boolean isNotEmpty(String str) {

        return str != null && !str.trim().isEmpty();
    }
}
