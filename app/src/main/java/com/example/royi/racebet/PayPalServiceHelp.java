package com.example.royi.racebet;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;

import java.math.BigDecimal;

public class PayPalServiceHelp {
    private String amount;
    private String desc;

    public PayPalServiceHelp(){

    }

    public PayPalServiceHelp(String amount, String desc){
        this.amount=amount;
        this.desc=desc;
    }

    public PayPalConfiguration getPayPalConfig(){
        return new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).
                clientId(Config.PAYPAL_KEY);
    }

    public PayPalPayment getPayPalPayment() {
        return new PayPalPayment(new BigDecimal(amount), "USD", desc,
                PayPalPayment.PAYMENT_INTENT_SALE);
    }

}
