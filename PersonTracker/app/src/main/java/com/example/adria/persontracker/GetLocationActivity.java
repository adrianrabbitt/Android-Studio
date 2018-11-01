package com.example.adria.persontracker;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetLocationActivity extends AsyncTask<String,Void, String> {

    HttpURLConnection con;
    URL url;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... params) {
        try {
            // Enter URL address where your php file resides
            url = new URL("http://persontracker.000webhostapp.com/output.php");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoOutput(true);

            InputStream input = con.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
            StringBuilder echo = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                echo.append(line);
            }

            // Pass data to onPostExecute method
            return (echo.toString());

        } catch (Exception e) {

            return e.toString();
        }




    }


    String number;
    @Override
    protected void onPostExecute(String result) {
try {
    number = result.toString();
    String[] arr = number.split("\\s+");
    lat = Double.parseDouble(arr[0]);
    lng = Double.parseDouble(arr[1]);
}

    catch (Exception e) {


    }

    }
    public static double lat;
    public static double lng;
}