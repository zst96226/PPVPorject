package com.example.beyondsys.ppv.bussiness;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.entities.APIEntity;
import com.example.beyondsys.ppv.entities.ThreadAndHandlerLabel;
import com.example.beyondsys.ppv.entities.UserInTeam;
import com.example.beyondsys.ppv.tools.AsyncBitmapLoader;
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
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by zhsht on 2017/2/7.上传头像
 */
public class ImgBusiness
{
    /*上传头像*/
    public void uploadImg(final Handler handler,final String img ,final String imgname){
        new Thread() {
            public void run() {
                /*根据命名空间和方法得到SoapObject对象*/
                SoapObject soapObject = new SoapObject(APIEntity.NAME_SPACE, APIEntity.METHOD_IMG_NAME);
                soapObject.addProperty("fileName", imgname+".png");
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
    public void downloadImg(final String picurl,final String name,final File fileDir) {
        new Thread() {
            public void run() {
//                File fileDir;
//              String path = Environment.getExternalStorageDirectory() + "/listviewImg/";// 文件目录
//                /**
//                 * 文件目录如果不存在，则创建
//                 */
//                fileDir = new File(path);
//                if (!fileDir.exists()) {
//                    fileDir.mkdirs();
//                }
                FileOutputStream fos = null;
                InputStream in = null;

                // 创建文件
                File file = new File(fileDir, name);
                Log.i("creat", "qq");
                try {

                    fos = new FileOutputStream(file);

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


/* 返回bitmap   ImageName 为  人员ID.png */
    public Bitmap setImg(String ImageName) {
        File fileDir;
        Bitmap bitmap = null;
        File file;
        // Drawable drawable=null;
        String path = Environment.getExternalStorageDirectory() + "/listviewImg/";// 文件目录
        fileDir = new File(path);
        if (!fileDir.exists()) {
            Log.i("exit", "qq");
            fileDir.mkdirs();
        }
        String picurl = APIEntity.ImagePath + ImageName;
        file = new File(fileDir, ImageName);
        if (!file.exists()) {// 如果本地图片不存在则从网上下载
            ImgBusiness imgBusiness = new ImgBusiness();
            imgBusiness.downloadImg(picurl, ImageName,fileDir);
        } else {
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            // drawable =new BitmapDrawable(bitmap);
        }
        return bitmap;
    }

    private AsyncBitmapLoader asyncBitmapLoader;
    public  void  aa(){
//
//          String imageURL="http://s.ata.net.cn/4f98db46908987a21a000003/logo/2012/04/114_80aaf295c083d07a496743699aac3193.png";
//    Bitmap bitmap=asyncBitmapLoader.loadBitmap(image, imageURL, new AsyncBitmapLoader.ImageCallBack() {
//
//        @Override
//        public void imageLoad(ImageView imageView, Bitmap bitmap) {
//            // TODO Auto-generated method stub
//            imageView.setImageBitmap(bitmap);
//        }
//    });
//    if(bitmap == null)
//    {
//        image.setImageResource(R.drawable.ic_launcher);
//    }
//    else
//    {
//        image.setImageBitmap(bitmap);
//    }
//
//    return convertView;


}
    public  void getAllUserImg(List<UserInTeam> teamUsers){
      if(teamUsers!=null&&teamUsers.size()!=0)
      {
          File fileDir;
          File file;
          String path = Environment.getExternalStorageDirectory() + "/listviewImg/";// 文件目录
          fileDir = new File(path);
          if (!fileDir.exists()) {
              Log.i("exit", "qq");
              fileDir.mkdirs();
          }
          for (UserInTeam user:teamUsers)
          {
              String ImgName=user.UserID+".png";
              String picurl = APIEntity.ImagePath + ImgName;
              file = new File(fileDir,ImgName);
              if (!file.exists()) {// 如果本地图片不存在则从网上下载
                  ImgBusiness imgBusiness = new ImgBusiness();
                  imgBusiness.downloadImg(picurl, ImgName,fileDir);
              }
          }
//
      }
    }
}
