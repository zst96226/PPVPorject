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
        Creater = "";
        CreaterID ="";
        CreateTime = "";
        ModifierID = "";
        ModifierName = "";
        ModifyTime = "";
        Status = 0;
        Deadline = "";
        Description = "";
        WorkName = "";
        AccessResult = -3;
        BusinessValue = 0.00;
        TheTimeStamp = 0;
        Category = 0;
        FID="";
        Belong2ID="";
        Belong2Name="";
        Remark="";
        BID="";
        ID="";
        HardScale=0.00;
        BasicScore=0.00;
        CheckedScore=0.00;
         RID="";
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
    public  String ID;
    public double HardScale;
    public  double BasicScore;
    public  double CheckedScore;
    public  String RID;

}
