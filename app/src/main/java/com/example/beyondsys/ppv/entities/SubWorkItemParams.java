package com.example.beyondsys.ppv.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by s on 2017/2/16.
 */
public class SubWorkItemParams implements Serializable {
    public SubWorkItemParams()
    {
        WorkName =  "";
        WorkType = 0;
        WorkID =   "";
        Score = 0;
        StartTime =   "";
        EndTime =   "";
        Status = 0;
        TheTimeStamp = 0;
        FID =   "";
    }

    public String WorkName;
    public int WorkType;
    public String WorkID;
    public double Score;
    public String StartTime;
    public String EndTime;
    public int Status;
    public String FID;
    public int TheTimeStamp;
}
