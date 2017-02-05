package com.example.beyondsys.ppv.entities;

import java.io.Serializable;

/**
 * Created by zhsht on 2017/2/5.账号密码
 */
public class AccAndPwd implements Serializable{
    public String Acc;
    public String Pwd;
    public AccAndPwd(String acc,String pwd){
        this.Acc=acc;
        this.Pwd=pwd;
    }
}
