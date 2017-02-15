package com.example.beyondsys.ppv.entities;

import java.io.Serializable;

/**
 * Created by s on 2017/2/15.
 */
public class ValueDetailResultParam implements Serializable {
    public  ValueDetailResultParam()
    {
        WorkName = null;
        Category = 0;
        WorkID = null;
        IdealScore = 0;
        BasicScore = 0;
        CheckedScore = 0;
    }

    public String WorkName ;
    public int Category ;
    public String WorkID ;
    public double IdealScore;
    public double BasicScore ;
    public double CheckedScore ;
}
