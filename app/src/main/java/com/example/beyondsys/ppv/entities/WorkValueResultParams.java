package com.example.beyondsys.ppv.entities;

import java.io.Serializable;

/**
 * Created by s on 2017/2/13.
 */
public class WorkValueResultParams implements Serializable {
    public  WorkValueResultParams()
    {
        IMGTarget = null;
        Name=null;
        UserID = null;
        AccumulateScore = 0;
        Month = 0;
    }

    public String IMGTarget;
    public String Name ;
    public String UserID;
    public double AccumulateScore ;
    public int Month;
}
