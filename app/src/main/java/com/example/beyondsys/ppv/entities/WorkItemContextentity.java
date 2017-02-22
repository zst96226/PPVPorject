package com.example.beyondsys.ppv.entities;

import java.io.Serializable;
import java.nio.channels.SeekableByteChannel;
import java.util.List;

/**
 * Created by wangb on 2017/2/21
 */
public class WorkItemContextentity implements Serializable {
    public  WorkItemContextentity()
    {
        WorkDetailsOutputParams = null;
        AccessResult = -3;
    }

    public List<WorkDetailResult> WorkDetailsOutputParams ;
    public int AccessResult;
}
