package com.example.xxx;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class NCGtravels  extends AppCompatActivity {

    RecyclerView recyclerView;
    adapter mainAdapter;

    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traveldetails);

        recyclerView =(RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<Mainmodel> options =

            new FirebaseRecyclerOptions.Builder<Mainmodel>()
                    .setQuery(FirebaseDatabase.getInstance().getReference("buses").orderByChild("travel_name")

                            .equalTo("NCG"),Mainmodel.class)
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
