package com.example.jonas.phonedialer;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class ShowCountry extends AppCompatActivity implements View.OnClickListener {

    private PhoneNumberUtil phoneUtil;// = PhoneNumberUtil.getInstance();
    private Phonenumber.PhoneNumber NumberProto;// = phoneUtil.parse(NumberStr, "NL");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_country);
        Intent intent = getIntent();
        String NumberStr = intent.getStringExtra(Dialer.EXTRA_MESSAGE);
        phoneUtil = PhoneNumberUtil.getInstance();
        TextView number = (TextView) this.findViewById(R.id.textView2);
        TextView country = (TextView) this.findViewById(R.id.textView);
        ImageView img = (ImageView) this.findViewById(R.id.imageView3);
        Context context = img.getContext();
        String countryCode = null;
        String countryCodeLetters = null;
        int id;

        try {
            Log.d("number", NumberStr);
            if(NumberStr.length() > 0){
                NumberProto = phoneUtil.parse(NumberStr, "NL");
                if (phoneUtil.isPossibleNumber(NumberProto) && phoneUtil.isValidNumber(NumberProto) ) {
                    countryCode = phoneUtil.getRegionCodeForCountryCode(NumberProto.getCountryCode());
                    countryCodeLetters = phoneUtil.format(NumberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
                    if (countryCode != null && countryCodeLetters != null) {
                        country.setText(countryCode.toCharArray(), 0, countryCode.length());
                        number.setText(countryCodeLetters.toCharArray(), 0, countryCodeLetters.length());
                        id = context.getResources().getIdentifier("flag_" + phoneUtil.getRegionCodeForCountryCode(NumberProto.getCountryCode()).toLowerCase(), "drawable", context.getPackageName());
                        img.setImageResource(id);
                    }
                } else {
                    number.setText(("Number is not valid").toCharArray(), 0, ("Number is not valid").length());
                    country.setText(("--").toCharArray(), 0, ("--").length());
                }
            }else{
                number.setText(("Number is empty").toCharArray(), 0, ("Number is empty").length());
                country.setText(("--").toCharArray(), 0, ("--").length());
            }
        } catch (NumberParseException e) {
            Log.d("err", "NumberParseException was thrown: " + e.toString());

        }  catch (Exception e) {
        Log.d("err", "Wiener was thrown: " + e.toString());

    }
    }

    @Override
    public void onClick(View v) {
        this.finish();
    }

    public void onCall(View v) {
        String uri = "tel:" + phoneUtil.format(NumberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL).trim();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }


}
