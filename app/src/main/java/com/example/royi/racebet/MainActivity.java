package com.example.royi.racebet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    EditText userName,userPassword;
    Button btnLogin,btnSignup;
    String userNameText,userPasswordText;
    FirebaseAuth userAuth;
    //check

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jump(); //if the user already sign-in.

        userName = findViewById(R.id.userNameInput);
        userPassword = findViewById(R.id.userPasswordInput);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        userAuth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SignupPage.class));
            }
        });

        /*
        * btnLogin press:
        * take the user name & password from the input fields.
        *
        * save the user token access in SharedPreference to know if he already logged in.
        * */
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameText = userName.getText().toString();
                userPasswordText = userPassword.getText().toString();


                if (userNameText.equals("") || userPasswordText.equals(""))
                    Toast.makeText(getApplicationContext(), "Email or Password Empty!", Toast.LENGTH_LONG).show();
                else {

                    userAuth.signInWithEmailAndPassword(userNameText, userPasswordText)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                        SharedPreferences.Editor editor = sharedPref.edit();
                                        editor.putBoolean("Registered", true);
                                        editor.putString("Username", userNameText);
                                        editor.putString("Password", userPasswordText);
                                        editor.apply();
                                        // Sign in success, update UI with the signed-in user's information
                                        startActivity(new Intent(MainActivity.this, MainLandingPage.class));
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }
            }
        });


    }

    /*
    * method to check if user is logged in,
    * if yes jump to MainLendingPage.
    * */
    private void jump() {
        if(isFinishing())
            return;
        Boolean Registered;
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Registered = sharedPref.getBoolean("Registered",false);

        if (!Registered)
        {
            //startActivity(new Intent(this,LoginPage.class));
        }else {
            startActivity(new Intent(this,MainLandingPage.class));
            finish();
        }
    }

}
