package com.example.android.live_track;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    Switch simpleSwitch;
    TextView text;
    TextView text2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simpleSwitch =  findViewById(R.id.switches);
        text = findViewById(R.id.switch2);
        text2 = findViewById(R.id.switch1);

        if (simpleSwitch != null) {
            simpleSwitch.setOnCheckedChangeListener(this);
        }
        setContentView(R.layout.activity_welcome);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
            text.setVisibility(View.VISIBLE);
            text2.setVisibility(View.GONE);

        } else {
            text2.setVisibility(View.VISIBLE);
            text.setVisibility(View.GONE);
        }
    }
}