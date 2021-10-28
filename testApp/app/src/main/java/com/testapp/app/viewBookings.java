package com.testapp.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class viewBookings extends AppCompatActivity {
    public List<Booking> bookings = new ArrayList<Booking>();
    public List<String> userIDs = new ArrayList<String>();
    TextView textview = new TextView(this);
    LinearLayout layout = (LinearLayout) findViewById(R.id.listLayout);
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //int counter = 0;
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        setContentView(R.layout.activity_view_bookings);
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid(); //String variable
        FirebaseDatabase.getInstance("https://pace-booking-app-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Bookings")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Booking booking = dataSnapshot.getValue(Booking.class);
                            String userId = booking.userID;
                            userIDs.add(userId);
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
         //displayEventData(); //calling method


    }
   /* public void displayEventData(){
        LinearLayout layout = (LinearLayout) findViewById(R.id.listLayout);
        if(counter == 0){
            TextView textView = new TextView(this);
            textView.setText("No current bookings!");
            layout.addView(textView);
        }else{
            for (int i = 0; i <= bookings.size(); i++){
                TextView textView = new TextView(this);
                textView.setText(bookings.get(i).eventName);
                layout.addView(textView);

        }

    }*/
}