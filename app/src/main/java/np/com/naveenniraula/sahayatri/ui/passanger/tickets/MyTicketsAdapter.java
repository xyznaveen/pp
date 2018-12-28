package np.com.naveenniraula.sahayatri.ui.passanger.tickets;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.data.model.BookingModel;

public class MyTicketsAdapter extends RecyclerView.Adapter<MyTicketsAdapter.Vh> {

    private List<BookingModel> bookingModels;

    public MyTicketsAdapter() {
        bookingModels = new ArrayList<>();
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_ticket, viewGroup, false);
        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {

        BookingModel itrModel = bookingModels.get(i);


        vh.root.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(vh.root.getContext());
            builder.setTitle(itrModel.getOwnerName() + " \nTicket No. " + itrModel.getKey());
            builder.setMessage("You are qualified to onboard vehicle."
                    + "This acts as electronic ticket whenever you travel."
                    + "One of the crew will match the ticket number to verify you.");
            builder.setPositiveButton("Understood", (a, b) -> {

            });

            AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();
        });
        vh.date.setText(itrModel.getOwnerName());
        vh.busName.setText(itrModel.getSeatIdentifier());
    }

    @Override
    public int getItemCount() {
        return bookingModels.size();
    }

    public void add(List<BookingModel> models) {

        clear();

        bookingModels.addAll(models);
        notifyDataSetChanged();
    }

    private void clear() {

        bookingModels.clear();
        notifyDataSetChanged();
    }

    public class Vh extends RecyclerView.ViewHolder {

        ConstraintLayout root;
        TextView date;
        TextView busName;

        public Vh(@NonNull View itemView) {
            super(itemView);

            root = itemView.findViewById(R.id.itRoot);
            busName = itemView.findViewById(R.id.itBusName);
            date = itemView.findViewById(R.id.itDate);
        }

    }


}
