package com.example.beyondsys.ppv.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangb on 2017/2/22.
 */
public class ChildWorkItemEntity implements Serializable {
    public int AccessResult;
    public List<SubWorkItemParams> WorkSubItemParams;
    public ChildWorkItemEntity(){
        AccessResult=-3;
        WorkSubItemParams=new ArrayList<>();
    }
}
