package com.testapp.app;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button signIn;

    private FirebaseAuth mAuth;

    public static final String Error_Detected = "No NFC Tag Detected";
    public static final String Write_Success = "Text Written Successfully";
    public static final String Write_Error = "Error during writing, Try Again!";
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter writingTagFilters;
    boolean writeMode;
    Tag myTag;
    Context context;

    //button to link to the sign up page
    Button SignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // creating functionality for sign up button, redirect to sign up page.
        SignUpButton = findViewById(R.id.registerButton);
        SignUpButton.setOnClickListener(new View.OnClickListener() { // listener for user pressing on sign up button.
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (MainActivity.this, SignUpActivity.class);
                startActivity(intent); // intent redirects user to SignUpActivity.class page.
            }
        });
        // getting user input from text fields email and password on the login page.
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance(); // authenticating the user.

        signIn = (Button) findViewById(R.id.loginBtn); // creating functionality for the login button, redirects user to welcomepage.java
        signIn.setOnClickListener(new View.OnClickListener() { // listener for user pressing login button.
            @Override
            public void onClick(View v) {
                userLogin(); // on user click, calls userLogin method

            }
        });

    }
    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        // if statements used for input validation.
        if(email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email!");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 8){
            editTextPassword.setError("Password cannot be less than 8 characters!");
            editTextPassword.requestFocus();
            return;
        }
        // checks firebase authentication to sign in the users account.
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // firebase successfully authenticates user
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    // if statement to indicate whether the email address is valid
                    if(user.isEmailVerified()){

                        // redirect user to welcome page/user profile
                        startActivity(new Intent(MainActivity.this, welcomepage.class));

                    }else{
                        user.sendEmailVerification(); // email will be sent to user with verification link
                        Toast.makeText(MainActivity.this,"Check your email to verify your account", Toast.LENGTH_LONG).show(); // prompt user that email has been sent email address
                    }


                }else{
                    Toast.makeText(MainActivity.this, "Incorrect email or password! Please try again!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
