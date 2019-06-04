package com.example.royi.racebet;

import android.support.annotation.Nullable;
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
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;


public class CreateGroupPage extends AppCompatActivity {


    public static int PalPalResultCode = 7171;

    private String amount="";

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)//use sandbox on test
            .clientId(Config.PAYPAL_KEY);

    private Button btnCreateGroup, btnAddFriend;
    private EditText txtGroupName,txtBet,txtDuration,txtPhoneNumber;
    FirebaseAuth mAuth;
    DatabaseReference dRef;

    @Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group_page);

        //start Paypal service
        Intent intent=new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);

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
                /*Group group = new Group(dRef.push().getKey(),txtGroupName.getText().toString(),txtDuration.getText().toString(),null,mAuth.getUid(),null);
                dRef.child(group.getGruopID()).setValue(group);
                Intent intent=new Intent(CreateGroupPage.this,PaypalPage.class);
                startActivity(intent);*/
                amount = txtBet.getText().toString();
                PayPalPayment payPalPayment=new PayPalPayment(new BigDecimal(String.valueOf(amount)),"USD",
                        "The Game started",PayPalPayment.PAYMENT_INTENT_SALE);
                Intent intent=new Intent(getApplicationContext(), PaymentActivity.class);
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
                startActivityForResult(intent,PalPalResultCode);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PalPalResultCode)
        {
            if(resultCode == RESULT_OK)
            {
                PaymentConfirmation confirmation=data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation !=null)
                {
                    try{
                        String PaymentDetails=confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this,PaymentDetails.class)
                                .putExtra("PaymentDetails",PaymentDetails)
                                .putExtra("PaymentAmount",amount)
                        );



                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
            else if(resultCode == RESULT_CANCELED)
                Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(this,"Invalid",Toast.LENGTH_SHORT).show();
    }
}


