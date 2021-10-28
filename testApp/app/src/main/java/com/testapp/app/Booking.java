package com.testapp.app;


public class Booking {
    // creating variables
    public String eventName, eventID, userID, date;
    // empty constructor
    public Booking(){

    }
    // creating a constructor which assigns values to the booking properties.
    public Booking(String eventName, String eventID, String userID, String date){
        this.eventName = eventName;
        this.eventID = eventID;
        this.userID = userID;
        this.date = date;
    }
}
