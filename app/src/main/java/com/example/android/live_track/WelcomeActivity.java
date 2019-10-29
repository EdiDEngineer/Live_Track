package com.example.android.live_track;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.suke.widget.SwitchButton;

public class WelcomeActivity extends AppCompatActivity {
    TextView key;
    TextView status;
    SwitchButton switchbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        key = findViewById(R.id.key_value);
        switchbutton = findViewById(R.id.switch1);
        status = findViewById(R.id.status);
        key.setText(getIntent().getStringExtra("EXTRA_SESSION"));

        switchbutton.setOnCheckedChangeListener(
                new SwitchButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                        if (isChecked) {
                            status.setText("Tracking ON");
                        } else {
                            status.setText("Tracking OFF");
                        }

                    }
                });
    }
}