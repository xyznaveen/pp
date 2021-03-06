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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import np.com.naveenniraula.sahayatri.BaseActivity;
import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.WelcomeActivity;
import np.com.naveenniraula.sahayatri.data.local.UserEntity;
import np.com.naveenniraula.sahayatri.ui.login.LoginFragment;
import np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.BookVehicleFragment;
import np.com.naveenniraula.sahayatri.ui.passanger.dashboard.PassangerDashboardFragment;
import np.com.naveenniraula.sahayatri.ui.passanger.profile.ProfileFragment;
import np.com.naveenniraula.sahayatri.ui.passanger.tickets.MyTicketsFragment;
import np.com.naveenniraula.sahayatri.util.MessageHelper;
import np.com.naveenniraula.sahayatri.util.PreferenceUtil;

public class PassangerDashboardActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passanger_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        changeTitle("Passenger");

        mAuth = FirebaseAuth.getInstance();
        testFirebaseQuery();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            MessageHelper.regularSnack(view, "This message will be shown.");
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

    private void testFirebaseQuery() {

        PreferenceUtil preferenceUtil = new PreferenceUtil(this);
        preferenceUtil.saveString(LoginFragment.USER_TYPE, LoginFragment.PASSANGER);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference();
        Query qu =
                ref.child(LoginFragment.PASSANGER)
                        .orderByChild(mAuth.getUid());

        qu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {

                    for (DataSnapshot ds :
                            dataSnapshot.getChildren()) {

                        preferenceUtil.saveString(LoginFragment.USER_NAME, ds.getValue(UserEntity.class).getName());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("BQ7CH72", "Error " + databaseError.getDetails());
            }
        });
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
            return;
        }

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment instanceof BookVehicleFragment) {
            return;
        }

        super.onBackPressed();
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

        if (id == R.id.nav_action_dashboard) {

            replaceFragment(PassangerDashboardFragment.newInstance());
        } else if (id == R.id.nav_action_profile) {

            replaceFragment(ProfileFragment.newInstance());
        } else if (id == R.id.nav_pass_new_booking) {

            replaceFragment(BookVehicleFragment.newInstance());
        } else if (id == R.id.nav_pass_tickets) {

            replaceFragment(MyTicketsFragment.newInstance());
        } else if (id == R.id.nav_action_logout) {

            mAuth.signOut();
            finish();
            startActivity(new Intent(PassangerDashboardActivity.this, WelcomeActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
