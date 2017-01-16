package com.example.beyondsys.ppv.entities;

/**
 * Created by zhsht on 2017/1/12.价值实体类
 */
public class WorkValueEntity {
    private String Name;
    private String State;
    private String Score;
    private int Month;
    private String ID;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public int getMonth() {
        return Month;
    }

    public void setMonth(int month) {
        Month = month;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
