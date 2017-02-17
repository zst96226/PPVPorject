package com.example.beyondsys.ppv.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by s on 2017/2/13.
 */
public class WorkValueResultEntity implements Serializable {
    public List<WorkValueResultParams> Score;
    public int AccessResult;

    public WorkValueResultEntity() {
        Score = null;
        AccessResult = -3;
    }
}
