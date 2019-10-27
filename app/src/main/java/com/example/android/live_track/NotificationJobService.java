package com.example.android.live_track;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationJobService extends JobService {
    private boolean jobCancelled = false;
    public static String mKey;

    @Override
    public boolean onStartJob(JobParameters params) {
        Toast.makeText(getApplicationContext(),"Job started",Toast.LENGTH_SHORT).show();

        SharedPreferences keyPref = getBaseContext().getSharedPreferences("AppPrefs",Context.MODE_PRIVATE);
        mKey = keyPref.getString("keyPref","");
        doBackgroundWork(params);

        return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener());
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                String longitude = location.getLongitude() + "";
                String latitude = location.getLatitude() + "";

                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
                Date date = new Date(System.currentTimeMillis());
                String dateNow = formatter.format(date);

                Data data = new Data(mKey, longitude, latitude, dateNow);
                data.Push();


                Toast.makeText(getApplicationContext(),"Job finished", Toast.LENGTH_SHORT).show();
                jobFinished(params, false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        jobCancelled = true;
        return true;
    }
}
