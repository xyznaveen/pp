package np.com.naveenniraula.sahayatri;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import np.com.naveenniraula.rectify.Rectify;

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
        rectify.email(til);
        rectify.basic(til2);
        rectify.password(til3);
    }

    public void validate(View view) {

        rectify.validate();
    }
}
