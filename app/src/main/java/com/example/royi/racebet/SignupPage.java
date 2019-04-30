package com.example.royi.racebet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupPage extends AppCompatActivity {

    EditText userEmail,userPhone,userFullName,userPassword,userRePassword;

    Button btnSigup;

    FirebaseAuth mAuth;
    DatabaseReference dRef;

    User user;

    String userEmailText,userPhoneText,userFullNameText,userPasswordText,userRePasswordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        userEmail = findViewById(R.id.userEmail);
        userPhone = findViewById(R.id.userPhone);
        userFullName = findViewById(R.id.userFullName);
        userPassword = findViewById(R.id.userPassword);
        userRePassword = findViewById(R.id.userRePassword);

        btnSigup = findViewById(R.id.btnSignup);

        mAuth = FirebaseAuth.getInstance();
        dRef = FirebaseDatabase.getInstance().getReference();

        btnSigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEmailText = userEmail.getText().toString();
                userFullNameText = userFullName.getText().toString();
                userPhoneText = userPhone.getText().toString();
                userPasswordText = userPassword.getText().toString();
                userRePasswordText = userRePassword.getText().toString();

                if(userPasswordText.equals(userRePasswordText)){
                    mAuth.createUserWithEmailAndPassword(userEmailText,userPasswordText)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                       user = new User(userFullNameText,userPhoneText,null,null);
                                       dRef.child("Users").child(mAuth.getUid()).setValue(user);
                                       startActivity(new Intent(SignupPage.this,MainLandingPage.class));
                                    } else {
                                        Toast.makeText(getApplicationContext(),"Sign Up Filed!",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
                else
                    Toast.makeText(getApplicationContext(),"Password Doesn't Match!",Toast.LENGTH_LONG).show();

            }
        });

    }
}
