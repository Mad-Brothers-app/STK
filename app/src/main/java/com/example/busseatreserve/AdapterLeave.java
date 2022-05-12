package com.example.busseatreserve;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterLeave extends RecyclerView.Adapter<AdapterLeave.HolderView> implements Filterable {
    private Context context;
    public ArrayList<ModelLeave> leaveList, filterList;
    private filterLeave filter;

    public AdapterLeave(Context context, ArrayList<ModelLeave> leaveList) {
        this.context = context;
        this.leaveList = leaveList;
        this.filterList = leaveList;
    }

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //infinate layout
        //in here xml code will be change to java code
        View view = LayoutInflater.from(context).inflate(R.layout.single_leave_view, parent, false);
        return new HolderView(view);
    }
    //create dynamic list
    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        //get data
        ModelLeave modelLeave = leaveList.get(position);
        String Id = modelLeave.getLeaveID();
        String type = modelLeave.getLeaveType();
        String sdate = modelLeave.getSdate();
        String eDate = modelLeave.getEdate();
        String pno = modelLeave.getPno();
        String name = modelLeave.getName();
        String route = modelLeave.getRoute();
        String uid = modelLeave.getUid();

        //setData
        holder.type.setText(type);
        holder.startDate.setText(sdate);
        holder.endDate.setText(eDate);
        holder.route.setText(route);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //handle leave clicks,show leave details
                detailsBottomStart(modelLeave);//here modelProduct contains details of clicked product

            }
        });
    }

    private void detailsBottomStart(ModelLeave modelLeave) {
        //bottom sheet
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);

        //inflate view for bottom sheet
        View view = LayoutInflater.from(context).inflate(R.layout.leave_view, null);
        //set view to bottom sheet
        bottomSheetDialog.setContentView(view);


        //init views of bottom sheet
        ImageButton backBtn = view.findViewById(R.id.backward_btn);
        ImageButton delete_Btn = view.findViewById(R.id.delete_Btn);
        ImageButton edit_btn = view.findViewById(R.id.edit_btn);
        TextView nameTV = view.findViewById(R.id.nameTV);
        TextView driver_name = view.findViewById(R.id.driver_name);
        TextView leave_type = view.findViewById(R.id.leave_type);
        TextView pno = view.findViewById(R.id.p_no);
        TextView startDate = view.findViewById(R.id.date_start);
        TextView dateEnd = view.findViewById(R.id.date_end);
        TextView route = view.findViewById(R.id.route);

        //get data
        String Id = modelLeave.getLeaveID();
        String type = modelLeave.getLeaveType();
        String startdate = modelLeave.getSdate();
        String endDate = modelLeave.getEdate();
        String p_no = modelLeave.getPno();
        String name = modelLeave.getName();
        String Route = modelLeave.getRoute();
        String uid = modelLeave.getUid();

        //setData
        driver_name.setText(name);
        leave_type.setText(type);
        pno.setText(p_no);
        startDate.setText(startdate);
        dateEnd.setText(endDate);
        route.setText(Route);

        //show dialog
        bottomSheetDialog.show();

        //edit click
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open edit leave activity
                bottomSheetDialog.dismiss();
                Intent i = new Intent(context, EditLeave.class);
                i.putExtra("LeaveId", Id);
                context.startActivity(i);
            }
        });


        //delete click
        delete_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                //show delete confirmation
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete")
                        .setMessage("Are you sure you want to delete Leave " + type + " ?")
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //delete
                                deleteLeave(Id);//id is leave id

                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //cancel
                                dialogInterface.dismiss();

                            }
                        })
                        .show();

            }
        });

        //back clicked
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dismiss bottom
                bottomSheetDialog.dismiss();

            }
        });


    }

    private void deleteLeave(String id) {
        //delete leave using leave id
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("driver");
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("leave").child(id).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //leave delete
                        Toast.makeText(context, "Leave Deleted ....", Toast.LENGTH_SHORT).show();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed deleting data 
                        Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


    }

    @Override
    public int getItemCount() {
        return leaveList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new filterLeave(this, filterList);

        }
        return filter;
    }

    class HolderView extends RecyclerView.ViewHolder {
        private TextView type, startDate, endDate, route;
        private ImageButton details;

        //holds view of recycler view
        public HolderView(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
            startDate = itemView.findViewById(R.id.startDate);
            endDate = itemView.findViewById(R.id.endDate);
            route = itemView.findViewById(R.id.route);
            // details = itemView.findViewById(R.id.des_card);
        }
    }
}
