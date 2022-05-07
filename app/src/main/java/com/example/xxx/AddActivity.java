package com.example.xxx;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
    EditText travel_name,bus_no,seat_no,surl;
    Button btnAdd,btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        travel_name=(EditText) findViewById(R.id.txttravel);
        bus_no=(EditText) findViewById(R.id.txtbus_no);
        seat_no=(EditText) findViewById(R.id.txtseat_no);
        surl=(EditText) findViewById(R.id.img_url);

        btnAdd=(Button) findViewById(R.id.btnAdd);
        btnBack=(Button) findViewById(R.id.btnBack);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertData();
                clearAll();

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });
    }

    private void insertData(){
//        if (bus_no.length() == 0) {
//            bus_no.setError("This field is required");
//            return;
//        }

        Map<String,Object> map= new HashMap<>();

        map.put("travel_name", travel_name.getText().toString());
        map.put("bus_no",bus_no.getText().toString());
        map.put("seat_no",seat_no.getText().toString());
        map.put("surl",surl.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("buses").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this, "Data Inserted Successfully.",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddActivity.this, "Data not inserted.",Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private void clearAll(){
        travel_name.setText("");
        bus_no.setText("");
        seat_no.setText("");
        surl.setText("");
    }


    public void onBackPressed(){
        startActivity(new Intent(AddActivity.this,BusActivity.class));

    }
}



