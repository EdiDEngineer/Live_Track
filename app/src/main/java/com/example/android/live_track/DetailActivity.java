package com.example.android.live_track;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Scanner;

public class DetailActivity extends AppCompatActivity {
    public static URL mUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String url = "";
        try{ mUrl = new URL(url);}
        catch(Exception e) {}

    }

    public String getJson() throws IOException{
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
