package com.example.xxx;
//
//
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.firebase.ui.database.FirebaseListOptions;
//import com.google.firebase.database.FirebaseDatabase;
//
//public class Traveldetails extends AppCompatActivity {
//
//    RecyclerView recyclerView;
//  adapter Adapter;
//
//   //loatingActionButton floatingActionButton;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_traveldetails);
//
//        recyclerView =(RecyclerView) findViewById(R.id.rv);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        FirebaseListOptions<Mainmodel>options=new FirebaseListOptions.Builder<Mainmodel>()
//                .setQuery(FirebaseDatabase.getInstance().getReference().child("buses"), Mainmodel.class)
//                .build();      Adapter = new adapter(options);
//      recyclerView.setAdapter(Adapter);
//
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Adapter.startListening();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Adapter.stopListening();
//    }
//
////    @Override
////    public boolean onCreateOptionsMenu(Menu menu) {
////        getMenuInflater().inflate(R.menu.search,menu);
////        MenuItem item= menu.findItem(R.id.search);
////        SearchView searchView =(SearchView)item.getActionView();
////        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
////            @Override
////            public boolean onQueryTextSubmit(String query) {
////                txtSearch(query);
////                return false;
////            }
////
////            @Override
////            public boolean onQueryTextChange(String query) {
////                txtSearch(query);
////                return false;
////            }
////        });
////
////        return super.onCreateOptionsMenu(menu);
////    }
////    private void txtSearch(String str){
////        FirebaseRecyclerOptions<model> options =
////                new FirebaseRecyclerOptions.Builder<model>()
////                        .setQuery(FirebaseDatabase.getInstance().getReference().child("buses").orderByChild("travel_name").startAt(str).endAt(str+"~"),MainModel.class)
////                        .build();
////
////
////        Adapter =new adapter(options);
////        Adapter.startListening();
////        recyclerView.setAdapter(Adapter);
//
//    }



import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.xxx.Mainmodel;
import com.example.xxx.R;
import com.example.xxx.adapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class JDCtravels extends AppCompatActivity {

    RecyclerView recyclerView;
    adapter mainAdapter;

    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traveldetails);

        recyclerView =(RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Mainmodel>options =
                new FirebaseRecyclerOptions.Builder<Mainmodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("buses").orderByChild("travel_name").equalTo("JDC"),Mainmodel.class)
                        .build();

        mainAdapter = new adapter(options);
        recyclerView.setAdapter(mainAdapter);

//        floatingActionButton=(FloatingActionButton)findViewById(R.id.floatingActionButton);
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),AddActivity.class));
//            }
 //     });
  }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }


}