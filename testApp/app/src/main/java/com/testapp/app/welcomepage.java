package com.testapp.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class welcomepage extends AppCompatActivity {
    // creating variables
    private Button logout;
    Button BookingButton;
    Button ViewBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomepage);
        // creating functionality for the booking button
        BookingButton = findViewById(R.id.viewEvents);
        BookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirects user to the target page
                Intent intent = new Intent(welcomepage.this, events.class);
                startActivity(intent);
            }
        });
        // creating functionality for the sign out button
        logout = (Button) findViewById(R.id.signOut);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                // redirects user to the target page
                startActivity(new Intent(welcomepage.this, MainActivity.class));
            }
        });
        ViewBooking = findViewById(R.id.myBookings);
        ViewBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(welcomepage.this, viewBookings.class);
                startActivity(intent);
            }
        });
    }
}