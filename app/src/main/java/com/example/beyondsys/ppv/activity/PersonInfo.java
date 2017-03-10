package com.example.beyondsys.ppv.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.anupcowkur.reservoir.Reservoir;
import com.anupcowkur.reservoir.ReservoirPutCallback;
import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.bussiness.ImgBusiness;
import com.example.beyondsys.ppv.bussiness.OneSelfBusiness;
import com.example.beyondsys.ppv.bussiness.OtherBusiness;
import com.example.beyondsys.ppv.dataaccess.ACache;
import com.example.beyondsys.ppv.entities.LocalDataLabel;
import com.example.beyondsys.ppv.entities.PersonInfoEntity;
import com.example.beyondsys.ppv.entities.SubmitInfoResult;
import com.example.beyondsys.ppv.entities.TeamEntity;
import com.example.beyondsys.ppv.entities.ThreadAndHandlerLabel;
import com.example.beyondsys.ppv.entities.UIDEntity;
import com.example.beyondsys.ppv.entities.UserInTeamResult;
import com.example.beyondsys.ppv.entities.UserInfoResultParams;
import com.example.beyondsys.ppv.tools.GsonUtil;
import com.example.beyondsys.ppv.tools.JsonEntity;
import com.example.beyondsys.ppv.tools.ValidaService;
import com.example.beyondsys.ppv.tools.TakePhotoPopWin;
import com.example.beyondsys.ppv.tools.Tools;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class PersonInfo extends AppCompatActivity {
    /*本地缓存操作对象*/
    ACache mCache = null;
    /*裁剪后的图像*/
    private Bitmap bitmap = null;
    private LinearLayout infoModify,back;
    private ImageView modifyImg, myImg;
    private RelativeLayout myImgLayout, myNameLayout, myPhoneLayout, myEmailLayout, myIDlayout, myAdressLayout, myDesLayout;
    private EditText myNameEdt, myPhoneEdt, myEmailEdt, myIDEdt, myAdressEdt, myDesEdt;
    private boolean editFlag = false;
    private    TakePhotoPopWin takePhotoPopWin;
    private String IMAGE_FILE_LOCATION = Tools.getSDPath() + File.separator + "photo.jpeg";
    File file;
    private UserInfoResultParams personInfoEntity, newInfo;
    private UIDEntity uidEntity;
    private  ImgBusiness imgBusiness;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == ThreadAndHandlerLabel.UploadImg) {
                Log.i("上传图片返回值：" + msg.obj, "FHZ");
                String  jsonStr=msg.obj.toString();
                if (!msg.obj.toString().equals("") && msg.obj!=null&& !jsonStr.equals("anyType{}"))
                {
                    Toast.makeText(PersonInfo.this, "修改成功", Toast.LENGTH_SHORT).show();
                    //删除原图片保存新图
                    deleteFile();
                    String imgname=uidEntity.UID.toString()+".png";
                    Log.i("imgname:"+imgname,"FHZ");

                    imgBusiness.setImg(imgname);
                }
                else
                {
                    Toast.makeText(PersonInfo.this, "修改失败", Toast.LENGTH_SHORT).show();
                }
            }else if(msg.what==ThreadAndHandlerLabel.OneselfInf){
                String  jsonStr=msg.obj.toString();
                 if(msg.obj!=null&&!jsonStr.equals("anyType{}"))
                 {
                     Log.i("修改个人信息返回值：" + msg.obj, "FHZ");
                   //  SubmitInfoResult result= JsonEntity.ParseJsonForSubmitResult(msg.obj.toString());
//                     if(result!=null)
//                     {
                               int flag=Integer.parseInt(jsonStr);
                               if(flag==0)
                              {
                                     Log.i("修改个人信息返回值：成功" , "FHZ");
                                     Toast.makeText(PersonInfo.this, "修改成功", Toast.LENGTH_SHORT).show();
                                  GetAllUserForTeam();
//                                     String json= GsonUtil.t2Json2(newInfo);
//                                     mCache.put(LocalDataLabel.CurPerson,json);
                                  Reservoir.putAsync(LocalDataLabel.CurPerson, newInfo, new ReservoirPutCallback() {
                                      @Override
                                      public void onSuccess() {
                                          if(bitmap!=null)
                                          {
                                              if(uidEntity!=null)
                                              {
                                                   /*上传头像，不在这个位置，以后会改*/
                                                  ImgBusiness uploadImg = new ImgBusiness();
                                                  uploadImg.uploadImg(handler, Tools.bitmap2Base64(bitmap),uidEntity.UID);
                                              }

                                          }
                                      }

                                      @Override
                                      public void onFailure(Exception e) {

                                      }
                                  });

                              }
                              else{
                                     Toast.makeText(PersonInfo.this, "修改失败", Toast.LENGTH_SHORT).show();
                              }
//                     }
    //                 int flag=Integer.parseInt(msg.obj.toString().trim());
    //                 if(flag==0)
    //                 {
    //                     Toast.makeText(PersonInfo.this, "修改成功", Toast.LENGTH_SHORT).show();
    //                 }
    //                 else
    //                 {
    //                     Toast.makeText(PersonInfo.this, "修改失败", Toast.LENGTH_SHORT).show();
    //                 }
                 }
            }else if(msg.what==ThreadAndHandlerLabel.GetOneSelf){
                String  jsonStr=msg.obj.toString();
                 if(msg.obj!=null&&!jsonStr.equals("anyType{}"))
                 {
                     Log.i("获取当前用户信息返回值："+msg.obj,"FHZ");

                     try{
                         UserInfoResultParams userInfoResultParams=JsonEntity.ParseJsonForUserInfoResult(jsonStr);
                         if(userInfoResultParams!=null)
                         {
                             Log.i("获取当前用户信息返回值：保存"+userInfoResultParams.UserID,"FHZ");
                             Reservoir.putAsync(LocalDataLabel.CurPerson, userInfoResultParams, new ReservoirPutCallback() {
                                 @Override
                                 public void onSuccess() {
                                     setData();
                                 }

                                 @Override
                                 public void onFailure(Exception e) {

                                 }
                             });
                         }
//                        PersonInfoEntity personInfoEntity= JsonEntity.ParseJsonForPerson(jsonStr);
//                        String curPerson= GsonUtil.t2Json2(personInfoEntity);
//                        mCache.put(LocalDataLabel.CurPerson,curPerson);
                     }catch (Exception e)
                     {
                         Log.i("获取当前用户信息返回值：异常","FHZ");
                     }
                 }else {
                     Toast.makeText(PersonInfo.this,"服务端验证出错，请联系管理员",Toast.LENGTH_SHORT).show();
                 }

            }else  if (msg.what == ThreadAndHandlerLabel.GetAllStaff) {
                if (msg.obj != null) {
                    final String jsonStr = msg.obj.toString();
                    System.out.print("全部人员："+jsonStr);
                    /*解析Json*/
                    try {
                        UserInTeamResult result = JsonEntity.ParseJsonForUserInTeamResult(jsonStr);
                        if (result != null) {
                            if (result.AccessResult == 0) {
                                Reservoir.putAsync(LocalDataLabel.AllUserInTeam, result.teamUsers, new ReservoirPutCallback() {
                                    @Override
                                    public void onSuccess() {
                                        Log.i("cache",jsonStr);
                                    }

                                    @Override
                                    public void onFailure(Exception e) {

                                    }
                                });
                            }
                        }
                    } catch (Exception e) {
                    }
                } else {
                    Toast.makeText(PersonInfo.this, "服务端验证出错，请联系管理员", Toast.LENGTH_SHORT).show();
                }
            }
            else if (msg.what == ThreadAndHandlerLabel.CallAPIError) {
                Toast.makeText(PersonInfo.this, "修改失败，请检查网络连接", Toast.LENGTH_SHORT).show();
            }else if (msg.what == ThreadAndHandlerLabel.LocalNotdata) {
                Toast.makeText(PersonInfo.this, "读取缓存失败，请检查内存重新登录", Toast.LENGTH_SHORT).show();
                /*清除其余活动中Activity以及全部缓存显示登录界面*/
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Reservoir.init(PersonInfo.this, 4096);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        //设置statusbar的图标颜色高亮反转
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        //设置statusbar的颜色
        getWindow().setStatusBarColor(Color.parseColor("#000000"));
        setContentView(R.layout.activity_person_info);
        init();
       // initCache();
        setData();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        infoModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isModify(v);
            }
        });

    }

    private void init() {
        imgBusiness=new ImgBusiness();
        infoModify = (LinearLayout) findViewById(R.id.infoModify);
        back = (LinearLayout) this.findViewById(R.id.dttail_back);
        myImgLayout = (RelativeLayout) findViewById(R.id.myImg_layout);
        myNameLayout = (RelativeLayout) findViewById(R.id.myName_layout);
        myPhoneLayout = (RelativeLayout) findViewById(R.id.myPhone_layout);
        myEmailLayout = (RelativeLayout) findViewById(R.id.myEmail_layout);
        myIDlayout = (RelativeLayout) findViewById(R.id.myID_layout);
        myAdressLayout = (RelativeLayout) findViewById(R.id.myAddress_layout);
        myDesLayout = (RelativeLayout) findViewById(R.id.myDes_layout);
        myImg = (ImageView) findViewById(R.id.myImg_img);
        myNameEdt = (EditText) findViewById(R.id.myName_edt);
        myPhoneEdt = (EditText) findViewById(R.id.myPhone_edt);
        myEmailEdt = (EditText) findViewById(R.id.myEmail_edt);
        myIDEdt = (EditText) findViewById(R.id.myID_edt);
        myAdressEdt = (EditText) findViewById(R.id.myAddress_edt);
        myDesEdt = (EditText) findViewById(R.id.myDes_edt);
        //实际上要从缓存或服务器中获取
//        personInfoEntity=new PersonInfoEntity();
//        personInfoEntity.BID="BID";
//        personInfoEntity.ID="ID";
        try {
            if (Reservoir.contains(LocalDataLabel.UserID)) {
                uidEntity = Reservoir.get(LocalDataLabel.UserID, UIDEntity.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  void initCache() {

    }

    private  void setData() {
//    personInfoEntity=(UserInfoResultParams)mCache.getAsObject(LocalDataLabel.CurPerson);
//    if(personInfoEntity!=null)
//    {
//        //个人头像未完成
//        setImg(myImg);
//     //   myImg.setImageResource(R.drawable.person);
//        myNameEdt.setText(personInfoEntity.Name);
//        myPhoneEdt.setText(personInfoEntity.Tel);
//        myEmailEdt.setText(personInfoEntity.EMail);
//        myAdressEdt.setText(personInfoEntity.Address);
//        myIDEdt.setText(personInfoEntity.IDNo);
//        myDesEdt.setText(personInfoEntity.Sign);
//    }
    boolean isCache=setCache();
    if(!isCache)
    {
        setService();
    }
}

    public   void GetAllUserForTeam() {
        List<TeamEntity> label = null;
        try {
            if (Reservoir.contains(LocalDataLabel.Label)) {
                Type resultType = new TypeToken<List<TeamEntity>>() {
                }.getType();
                label = Reservoir.get(LocalDataLabel.Label, resultType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (label != null) {
            String TeamID="";
            if(label.size()>0)
            {
                TeamID= label.get(0).TeamID;
            }
            OtherBusiness other = new OtherBusiness();
            other.GetAllStaffForTeam(handler, TeamID);
        }
    }

    private  boolean setCache()
    {
            try{
                if(Reservoir.contains(LocalDataLabel.CurPerson))
                {
                    personInfoEntity=Reservoir.get(LocalDataLabel.CurPerson,UserInfoResultParams.class);
                }

            }catch (Exception e)
            {
                e.printStackTrace();
            }
            if(personInfoEntity!=null)
            {
                Log.i("personInfoEntity","FHZ");
             //   myImg.setImageResource(R.drawable.person);
                myNameEdt.setText(personInfoEntity.Name);
                myPhoneEdt.setText(personInfoEntity.Tel);
                myEmailEdt.setText(personInfoEntity.EMail);
                myAdressEdt.setText(personInfoEntity.Address);
                myIDEdt.setText(personInfoEntity.IDNo);
                myDesEdt.setText(personInfoEntity.Sign);
                //个人头像
                String imgname=uidEntity.UID+".png";
                Log.i("imgname"+imgname,"FHZ");
                Bitmap userBitmap=imgBusiness.setImg(imgname);
                myImg.setImageBitmap(userBitmap);
                return  true;
            }
            return  false;
    }
    private  void setService()
    {
        //缓存中未获取到用户个人信息 从服务加载
        Log.i("缓存中未获取到用户个人信息 从服务加载" , "FHZ");
        OneSelfBusiness oneSelfBusiness=new OneSelfBusiness();
        oneSelfBusiness.GetOneSelf(handler);
    }
//    private Bitmap setImg(String id)
//    {
//        File fileDir;
//        Bitmap bitmap=null;
//        // Drawable drawable=null;
//        String path = Environment.getExternalStorageDirectory()
//                + "/listviewImg/";// 文件目录
//        fileDir = new File(path);
//        if (!fileDir.exists()) {
//            Log.i("exit","qq");
//            fileDir.mkdirs();
//        }
//        String picurl="http://120.26.37.247:8181/File/"+id+".png";
//        String      name=id+".png";
//        file = new File(fileDir, name);
//        if (!file.exists())
//        {// 如果本地图片不存在则从网上下载
//            Log.i("wwwwwwwww","qq");
//            ImgBusiness imgBusiness=new ImgBusiness();
//            imgBusiness.downloadImg(picurl,name);
//            Log.i("end", "qq");
//        } else {// 图片存在则填充到view上
//            Log.i("ttttt", "qq");
//            bitmap = BitmapFactory
//                    .decodeFile(file.getAbsolutePath());
//            // drawable =new BitmapDrawable(bitmap);
//        }
//        return bitmap;
//    }
    public void isModify(View v) {
        modifyImg = (ImageView) findViewById(R.id.infoModify_img);
        if (!editFlag) {
            modifyImg.setImageResource(R.drawable.img_del);
            myNameEdt.setEnabled(true);
            myPhoneEdt.setEnabled(true);
            myEmailEdt.setEnabled(true);
            myIDEdt.setEnabled(true);
            myAdressEdt.setEnabled(true);
            myDesEdt.setEnabled(true);
            editFlag = true;
        } else {
            if (TextUtils.isEmpty(myNameEdt.getText()) || TextUtils.isEmpty(myPhoneEdt.getText()) || TextUtils.isEmpty(myEmailEdt.getText()) || TextUtils.isEmpty(myIDEdt.getText())) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonInfo.this);
                builder.setTitle("输入无效");
                builder.setMessage("姓名，电话，邮箱，身份证号不能为空！");
                builder.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editFlag = false;
                        isModify(myImgLayout);
                    }
                });
                builder.show();
                return;
            }
            //验证
            boolean phoneCheck = ValidaService.isMobileOrPhone(myPhoneEdt.getText().toString());
            boolean nameCheck = ValidaService.isNameLength(myNameEdt.getText().toString());
            boolean emailCheck = ValidaService.isValidEmail(myEmailEdt.getText().toString());
            boolean idCheck = ValidaService.isValidIdCard(myIDEdt.getText().toString());
            boolean addressChek = ValidaService.isAddressLength(myAdressEdt.getText().toString());
            boolean desCheck = ValidaService.isRemarksLength(myDesEdt.getText().toString());
            Log.e(phoneCheck + "phoneCheck", "qqww");
            Log.e(nameCheck + "nameCheck", "qqww");
            Log.e(emailCheck + "emailCheck", "qqww");
            Log.e(idCheck + "idCheck", "qqww");
            Log.e(addressChek + "addressChek", "qqww");
            Log.e(desCheck + "desCheck", "qqww");
            if (!(phoneCheck && nameCheck && emailCheck && idCheck && addressChek && desCheck)) {
                Log.e("logggggggg", "qqww");
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonInfo.this);
                builder.setTitle("输入无效");
                builder.setMessage("请正确填写个人信息！");
                builder.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editFlag = false;
                        isModify(myImgLayout);
                    }
                });
                builder.show();
                return;
            }
            myNameEdt.setEnabled(false);
            myPhoneEdt.setEnabled(false);
            myEmailEdt.setEnabled(false);
            myIDEdt.setEnabled(false);
            myAdressEdt.setEnabled(false);
            myDesEdt.setEnabled(false);
            editFlag = false;
            modifyImg.setImageResource(R.drawable.passwordchange);//  android:src="@drawable/passwordchange"
            //服务器保存操作
            newInfo=new UserInfoResultParams();
           //图片未完成
           // newInfo.IMGTarget=myImg.getDrawable().toString();
            newInfo.Name=myNameEdt.getText().toString().trim();
            newInfo.Tel=myPhoneEdt.getText().toString().trim();
            newInfo.EMail=myEmailEdt.getText().toString().trim();
            newInfo.IDNo=myIDEdt.getText().toString().trim();
            newInfo.Address=myAdressEdt.getText().toString().trim();
            newInfo.Sign=myDesEdt.getText().toString().trim();
            OneSelfBusiness oneSelfBusiness=new OneSelfBusiness();
            oneSelfBusiness.ChangeOneSelf(handler, newInfo);

        }
    }

    public void selectImg(View v) {
        if (!editFlag) {
            return;
        }
         takePhotoPopWin = new TakePhotoPopWin(this, onClickListener);
        //showAtLocation(View parent, int gravity, int x, int y)
        takePhotoPopWin.showAtLocation(findViewById(R.id.person_info), Gravity.CENTER, 0, 0);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_take_photo:
                    chooseFromCamera();
                    break;
                case R.id.btn_pick_photo:
                    chooseFromGallery();
                    break;
            }
        }
    };

    /**
     * 拍照选择图片
     */
    private void chooseFromCamera() {
        //构建隐式Intent
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //调用系统相机
        startActivityForResult(intent, 1);
    }

    /**
     * 从相册选择图片
     */
    private void chooseFromGallery() {
        //构建一个内容选择的Intent
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //设置选择类型为图片类型
        intent.setType("image/*");
        //打开图片选择
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            switch (requestCode) {
                case 1:
                    //用户点击了取消
                    if (data == null) {

                        return;
                    } else {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            //获得拍的照片
                            Bitmap bm = extras.getParcelable("data");
                            //将Bitmap转化为uri
                            Uri uri = saveBitmap(bm, "temp");
                            //启动图像裁剪
                            startImageZoom(uri);
                        }
                    }
                    break;
                case 2:
                    if (data == null) {
                        return;
                    } else {
                        //用户从图库选择图片后会返回所选图片的Uri
                        Uri uri;
                        //获取到用户所选图片的Uri
                        uri = data.getData();
                        //返回的Uri为content类型的Uri,不能进行复制等操作,需要转换为文件Uri
                        uri = convertUri(uri);
                        startImageZoom(uri);
                    }
                    break;
                case 3:
                    if (data == null) {
                        return;
                    } else {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            //获取到裁剪后的图像
                            bitmap = extras.getParcelable("data");
                            myImg.setImageBitmap(bitmap);
                            takePhotoPopWin.dismiss();

                        }
                    }
                    break;
                default:
                    Log.i("default","FHZ");
                    break;
            }
        }catch (Exception e){}

    }

    /**
     * 将content类型的Uri转化为文件类型的Uri
     *
     * @param uri
     * @return
     */
    private Uri convertUri(Uri uri) {
        InputStream is;
        try {
            //Uri ----> InputStream
            is = getContentResolver().openInputStream(uri);
            //InputStream ----> Bitmap
            Bitmap bm = BitmapFactory.decodeStream(is);
            //关闭流
            is.close();
            return saveBitmap(bm, "temp");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将Bitmap写入SD卡中的一个文件中,并返回写入文件的Uri
     *
     * @param bm
     * @param dirPath
     * @return
     */
    private Uri saveBitmap(Bitmap bm, String dirPath) {
        //新建文件夹用于存放裁剪后的图片
        File tmpDir = new File(Environment.getExternalStorageDirectory() + "/" + dirPath);
        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }

        //新建文件存储裁剪后的图片
        File img = new File(tmpDir.getAbsolutePath() + "/avator.png");
        try {
            //打开文件输出流
            FileOutputStream fos = new FileOutputStream(img);
            //将bitmap压缩后写入输出流(参数依次为图片格式、图片质量和输出流)
            bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
            //刷新输出流
            fos.flush();
            //关闭输出流
            fos.close();
            //返回File类型的Uri
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 通过Uri传递图像信息以供裁剪
     *
     * @param uri
     */
    private void startImageZoom(Uri uri) {
        //构建隐式Intent来启动裁剪程序
        Intent intent = new Intent("com.android.camera.action.CROP");
        //设置数据uri和类型为图片类型
        intent.setDataAndType(uri, "image/*");
        //显示View为可裁剪的
        intent.putExtra("crop", true);
        //裁剪的宽高的比例为1:1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //输出图片的宽高均为150
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        //裁剪之后的数据是通过Intent返回
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    /**
     * 删除SD卡或者手机的缓存图片和目录
     */
    public void deleteFile() {
        String path = Environment.getExternalStorageDirectory() + "/listviewImg/"+ uidEntity.UID+".png";// 文件目录
        Log.i("INFO", path  + "========================");
        File dirFile = new File(path);
        if(! dirFile.exists()){
            return;
        }
        if (dirFile.isDirectory()) {
            String[] children = dirFile.list();
            for (int i = 0; i < children.length; i++) {
                new File(dirFile, children[i]).delete();
            }
        }

        dirFile.delete();
        Log.i("INFO",  "=========delete========");
    }
}
