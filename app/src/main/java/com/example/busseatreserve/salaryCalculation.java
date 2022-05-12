package com.example.busseatreserve;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class salaryCalculation extends AppCompatActivity {
    //default constructor
    public salaryCalculation() {

    }

    private ImageButton back_btn;
    private TextView salary_et;
    private int countLeave = 0, total = 0, salary = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hid action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_salary_calculation);

        back_btn = findViewById(R.id.back_btn);
        salary_et = findViewById(R.id.totalSalary);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //navigate to home page
                startActivity(new Intent(salaryCalculation.this, driver_content.class));
            }
        });
        //get leave details in fireBase Connection
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("driver");
        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("leave").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    countLeave = (int) snapshot.getChildrenCount();
                    total = (50000 - (countLeave * 500));
                    salary_et.setText("RS " + Integer.toString(total));

                    calculationTest(countLeave, salary);
                } else {
                    total = 50000;
                    salary_et.setText("Rs " + Integer.toString(total));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    int calculationTest(int salary, int count) {
        total = (salary - (count * 500));
        return total;
    }

    public int test(int count){
        int result=count;
        return count;
    }

}
