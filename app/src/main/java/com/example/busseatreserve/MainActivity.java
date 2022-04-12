package com.example.busseatreserve;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showLogin(View view) {
        Intent intent = new Intent(this, driverLogin.class);
        startActivity(intent);
    }

//    public void contentDriver(View view) {
//        Intent intent=new Intent(this,register_driver.class);
//        startActivity(intent);
//    }
}
