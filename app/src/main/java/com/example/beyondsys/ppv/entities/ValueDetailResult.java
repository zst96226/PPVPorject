package com.example.beyondsys.ppv.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by s on 2017/2/15.
 */
public class ValueDetailResult implements Serializable{
    public  ValueDetailResult()
    {
        ScoredetailsList = null;
        AccessResult = -3;
    }

    public List<ValueDetailResultParam> ScoredetailsList ;
    public int AccessResult;
}
