package com.example.busseatreserve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class register_driver extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;


    private Button reg_btn;
    private EditText fullname_et;
    private EditText Email_et;
    private EditText Route_et;
    private EditText licNo_et;
    private EditText password_et;
    private EditText confirmPassword_et;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register_driver);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        fullname_et = (EditText) findViewById(R.id.fullName);
        Email_et = (EditText) findViewById(R.id.email);
        Route_et = (EditText) findViewById(R.id.route);
        licNo_et = (EditText) findViewById(R.id.lic_no);
        password_et = (EditText) findViewById(R.id.password);
        confirmPassword_et = (EditText) findViewById(R.id.con_passsword);
        reg_btn = (Button) findViewById(R.id.register_driver);

        reg_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();
            }
        });
    }

    private String name, email, route, lic_no, password, confirmPassword;

    private void inputData() {
        //input data
        name = fullname_et.getText().toString().trim();
        email = Email_et.getText().toString().trim();
        route = Route_et.getText().toString().trim();
        lic_no = licNo_et.getText().toString().trim();
        password = password_et.getText().toString().trim();
        confirmPassword = confirmPassword_et.getText().toString().trim();

        //validation
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Enter name ..", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(route)) {
            Toast.makeText(this, "Enter route ..", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(lic_no)) {
            Toast.makeText(this, "Enter lic no ..", Toast.LENGTH_SHORT).show();
            return;

        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email ..", Toast.LENGTH_SHORT).show();
        }

        if (password.length() < 6) {
            Toast.makeText(this, "password must be at least 6 characters long ..", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "password not matched ..", Toast.LENGTH_SHORT).show();
            return;

        }

        createAccount();


    }

    private void createAccount() {
        progressDialog.setMessage("Creating Account....");
        progressDialog.show();

        //create account
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //account Created
                        saverFirebaseData();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed Creating Account
                        progressDialog.dismiss();
                        Toast.makeText(register_driver.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void saverFirebaseData() {
        progressDialog.setMessage("Saving Account ..");
        String timeStamp = "" + System.currentTimeMillis();

        //set up data to save
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", "" + firebaseAuth.getUid());
        hashMap.put("email", "" + email);
        hashMap.put("name", "" + name);
        hashMap.put("route", "" + route);
        hashMap.put("lic", "" + lic_no);

        //save to db
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("driver");
        ref.child(firebaseAuth.getUid()).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //db updated
                        progressDialog.dismiss();
                        startActivity(new Intent(register_driver.this, driver_content.class));
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed updating db
                        progressDialog.dismiss();
                        startActivity(new Intent(register_driver.this, register_driver.class));
                        finish();

                    }
                });
    }


}
