package com.example.royi.racebet;

import android.app.Activity;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Date;
import java.util.List;
import java.util.Calendar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Subscription;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "BasicRecordingApi";
    private static final int REQUEST_OAUTH_REQUEST_CODE = 1;
    EditText userName,userPassword;
    Button btnLogin,btnSignup;
    String userNameText,userPasswordText;
    FirebaseAuth userAuth;
<<<<<<< HEAD

=======
    //check
>>>>>>> ff5de3d74663074a7f441a84b2aa1bc3d959349c

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FitnessOptions fitnessOptions =
                FitnessOptions.builder().addDataType(DataType.TYPE_ACTIVITY_SAMPLES).build();

        // Check if the user has permissions to talk to Fitness APIs, otherwise authenticate the
        // user and request required permissions.
        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    this,
                    REQUEST_OAUTH_REQUEST_CODE,
                    GoogleSignIn.getLastSignedInAccount(this),
                    fitnessOptions);
        } else {
            subscribe();
        }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_OAUTH_REQUEST_CODE) {
                subscribe();
            }
        }
    }

    /**
     * Subscribes to an available {@link DataType}. Subscriptions can exist across application
     * instances (so data is recorded even after the application closes down).  When creating
     * a new subscription, it may already exist from a previous invocation of this app.  If
     * the subscription already exists, the method is a no-op.  However, you can check this with
     * a special success code.
     */
    public void subscribe() {
        // To create a subscription, invoke the Recording API. As soon as the subscription is
        // active, fitness data will start recording.
        // [START subscribe_to_datatype]
        Fitness.getRecordingClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .subscribe(DataType.TYPE_ACTIVITY_SAMPLES)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "Successfully subscribed!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "There was a problem subscribing.");
                    }
                });
        // [END subscribe_to_datatype]
    }

    /**
     * Cancels the ACTIVITY_SAMPLE subscription by calling unsubscribe on that {@link DataType}.
     */
    private void cancelSubscription() {
        final String dataTypeStr = DataType.TYPE_ACTIVITY_SAMPLES.toString();

        // Invoke the Recording API to unsubscribe from the data type and specify a callback that
        // will check the result.
        // [START unsubscribe_from_datatype]
        Fitness.getRecordingClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .unsubscribe(DataType.TYPE_ACTIVITY_SAMPLES);
        // [END unsubscribe_from_datatype]
    }

    public int getStepsFromCertainDate(Date date){

        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.setTime(date);
        long startTime = cal.getTimeInMillis();
        //TODO : DataReadRequest readRequest
        return 0;

    }

}
