package com.example.beyondsys.ppv.entities;

import java.io.Serializable;

/**
 * Created by wangb on 2017/2/22
 */
public class WorkItemChildResultParams implements Serializable {
    public WorkItemChildResultParams() {
        WorkName = "";
        WorkType = 0;
        WorkID = "";
        Score = 0;
        StartTime = "";
        EndTime = "";
        Status = 0;
        TheTimeStamp = 0;
        FID ="";
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
