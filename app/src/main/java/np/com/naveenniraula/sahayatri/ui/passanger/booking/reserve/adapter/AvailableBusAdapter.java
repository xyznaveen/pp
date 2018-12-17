package np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.data.model.Vehicle;

public class AvailableBusAdapter<T> extends FirebaseRecyclerAdapter<T, AvailableBusAdapter.FirebaseRecyclerVH> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    private final Context ctx;
    private static BusSelectedListener optionListener;
    private static WeakReference<AvailableBusAdapter> weakReference;
    private DataFetchComplete dataFetchCompleteListener;

    List<ConstraintLayout> constraintLayoutList;

    public AvailableBusAdapter(@NonNull FirebaseRecyclerOptions options, Context ctx) {
        super(options);
        this.ctx = ctx;
        weakReference = new WeakReference<>(this);
        constraintLayoutList = new ArrayList<>();
    }

    @Override
    protected void onBindViewHolder(@NonNull FirebaseRecyclerVH holder, int position, @NonNull T model) {

        Vehicle v = (Vehicle) model;
        holder.model.setText(v.getModel());
        holder.regNumber.setText(v.getRegistrationNumber());

        holder.root.setBackgroundColor(Color.WHITE);
        if (currentPos == position) {
            holder.root.setBackgroundColor(Color.parseColor("#C1C1C1"));
        }

    }

    private void changeColor() {
        notifyItemChanged(lastPos);
        notifyItemChanged(currentPos);
    }

    @NonNull
    @Override
    public FirebaseRecyclerVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_vehicle_bookin, viewGroup, false);
        return new FirebaseRecyclerVH(view);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();

        if (dataFetchCompleteListener != null) {

            dataFetchCompleteListener.onDataFetch(getItemCount());
        }
    }

    public void setBusSelectedListener(BusSelectedListener optionListener) {
        AvailableBusAdapter.optionListener = optionListener;
    }

    public void setDataFetchCompleteListener(DataFetchComplete dataFetchCompleteListener) {
        this.dataFetchCompleteListener = dataFetchCompleteListener;
    }

    private int lastPos = -1;
    private int currentPos = -1;

    static class FirebaseRecyclerVH extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        TextView model;
        TextView regNumber;
        ImageView options;
        ConstraintLayout root;


        FirebaseRecyclerVH(@NonNull View itemView) {
            super(itemView);

            model = itemView.findViewById(R.id.ivModel);
            regNumber = itemView.findViewById(R.id.ivRegNum);
            options = itemView.findViewById(R.id.ivOptions);
            root = itemView.findViewById(R.id.ivbRoot);
            root.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (AvailableBusAdapter.weakReference.get() == null) {
                return;
            }
            weakReference.get().lastPos = weakReference.get().currentPos;
            weakReference.get().currentPos = getAdapterPosition();

            if (optionListener != null) {
                optionListener.onSelectionChange(getAdapterPosition(),
                        (Vehicle) (AvailableBusAdapter.weakReference.get().getItem(getAdapterPosition())));
            }

            weakReference.get().changeColor();
        }

    }

    public interface BusSelectedListener {
        void onSelectionChange(int position, Vehicle model);
    }

    private interface CheckChangeListener {
        void onItemSelected(int position);
    }

    public interface DataFetchComplete {
        void onDataFetch(int dataCount);
    }

}
