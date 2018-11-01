package com.example.adrian.firebase;

/**
 * Created by Adrian on 25/01/2018.
 */

public class Student {


    private String sho;
    private String title1;
    private String fName;
    private String sName;
    private String sex;
    private String job;
    private int day;
    private int month;
    private int year;
    private String emailID;
    private String mobile;
    private String phone;
    private String iaddress1;
    private String iaddress2;
    private String info;
    private String ptitle;
    private String pdes;
    private String skill;
    private String bio;
    private double lat;
    private double lng;
    private String current;
    private String dist;
    private String age;



    public Student(String title, String fName, String sName,String sex, String job,int day,int month,int year,String email,String mobile,String phone,String address1,String address2, String info,String previous,String previous1,String skill, double lat, double lng,String bio , String current,String sho) {
        this.title1 = title;
        this.fName = fName;
        this.sName = sName;
        this.sex = sex;
        this.job = job;
        this.day= day;
        this.month = month;
        this.year = year;
        this.emailID= email;
        this.mobile = mobile;
        this.phone= phone;
        this.iaddress1= address1;
        this.iaddress2 = address2;
        this.info= info;
        this.ptitle = previous;
        this.pdes = previous1;
        this.lat = lat;
        this.lng = lng;
        this.skill = skill;
        this.bio = bio;
        this.current = current;
        this.sho = sho;
    }



    public String getSho() {
        return sho;
    }

    public String getTitle1() {
        return title1;
    }
    public String getdis(){return dist;}
    public String getfName() {
        return fName;
    }
    public String getsName() {
        return sName;
    }
    public String getSex() {
        return sex;
    }
    public String getJob() {return job;}
    public int getDay() {return day;}
    public int getMonth() {return month;}
    public int getYear() {return year;}
    public String getEmailID() {return emailID;}
    public String getMobile() {return mobile;}
    public String getPhone() {
        return phone;
    }
    public String getIAddress1() {return iaddress1;}
    public String getIAddress2() {return iaddress2;}
    public String getInfo() {return info;}
    public String getPtitle() {return ptitle;}
    public String getSkill() {return skill;}
    public String getPdes() {return pdes;}
    public double getlat() {return lat;}
    public double getlng(){return lng;}
    public String getBio() {
        return bio;
    }
    public String getCurrent() {
        return current;
    }
    public String getAge(){return age;}
    public Student() {
    }
}
