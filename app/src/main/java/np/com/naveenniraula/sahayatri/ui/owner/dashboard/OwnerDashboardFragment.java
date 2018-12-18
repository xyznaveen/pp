package np.com.naveenniraula.sahayatri.ui.owner.dashboard;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

import np.com.naveenniraula.sahayatri.R;

public class OwnerDashboardFragment extends Fragment {

    private OwnerDashboardViewModel mViewModel;

    public static OwnerDashboardFragment newInstance() {
        return new OwnerDashboardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.owner_dashboard_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        greetUser();
    }

    private void greetUser() {

        TextView greeting = getView().findViewById(R.id.odfGreetingText);

        String string = getString(R.string.greet_afternoon);
        greeting.setText(String.format(string, getGreetingMessage(), "Naveen"));
    }

    private String getGreetingMessage() {

        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        if (hour >= 3 && hour <= 11) {

            return "Good Morning";
        } else if (hour >= 12 && hour <= 16) {

            return "Good Afternoon";
        } else if (hour > 16 && hour <= 20) {

            return "Good Evening";
        } else {
            return "Good Night";
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(OwnerDashboardViewModel.class);
    }

}
