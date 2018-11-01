package com.example.adrian.firebase;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Adrian on 23/03/2018.
 */



    public class MapsActivityTest {
    @Test
    public void onMapReady() throws Exception {
    }

    @Test
    public void onMarkerClick() throws Exception {
    }

    @Test
    public void getdistance() throws Exception {

        double a = 1.0;
        double c = 1.0;
      MapsActivity m  =new MapsActivity();
      // ;
       assertEquals(a,m.getdistance(1.0,1.0),.1);

    }


}