package com.example.adrian.firebase;

import java.io.Serializable;

/**
 * Created by Adrian on 16/02/2018.
 */

public class Rtype implements Serializable {

    private String reg;



    public  Rtype(String name)
    {

        this.reg = name;
    }

    public Rtype(){}
    private String getJobType()
    {return reg;}

}
