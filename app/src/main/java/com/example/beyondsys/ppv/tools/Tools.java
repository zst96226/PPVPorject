package com.example.beyondsys.ppv.tools;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by zhsht on 2017/1/16.工具类
 */
public class Tools {

//  获取SD卡根目录
    public static String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        Log.e("SD卡是否存在"+sdCardExist,"123");
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
    /*GUID号生成器*/
    public String GetGUID()
    {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
