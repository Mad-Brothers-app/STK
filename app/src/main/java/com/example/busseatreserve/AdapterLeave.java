package com.example.busseatreserve;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        View view = LayoutInflater.from(context).inflate(R.layout.single_leave_view, parent, false);
        return new HolderView(view);
    }

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
            details = itemView.findViewById(R.id.des_card);
        }
    }
}
