package np.com.naveenniraula.sahayatri.ui.passanger.booking.reserve.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import np.com.naveenniraula.sahayatri.R;

/**
 * Created by Naveen Niraula @ 12/12/18 : 9:07 PM
 * This file cannot be modified without written consent of Naveen Niraula
 * <p>
 * But may be reproduced if the author intends no benefit from it and wants
 * to contribute to open source.
 **/
public class BusSeatAdapter<T> extends FirebaseRecyclerAdapter<T, BusSeatAdapter.BusSeatAdapterViewHolder> {

    private final Context ctx;

    public BusSeatAdapter(@NonNull FirebaseRecyclerOptions options, Context ctx) {
        super(options);
        this.ctx = ctx;
    }

    @Override
    protected void onBindViewHolder(@NonNull BusSeatAdapterViewHolder holder, int position, @NonNull T model) {
    }

    @NonNull
    @Override
    public BusSeatAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_vehicle, viewGroup, false);
        return new BusSeatAdapterViewHolder(view);
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
    }

    static class BusSeatAdapterViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        BusSeatAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {
        }
    }

}
