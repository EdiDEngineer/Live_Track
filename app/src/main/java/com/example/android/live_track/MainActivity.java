package com.example.android.live_track;

import com.example.android.live_track.SharedPreferenceUtil;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.NotificationChannel;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static com.example.android.live_track.NotificationService.CHANNEL_ID;
import static com.example.android.live_track.SharedPreferenceUtil.initPref;

public class MainActivity extends AppCompatActivity {

    SharedPreferences mSettings;
    private ProgressBar mProgressBar;
    public static URL mUrl;
    Boolean mWrong = false;
    TextView input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSettings = initPref(getApplicationContext());

        if (!mSettings.getBoolean("signedUp", true)) {
            Intent intent = new Intent(this, DetailActivity.class);
            startActivity(intent);

        }

        requestPermissions();

        if(!getConnectivityStatus(getApplicationContext())){
            Intent intent = new Intent(this,NoInternetActivity.class);
            startActivity(intent);
        }



        createNotificationChannel();
        Intent intent = new Intent(this, NotificationService.class);
        ContextCompat.startForegroundService(this,intent);

    }

    private void requestPermissions(){
        if((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED)
             && ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
         {
             ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE},101);
        }
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

    public void verifyUser(View view){
        input = findViewById(R.id.code1);
        final String key = (String) input.getText();
        SharedPreferenceUtil.setPrefString(view.getContext(),"keyPref", key);

        mProgressBar = view.findViewById(R.id.progressBar);

        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                String url = "http://live-track-api.herokuapp.com/api/users/verify?accountKey="+key;
                try{
                    String json = getJson(url);
                    JSONObject jsonObject = new JSONObject(json);
                    if(jsonObject.getString("ok") != "true"){mWrong = true; return null;}
                    SharedPreferenceUtil.setPrefString(getApplicationContext(),"namePref", jsonObject.getString("name"));
                    SharedPreferenceUtil.setPrefString(getApplicationContext(),"emailPref", jsonObject.getString("name"));

                }
                catch (Exception e){Toast.makeText(getApplicationContext(),"GetJsonError", Toast.LENGTH_SHORT); finishActivity(1);}

                return null;
            }

            @Override
            protected void onPreExecute() {
                mProgressBar.setVisibility(View.VISIBLE);
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Object o) {
                mProgressBar.setVisibility(View.INVISIBLE);
                super.onPostExecute(o);
            }
        };

        task.execute();

        if(!mWrong){
            mSettings.edit().putBoolean("signedUp", false).commit();
            Intent intent = new Intent(this, DetailActivity.class);
            startActivity(intent);
        }
        else{
            new AlertDialog.Builder(this)
                    .setTitle("Longin Error!")
                    .setMessage("Retype your password")
                    .show();
        }


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

    public String getJson(String url) throws IOException {

        try{ mUrl = new URL(url);}
        catch(Exception e) {}

        HttpURLConnection connection = (HttpURLConnection) mUrl.openConnection();
        InputStream stream = connection.getInputStream();
        Scanner scanner = new Scanner(stream);
        scanner.useDelimiter("\\A");
        if(scanner.hasNext()){
            connection.disconnect();
            return scanner.next();
        }
        else {
            Toast.makeText(this.getApplicationContext(),"No data",Toast.LENGTH_LONG).show();
            connection.disconnect();
            return null;
        }


    }
}
