package com.example.busseatreserve;

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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Calendar;
import java.util.HashMap;

public class EditLeave extends AppCompatActivity {
    private String LeaveId;

    private DatePickerDialog dpl;
    private EditText dateStart, dateEnd;
    private Button btn_update, btn_cancel;
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
        setContentView(R.layout.activity_edit_leave);

        //get id from leave id from intent
        LeaveId = getIntent().getStringExtra("LeaveId");
        loadLeaveDetails();//to set view



        //getting text values ..
        name_et = findViewById(R.id.fullName);
        reason_et = findViewById(R.id.reason_et);
        pno_et = findViewById(R.id.pno_et);
        startDate_et = findViewById(R.id.date_leave_start);
        endDate_et = findViewById(R.id.date_end);
        Route_et = findViewById(R.id.route_et);
        btn_update = findViewById(R.id.edit_Leave);
        btn_cancel = findViewById(R.id.request_cancel);

        //firebase connection progress connection
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait ...");
        progressDialog.setCanceledOnTouchOutside(false);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        //validation and toast instruction all fields
        if (TextUtils.isEmpty(fullname)) {
            Toast.makeText(EditLeave.this, "name is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(Reason)) {
            Toast.makeText(EditLeave.this, "name is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(start)) {
            Toast.makeText(EditLeave.this, "date is required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(end)) {
            Toast.makeText(EditLeave.this, "end date is required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (p_no.length() != 10) {
            Toast.makeText(EditLeave.this, "Wrong phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(route)) {
            Toast.makeText(EditLeave.this, "Route is required", Toast.LENGTH_SHORT).show();
            return;
        }
        UpdateLeave();
    }
    //category
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

    private void loadLeaveDetails() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("driver");
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("leave").child(LeaveId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String leaveId = "" + snapshot.child("leaveID").getValue();
                        String leaveType = "" + snapshot.child("leaveType").getValue();
                        String route = "" + snapshot.child("route").getValue();
                        String Sdate = "" + snapshot.child("Sdate").getValue();
                        String Edate = "" + snapshot.child("Edate").getValue();
                        String name = "" + snapshot.child("name").getValue();
                        String pno = "" + snapshot.child("pno").getValue();
                        String timeStamp = "" + snapshot.child("timeStamp").getValue();
                        String uid = "" + snapshot.child("uid").getValue();

                        //set data to views

                        name_et.setText(name);
                        reason_et.setText(leaveType);
                        pno_et.setText(pno);
                        startDate_et.setText(Sdate);
                        endDate_et.setText(Edate);
                        Route_et.setText(route);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }



    private void UpdateLeave() {
        //show progress
        progressDialog.setMessage("Updating Leave...");
        progressDialog.show();

        //hashmap to update
        HashMap<String, Object> hashmap = new HashMap<>();

        hashmap.put("leaveType", "" + Reason);
        hashmap.put("route", "" + route);
        hashmap.put("Sdate", "" + start);
        hashmap.put("Edate", "" + end);
        hashmap.put("name", "" + fullname);
        hashmap.put("pno", "" + p_no);

        //update to db
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("driver");
        reference.child(firebaseAuth.getCurrentUser().getUid()).child("leave").child(LeaveId)
                .updateChildren(hashmap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //update success
                        progressDialog.dismiss();
                        Toast.makeText(EditLeave.this, "Leave updated ..", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditLeave.this,LeaveContent.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(EditLeave.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

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

    //changing month month as jan,feb
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
}