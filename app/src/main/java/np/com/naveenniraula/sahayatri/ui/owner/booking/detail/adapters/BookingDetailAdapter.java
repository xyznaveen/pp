package np.com.naveenniraula.sahayatri.ui.owner.booking.detail.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.data.model.BookingModel;

public class BookingDetailAdapter extends RecyclerView.Adapter<BookingDetailAdapter.Vh> {

    private List<BookingModel> bookingModelList;

    public BookingDetailAdapter() {
        this.bookingModelList = new ArrayList<>();
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater =
                (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_owner_book_details, viewGroup, false);
        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {

        BookingModel itrModel = bookingModelList.get(i);
        vh.bookedBy.setText(itrModel.getKey());
        vh.seatNumber.setText(itrModel.getSeatIdentifier());
        vh.bookedOn.setText(String.valueOf(itrModel.getBookedOn()));
    }

    @Override
    public int getItemCount() {
        return bookingModelList.size();
    }

    public class Vh extends RecyclerView.ViewHolder {

        TextView bookedBy;
        TextView seatNumber;
        TextView bookedOn;

        public Vh(@NonNull View itemView) {
            super(itemView);

            bookedBy = itemView.findViewById(R.id.iobBookedBy);
            seatNumber = itemView.findViewById(R.id.iobdSeatNumber);
            bookedOn = itemView.findViewById(R.id.iobdBookedOn);
        }
    }

    public void setBookingModelList(List<BookingModel> bookingModelList) {

        int size = this.bookingModelList.size();
        this.bookingModelList.clear();
        notifyItemRangeRemoved(this.bookingModelList.size(), size);

        this.bookingModelList.addAll(bookingModelList);
        notifyItemRangeInserted(this.bookingModelList.size(), bookingModelList.size());
    }

    public BookingModel getBookingModel(int index) {
        return bookingModelList.get(index);
    }
}
