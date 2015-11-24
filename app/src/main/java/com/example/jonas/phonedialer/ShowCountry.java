package com.example.jonas.phonedialer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class ShowCountry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_country);
        Intent intent = getIntent();
        String NumberStr = intent.getStringExtra(Dialer.EXTRA_MESSAGE);
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        try {
            Phonenumber.PhoneNumber NumberProto = phoneUtil.parse(NumberStr, "NL");
            if (phoneUtil.isValidNumber(NumberProto)){
                phoneUtil.format(NumberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
            }
            else{

            }
        } catch (NumberParseException e) {
            Log.d("err","NumberParseException was thrown: " + e.toString());
        }
    }
}
