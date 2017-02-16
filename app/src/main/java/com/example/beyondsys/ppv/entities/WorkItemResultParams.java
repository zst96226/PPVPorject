package com.example.beyondsys.ppv.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by s on 2017/2/13.
 */
public   class WorkItemResultParams implements Serializable {
    public WorkItemResultParams() {
        WorkName = null;
        Category = 0;
        WorkID = null;
        Workscore = 0;
        StartTime = null;
        EndTime = null;
        Status = 0;
        BID=null;
        FID=null;
    }

    public String BID;
    public String FID;
    public String WorkName;
    public int Category;
    public String WorkID;
    public double Workscore;
    public String StartTime;
    public String EndTime;
    public int Status;
}