package com.testapp.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class events extends AppCompatActivity {
    // creating global variables for booking data
    public String eventName, eventID, userID, date;

    Button comicCon;
    Button eSport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        // storing current users uid
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        comicCon = findViewById(R.id.comic);
        comicCon.setOnClickListener(new View.OnClickListener() {
        // creating functionality for the comic con event button.
            @Override
            // calls comicConEvent method when user clicks on the comic con booking button.
            public void onClick(View v) {
                comicConEvent();
            }
        });
        eSport = findViewById(R.id.esport);
        eSport.setOnClickListener(new View.OnClickListener() {

            @Override
            // calls esportEvent method when user clicks on the esport booking button.
            public void onClick(View v) {
                esportEvent();
            }
        });
    }
    public void comicConEvent(){
        // assigning event data to variables
        eventName = "Comic Con";
        eventID = "id412";
        date = "21/10/2021";
        Booking booking = new Booking(eventName, eventID, userID, date); // creating an object of Booking.class and storing booking data within the object.
        FirebaseDatabase.getInstance("https://pace-booking-app-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Bookings").push() //.child OOP used to enable json list format within realtime database.
                .setValue(booking).addOnCompleteListener(new OnCompleteListener<Void>() { // storing booking object within the database.
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(events.this, "Booking for Comic Con confirmed!", Toast.LENGTH_LONG ).show(); //Output message telling user the account has been registered.


                }else{
                    Toast.makeText(events.this, "Failed to book! Try again", Toast.LENGTH_LONG).show(); //Error occurs if something happens e.g. Account already registered.
                }

            }
        });
    }
    public void esportEvent(){
        // assigning event data to variables
        eventName = "E-sport";
        eventID = "id411";
        date = "09/11/2021";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // creating an object of Booking.class and storing booking data within the object.
        Booking booking = new Booking(eventName, eventID, userID, date);
        FirebaseDatabase.getInstance("https://pace-booking-app-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("Bookings").push() //.child OOP used to enable json list format within realtime database
                .setValue(booking).addOnCompleteListener(new OnCompleteListener<Void>() { // storing booking object within the database.
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(events.this, "Booking for E-sports confirmed!", Toast.LENGTH_LONG ).show(); //Output message telling user the account has been registered.


                }else{
                    Toast.makeText(events.this, "Failed to book! Try again", Toast.LENGTH_LONG).show(); //Error occurs if something happens e.g. Account already registered.
                }

            }
        });
    }
}