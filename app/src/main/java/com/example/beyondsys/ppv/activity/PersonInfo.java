package com.example.beyondsys.ppv.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.bussiness.ImgBusiness;
import com.example.beyondsys.ppv.dataaccess.ACache;
import com.example.beyondsys.ppv.entities.PersonInfoEntity;
import com.example.beyondsys.ppv.entities.ThreadAndHandlerLabel;
import com.example.beyondsys.ppv.tools.ValidaService;
import com.example.beyondsys.ppv.tools.TakePhotoPopWin;
import com.example.beyondsys.ppv.tools.Tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PersonInfo extends AppCompatActivity {
    /*本地缓存操作对象*/
    ACache mCache = null;
    /*裁剪后的图像*/
    private Bitmap bitmap = null;
    private LinearLayout infoModify;
    private ImageView back;
    private ImageView modifyImg, myImg;
    private RelativeLayout myImgLayout, myNameLayout, myPhoneLayout, myEmailLayout, myIDlayout, myAdressLayout, myDesLayout;
    private EditText myNameEdt, myPhoneEdt, myEmailEdt, myIDEdt, myAdressEdt, myDesEdt;
    private boolean editFlag = false;
    private    TakePhotoPopWin takePhotoPopWin;
    private String IMAGE_FILE_LOCATION = Tools.getSDPath() + File.separator + "photo.jpeg";
    private PersonInfoEntity personInfoEntity;
    private Handler threadHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == ThreadAndHandlerLabel.UploadImg) {
                Log.i("上传返回值："+msg.obj,"UPIMG");
                if (!msg.obj.toString().equals("") && msg.obj!=null)
                {
                    Toast.makeText(PersonInfo.this, "修改成功", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(PersonInfo.this, "修改失败", Toast.LENGTH_SHORT).show();
                }
            } else if (msg.what == ThreadAndHandlerLabel.CallAPIError) {
                Toast.makeText(PersonInfo.this, "修改失败，请检查网络连接", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        initData();
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

    private void initData() {
        infoModify = (LinearLayout) findViewById(R.id.infoModify);
        back = (ImageView) this.findViewById(R.id.dttail_back);
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
        personInfoEntity=new PersonInfoEntity();
        personInfoEntity.BID="BID";
        personInfoEntity.ID="ID";

    }

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
            boolean nameCheck = ValidaService.isUserName(myNameEdt.getText().toString());
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
            //数据库保存操作
            modifyImg.setImageResource(R.drawable.img_pro);
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
                        /*上传头像，不在这个位置，以后会改*/
                        ImgBusiness uploadImg = new ImgBusiness();
                        uploadImg.uploadImg(threadHandler, Tools.bitmap2Base64(bitmap));
                    }
                }
                break;
            default:
                break;
        }
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
}
