package com.example.busseatreserve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class driver_content extends AppCompatActivity {
    private TextView uname, route;
    private ImageButton logout_btn;
    private FirebaseAuth firebaseAuth;
    ImageButton leaveRequest;
    private ImageButton report_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_driver_content);

        uname = findViewById(R.id.user_name_profile);
        route = findViewById(R.id.pno_et);
        logout_btn = findViewById(R.id.logout_btn);
        leaveRequest = findViewById(R.id.leaveRequest);
        report_call = findViewById(R.id.accidentReport);
        firebaseAuth = FirebaseAuth.getInstance();


        loadmyInfo();
        leaveRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(driver_content.this, LeaveContent.class));
            }
        });
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
            }
        });

        report_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call method
                String phone = "1990";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);

            }
        });
    }


    private void loadmyInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("driver");
        ref.orderByChild("uid").equalTo(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            String name = "" + ds.child("name").getValue();
                            String route_view = "" + ds.child("route").getValue();

                            uname.setText("Hello " + name);
                            route.setText(route_view);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }
}