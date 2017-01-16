package com.example.beyondsys.ppv.tools;

import android.os.Environment;

import java.io.File;

/**
 * Created by zhsht on 2017/1/16.工具类
 */
public class Tools {

//    获取SD卡根目录
    public static String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if(sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
            //E//storage/emulated/0
        }
        return sdDir.toString();
    }
//  判断文件是否存在
    public static boolean fileIsExists(String strFile) {
        try
        {
            File f=new File(strFile);
            if(!f.exists())
            {
                return false;
            }

        }
        catch (Exception e)
        {
            return false;
        }

        return true;
    }
}
