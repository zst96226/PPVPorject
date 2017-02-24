package com.example.beyondsys.ppv.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by s on 2017/2/14.
 */
public class WorkDetailResult implements Serializable {
    public WorkDetailResult()
    {
        AssignerID = "";
        AssignerName = "";
        CheckerID = "";
        CheckerName = "";
        CreateTime = "";
        ModifierID = "";
        ModifierName = "";
        ModifyTime = "";
        Status = 0;
        Deadline = "";
        Description = "";
        AccessResult = -3;
        Creater = "";
        CreaterID ="";
        WorkName = "";
        BusinessValue = 0;
        TheTimeStamp = 0;
        Category = 0;
        FID="";
        BID="";
        Belong2ID="";
        Belong2Name="";
        Remark="";



    }

    public String AssignerID ;
    public String AssignerName;
    public String  CheckerID ;
    public String  CheckerName ;
    public String  Creater ;
    public String  CreaterID ;
    public String CreateTime ;
    public String  ModifierID ;
    public String  ModifierName ;
    public String ModifyTime ;
    public int Status ;
    public String Deadline;
    public String  Description ;
    public String  WorkName ;
    public int AccessResult ;
    public double BusinessValue;
    public int TheTimeStamp;
    public int Category;
    public String FID;
    public  String BID;
    public String Belong2ID;
    public String Belong2Name;
    public String Remark;

}
