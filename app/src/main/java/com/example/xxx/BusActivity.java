package com.example.xxx;



import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class BusActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BusAdapter busAdapter;

    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        recyclerView =(RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<BusModel> options =
                new FirebaseRecyclerOptions.Builder<BusModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("buses"), BusModel.class)
                        .build();

        busAdapter = new BusAdapter(options);
        recyclerView.setAdapter(busAdapter);

        floatingActionButton=(FloatingActionButton)findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        busAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        busAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem item= menu.findItem(R.id.search);
        SearchView searchView =(SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    private void txtSearch(String str){
        FirebaseRecyclerOptions<BusModel> options =
                new FirebaseRecyclerOptions.Builder<BusModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("buses").orderByChild("travel_name").startAt(str.toUpperCase()).endAt(str.toUpperCase()+"~"), BusModel.class)
                        .build();
       // options.toString().toUpperCase();


        busAdapter =new BusAdapter(options);
        busAdapter.startListening();
        recyclerView.setAdapter(busAdapter);

    }
}