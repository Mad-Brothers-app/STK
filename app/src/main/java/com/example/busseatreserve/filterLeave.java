package com.example.busseatreserve;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.Locale;

public class filterLeave extends Filter {
    private AdapterLeave adapter;
    private ArrayList<ModelLeave> filterList;

    public filterLeave(AdapterLeave adapter, ArrayList<ModelLeave> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        //validate data for search query
        if (constraint != null && constraint.length() > 0) {
            //search filed not empty, searching something, perform search
            //change to uppercase, to make case insensitive
            constraint = constraint.toString().toUpperCase();
            //store our filter list
            ArrayList<ModelLeave> filteredModels = new ArrayList<>();
            for (int i = 0; i < filterList.size(); i++) {
                //check
                if (filterList.get(i).getLeaveType().toUpperCase().contains(constraint) ||
                        filterList.get(i).getRoute().toUpperCase().contains(constraint)) {
                    //add filter data to list
                    filteredModels.add(filterList.get(i));


                }
            }
            results.count = filteredModels.size();
            results.values = filteredModels;

        } else {
            //search filed empty, not searching, return /all/original/ complete list
            results.count = filterList.size();
            results.values = filterList;

        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapter.leaveList = (ArrayList<ModelLeave>) filterResults.values;
        //refresh adapter
        adapter.notifyDataSetChanged();

    }
}
