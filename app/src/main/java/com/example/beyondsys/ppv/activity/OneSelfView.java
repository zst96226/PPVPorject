package com.example.beyondsys.ppv.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.tools.CustomDialog;
import com.example.beyondsys.ppv.tools.ValidaService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhsht on 2017/1/13.个人信息界面
 */
public class OneSelfView extends Fragment {
private LinearLayout personInfoLayout;
    private LinearLayout passwordChangeLayout,qiutLayout;
    private CustomDialog dialog;
    private RelativeLayout changeTeam_layout;
    private ListView child_list;
    private ImageView show_team;
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
        changeTeam_layout=(RelativeLayout)rootView.findViewById(R.id.changeTeam_layout);
        child_list=(ListView)rootView.findViewById(R.id.wid_list);
        show_team=(ImageView)rootView.findViewById(R.id.show_team);
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
        changeTeam_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (child_list.getVisibility() == View.GONE) {
                    child_list.setVisibility(View.VISIBLE);
                    setList();
                    show_team.setImageResource(R.drawable.arrow_up_float);
                } else {
                    child_list.setVisibility(View.GONE);
                   show_team.setImageResource(R.drawable.arrow_down_float);
                }
            }
        });
    }

    private  void setList()
    {
        SimpleAdapter adapter=new SimpleAdapter(getActivity(),getData(),R.layout.teamliststyle,
                new String[]{"teamName","teamLevel"},new int []{R.id.TeamName_tex,R.id.TeamLevel_tex});
        child_list.setAdapter(adapter);
    }
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("teamName", "杉石科技");
        map.put("teamLevel", "员工");
        list.add(map);
        map=new HashMap<String,Object>();
        map.put("teamName", "杉石科技");
        map.put("teamLevel", "经理");
        list.add(map);
        map=new HashMap<String,Object>();
        map.put("teamName", "杉石科技");
        map.put("teamLevel", "项目总监");
        list.add(map);
    return  list;
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
