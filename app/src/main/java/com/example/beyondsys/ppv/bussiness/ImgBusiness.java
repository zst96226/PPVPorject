package com.example.beyondsys.ppv.bussiness;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.beyondsys.ppv.entities.APIEntity;
import com.example.beyondsys.ppv.entities.ThreadAndHandlerLabel;
import com.example.beyondsys.ppv.tools.Tools;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zhsht on 2017/2/7.上传头像
 */
public class ImgBusiness
{
    /*上传头像*/
    public void uploadImg(final Handler handler,final String img){
        new Thread() {
            public void run() {
                /*根据命名空间和方法得到SoapObject对象*/
                SoapObject soapObject = new SoapObject(APIEntity.NAME_SPACE, APIEntity.METHOD_IMG_NAME);
                soapObject.addProperty("fileName", "ppv.png");
                soapObject.addProperty("image", img);
                // 通过SOAP1.1协议得到envelop对象
                SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                // 将soapObject对象设置为envelop对象，传出消息
                envelop.bodyOut = soapObject;
                envelop.dotNet = true;
                HttpTransportSE httpSE = new HttpTransportSE(APIEntity.WSDL_URL);
                // 开始调用远程方法
                try {
                    httpSE.call(APIEntity.NAME_SPACE + APIEntity.METHOD_IMG_NAME, envelop);
                    // 得到远程方法返回的SOAP对象
                    SoapPrimitive result = (SoapPrimitive) envelop.getResponse();
                    Message msg = Message.obtain();
                    msg.what = ThreadAndHandlerLabel.UploadImg;
                    msg.obj = result;
                    handler.sendMessage(msg);
                } catch (IOException | XmlPullParserException e) {
                    e.printStackTrace();
                    Message msg = Message.obtain();
                    msg.what = ThreadAndHandlerLabel.CallAPIError;
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }
    /*下载头像*/
    public void downloadImg(final String picurl,final String name) {
        new Thread() {
            public void run() {
                File fileDir;
                String path = Environment.getExternalStorageDirectory() + "/listviewImg/";// 文件目录
                /**
                 * 文件目录如果不存在，则创建
                 */
                fileDir = new File(path);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
                FileOutputStream fos = null;
                InputStream in = null;

                // 创建文件
               // File file = new File(fileDir);
                try {

                    fos = new FileOutputStream(fileDir);

                    URL url = new URL(picurl);
                    in = url.openStream();
                    int len = -1;
                    byte[] b = new byte[1024];
                    while ((len = in.read(b)) != -1) {
                        fos.write(b, 0, len);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                        if (in != null) {
                            in.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

}
