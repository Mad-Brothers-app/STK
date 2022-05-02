package com.example.busseatreserve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class LeaveContent extends AppCompatActivity {

    ImageButton addLeave_btn;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_content);
        addLeave_btn = findViewById(R.id.addNewLeave_btn);

        addLeave_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LeaveContent.this, leave_request.class));
            }
        });
        showLeaveUI();
    }

    private void showLeaveUI() {
        //show leave request ui

    }
}