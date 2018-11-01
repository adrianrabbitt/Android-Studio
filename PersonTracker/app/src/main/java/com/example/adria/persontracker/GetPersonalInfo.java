package com.example.adria.persontracker;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class GetPersonalInfo extends AsyncTask<String,Void, String> {

    HttpURLConnection con;
    URL url;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... arg0) {
        String name = arg0[0];
        String link;
        String data;


        try {
            data = "?name=" + URLEncoder.encode(name, "UTF-8");
            link = "http://persontracker.000webhostapp.com/getPersonal.php" + data;
            url = new URL(link);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setDoOutput(true);
            con.setDoInput(true);

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



    @Override
    protected void onPostExecute(String result) {

        number = result.toString();


        String[] arr = number.split("\\.");
        addressP = arr[0];
        numberP = arr[1];


    }
    public static double lat;
    public static double lng;
    public static String number;
    public static String addressP;
    public static String numberP;
}