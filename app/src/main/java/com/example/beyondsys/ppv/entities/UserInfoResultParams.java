package com.example.beyondsys.ppv.entities;

import java.io.Serializable;

/**
 * Created by s on 2017/2/14.
 */
public class UserInfoResultParams implements Serializable {
    public UserInfoResultParams()
    {
        UserID = "";
        IMGTarget ="";
        TotalScore = 0;
        TotalMonth = 0;
        Name ="";
        EMail = "";
        Tel ="";
        IDNo = "";
        Address ="";
        Sign ="";
        AccessResult = -3;
    }

    public String UserID;
    public String IMGTarget;
    public double TotalScore;
    public int TotalMonth;
    public String Name;
    public String EMail;
    public String Tel;
    public String IDNo;
    public String Address;
    public String Sign ;
    public int AccessResult;
}
