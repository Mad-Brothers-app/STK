package com.example.last;

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

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder ,final int position, @NonNull MainModel model) {
        holder.name.setText(model.getName());
       // holder.contact.setText(model.getContact());
        holder.from.setText(model.getFrom());
        holder.to.setText(model.getTo());

        Glide.with(holder.img1.getContext())
                .load(model.getSurl())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img1);


        // UPDATE CODE

        holder.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img1.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true,1200)
                        .create();

                //dialogPlus.show();

                View view = dialogPlus.getHolderView();

                EditText name = view.findViewById(R.id.txtname);
                EditText from = view.findViewById(R.id.txtfrom);
                EditText to = view.findViewById(R.id.txtto);
                //EditText surl = view.findViewById(R.id.txtimageurl);
                //EditText contact = view.findViewById(R.id.txtcontact);

                Button btnupdate = view.findViewById(R.id.btnupdate);

                name.setText(model.getName());
                from.setText(model.getFrom());
                to.setText(model.getTo());
               // surl.setText(model.getSurl());
               // contact.setText(model.getContact());

                dialogPlus.show();

                btnupdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("from",from.getText().toString());
                        map.put("to",to.getText().toString());
                       // map.put("surl",surl.getText().toString());
                       // map.put("contact",contact.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("public")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(holder.name.getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.name.getContext(), "Error While Updating", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });

;            }
        });  //UPDATE CODE END


        //DELETE BUTTON CODE

        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are You Sure?");
                builder.setMessage("Deleted data cant't be undo.");

                builder.setPositiveButton("DElete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("public")
                                .child(getRef(position).getKey()).removeValue();

                    }
                });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Toast.makeText(holder.name.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();

                            }
                        });
                                builder.show();
            }
        });

        // DELETE CODE END

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);

        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img1;
        TextView name,from,to,contact;

        Button btndelete,btnedit,btnagain;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img1 = (CircleImageView)itemView.findViewById(R.id.img1);
            name = (TextView)itemView.findViewById(R.id.nametext);
          // contact = (TextView)itemView.findViewById(R.id.contacttext);
            from = (TextView)itemView.findViewById(R.id.fromtext);
            to = (TextView)itemView.findViewById(R.id.totext);

            btnedit = (Button)itemView.findViewById(R.id.btnedit);
            btndelete = (Button)itemView.findViewById(R.id.btndelete);
            btnagain = (Button)itemView.findViewById(R.id.btnagain);

        }
    }



}
