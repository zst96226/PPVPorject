package com.example.beyondsys.ppv.entities;

import java.io.Serializable;
import java.security.PrivateKey;

/**
 * Created by zhsht on 2017/1/12.工作项实体
 */
public class WorkItemEntity implements Serializable {

    public String BID;//归属ID
    public String ID;//本身ID
    public String FID;//父工作项ID
    public String RID;//关系ID
    public String Name;//名称
    public String Description;//描述
    public int Category;//类别 0:B，1：T
    public int Status;//状态
    public String Assigned2;//指定人
    public String Belong2;//留空
    public String Checker;//检查人
    public String ClosingTime;//截止时间
    public String Creater;//创建人
    public String CreateTime;//创建时间
    public String Modifier;//修改人
    public String ModifyTime;//修改时间
    public double BusinessValue;//分值
    public double BasicScore;//基本分值
    public double CheckedScore;//检查分值
    public double HardScale;//难易系数
    public String Remark;//备注

    public WorkItemEntity(){
        this.BID=null;
        this.ID=null;
        this.FID=null;
        this.RID=null;
        this.Name=null;
        this.Description=null;
        this.Category=0;
        this.Status=0;
        this.Assigned2=null;
        this.Belong2=null;
        this.Checker=null;
        this.ClosingTime=null;
        this.Creater=null;
        this.CreateTime=null;
        this.Modifier=null;
        this.ModifyTime=null;
        this.BusinessValue=0.00;
        this.BasicScore=0.00;
        this.CheckedScore=0.00;
        this.HardScale=0.00;
        this.Remark=null;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("WorkItemEntity{ ");
        sb.append("BID=").append(BID);
        sb.append(",ID=").append(ID);
        sb.append(",FID=").append(FID);
        sb.append(",RID=").append(RID);
        sb.append(",Name=").append(Name);
        sb.append(",Description=").append(Description);
        sb.append(",Category=").append(Category);
        sb.append(",Status=").append(Status);
        sb.append(",Assigned2=").append(Assigned2);
        sb.append(",Belong2=").append(Belong2);
        sb.append(",Checker=").append(Checker);
        sb.append(",ClosingTime=").append(ClosingTime);
        sb.append(",Creater=").append(Creater);
        sb.append(",CreateTime=").append(CreateTime);
        sb.append(",Modifier=").append(Modifier);
        sb.append(",ModifyTime=").append(ModifyTime);
        sb.append(",BusinessValue=").append(BusinessValue);
        sb.append(",BasicScore=").append(BasicScore);
        sb.append(",CheckedScore=").append(CheckedScore);
        sb.append(",HardScale=").append(HardScale);
        sb.append(",Remark=").append(Remark);
        return sb.toString();
    }
}
