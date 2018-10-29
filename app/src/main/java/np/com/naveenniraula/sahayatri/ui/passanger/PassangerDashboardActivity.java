package np.com.naveenniraula.sahayatri.ui.passanger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import np.com.naveenniraula.sahayatri.BaseActivity;
import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.WelcomeActivity;
import np.com.naveenniraula.sahayatri.ui.passanger.dashboard.PassangerDashboardFragment;
import np.com.naveenniraula.sahayatri.ui.passanger.profile.ProfileFragment;
import np.com.naveenniraula.sahayatri.util.MiscUtil;

public class PassangerDashboardActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private Fragment visibleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passanger_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        changeTitle("Passanger");

        mAuth = FirebaseAuth.getInstance();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            MiscUtil.snack(this, view, "This message will be shown.");
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                NavigationView navigationView = findViewById(R.id.nav_view);
                View headerView = navigationView.getHeaderView(0);
                FirebaseUser user = mAuth.getCurrentUser();

                if (user != null) {

                    boolean isUserVerified = user.isEmailVerified();

                    TextView resendVerification = headerView.findViewById(R.id.headerSendVeriEmail);
                    TextView email = headerView.findViewById(R.id.headerUserEmail);
                    TextView name = headerView.findViewById(R.id.headerUsername);

                    email.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                            isUserVerified
                                    ? R.drawable.ic_verified_12
                                    : R.drawable.ic_not_verified, 0);
                    email.setText(user.getEmail());

                    // show send verification email button
                    if (!isUserVerified) {
                        resendVerification.setVisibility(View.VISIBLE);
                    }
                    resendVerification.setOnClickListener(resendEmailLstnr);
                }

                fetchUserDetails();
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // display a default fragment
        replaceFragment(PassangerDashboardFragment.newInstance());
    }

    private void fetchUserDetails() {


    }

    View.OnClickListener resendEmailLstnr = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!mAuth.getCurrentUser().isEmailVerified()) {

                mAuth.getCurrentUser().sendEmailVerification();
                Toast.makeText(PassangerDashboardActivity.this, "Verification Email Sent", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.passanger_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_action_dashboard: {
                visibleFragment = PassangerDashboardFragment.newInstance();
                break;
            }
            case R.id.nav_action_profile: {
                visibleFragment = ProfileFragment.newInstance();
                break;
            }
            case R.id.nav_action_logout: {
                mAuth.signOut();
                finish();
                startActivity(new Intent(PassangerDashboardActivity.this, WelcomeActivity.class));
                break;
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    DrawerLayout.DrawerListener drawerListener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View view, float v) {

        }

        @Override
        public void onDrawerOpened(@NonNull View view) {
        }

        @Override
        public void onDrawerClosed(@NonNull View view) {

            Fragment thisFragment = getSupportFragmentManager().findFragmentById(R.id.content);

            if (thisFragment == visibleFragment) {

                replaceFragment(visibleFragment);
            }
        }

        @Override
        public void onDrawerStateChanged(int i) {

        }
    };

}
