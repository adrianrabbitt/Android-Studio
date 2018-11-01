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

public class SendPersonal extends AsyncTask<String, Void, String> {



    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... arg0) {

        String name = arg0[0];
        String address = arg0[1];
        String number = arg0[2];


        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try
        {
            data = "?name=" + URLEncoder.encode(name, "UTF-8");
            data += "&address=" + URLEncoder.encode(address, "UTF-8");
            data += "&number=" + URLEncoder.encode(number, "UTF-8");

            link = "http://persontracker.000webhostapp.com/insertPersonal.php" + data;
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            return result;
        }
        catch (Exception e)
        {
            return new String("Exception: " + e.getMessage());
        }
    }


}