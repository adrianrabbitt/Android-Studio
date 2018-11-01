package com.example.adria.persontracker;

/**
 * Created by adria on 2/20/2017.
 */
import android.content.Context;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SendLocationActivity extends AsyncTask<String,Void,Void> {


    String coordinates;
    protected void onPreExecute() {

    }

    @Override
    public Void doInBackground(String... arg0) {

        String mobileNo = arg0[0];
        String latitude = arg0[1];
        String longitude = arg0[2];
        String URLlink;
        try
        {
            coordinates = "?latitude=" + URLEncoder.encode(latitude, "UTF-8");
            coordinates += "&longitude=" + URLEncoder.encode(longitude, "UTF-8");
            coordinates += "&mobileNo=" + URLEncoder.encode(mobileNo, "UTF-8");
            URLlink = "http://persontracker.000webhostapp.com/insertLocationMobile.php" + coordinates;
            URL urlphp = new URL(URLlink);
            HttpURLConnection con = (HttpURLConnection) urlphp.openConnection();
            con.getInputStream();

        }
        catch (Exception e)
        {

        }
       return null;
    }

}
