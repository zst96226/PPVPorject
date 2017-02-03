package com.example.beyondsys.ppv.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.tools.SelectPicPopup;
import com.example.beyondsys.ppv.tools.TakePhotoPopWin;
import com.example.beyondsys.ppv.tools.Tools;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class PersonInfo extends AppCompatActivity {
    private LinearLayout infoModify;
    private  ImageView back;
    private ImageView modifyImg,myImg;
    private RelativeLayout myImgLayout,myNameLayout,myPhoneLayout,myEmailLayout,myIDlayout,myAdressLayout,myDesLayout;
    private EditText myNameEdt,myPhoneEdt,myEmailEdt,myIDEdt,myAdressEdt,myDesEdt;
    private boolean editFlag=false;

    private  String IMAGE_FILE_LOCATION = Tools.getSDPath()+"/ppvimg/*.img";
    Uri imageUri = Uri.parse(IMAGE_FILE_LOCATION);

    private static final String IMAGE_UNSPECIFIED = "image/*";
    private static final int ALBUM_REQUEST_CODE = 1;
    private static final int CAMERA_REQUEST_CODE = 2;
    private static final int CROP_REQUEST_CODE = 4;
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
    private void initData()
    {
        infoModify=(LinearLayout)findViewById(R.id.infoModify);
        back = (ImageView) this.findViewById(R.id.dttail_back);
        myImgLayout=(RelativeLayout)findViewById(R.id.myImg_layout);
        myNameLayout=(RelativeLayout)findViewById(R.id.myName_layout);
        myPhoneLayout=(RelativeLayout)findViewById(R.id.myPhone_layout);
        myEmailLayout=(RelativeLayout)findViewById(R.id.myEmail_layout);
        myIDlayout=(RelativeLayout)findViewById(R.id.myID_layout);
        myAdressLayout=(RelativeLayout)findViewById(R.id.myAddress_layout);
        myDesLayout=(RelativeLayout)findViewById(R.id.myDes_layout);
        myImg=(ImageView)findViewById(R.id.myImg_img);
        myNameEdt=(EditText)findViewById(R.id.myName_edt);
        myPhoneEdt=(EditText)findViewById(R.id.myPhone_edt);
        myEmailEdt=(EditText)findViewById(R.id.myEmail_edt);
        myIDEdt=(EditText)findViewById(R.id.myID_edt);
        myAdressEdt=(EditText)findViewById(R.id.myAddress_edt);
        myDesEdt=(EditText)findViewById(R.id.myDes_edt);
    }
    public void isModify(View v)
    {
        modifyImg=(ImageView)findViewById(R.id.infoModify_img);
        if(!editFlag)
        {
            modifyImg.setImageResource(R.drawable.img_del);
            myNameEdt.setEnabled(true);
            myPhoneEdt.setEnabled(true);
            myEmailEdt.setEnabled(true);
            myIDEdt.setEnabled(true);
            myAdressEdt.setEnabled(true);
            myDesEdt.setEnabled(true);
            editFlag=true;
        }
        else
        {
            if(TextUtils.isEmpty(myNameEdt.getText())||TextUtils.isEmpty(myPhoneEdt.getText())||TextUtils.isEmpty(myEmailEdt.getText()) || TextUtils.isEmpty(myIDEdt.getText()))
            {
                AlertDialog.Builder builder  = new AlertDialog.Builder(PersonInfo.this);
                builder.setTitle("输入无效") ;
                builder.setMessage("姓名，电话，邮箱，身份证号不能为空！") ;
                builder.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editFlag = false;
                        isModify(myImgLayout);
                    }
                });
                builder.show();
            }
            myNameEdt.setEnabled(false);
            myPhoneEdt.setEnabled(false);
            myEmailEdt.setEnabled(false);
            myIDEdt.setEnabled(false);
            myAdressEdt.setEnabled(false);
            myDesEdt.setEnabled(false);
            editFlag=false;
            //数据库保存操作
            modifyImg.setImageResource(R.drawable.img_pro);
        }
    }
    public  void selectImg(View v)
    {
        if(!editFlag)
        {
            return;
        }
        TakePhotoPopWin takePhotoPopWin = new TakePhotoPopWin(this, onClickListener);
        //showAtLocation(View parent, int gravity, int x, int y)
        takePhotoPopWin.showAtLocation(findViewById(R.id.person_info), Gravity.CENTER, 0, 0);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_take_photo:
                    //调用相机拍照
                    Intent intent_take = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent_take.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.
                            getExternalStorageDirectory(), "temp.jpg")));
                    startActivityForResult(intent_take, CAMERA_REQUEST_CODE);
                    break;
                case R.id.btn_pick_photo:
                    //调用系统相册
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
                    startActivityForResult(intent, ALBUM_REQUEST_CODE);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ALBUM_REQUEST_CODE:
                if (data == null) {
                    return;
                }
                startCrop(data.getData());
                break;
            case CAMERA_REQUEST_CODE:
                File picture = new File(Environment.getExternalStorageDirectory()
                        + "/temp.jpg");
                startCrop(Uri.fromFile(picture));
                break;
            case CROP_REQUEST_CODE:
                Log.e("裁剪完成", "123");
                if (data == null) {
                    // TODO 如果之前以后有设置过显示之前设置的图片 否则显示默认的图片
                    return;
                }
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0-100)压缩文件
                    myImg.setImageBitmap(photo); //把图片显示在ImageView控件上
                }
                break;
            default:
                break;
        }

    }

    private void startCrop(Uri uri) {
        Log.e("开始裁剪","123");

        Intent intent = new Intent("com.android.camera.action.CROP"); //调用Android系统自带的一个图片剪裁页面,
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");//进行修剪
        // aspectX aspectY 是宽高的比例
        if(android.os.Build.MODEL.contains("HUAWEI"))
        {
            //华为特殊处理 不然会显示圆
            intent.putExtra("aspectX", 9998);
            intent.putExtra("aspectY", 9999);
        }
        else
        {
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
        }
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 500);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }
}
