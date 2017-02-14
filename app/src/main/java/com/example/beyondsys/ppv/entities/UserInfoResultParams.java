package com.example.beyondsys.ppv.entities;

import java.io.Serializable;

/**
 * Created by s on 2017/2/14.
 */
public class UserInfoResultParams implements Serializable {
    public UserInfoResultParams()
    {
        UserID = null;
        IMGTarget = null;
        TotalScore = 0;
        TotalMonth = 0;
        Name = null;
        EMail = null;
        Tel = null;
        IDNo = null;
        Address =null;
        Sign = null;
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
