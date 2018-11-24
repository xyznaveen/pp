package np.com.naveenniraula.sahayatri;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import np.com.naveenniraula.rectify.Rectify;
import np.com.naveenniraula.rectify.Validator;

public class ValidationText extends AppCompatActivity {

    Rectify rectify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation_text);

        TextInputLayout til = findViewById(R.id.textInputLayout1);
        TextInputLayout til2 = findViewById(R.id.textInputLayout3);
        TextInputLayout til3 = findViewById(R.id.tilLast);

        rectify = new Rectify();
        rectify.add(new Rectify.ValidatorBuilder(this)
                .setTextInputLayout(til)
                .setRule(Validator.Rule.EMAIL)
                .setErrorMessage(R.string.msg_invalid_email)
                .build());
        rectify.add(new Rectify.ValidatorBuilder(this)
                .setTextInputLayout(til2)
                .setRule(Validator.Rule.NOTEMPTY)
                .setErrorMessage("This is actually awesome.")
                .build());
        rectify.add(new Rectify.ValidatorBuilder(this)
                .setTextInputLayout(til3)
                .setRule(Validator.Rule.PASSWORD)
                .setErrorMessage(R.string.msg_invalid_password)
                .build());
    }

    public void validate(View view) {

        rectify.validate();
    }
}
