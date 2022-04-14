package com.example.busseatreserve;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class driverLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_driver_login);
    }

    //activity change to register activity
    public void registerUser(View view) {
        Intent i = new Intent(this, register_driver.class);
        startActivity(i);
    }

    public void contentView(View view) {
        Intent i = new Intent(this, driver_content.class);
        startActivity(i);
    }
}