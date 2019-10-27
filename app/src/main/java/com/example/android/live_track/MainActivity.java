package com.example.android.live_track;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    EditText editText6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        editText1 = findViewById(R.id.code1);
        editText2 = findViewById(R.id.code2);
        editText3 = findViewById(R.id.code3);
        editText4 = findViewById(R.id.code4);
        editText5 = findViewById(R.id.code5);
        editText6 = findViewById(R.id.code6);
        if (editText1.length()==1) {
            editText2.requestFocus();
        }
    }


    public void verify(View view) {
        Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
        startActivity(intent);
    }



}
