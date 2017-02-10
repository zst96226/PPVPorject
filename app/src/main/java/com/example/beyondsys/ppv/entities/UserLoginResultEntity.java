package com.example.beyondsys.ppv.entities;

import java.io.Serializable;

/**
 * Created by zhsht on 2017/2/4.登录时的返回结果
 */
public class UserLoginResultEntity implements Serializable {
//    用户凭据
    public String TicketID;
//    返回结果 0:出错，1：登录成功，2：密码错误，3：账号不存在
    public int LoginResult;

    public String UserID;

    public UserLoginResultEntity(){
        this.TicketID=null;
        this.LoginResult=-1;
        this.UserID=null;
    }
}
