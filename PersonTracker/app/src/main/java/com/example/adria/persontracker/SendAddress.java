package com.example.adria.persontracker;

/**
 * Created by adria on 2/20/2017.
 */

import android.os.AsyncTask;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SendAddress extends AsyncTask<String,Void,Void> {

    protected void onPreExecute() {

    }

    @Override
    protected Void doInBackground(String... arg0) {

        String mobileNo = arg0[0];
        String link;
        String address;

        try
        {
            address= "?mobileNo=" + URLEncoder.encode(mobileNo, "UTF-8");
            link = "http://persontracker.000webhostapp.com/insertAdress.php" + address;
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

           con.getInputStream();

        }
        catch (Exception e)
        {

        }
        return null;
    }


}
