package com.example.beyondsys.ppv.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by s on 2017/2/14.
 */
public class WorkDetailResult implements Serializable {
    public WorkDetailResult()
    {
        AssignerID = null;
        AssignerName = null;
        CheckerID = null;
        CheckerName = null;
        CreateTime = null;
        ModifierID = null;
        ModifierName = null;
        ModifyTime = null;
        Status = 0;
        Deadline = null;
        Description = null;
        AccessResult = -3;
        Creater = null;
        CreaterID =null;
        WorkName = null;
        BusinessValue = 0;
        TheTimeStamp = 0;
        Category = 0;
        FID=null;
    }

    public String AssignerID ;
    public String AssignerName;
    public String  CheckerID ;
    public String  CheckerName ;
    public String  Creater ;
    public String  CreaterID ;
    public Date CreateTime ;
    public String  ModifierID ;
    public String  ModifierName ;
    public Date ModifyTime ;
    public int Status ;
    public Date Deadline;
    public String  Description ;
    public String  WorkName ;
    public int AccessResult ;
    public double BusinessValue;
    public int TheTimeStamp;
    public int Category;
    public String FID;
}
