package com.example.beyondsys.ppv.entities;

import java.security.PrivateKey;

/**
 * Created by zhsht on 2017/1/12.工作项实体
 */
public class WorkItemEntity {

    private String Name;
    private String Score;
    private String EndTime;
    private String WorkID;
    private String Index;
    private int Type;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getWorkID() {
        return WorkID;
    }

    public void setWorkID(String workID) {
        WorkID = workID;
    }

    public String getIndex() {
        return Index;
    }

    public void setIndex(String index) {
        Index = index;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }
}
