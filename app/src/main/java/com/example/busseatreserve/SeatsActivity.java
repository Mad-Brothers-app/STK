package com.example.last;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SeatsActivity extends AppCompatActivity {

    GridLayout mainGrid;
    Double seatPrice = 2700.00;
    Double totatCost = 0.0;
    int totalSeats = 0;
    TextView totalPrice;
    TextView totalBookedSeats;
    private Button buttonBook;

    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);

        getSupportActionBar().setTitle("Select Your Favorite Seats");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        databaseReference = FirebaseDatabase.getInstance().getReference();

        mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        totalBookedSeats = (TextView) findViewById(R.id.total_seats);
        totalPrice = (TextView) findViewById(R.id.total_cost);
        buttonBook = (Button) findViewById(R.id.btnBook);

        //Set Event
        setToggleEvent(mainGrid);
        final String nameBus=getIntent().getStringExtra("NAME_BUS");
        final String dateBus=getIntent().getStringExtra("DATE_BUS");
        final String conditionBus=getIntent().getStringExtra("CONDITION_BUS");

        buttonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String totalPriceI=totalPrice.getText().toString().trim();
                String totalBookedSeatsI=totalBookedSeats.getText().toString().trim();
                PaymentDetail paymentDetail=new PaymentDetail(totalPriceI,totalBookedSeatsI);

                
                databaseReference.child("SeatDetails").setValue(paymentDetail);

                Intent intent=new Intent(SeatsActivity.this,PaybleActivity.class);
                intent.putExtra("TOTALCOST",totalPriceI);
                intent.putExtra("TOTALSEAT",totalBookedSeatsI);
                intent.putExtra("NAME_BUS",nameBus);
                intent.putExtra("DATE_BUS",dateBus);
                intent.putExtra("CONDITION_BUS",conditionBus);

                startActivity(intent);

            }
        });

    }


    private void setToggleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cardView.getCardBackgroundColor().getDefaultColor() == -1) {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#00FF00"));
                        totatCost += seatPrice;
                        ++totalSeats;
                        Toast.makeText(SeatsActivity.this, "You Selected Seat Number :" + (finalI + 1), Toast.LENGTH_SHORT).show();

                    } else {
                        //Change background color
                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                        totatCost -= seatPrice;
                        --totalSeats;
                        Toast.makeText(SeatsActivity.this, "You Unselected Seat Number :" + (finalI + 1), Toast.LENGTH_SHORT).show();
                    }
                    totalPrice.setText("" + totatCost+"0");
                    totalBookedSeats.setText("" + totalSeats);
                }
            });
        }
    }}



