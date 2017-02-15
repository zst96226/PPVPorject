package com.example.beyondsys.ppv.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by s on 2017/2/14.
 */
public class IdentifyResult implements Serializable {
    public IdentifyResult()
    {
        Team = null;
        AccessResult = -3;
        UID = null;
    }

    public Object  Team;
    public int AccessResult;
    public String UID;
}
