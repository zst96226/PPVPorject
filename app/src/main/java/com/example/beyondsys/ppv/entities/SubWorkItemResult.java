package com.example.beyondsys.ppv.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by s on 2017/2/16.
 */
public class SubWorkItemResult implements Serializable {
    public SubWorkItemResult() {
        subItem = null;
        AccessResult = 0;
    }

    public List<SubWorkItemParams> subItem;
    public int AccessResult;
}
