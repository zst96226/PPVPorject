package com.example.beyondsys.ppv.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by s on 2017/2/16.
 */
public class SubWorkItemParams implements Serializable {
    public SubWorkItemParams()
    {
        WorkName =  null;
        WorkType = 0;
        WorkID =  null;
        Score = 0;
        StartTime =  null;
        EndTime = null;
        Status = 0;
        TheTimeStamp = 0;
        FID = null;
    }

    public String WorkName;
    public int WorkType;
    public String WorkID;
    public double Score;
    public Date StartTime;
    public Date EndTime;
    public int Status;
    public String FID;
    public int TheTimeStamp;
}
