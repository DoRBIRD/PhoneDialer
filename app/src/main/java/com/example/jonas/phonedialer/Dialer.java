package com.example.jonas.phonedialer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class Dialer extends ActionBarActivity implements View.OnClickListener {
    public final static String EXTRA_MESSAGE = "com.jonas.PhoneDialer.MESSAGE";
    protected  String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phoneNumber = new String();
        setContentView(R.layout.activity_dialer);
        EditText txt = (EditText)this.findViewById(R.id.NumberDiplay);
        //txt.setInputType(InputType.TYPE_NULL);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        txt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.onTouchEvent(event);
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        EditText txt = (EditText)this.findViewById(R.id.NumberDiplay);
        phoneNumber = txt.getText().toString();
        Button b = (Button)v;
        String buttonText = b.getText().toString();
        this.addNumber(buttonText);
        if (txt == null)
            return ;
    }

    public void addNumber(String number){
        EditText txt = (EditText)this.findViewById(R.id.NumberDiplay);
        int start;
        int end;
        if (txt.getSelectionStart() < 0 || txt.getSelectionEnd() < 0){
            start = txt.length();
            end = txt.length();
        }else{
            start = txt.getSelectionStart();
            end = txt.getSelectionEnd();
        }
        phoneNumber = phoneNumber.substring(0,start) + number + phoneNumber.substring(end,phoneNumber.length());
        txt.setText(this.phoneNumber.toCharArray(), 0, this.phoneNumber.length());
        txt.setSelection(start + 1);
    }
    public void removeNumber(View view){
        EditText txt = (EditText)this.findViewById(R.id.NumberDiplay);
        int start;
        int end;
        if (txt.getSelectionStart() < 0 || txt.getSelectionEnd() < 0){
            start = txt.length();
            end = txt.length();
        }else{
            start = txt.getSelectionStart();
            end = txt.getSelectionEnd();
        }
        phoneNumber = txt.getText().toString();
        if(start != end){
            phoneNumber = phoneNumber.substring(0,start) + phoneNumber.substring(end,phoneNumber.length());
            txt.setText(this.phoneNumber.toCharArray(), 0, this.phoneNumber.length());
            txt.setSelection(start);
        }else if(start == end && start >0){
            phoneNumber = phoneNumber.substring(0,start-1) + phoneNumber.substring(end,phoneNumber.length());
            txt.setText(this.phoneNumber.toCharArray(), 0, this.phoneNumber.length());
            txt.setSelection(start-1);
        }
    }

    public void sendNumber(View view) {
        Intent intent = new Intent(this, ShowCountry.class);
        EditText editText = (EditText) findViewById(R.id.NumberDiplay);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
