package np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.data.model.SeatModel;

public class BusSeatAdapter extends RecyclerView.Adapter<BusSeatAdapter.ViewHolder> {

    private List<SeatModel> seatModelList;

    public BusSeatAdapter() {
        seatModelList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_vehicle_seat, viewGroup, false);
        return new ViewHolder(view);
    }

    int skipAt = 2;

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final SeatModel itrModel = seatModelList.get(i);
        if (itrModel.isSelected()) {
            viewHolder.root.setBackgroundColor(Color.parseColor("#1565c0"));
        } else {
            viewHolder.root.setBackgroundColor(Color.parseColor("#008577"));
        }

        if (skipAt == i) {

            if (skipAt > 2) {
                viewHolder.root.setBackgroundColor(Color.parseColor("#f1f1f1"));
            } else {
                viewHolder.setBusSeatClickListener(lstner);
            }
            skipAt += 5;
            return;
        }

        viewHolder.seatName.setText(itrModel.getSeatNumber());
        viewHolder.setBusSeatClickListener(lstner);
    }

    private BusSeatClickListener lstner = position -> {

        SeatModel sm = seatModelList.get(position);

        if (sm.isAvailable()) {
            sm.setBackgroundColor(Color.BLUE);
            sm.setSelected(!sm.isSelected());
            seatModelList.set(position, sm);
            notifyItemChanged(position);
        }
    };

    @Override
    public int getItemCount() {
        return seatModelList.size();
    }

    public void add(List<SeatModel> seatList) {

        Log.i("BQ7CH72", "Calleed addddddd again.");
        seatModelList.clear();
        notifyDataSetChanged();
        seatModelList.addAll(seatList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private BusSeatClickListener listener;
        ConstraintLayout root;
        TextView seatName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            root = itemView.findViewById(R.id.ivsRoot);
            seatName = itemView.findViewById(R.id.ivsName);
            root.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            listener.seatClicked(getAdapterPosition());
        }

        public void setBusSeatClickListener(BusSeatClickListener listener) {
            this.listener = listener;
        }
    }

    interface BusSeatClickListener {
        void seatClicked(int position);
    }

    public List<SeatModel> getSeatModelList() {
        return seatModelList;
    }
}
