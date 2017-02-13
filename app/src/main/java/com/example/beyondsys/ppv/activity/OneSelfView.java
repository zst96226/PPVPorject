package com.example.beyondsys.ppv.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.bussiness.LoginBusiness;
import com.example.beyondsys.ppv.bussiness.OneSelfBusiness;
import com.example.beyondsys.ppv.dataaccess.ACache;
import com.example.beyondsys.ppv.entities.LocalDataLabel;
import com.example.beyondsys.ppv.entities.PersonInfoEntity;
import com.example.beyondsys.ppv.entities.ThreadAndHandlerLabel;
import com.example.beyondsys.ppv.tools.CustomDialog;
import com.example.beyondsys.ppv.tools.GsonUtil;
import com.example.beyondsys.ppv.tools.JsonEntity;
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
    ACache mCache=null;
    private LinearLayout passwordChangeLayout,qiutLayout;
    private CustomDialog dialog;
    private RelativeLayout changeTeam_layout;
    private ListView child_list;
    private ImageView show_team;
    private TextView teamName_tex,teamlevel_tex,personname_tex,valuesum_tex,monthsum_tex;
    private PersonInfoEntity personInfoEntity;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg)
        {
            if(msg.what== ThreadAndHandlerLabel.ChangePwd)
            {
                if(msg.obj!=null)
                {
                    try{
                        int flag = Integer.parseInt(msg.obj.toString());
                        if(flag==0)
                        {
                            Toast toast=Toast.makeText(getActivity().getApplicationContext(),"修改成功!",Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            dialog.dismiss();
                        }else {
                            Toast.makeText(OneSelfView.this.getActivity(),"修改密码失败，请稍后再试！",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){}
                }else{
                    Toast.makeText(OneSelfView.this.getActivity(),"服务端验证出错，请联系管理员",Toast.LENGTH_SHORT).show();
                }

            }else if(msg.what==ThreadAndHandlerLabel.GetOneSelf){
                if(msg.obj!=null)
                {
                    Log.i("当前用户信息返回值："+msg.obj,"FHZ");
                    String  jsonStr=msg.obj.toString();
                    try{
                        PersonInfoEntity personInfoEntity= JsonEntity.ParseJsonForPerson(jsonStr);
                        String curPerson= GsonUtil.t2Json2(personInfoEntity);
                        mCache.put(LocalDataLabel.CurPerson,curPerson);
                    }catch (Exception e){}
                }else{
                    Toast.makeText(OneSelfView.this.getActivity(),"没有当前用户的数据",Toast.LENGTH_SHORT).show();
                }

            }else if(msg.what==ThreadAndHandlerLabel.CallAPIError){
                Toast.makeText(OneSelfView.this.getActivity(),"请求失败，请检查网络连接",Toast.LENGTH_SHORT).show();
            }else  if(msg.what==ThreadAndHandlerLabel.LocalNotdata){
                Toast.makeText(OneSelfView.this.getActivity(),"读取缓存失败，请检查内存重新登录",Toast.LENGTH_SHORT).show();
            }

        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.oneself_view, container, false);
        init(rootView);
        setData();
       setListener();
        return rootView;
    }

    private  void init(View  rootView)
    {
        mCache=ACache.get(getActivity());
        personInfoLayout=(LinearLayout)rootView.findViewById(R.id.personInfo_layout);
        passwordChangeLayout=(LinearLayout)rootView.findViewById(R.id.passwordChange_layout);
        qiutLayout=(LinearLayout)rootView.findViewById( R.id.quit_layout);
        changeTeam_layout=(RelativeLayout)rootView.findViewById(R.id.changeTeam_layout);
        child_list=(ListView)rootView.findViewById(R.id.wid_list_of);
        show_team=(ImageView)rootView.findViewById(R.id.show_team);
        teamlevel_tex=(TextView)rootView.findViewById(R.id.personlevel_tex);
        teamName_tex=(TextView)rootView.findViewById(R.id.personteam_tex);
        personname_tex=(TextView)rootView.findViewById(R.id.personname_tex);
        valuesum_tex=(TextView)rootView.findViewById(R.id.valuesum_tex);
        monthsum_tex=(TextView)rootView.findViewById(R.id.monthsum_tex);
    }
    private  void setData()
    {
        personInfoEntity=(PersonInfoEntity)mCache.getAsObject(LocalDataLabel.CurPerson);
        if(personInfoEntity==null)
        {
            //缓存中未获取到用户个人信息 从服务加载
            OneSelfBusiness oneSelfBusiness=new OneSelfBusiness();
            oneSelfBusiness.GetOneSelf(handler,mCache);
        }
        else
        {
            personname_tex.setText(personInfoEntity.Name);
            valuesum_tex.setText(String.valueOf(personInfoEntity.ScoreCount));
            monthsum_tex.setText(String.valueOf(personInfoEntity.MonthCount));
        }
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
//               Toast toast = Toast.makeText(getActivity().getApplicationContext(),
//                       "已退出登录！", Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();
                //注销账户
                Intent intent = new Intent(getActivity(),Login.class);
                startActivity(intent);
            }
        });
        changeTeam_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (child_list.getVisibility() == View.GONE) {
                    child_list.setVisibility(View.VISIBLE);
                    setList();
                   // setListListener();
                    show_team.setImageResource(R.drawable.arrow_up_float);
                } else {
                    child_list.setVisibility(View.GONE);
                   show_team.setImageResource(R.drawable.arrow_down_float);
                }
            }
        });
        child_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             //   CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
                TextView  teamName=(TextView)view.findViewById(R.id.TeamName_tex);
                TextView teamLevel=(TextView)view.findViewById(R.id.TeamLevel_tex);
               // checkBox.setChecked(true);
                teamName_tex.setText(teamName.getText());
                teamlevel_tex.setText(teamLevel.getText());

                child_list.setVisibility(View.GONE);
                show_team.setImageResource(R.drawable.arrow_down_float);
            }
        });
    }

    private  void setList()
    {
        SimpleAdapter adapter=new SimpleAdapter(getActivity(),getData(),R.layout.teamliststyle,
                new String[]{"teamName","teamLevel","teamId"},new int []{R.id.TeamName_tex,R.id.TeamLevel_tex,R.id.TeamID_tex});
        child_list.setAdapter(adapter);

    }
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("teamName", "杉石科技");
        map.put("teamLevel", "员工");
        map.put("teamId","1122");
        list.add(map);

        map=new HashMap<String,Object>();
        map.put("teamName", "杉石科技");
        map.put("teamLevel", "经理");
        map.put("teamId","2233");
        list.add(map);

        map=new HashMap<String,Object>();
        map.put("teamName", "杉石科技");
        map.put("teamLevel", "项目总监");
        map.put("teamId","3344");
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
                        //验证原密码 正确则提交更改到服务器
                        String userPass=personInfoEntity.AccPwd.toString().trim();
                        String inputPass=dialog.getoldpass().toString().trim();
                        String newPass=dialog.getNewPass().toString().trim();
                        if(!userPass.equals(inputPass))
                        {
                            Toast toast=Toast.makeText(getActivity().getApplicationContext(),"原密码不正确!",Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER,0,0);
                            toast.show();
                            dialog.dismiss();
                        }else{
                            //与服务器交互
                            LoginBusiness loginBusiness=new LoginBusiness();
                            loginBusiness.ChangePWD(handler,mCache,newPass);
                        }
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
