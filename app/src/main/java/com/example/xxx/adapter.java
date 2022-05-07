package com.example.xxx;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


import de.hdodenhof.circleimageview.CircleImageView;

public class adapter extends FirebaseRecyclerAdapter<Mainmodel,adapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public adapter(@NonNull FirebaseRecyclerOptions<Mainmodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Mainmodel Mainmodel) {
        holder.travel_name.setText(Mainmodel.getTravel_name());
        holder.seat_no.setText(Mainmodel.seat_no);
        holder.bus_no.setText(Mainmodel.bus_no);

        Glide.with(holder.img.getContext())
                .load(Mainmodel.getSurl())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView travel_name,bus_no,seat_no ;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img =(CircleImageView) itemView.findViewById(R.id.img1);
            travel_name =(TextView) itemView.findViewById(R.id.nametext);
            bus_no=(TextView) itemView.findViewById(R.id.busno);
            seat_no=(TextView)  itemView.findViewById(R.id.seatno);

//            btnEdit= (Button) itemView.findViewById(R.id.btnEdit);
//            btnDelete =(Button) itemView.findViewById(R.id.btnDelete);
        }
    }


}
