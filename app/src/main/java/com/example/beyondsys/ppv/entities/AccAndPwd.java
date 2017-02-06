package com.example.beyondsys.ppv.entities;

import java.io.Serializable;

/**
 * Created by zhsht on 2017/2/5.账号密码
 */
public class AccAndPwd implements Serializable{
    public String AccountName;
    public String Password;
    public AccAndPwd(String acc,String pwd){
        this.AccountName=acc;
        this.Password=pwd;
    }
}
