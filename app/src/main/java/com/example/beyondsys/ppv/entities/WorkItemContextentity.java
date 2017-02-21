package com.example.beyondsys.ppv.entities;

import java.io.Serializable;
import java.nio.channels.SeekableByteChannel;
import java.util.List;

/**
 * Created by wangb on 2017/2/21.
 */
public class WorkItemContextentity implements Serializable {
    public  WorkItemContextentity()
    {
        ScoredetailsList = null;
        AccessResult = -3;
    }

    public List<WorkDetailResult> ScoredetailsList ;
    public int AccessResult;
}
