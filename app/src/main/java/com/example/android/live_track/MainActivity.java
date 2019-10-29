package com.example.android.live_track;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.poovam.pinedittextfield.PinField;

public class MainActivity extends AppCompatActivity {
    PinField pinField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        pinField = findViewById(R.id.pin);
    }

    public void verify(View view) {
        Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
        intent.putExtra("EXTRA_SESSION", pinField.getText().toString());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


}
