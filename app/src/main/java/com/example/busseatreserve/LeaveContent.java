package com.example.busseatreserve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LeaveContent extends AppCompatActivity {

    private ImageButton addLeave_btn, back_btn;
    private RecyclerView leaveRv;
    private EditText searchLeave_btn;
    private ImageView filterLeave;
    private ArrayList<ModelLeave> LeaveList;
    private AdapterLeave adapter;
    private FirebaseAuth firebaseAuth;
    private int countLeave;
    private TextView getLeave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide the action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_leave_content);

        //get edit text details
        addLeave_btn = findViewById(R.id.addNewLeave_btn);
        searchLeave_btn = findViewById(R.id.searchLeave_btn);
        filterLeave = findViewById(R.id.filterLeave);
        leaveRv = findViewById(R.id.leaveRv);
        back_btn = findViewById(R.id.back_home);
        loadAllLeave();
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();
        getLeave = findViewById(R.id.totalLeaveCount);


        //search
        searchLeave_btn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    adapter.getFilter().filter(charSequence);

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        addLeave_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LeaveContent.this, leave_request.class));
            }
        });
        showLeaveUI();


        filterLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LeaveContent.this);
                builder.setTitle("Choose Category : ")
                        .setItems(Constants.productCatgories1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //get selected leave
                                String selected = Constants.productCatgories1[i];
                                if (selected.equals("All")) {
                                    //load all
                                    loadAllLeave();
                                } else {
                                    //load filtered
                                    loadFilterdLeave(selected);

                                }
                            }
                        })
                        .show();

            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LeaveContent.this, driver_content.class));
            }
        });
    }

    private void loadFilterdLeave(String selected) {

        LeaveList = new ArrayList<>();

        //get all leaves
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("driver");
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("leave")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //before getting reset list
                        LeaveList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            String productCategory = "" + ds.child("leaveType").getValue();
                            if (selected.equals(productCategory)) {
                                ModelLeave modelLeave = ds.getValue(ModelLeave.class);
                                LeaveList.add(modelLeave);
                            }
                        }
                        //set up adapter
                        adapter = new AdapterLeave(LeaveContent.this, LeaveList);
                        //set adapter
                        leaveRv.setAdapter(adapter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void loadAllLeave() {
        LeaveList = new ArrayList<>();

        //calculate total leave

        //get leave details in fireBase Connection
        DatabaseReference count = FirebaseDatabase.getInstance().getReference("driver");
        count.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("leave").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    countLeave = (int) snapshot.getChildrenCount();
                    getLeave.setText("Total Leave(" + Integer.toString(countLeave) + ")");
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //get all leaves
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("driver");
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("leave")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        LeaveList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            //before getting reset list
                            ModelLeave modelLeave = ds.getValue(ModelLeave.class);
                            LeaveList.add(modelLeave);
                        }
                        //set up adapter
                        adapter = new AdapterLeave(LeaveContent.this, LeaveList);
                        //set adapter
                        leaveRv.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }
    private void showLeaveUI() {
        //show leave request ui
    }

}