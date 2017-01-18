package com.example.beyondsys.ppv.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.tools.SelectPicPopup;

public class PersonInfo extends AppCompatActivity {
private  ImageView back;
    private ImageView modifyImg,myImg;
    private LinearLayout modifyLayout;
    private RelativeLayout myImgLayout,myNameLayout,myPhoneLayout,myEmailLayout,myIDlayout,myAdressLayout,myDesLayout;
    private EditText myNameEdt,myPhoneEdt,myEmailEdt,myIDEdt,myAdressEdt,myDesEdt;
    private boolean editFlag=false;
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

    }
    private void initData()
    {
        back = (ImageView) this.findViewById(R.id.dttail_back);
        modifyLayout=(LinearLayout) this.findViewById(R.id.infoModify_layout);
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
        //弹出相册选择框
        startActivity(new Intent(PersonInfo.this,SelectPicPopup.class));
    }
}
