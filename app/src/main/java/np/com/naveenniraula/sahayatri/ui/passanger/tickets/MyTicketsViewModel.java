package np.com.naveenniraula.sahayatri.ui.passanger.tickets;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import np.com.naveenniraula.sahayatri.data.model.BookingModel;
import np.com.naveenniraula.sahayatri.data.model.DateModel;

public class MyTicketsViewModel extends ViewModel {

    private MutableLiveData<List<DateModel>> dateKeyLiveData;

    public MutableLiveData<List<DateModel>> observeDateKey() {

        if (dateKeyLiveData == null) {

            dateKeyLiveData = new MutableLiveData<>();
        }
        return dateKeyLiveData;
    }

    public void fetchDateKey() {
        DatabaseReference databaseReference =
                FirebaseDatabase.getInstance()
                        .getReference("Bookings");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<DateModel> dateModels = new ArrayList<>();
                for (DataSnapshot ds :
                        dataSnapshot.getChildren()) {

                    dateModels.add(new DateModel(getHumanReadableDate(ds.getKey())));
                }

                dateKeyLiveData.postValue(dateModels);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String getHumanReadableDate(String inputDate) {
        SimpleDateFormat parser = new SimpleDateFormat("ddMMMyyyy", Locale.ENGLISH);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
        try {
            Date date = parser.parse(inputDate);
            String hrDate = dateFormat.format(date);

            Log.i("BQ7CH72", "HUman readable date :: " + hrDate);

            return hrDate;
        } catch (ParseException ignore) {
        }

        return inputDate;
    }

    private String getQueryableDate(String inputDate) {
        SimpleDateFormat parser = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMMyyyy", Locale.ENGLISH);
        try {
            Date date = parser.parse(inputDate);
            String qrDate = dateFormat.format(date).toUpperCase();

            Log.i("BQ7CH72", "Query date :: " + qrDate);
            return qrDate;
        } catch (ParseException ignore) {
        }

        return inputDate;
    }

    private MutableLiveData<List<BookingModel>> bookingsLiveData;

    public MutableLiveData<List<BookingModel>> observeBookings() {

        if (bookingsLiveData == null) {

            bookingsLiveData = new MutableLiveData<>();
        }

        return bookingsLiveData;
    }

    public void fetchBookings(String bookingDate, String userKey) {

        DatabaseReference databaseReference =
                FirebaseDatabase.getInstance()
                        .getReference("Bookings")
                        .child(getQueryableDate(bookingDate));

        databaseReference
                .orderByChild("userKey")
                .equalTo(userKey)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        List<BookingModel> bookingModelList = new ArrayList<>();
                        for (DataSnapshot ds :
                                dataSnapshot.getChildren()) {

                            BookingModel bookingModel = ds.getValue(BookingModel.class);
                            if (bookingModel != null
                                    && "Booked".equals(bookingModel.getBookMode())) {

                                bookingModelList.add(bookingModel);
                            }

                        }

                        bookingsLiveData.postValue(bookingModelList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

}
