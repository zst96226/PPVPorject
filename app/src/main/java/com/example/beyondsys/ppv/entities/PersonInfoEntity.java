package com.example.beyondsys.ppv.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhsht on 2017/1/12.个人信息
*/
public class PersonInfoEntity implements Serializable {
    public  String BID;//归属ID
    public  String  ID;//本身ID
    public  String  Name;//名称
    public  String AccName;
    public  String  AccPwd;
    public  int  Status;//状态
    public  String IDNo;
    public  String Address;
    public  String Tel;
    public  String EMail;
    public  String IMGTarget;
    public  String Creater;
    public Date CreateTime;
    public  String Modifier;
    public Date ModifyTime;
    public  String Remark;
    public double ScoreCount;//总价值
    public int   MonthCount;//累计月份

    public PersonInfoEntity()
    {
        this.BID=null;
        this.ID=null;
        this.Name=null;
        this.AccName=null;
        this.AccPwd=null;
        this.Status=0;
        this.IDNo=null;
        this.Address=null;
        this.Tel=null;
        this.EMail=null;
        this.IMGTarget=null;
        this.Creater=null;
        this.CreateTime=null;
        this.Modifier=null;
        this.ModifyTime=null;
        this.Remark=null;
        this.ScoreCount=0.00;
        this.MonthCount=0;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("PersonInfoEntity{ ");
        sb.append("BID").append(BID);
        sb.append(",ID").append(ID);
        sb.append(",Name").append(Name);
        sb.append(",AccName").append(AccName);
        sb.append(",AccPwd").append(AccPwd);
        sb.append(",Status").append(Status);
        sb.append(",IDNo").append(IDNo);
        sb.append(",Address").append(Address);
        sb.append(",Tel").append(Tel);
        sb.append(",EMail").append(EMail);
        sb.append(",IMGTarget").append(IMGTarget);
        sb.append(",Creater").append(Creater);
        sb.append(",CreateTime").append(CreateTime);
        sb.append(",Modifier").append(Modifier);
        sb.append(",ModifyTime").append(ModifyTime);
        sb.append(",Remark").append(Remark);
        sb.append(",ScoreCount").append(ScoreCount);
        sb.append(",MonthCount").append(MonthCount);
        sb.append("}");
        return sb.toString();
    }
}
