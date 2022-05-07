package com.example.xxx;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;


import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class BusAdapter extends FirebaseRecyclerAdapter<BusModel, BusAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public BusAdapter(@NonNull FirebaseRecyclerOptions<BusModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder,final int position, @NonNull BusModel model) {
        holder.travel_name.setText(model.getTravel_name());
        holder.seat_no.setText(model.seat_no);
        holder.bus_no.setText(model.bus_no);

        Glide.with(holder.img.getContext())
                .load(model.getSurl())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus =DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true,1050)
                        .create();
                // dialogPlus.show();

                View view = dialogPlus.getHolderView();

                EditText travel_name= view.findViewById(R.id.txttravel);
                EditText bus_no = view.findViewById(R.id.txtbus_no);
                EditText seat_no = view.findViewById(R.id.txtseat_no);
                EditText surl = view.findViewById(R.id.img_url);

                Button btnUpdate = view.findViewById(R.id.btnUpdate);

                travel_name.setText(model.getTravel_name());
                bus_no.setText(model.getBus_no());
                seat_no.setText(model.getSeat_no());
                surl.setText(model.getSurl());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map= new HashMap<>();
                        map.put("travel_name", travel_name.getText().toString());
                        map.put("bus_no",bus_no.getText().toString());
                        map.put("seat_no",seat_no.getText().toString());
                        map.put("surl",surl.getText().toString());

                        // FirebaseDatabase.getInstance().getReference().child("buses")
                        //   .child(getRef(position).getKey()).updateChildren(map)
                        FirebaseDatabase.getInstance().getReference().child("buses")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.travel_name.getContext(), "Data Updated Successfully",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.travel_name.getContext(), "Error While Updating",Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });

                    }
                });

            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(holder.travel_name.getContext());
                builder.setTitle("Are You Sure?");
                builder.setMessage("Deleted data can't be restore!");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("buses")
                                .child(getRef(position).getKey()).removeValue();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();



            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView travel_name,bus_no,seat_no ;

        Button btnEdit,btnDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img =(CircleImageView) itemView.findViewById(R.id.img1);
            travel_name =(TextView) itemView.findViewById(R.id.nametext);
            bus_no=(TextView) itemView.findViewById(R.id.busno);
            seat_no=(TextView)  itemView.findViewById(R.id.seatno);

            btnEdit= (Button) itemView.findViewById(R.id.btnEdit);
            btnDelete =(Button) itemView.findViewById(R.id.btnDelete);
        }
    }
}