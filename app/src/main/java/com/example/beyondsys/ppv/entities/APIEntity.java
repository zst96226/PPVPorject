package com.example.beyondsys.ppv.entities;

/**
 * Created by zhsht on 2017/2/5.API类
 */
public class APIEntity {
    /*通用API地址*/
    public static String APIURL="";
    /*登录API*/
    public static String LoginAPI = "";
    /*获取标识API*/
    public static String IdentifyingAPI="";

    /*webServer命名空间*/
    public static final String NAME_SPACE ="http://sysmagic.com.cn/";

    /*调用的方法*/
    public static final String METHOD_NAME="ActionCommand";

    /*WSDL文档中的URL*/;
    public static final String WSDL_URL  = "http://120.26.37.247:8181/WSPPVService.asmx?wsdl";

    /*登录接口标识*/
    public static final int LOGIN=100;
    /*获取标识接口标识*/
    public static final int GETLOGO=8;
}
