package np.com.naveenniraula.sahayatri;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import np.com.naveenniraula.sahayatri.ui.login.LoginFragment;
import np.com.naveenniraula.sahayatri.ui.owner.OwnerDashboardActivity;
import np.com.naveenniraula.sahayatri.ui.passanger.PassangerDashboardActivity;
import np.com.naveenniraula.sahayatri.util.PreferenceUtil;

public class WelcomeActivity extends BaseActivity {

    private final FragmentManager.OnBackStackChangedListener backStackChangedListener =
            () -> {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);

                if (fragment instanceof LoginFragment
                        && getSupportActionBar() != null) {

                    toggleHomeAsUp(false);
                    return;
                }

                toggleHomeAsUp(true);
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);

        Toolbar toolbar = findViewById(R.id.rfToolbar);
        setSupportActionBar(toolbar);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if (getSupportActionBar() != null) {

            getSupportActionBar().setElevation(1.0F);

            toggleHomeAsUp(true);
        }

        if (savedInstanceState == null) {
            if (mAuth.getCurrentUser() != null) {

                PreferenceUtil pref = new PreferenceUtil(this);
                if (pref.getString(LoginFragment.USER_TYPE)
                        .equals(LoginFragment.PASSANGER)) {

                    startActivity(
                            new Intent(WelcomeActivity.this,
                                    PassangerDashboardActivity.class));
                } else if (pref.getString(LoginFragment.USER_TYPE)
                        .equals(LoginFragment.VEHICLE_OWNER)) {

                    startActivity(
                            new Intent(WelcomeActivity.this,
                                    OwnerDashboardActivity.class));
                }
            } else {

                replaceFragment(LoginFragment.newInstance());
                getSupportFragmentManager()
                        .addOnBackStackChangedListener(backStackChangedListener);
            }
        }
    }

    private void toggleHomeAsUp(final boolean flag) {

        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(flag);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: {

                getSupportFragmentManager().popBackStack();
                break;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);

        if (fragment instanceof LoginFragment) {
            //TODO show alret dialog to confirm exit
            System.exit(0);
            return;
        }
        super.onBackPressed();
    }

    public void changeTitle(String title) {
        super.changeTitle(title);
    }
}
