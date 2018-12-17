package np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.pages;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.util.Log;

public class PageChangeViewModel extends ViewModel {

    private MutableLiveData<Bundle> data;

    public MutableLiveData<Bundle> observeDataChanges() {

        if (data == null) {
            data = new MutableLiveData<>();
        }

        return data;
    }

    public void addData(Bundle bundle) {

        data.postValue(bundle);
    }

}
