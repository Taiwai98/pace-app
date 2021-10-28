package com.testapp.app;

public class User {
    // creating variables
    public String fullName, age, emailAddress;
    // empty constructor
    public User(){

    }
    // creating a constructor which assigns values to the user properties.
    public User(String fullName, String age, String email){
        this.fullName = fullName;
        this.age = age;
        this.emailAddress = email;
    }


}
