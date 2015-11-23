package com.example.jonas.phonedialer;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Dialer extends ActionBarActivity implements View.OnClickListener {
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
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null)
                   imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        EditText txt = (EditText)this.findViewById(R.id.NumberDiplay);
        Button b = (Button)v;
        String buttonText = b.getText().toString();
        this.addNumber(buttonText, txt);
        if (txt == null)
            return ;
    }

    public void addNumber(String number, EditText txt){
        int start = txt.getSelectionStart();
        int end = txt.getSelectionEnd();
        phoneNumber = phoneNumber.substring(0,start) + number + phoneNumber.substring(end,phoneNumber.length());
        txt.setText(this.phoneNumber.toCharArray(), 0, this.phoneNumber.length());
        txt.setSelection(start+1);
    }
}
