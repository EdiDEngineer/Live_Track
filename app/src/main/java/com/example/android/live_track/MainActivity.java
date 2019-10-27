package com.example.android.live_track;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.raycoarana.codeinputview.CodeInputView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);


    }

    public void verify(View view){

        Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
        CodeInputView codeInputView = findViewById(R.id.input_code);
        startActivity(intent);


    }
}
