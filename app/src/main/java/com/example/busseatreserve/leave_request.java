package com.example.busseatreserve;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class leave_request extends AppCompatActivity {

    private DatePickerDialog dpl;
    private EditText dateStart, dateEnd;
    Button btn_register, btn_cancel;

    private EditText name, reason, pno, startDate, endDate, Route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_leave_request);
        dateStart = findViewById(R.id.date_leave_start);
        dateEnd = findViewById(R.id.date_leave_end);
        dateStart.setText(getTodaysDate());
        dateEnd.setText(getLastDate());

        name = (EditText) findViewById(R.id.fullName);
        reason = (EditText) findViewById(R.id.email);
        pno = (EditText) findViewById(R.id.route);
        startDate = (EditText) findViewById(R.id.date_leave_start);
        endDate = (EditText) findViewById(R.id.date_leave_end);
        Route = (EditText) findViewById(R.id.con_passsword);
        btn_register = (Button) findViewById(R.id.reg_leave);
        btn_cancel = (Button) findViewById(R.id.request_cancel);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertLeave();

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    private void insertLeave() {

        Map<String, Object> map = new HashMap<>();
        map.put("name", name.getText().toString());
        map.put("reason", reason.getText().toString());
        map.put("pno", pno.getText().toString());
        map.put("startDate", startDate.getText().toString());
        map.put("endDate", endDate.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("leave").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(leave_request.this, "Data inserted Successfully", Toast.LENGTH_SHORT);


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(leave_request.this, "Data not inserted ..", Toast.LENGTH_SHORT).show();

                    }
                });


    }

    private String getLastDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        day = day + 3;
        return makeDateString(day, month, year);
    }

    public String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);

    }

    public void selectdate(View view) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);

                dateStart.setText(date);
                dateStart.setText(date);

            }

        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int Style = AlertDialog.THEME_HOLO_LIGHT;

        dpl = new DatePickerDialog(this, Style, dateSetListener, year, month, day);

    }

    private String makeDateString(int day, int month, int year) {
        return String.format("%s %d  %d", getMonthFormat(month), day, year);
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";
        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view) {
        dpl.show();
    }

    //back to the home page
    public void goHome(View view) {
        Intent i = new Intent(this, driver_content.class);
        startActivity(i);
    }


}


