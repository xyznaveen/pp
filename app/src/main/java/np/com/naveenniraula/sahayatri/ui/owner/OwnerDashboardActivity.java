package np.com.naveenniraula.sahayatri.ui.owner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.WelcomeActivity;
import np.com.naveenniraula.sahayatri.data.local.UserEntity;
import np.com.naveenniraula.sahayatri.ui.login.LoginFragment;
import np.com.naveenniraula.sahayatri.ui.owner.booking.BookingStatusFragment;
import np.com.naveenniraula.sahayatri.ui.owner.dashboard.OwnerDashboardFragment;
import np.com.naveenniraula.sahayatri.ui.owner.transaction.TransactionFragment;
import np.com.naveenniraula.sahayatri.ui.owner.vehicles.add.AddVehicleFragment;
import np.com.naveenniraula.sahayatri.ui.owner.vehicles.garage.GarageFragment;
import np.com.naveenniraula.sahayatri.ui.passanger.profile.ProfileFragment;
import np.com.naveenniraula.sahayatri.util.PreferenceUtil;

public class OwnerDashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        saveName();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(OwnerDashboardFragment.newInstance());
    }

    private void saveName() {

        PreferenceUtil preferenceUtil = new PreferenceUtil(this);
        preferenceUtil.saveString(LoginFragment.USER_TYPE, LoginFragment.PASSANGER);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference();
        Query qu =
                ref.child(LoginFragment.VEHICLE_OWNER.replaceAll(" ", ""))
                        .orderByChild(mAuth.getUid());

        qu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds :
                        dataSnapshot.getChildren()) {

                    TextView email = findViewById(R.id.nhodEmail);
                    email.setText(ds.getValue(UserEntity.class).getEmail());

                    TextView name = findViewById(R.id.nhodName);
                    name.setText(ds.getValue(UserEntity.class).getName());

                    preferenceUtil.saveString(LoginFragment.USER_NAME, ds.getValue(UserEntity.class).getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("BQ7CH72", "Error " + databaseError.getDetails());
            }
        });
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments.size() <= 1) {
            return;
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.owner_dashboard, menu);
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

            replaceFragment(AddVehicleFragment.newInstance());

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_owner_status) {

            replaceFragment(GarageFragment.newInstance());
        } else if (id == R.id.nav_owner_booking_status) {

            replaceFragment(BookingStatusFragment.newInstance());
        } else if (id == R.id.nav_owner_logout) {

            FirebaseAuth.getInstance().signOut();
            this.finish();
            startActivity(new Intent(this, WelcomeActivity.class));
        } else if (id == R.id.nav_owner_report) {

            replaceFragment(ReportFragment.newInstance());
        } else if (id == R.id.nav_owner_dashboard) {

            replaceFragment(OwnerDashboardFragment.newInstance());
        } else if (id == R.id.nav_owner_tansactions) {

            replaceFragment(TransactionFragment.newInstance());
        } else if (id == R.id.nav_owner_profile) {

            replaceFragment(ProfileFragment.newInstance());
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();

        if (manager != null) {
            manager.beginTransaction().replace(R.id.ownerFragHolder, fragment).commit();
        }
    }

    public void changeTitle(int titleId) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(titleId);
        }
    }

    public void changeSubTitle(int subtitleId) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setSubtitle(subtitleId);
        }
    }

}
