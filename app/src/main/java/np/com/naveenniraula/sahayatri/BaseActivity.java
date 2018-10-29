package np.com.naveenniraula.sahayatri;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Objects;

import np.com.naveenniraula.sahayatri.util.LocaleManager;

public abstract class BaseActivity extends AppCompatActivity {

    public static final String TAG = "DEBUGSahayatri";
    public static final int RELEASE = 0;
    public static final int DEBUG = 1;
    private static final int ENV = DEBUG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void printDebug(String... strings) {

        if (ENV == DEBUG) {

            try {

                Log.d(TAG, "\n\n\nfrom class -> " + strings[0]
                        + "; \ninside method -> " + strings[1]
                        + "; \nmessage -> \n\n" + strings[2]);
            } catch (IndexOutOfBoundsException ex) {

                // TODO handle exception when logging information
            }
        }
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_right, R.anim.exit_left, R.anim.enter_left, R.anim.exit_right)
                .replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleManager.setLocale(newBase));
    }

    public void changeLanguage() {

        if (LocaleManager.getLanguage(this).equals(LocaleManager.LANGUAGE_ENGLISH)) {

            LocaleManager.setNewLocale(this, LocaleManager.LANGUAGE_NEPALI);
        } else {

            LocaleManager.setNewLocale(this, LocaleManager.LANGUAGE_ENGLISH);
        }

        // restart the activity to reflect changes.
        recreate();
    }

    protected void changeTitle(String title) {
        ActionBar actionBar = Objects.requireNonNull(getSupportActionBar());
        actionBar.setTitle(title);
    }
}
