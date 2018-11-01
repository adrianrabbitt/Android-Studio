package com.example.adrian.firebase;

/**
 * Created by Adrian on 21/03/2018.
 */


//Creating object for Image List Adapter.
public class ImageUpload {

    public String name;
    public String url;

    public String getName() {
        return name;
    }
    //get Url og image (the reference will be linking to firebase storage)
    public String getUrl() {
        return url;
    }
    //set constructor for object
    public ImageUpload(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public ImageUpload(){}


}