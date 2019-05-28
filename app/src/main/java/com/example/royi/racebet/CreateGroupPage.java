package com.example.royi.racebet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CreateGroupPage extends AppCompatActivity {

    private Button btnCreateGroup, btnAddFriend;
    private EditText txtGroupName,txtBet,txtDuration,txtPhoneNumber;
    FirebaseAuth mAuth;
    DatabaseReference dRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group_page);

        mAuth = FirebaseAuth.getInstance();
        dRef = FirebaseDatabase.getInstance().getReference().child("Groups");

        btnCreateGroup = findViewById(R.id.btnCreateGroup);
        btnAddFriend=findViewById(R.id.btnAddFriend);
        txtGroupName=findViewById(R.id.txtGroupName);
        txtBet=findViewById(R.id.txtBetAmount);
        txtDuration=findViewById(R.id.txtDuration);
        txtPhoneNumber=findViewById(R.id.txtPhoneNumber);

        btnCreateGroup.setOnClickListener(new View.OnClickListener() {//יוצר קבוצה ומכניס לדאטה בייס
            @Override
            public void onClick(View v) {
                Group group = new Group(dRef.push().getKey(),txtGroupName.getText().toString(),txtDuration.getText().toString(),null,mAuth.getUid(),null);
                dRef.child(group.getGruopID()).setValue(group);
                Intent intent=new Intent(CreateGroupPage.this,PaypalPage.class);
                startActivity(intent);
            }
        });




        btnAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtPhoneNumber.length() != 10) // check if valid phone number
                {
                    Toast.makeText(CreateGroupPage.this,"NOT VALID phone number",Toast.LENGTH_LONG).show();
                }
                // ** neet to:insert data to sql + declares successful

                Toast.makeText(CreateGroupPage.this,"Member joined!:)",Toast.LENGTH_LONG).show();

            }
        });

    }
}

