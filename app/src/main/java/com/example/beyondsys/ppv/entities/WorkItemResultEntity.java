package com.example.beyondsys.ppv.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by s on 2017/2/13.
 */
public class WorkItemResultEntity implements Serializable {
    public String WorkItemList;
    public int AccessResult;
    public WorkItemResultEntity()
    {
        WorkItemList = null;
        AccessResult = -3;
    }

}