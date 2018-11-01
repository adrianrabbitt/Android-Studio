package com.example.adrian.firebase;

import android.graphics.Bitmap;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Adrian on 02/02/2018.
 */

public class MyTarget extends SimpleTarget<Bitmap> {

    private final LatLng latLng;
    private final GoogleMap googleMap;
    private Marker mapMarker;

    public MyTarget(LatLng latLng, GoogleMap googleMap) {
        this.latLng = latLng;
        this.googleMap = googleMap;
    }

    @Override
    public void onResourceReady(final Bitmap resource, final GlideAnimation<? super Bitmap> glideAnimation) {
        // use your `latLng`
       mapMarker = googleMap.addMarker(new MarkerOptions().position(latLng).title("sss"));

        int height = 120;
        int width = 120;
        Bitmap smallMarker = Bitmap.createScaledBitmap(resource, width, height, false);
        mapMarker.setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker));
    }
}