package np.com.naveenniraula.sahayatri.ui.owner.vehicles.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.lang.ref.WeakReference;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.data.model.Vehicle;

public class VehicleAdapter<T> extends FirebaseRecyclerAdapter<T, VehicleAdapter.FirebaseRecyclerVH> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    private final Context ctx;
    private static OptionsCallbackInterface optionListener;
    private static WeakReference<VehicleAdapter> weakReference;

    public VehicleAdapter(@NonNull FirebaseRecyclerOptions options, Context ctx) {
        super(options);
        this.ctx = ctx;
        weakReference = new WeakReference<>(this);
    }

    @Override
    protected void onBindViewHolder(@NonNull FirebaseRecyclerVH holder, int position, @NonNull T model) {

        Vehicle v = (Vehicle) model;
        holder.model.setText(v.getModel());
        holder.regNumber.setText(v.getRegistrationNumber());
    }

    @NonNull
    @Override
    public FirebaseRecyclerVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_vehicle, viewGroup, false);
        return new FirebaseRecyclerVH(view);
    }

    public void setOptionListener(OptionsCallbackInterface optionListener) {
        VehicleAdapter.optionListener = optionListener;
    }

    static class FirebaseRecyclerVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView model;
        TextView regNumber;
        ImageView options;

        FirebaseRecyclerVH(@NonNull View itemView) {
            super(itemView);

            model = itemView.findViewById(R.id.ivModel);
            regNumber = itemView.findViewById(R.id.ivRegNum);
            options = itemView.findViewById(R.id.ivOptions);
            options.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (VehicleAdapter.weakReference.get() == null) {
                return;
            }

            optionListener.onOptionClicked(getAdapterPosition(), (Vehicle) (VehicleAdapter.weakReference.get().getItem(getAdapterPosition())));
        }
    }

    public interface OptionsCallbackInterface {
        void onOptionClicked(int position, Vehicle model);
    }

}
