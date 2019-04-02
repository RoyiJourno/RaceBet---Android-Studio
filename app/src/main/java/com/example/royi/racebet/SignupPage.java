package com.example.royi.racebet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

    EditText userEmail,userFullName,userPhone,userPassword,userRePassword;
    Button btnSignup;
    FirebaseAuth mAuth;
    DatabaseReference dref;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        userEmail = findViewById(R.id.userEmail);
        userFullName = findViewById(R.id.userFullName);
        userPhone = findViewById(R.id.userPhone);
        userPassword = findViewById(R.id.userPassword);
        userRePassword = findViewById(R.id.userRePassword);

        btnSignup = findViewById(R.id.btnSignupToFirebase);

        mAuth = FirebaseAuth.getInstance();
        dref = FirebaseDatabase.getInstance().getReference();


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email,fullName,phone,password,repassword;

                email = userEmail.getText().toString();
                fullName = userFullName.getText().toString();
                phone = userPhone.getText().toString();
                password = userPassword.getText().toString();
                repassword = userRePassword.getText().toString();

                if(password.equals(repassword)){
                    user = new User(fullName,email,phone,null,null);
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                dref.child("Users").child(mAuth.getUid()).setValue(user);
                                startActivity(new Intent(SignupPage.this, MainActivity.class));
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), "Registration failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else
                    Toast.makeText(getApplicationContext(),"Password Doesnt Match!",Toast.LENGTH_LONG).show();
            }
        });

    }
}
