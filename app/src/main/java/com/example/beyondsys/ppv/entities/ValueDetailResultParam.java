package com.example.beyondsys.ppv.entities;

import java.io.Serializable;

/**
 * Created by s on 2017/2/15.
 */
public class ValueDetailResultParam implements Serializable {
    public  ValueDetailResultParam()
    {
        BID="";
        ID="";
        WorkName ="";
        Category = 0;
        WorkID ="";
        IdealScore = 0;
        Score = 0;
        Stage=0;
        UID="";
        TimeSection="";
    }
    public  String BID;
    public  String ID;
    public String WorkName ;
    public int Category ;
    public String WorkID ;
    public double IdealScore;
    public double Score ;
    public  int Stage;
    public  String UID;
    public String TimeSection ;
}
