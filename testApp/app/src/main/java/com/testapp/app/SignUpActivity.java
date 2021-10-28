package com.testapp.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    // creating variables
    private TextView registerUser;
    private EditText editTextName, editTextEmail, editTextAge, editTextPassword;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;


    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // creating functionality for back button button, redirect user back to previous page.
        backBtn = findViewById(R.id.backButton);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // setting up firebase auth within the class
        mAuth = FirebaseAuth.getInstance();
        // creating functionality for the register user button.
        registerUser = (Button) findViewById(R.id.register);
        registerUser.setOnClickListener(this);
        // retrieving data from the text fields within the sign up form.
        editTextName = (EditText) findViewById(R.id.name);
        editTextEmail = (EditText) findViewById(R.id.emailAddress);
        editTextAge = (EditText) findViewById(R.id.Age);
        editTextPassword = (EditText) findViewById(R.id.UserPassword);

    }

    @Override
    public void onClick(View v) {
        //Action for button
        switch (v.getId()) {
            case R.id.register:
                registerUser();
                break;
        }

    }

    public void registerUser() {
        // storing text field within string variables
        String name = editTextName.getText().toString().trim();
        String emailAddress = editTextEmail.getText().toString().trim();
        String UserPassword = editTextPassword.getText().toString().trim();
        String age = editTextAge.getText().toString();
    //Validation for user input fields
        if (name.isEmpty()) {
            editTextName.setError("Field is required!");
            editTextName.requestFocus();
            return;
        }

        if (emailAddress.isEmpty()) {
            editTextEmail.setError("Field is required!");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            editTextEmail.setError("Valid email required :)");
            editTextEmail.requestFocus();
            return;
        }

        if (age.isEmpty()) {
            editTextAge.setError("Field is required!");
            editTextAge.requestFocus();
            return;
        }

        if (UserPassword.isEmpty()) {
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if (UserPassword.length() < 8) {
            editTextPassword.setError("Password cannot be less than 8 characters!");
            editTextPassword.requestFocus();
            return;

        }
    //Adding current user to authentication and database to register a new user
        mAuth.createUserWithEmailAndPassword(emailAddress, UserPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                           User user = new User(name, age, emailAddress); //Creating an object of user to store the data within the database. Utilises the User class file to be sent to database.
                           FirebaseDatabase.getInstance("https://pace-booking-app-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users")
                                   .child(FirebaseAuth.getInstance().getCurrentUser().getUid()) //.child OOP used to enable json list format within realtime database
                                   .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUpActivity.this, "User has been registered successfully", Toast.LENGTH_LONG ).show(); //Output message telling user the account has been registered.


                                    }else{
                                        Toast.makeText(SignUpActivity.this, "Failed to register! Try again", Toast.LENGTH_LONG).show(); //Error occurs if something happens e.g. Account already registered.
                                    }

                                }
                            });

                        }else{
                            Toast.makeText(SignUpActivity.this, "Failed to register the user", Toast.LENGTH_LONG).show();
                        }
                    }

                });

    }
}