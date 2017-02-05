package com.example.beyondsys.ppv.entities;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * Created by yhp on 2017/1/23.
 */
public class ValueDetailEntity implements Serializable {
    public String BID;//归属ID
    public String ID;//本身ID
    public String FID;//父工作项ID
    public String Name;//名称
    public int Status;//状态
    public String Assigned2;//指派人
    public Date ClosingTime;//结束时间
    public double BusinessValue;//理想价值
    public double TrueValue;//实际价值

    public ValueDetailEntity()
    {
        this.BID=null;
        this.ID=null;
        this.FID=null;
        this.Name=null;
        this.Status=0;
        this.Assigned2=null;
        this.ClosingTime=null;
        this.BusinessValue=0.00;
        this.TrueValue=0.00;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("ValueDetailEntity{");
        sb.append("BID").append("BID");
        sb.append(",ID").append("ID");
        sb.append(",FID").append("FID");
        sb.append(",Name").append("Name");
        sb.append(",Status").append("Status");
        sb.append(",Assigned2").append("Assigned2");
        sb.append(",ClosingTime").append("ClosingTime");
        sb.append(",BusinessValue").append("BusinessValue");
        sb.append(",TrueValue").append("TrueValue");
        sb.append("}");
        return sb.toString();
    }
}
