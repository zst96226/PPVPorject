package com.example.beyondsys.ppv.tools;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Dictionary;
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
    public static String GetGUID()
    {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
    /*Bitmap转换成二进制*/
    public static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
    /*bitmap转base64*/
    public static String bitmap2Base64(Bitmap bm){
        String result="";
        ByteArrayOutputStream bos=null;
        try {
            if(null!=bm){
                bos=new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 90, bos);//将bitmap放入字节数组流中

                bos.flush();//将bos流缓存在内存中的数据全部输出，清空缓存
                bos.close();

                byte []bitmapByte=bos.toByteArray();

                result=Base64.encodeToString(bitmapByte, Base64.DEFAULT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null!=null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    /*base64转bitmap*/
    public static Bitmap base64ToBitmap(String base64String){
        byte[] bytes = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap bitmap=BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

    public static String getMD5(byte[] source) {
        String s = null;
        char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            md.update(source);
            byte tmp[] = md.digest();          // MD5 的计算结果是一个 128 位的长整数，
            // 用字节表示就是 16 个字节
            char str[] = new char[16 * 2];   // 每个字节用 16 进制表示的话，使用两个字符，
            // 所以表示成 16 进制需要 32 个字符
            int k = 0;                                // 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) {    // 从第一个字节开始，对 MD5 的每一个字节
                // 转换成 16 进制字符的转换
                byte byte0 = tmp[i];  // 取第 i 个字节
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];  // 取字节中高 4 位的数字转换,
                // >>> 为逻辑右移，将符号位一起右移
                str[k++] = hexDigits[byte0 & 0xf];   // 取字节中低 4 位的数字转换
            }
            s = new String(str);  // 换后的结果转换为字符串

        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

}
