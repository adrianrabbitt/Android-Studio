package com.example.adria.persontracker;
import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetAddressActivity extends AsyncTask<String, String, String> {
    public static String returned;
    public static String oneh;
    public static String twoh;
    public static String threeh;
    public static String fourh;
    public static String fiveh;
    public static String sixh;
    public static String sevenh;
    public static String eighth;


    HttpURLConnection con;
    URL url;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();



    }


    @Override
    protected String doInBackground(String... params) {
        try {

            url = new URL("http://persontracker.000webhostapp.com/outputAddress.php");
            con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            InputStream input = con.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }

            return (result.toString());
        } catch (Exception e) {
            return e.toString();
        }





    }


    @Override
    protected void onPostExecute(String result) {


        returned = result.toString();

        String[] arr = returned.split("\\.");
        oneh = arr[0];
        twoh = arr[1];
        threeh = arr[2];
        fourh = arr[3];
        fiveh = arr[4];
        sixh = arr[5];
        sevenh = arr[6];
        eighth = arr[7];




        // double value = Double.parseDouble(result);
        //textPHP.setText(" " + value);





    }

}