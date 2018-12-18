package np.com.naveenniraula.sahayatri.ui.owner.vehicles.garage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import np.com.naveenniraula.sahayatri.R;
import np.com.naveenniraula.sahayatri.data.model.Vehicle;

public class GarageAdapter extends RecyclerView.Adapter<GarageAdapter.Vh> {

    private List<Vehicle> vehicleList;
    private OnVehicleSelectedListener listener;


    public GarageAdapter() {
        vehicleList = new ArrayList<>();
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater =
                (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_vehicle, viewGroup, false);
        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int position) {

        Vehicle itrModel = vehicleList.get(position);

        vh.vehicleModel.setText(itrModel.getModel());
        vh.registrationNumber.setText(itrModel.getRegistrationNumber());
    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    public class Vh extends RecyclerView.ViewHolder implements View.OnClickListener {

        ConstraintLayout root;
        TextView registrationNumber;
        TextView vehicleModel;
        ImageView options;

        public Vh(@NonNull View itemView) {
            super(itemView);

            root = itemView.findViewById(R.id.ivRoot);
            registrationNumber = itemView.findViewById(R.id.ivRegNum);
            vehicleModel = itemView.findViewById(R.id.ivModel);
            options = itemView.findViewById(R.id.ivOptions);

            options.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if (listener != null) {
                listener.onVehicleSelected(getAdapterPosition());
            }
        }
    }

    public Vehicle getItemAt(int index) {
        return vehicleList.get(index);
    }

    public void setVehicleList(List<Vehicle> vehicleList) {

        int size = this.vehicleList.size();
        this.vehicleList.clear();
        notifyItemRangeRemoved(this.vehicleList.size(), size);

        this.vehicleList.addAll(vehicleList);
        notifyItemRangeInserted(this.vehicleList.size(), vehicleList.size());
    }

    public void setOnVehiicleSelectedListener(OnVehicleSelectedListener listener) {
        this.listener = listener;
    }

    public interface OnVehicleSelectedListener {
        void onVehicleSelected(int position);
    }
}
