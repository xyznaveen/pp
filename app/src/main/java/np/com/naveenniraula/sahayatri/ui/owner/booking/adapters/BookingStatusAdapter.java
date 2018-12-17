package np.com.naveenniraula.sahayatri.ui.owner.booking.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.data.model.Vehicle;

public class BookingStatusAdapter extends RecyclerView.Adapter<BookingStatusAdapter.Vh> {

    private List<Vehicle> dataSet;
    private OnBusClickListener listener;

    public BookingStatusAdapter() {
        this.dataSet = new ArrayList<>();
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_vehicle, viewGroup, false);
        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class Vh extends RecyclerView.ViewHolder implements View.OnClickListener {

        ConstraintLayout root;

        public Vh(@NonNull View itemView) {
            super(itemView);

            root = itemView.findViewById(R.id.ivRoot);
            root.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onClick(getAdapterPosition());
            }
        }
    }

    public void setOnBusClickListener(OnBusClickListener listener) {
        this.listener = listener;
    }

    public void setDataSet(List<Vehicle> dataSet) {
        int size = this.dataSet.size();
        this.dataSet.clear();
        notifyItemRangeRemoved(0, size);

        this.dataSet.addAll(dataSet);
        notifyItemRangeInserted(this.dataSet.size(), dataSet.size());
    }

    public interface OnBusClickListener {
        void onClick(int position);
    }

}
