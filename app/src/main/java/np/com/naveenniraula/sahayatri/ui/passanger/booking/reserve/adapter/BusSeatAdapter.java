package np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.data.model.SeatModel;
import np.com.naveenniraula.sahayatri.util.Constants;

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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final SeatModel itrModel = seatModelList.get(i);

        viewHolder.seatName.setText(
                itrModel.isSeat()
                        ? itrModel.getSeatIdentifier()
                        : ""
        );
        viewHolder.setBusSeatClickListener(busSeatClickListener);
        viewHolder.root.setBackgroundColor(itrModel.getBackgroundColor());
    }

    private BusSeatClickListener busSeatClickListener = position -> {

        SeatModel sm = seatModelList.get(position);

        if (sm.isAvailable() && sm.isSeat()) {

            int color = sm.isSelected()
                    ? Constants.SELECTED_COLOR
                    : Constants.SEAT_COLOR;

            sm.setSelected(!sm.isSelected());
            seatModelList.set(position, sm);
            sm.setBackgroundColor(color);

            notifyItemChanged(position);
        }
    };

    @Override
    public int getItemCount() {
        return seatModelList.size();
    }

    public void add(List<SeatModel> seatList) {

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

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            root = itemView.findViewById(R.id.ivsRoot);
            seatName = itemView.findViewById(R.id.ivsName);
            root.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            listener.seatClicked(getAdapterPosition());
        }

        void setBusSeatClickListener(BusSeatClickListener listener) {
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
