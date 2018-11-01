package com.example.adrian.firebase;

/**
 * Created by Adrian on 22/02/2018.
 */

public class Admin {


    /**
     * Created by Adrian on 25/01/2018.
     */


    private String dist;
    private String age;
    private String sex;


    public Admin(String age, String dist, String sex) {

        this.age = age;

        this.dist = dist;
        this.sex = sex;
    }

    public Admin(String sex) {


        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public String getDist() {
        return dist;
    }

    public String getSex() {
        return sex;
    }

    public Admin() {
    }
}


