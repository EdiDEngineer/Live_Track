package com.example.android.live_track;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Scanner;

import static com.example.android.live_track.MainActivity.getConnectivityStatus;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView nameText = findViewById(R.id.name_value);
        TextView emailText = findViewById(R.id.email_value);
        TextView keyText = findViewById(R.id.key_value);

        if(!getConnectivityStatus(getApplicationContext())){
            Intent intent = new Intent(this,NoInternetActivity.class);
            startActivity(intent);
        }

        String name = SharedPreferenceUtil.getPrefString(getApplicationContext(),"namePref");
        String email = SharedPreferenceUtil.getPrefString(getApplicationContext(),"emailPref");
        String key = SharedPreferenceUtil.getPrefString(getApplicationContext(),"keyPref");

        nameText.setText(name);
        emailText.setText(email);
        keyText.setText(key);

        scheduleJob(getBaseContext());



    }

    public void scheduleJob(Context context) {
        ComponentName componentName = new ComponentName(this, NotificationJobService.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Toast.makeText(context,"Job scheduled", Toast.LENGTH_SHORT);
        } else {
            Toast.makeText(context,"Job not scheduled", Toast.LENGTH_SHORT);
        }
    }


    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
