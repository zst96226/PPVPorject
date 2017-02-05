package com.example.beyondsys.ppv.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.tools.CustomDialog;
import com.example.beyondsys.ppv.tools.ValidaService;

/**
 * Created by zhsht on 2017/1/13.个人信息界面
 */
public class OneSelfView extends Fragment {
private LinearLayout personInfoLayout;
    private LinearLayout passwordChangeLayout,qiutLayout;
    private CustomDialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.oneself_view, container, false);
        init(rootView);
       setListener();
        return rootView;
    }

    private  void init(View  rootView)
    {

        personInfoLayout=(LinearLayout)rootView.findViewById(R.id.personInfo_layout);
        passwordChangeLayout=(LinearLayout)rootView.findViewById(R.id.passwordChange_layout);
        qiutLayout=(LinearLayout)rootView.findViewById( R.id.quit_layout);
    }

    private void setListener()
    {
        personInfoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonInfo.class);
                startActivity(intent);
            }
        });
        passwordChangeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });
        qiutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退出操作
               Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                       "已退出登录！", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
    }
    // 弹窗
    private void dialog() {
         dialog = new CustomDialog(OneSelfView.this.getActivity());
        dialog.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //验证输入。更新数据库
                boolean oldpass= ValidaService.isPasswLength(dialog.getoldpass())&&ValidaService.isPassword(dialog.getoldpass());
                boolean newpass=ValidaService.isPasswLength(dialog.getNewPass())&&ValidaService.isPassword(dialog.getNewPass());
                boolean chepass=ValidaService.isPasswLength(dialog.getChePass())&&ValidaService.isPassword(dialog.getChePass());
                boolean equals=dialog.getNewPass().equals(dialog.getChePass());
                if(!(oldpass&&newpass&&chepass))
                {
                    Toast toast=Toast.makeText(getActivity().getApplicationContext(),"密码格式不正确!",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
                else
                {
                    if(!equals)
                    {
                        Toast toast=Toast.makeText(getActivity().getApplicationContext(),"两次输入不一致!",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }
                    else
                    {
                        //服务端验证原密码
                        Toast toast=Toast.makeText(getActivity().getApplicationContext(),"修改成功!",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                        dialog.dismiss();
                    }
                }

            }
        });
        dialog.setOnNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
