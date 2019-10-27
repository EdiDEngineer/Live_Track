package com.example.android.live_track;

import com.example.android.live_track.SharedPreferenceUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.app.NotificationChannel;
import android.view.View;

import static com.example.android.live_track.NotificationService.CHANNEL_ID;
import static com.example.android.live_track.SharedPreferenceUtil.initPref;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!getConnectivityStatus(getApplicationContext())){
            Intent intent = new Intent(this,NoInternetActivity.class);
            startActivity(intent);
        }

        createNotificationChannel();

        Intent intent = new Intent(this, NotificationService.class);
        ContextCompat.startForegroundService(this,intent);



    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "LiveTrack",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }

    }

    public void verifyUser(View view, String key){
        SharedPreferenceUtil.setPrefString(view.getContext(),"keyPref", key);


    }

    public static boolean getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return true;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return true;
        }
        return false;
    }
}
