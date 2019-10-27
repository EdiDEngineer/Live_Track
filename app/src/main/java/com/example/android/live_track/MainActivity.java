package com.example.android.live_track;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.raycoarana.codeinputview.CodeInputView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        EditText editText1 = findViewById(R.id.code1);
       /* final EditText editText2 = findViewById(R.id.code2);
        EditText editText3 = findViewById(R.id.code3);
        EditText editText4 = findViewById(R.id.code4);
        EditText editText5 = findViewById(R.id.code5);
        EditText editText6 = findViewById(R.id.code6);*/
//editText1.length()
        editText1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
               // editText2.requestFocus();
            }
        });


    }

    public void verify(View view){
        Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
        startActivity(intent);
    }
}
