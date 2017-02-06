package com.example.beyondsys.ppv.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhsht on 2017/2/5.团队标识
 */
public class TeamEntity implements Serializable{
    public String TeamID;
    public String TeamName;
    public String TeamLeave;

    public TeamEntity()
    {
        TeamID=null;
        TeamLeave=null;
        TeamName=null;
    }
}
