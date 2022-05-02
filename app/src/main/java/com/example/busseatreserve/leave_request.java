package com.example.busseatreserve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class leave_request extends AppCompatActivity {

    private DatePickerDialog dpl;
    private EditText dateStart, dateEnd;
    private Button btn_register, btn_cancel;
    private ImageButton back_btn;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    private EditText name_et, reason_et, pno_et, startDate_et, endDate_et, Route_et;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_leave_request);


        name_et = findViewById(R.id.fullName);
        reason_et = findViewById(R.id.reason_et);
        pno_et = findViewById(R.id.pno_et);
        startDate_et = findViewById(R.id.date_leave_start);
        endDate_et = findViewById(R.id.date_end);
        Route_et = findViewById(R.id.route_et);
        btn_register = findViewById(R.id.reg_leave);
        btn_cancel = findViewById(R.id.request_cancel);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait ..");
        progressDialog.setCanceledOnTouchOutside(false);


        startDate_et.setText(getTodaysDate());
        endDate_et.setText(getLastDate());


        reason_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pickCategory
                categoryDialog();

            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //add data to db
                inputData();

            }
        });


    }

    private String fullname, Reason, p_no, start, end, route;

    private void inputData() {
        fullname = name_et.getText().toString().trim();
        Reason = reason_et.getText().toString().trim();
        start = startDate_et.getText().toString().trim();
        end = endDate_et.getText().toString().trim();
        p_no = pno_et.getText().toString().trim();
        route = Route_et.getText().toString().trim();

        if (TextUtils.isEmpty(fullname)) {
            Toast.makeText(this, "name is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(Reason)) {
            Toast.makeText(this, "name is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(start)) {
            Toast.makeText(this, "date is required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(end)) {
            Toast.makeText(this, "end date is required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (p_no.length() != 10) {
            Toast.makeText(this, "Wrong phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(route)) {
            Toast.makeText(this, "Route is required", Toast.LENGTH_SHORT).show();
            return;
        }
        addLeave();
    }

    private void addLeave() {
        progressDialog.setMessage("Adding Leave..");
        progressDialog.show();
        String timeStamp = "" + System.currentTimeMillis();

        //setup data to upload
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("leaveID", "" + timeStamp);
        hashMap.put("leaveType", "" + Reason);
        hashMap.put("route", "" + route);
        hashMap.put("Sdate", "" + start);
        hashMap.put("Edate", "" + end);
        hashMap.put("name", "" + fullname);
        hashMap.put("pno", "" + p_no);
        hashMap.put("timeStamp", "" + timeStamp);
        hashMap.put("uid", "" + firebaseAuth.getUid());

        //add to db
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("driver");
        reference.child(firebaseAuth.getUid()).child("leave").child(timeStamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //added to db
                        progressDialog.dismiss();
                        Toast.makeText(leave_request.this, "Leave Added...", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed to add
                        progressDialog.dismiss();
                        Toast.makeText(leave_request.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


    }

    private void categoryDialog() {
        //dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Leave category")
                .setItems(Constants.productCatgories, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        //get picked category
                        String category = Constants.productCatgories[which];

                        //set picked category
                        reason_et.setText(category);

                    }
                })
                .show();

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


