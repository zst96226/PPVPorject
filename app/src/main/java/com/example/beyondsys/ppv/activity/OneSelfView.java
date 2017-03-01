package com.example.beyondsys.ppv.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
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

import com.anupcowkur.reservoir.Reservoir;
import com.anupcowkur.reservoir.ReservoirPutCallback;
import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.bussiness.ImgBusiness;
import com.example.beyondsys.ppv.bussiness.LoginBusiness;
import com.example.beyondsys.ppv.bussiness.OneSelfBusiness;
import com.example.beyondsys.ppv.bussiness.OtherBusiness;
import com.example.beyondsys.ppv.dataaccess.ACache;
import com.example.beyondsys.ppv.entities.AccAndPwd;
import com.example.beyondsys.ppv.entities.IdentifyResult;
import com.example.beyondsys.ppv.entities.LocalDataLabel;
import com.example.beyondsys.ppv.entities.LogOutResult;
import com.example.beyondsys.ppv.entities.ModifyPwdResult;
import com.example.beyondsys.ppv.entities.PersonInfoEntity;
import com.example.beyondsys.ppv.entities.TeamEntity;
import com.example.beyondsys.ppv.entities.ThreadAndHandlerLabel;
import com.example.beyondsys.ppv.entities.UIDEntity;
import com.example.beyondsys.ppv.entities.UserInTeamResult;
import com.example.beyondsys.ppv.entities.UserInfoResultParams;
import com.example.beyondsys.ppv.tools.CustomDialog;
import com.example.beyondsys.ppv.tools.GsonUtil;
import com.example.beyondsys.ppv.tools.JsonEntity;
import com.example.beyondsys.ppv.tools.MD5;
import com.example.beyondsys.ppv.tools.ValidaService;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

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
    private ImageView show_team,person_img;
    private TextView teamName_tex,teamlevel_tex,personname_tex,valuesum_tex,monthsum_tex;
    private UserInfoResultParams personInfoEntity;
    private  String TeamID,UID;
  private ImgBusiness imgBusiness;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg)
        {
            if(msg.what== ThreadAndHandlerLabel.ChangePwd)
            {
                String jsonStr = msg.obj.toString();
                if(msg.obj!=null&& !jsonStr.equals("anyType{}"))
                {
                    Log.i("修改密码返回值：" + msg.obj, "FHZ");
                    try{
//                        ModifyPwdResult result=JsonEntity.ParseJsonForModifyPwdResult(jsonStr);
//                        int flag = result.Result;
                        int flag=Integer.parseInt(msg.obj.toString());
                        Log.i(flag+"","FHZ");
                        if(flag==0)
                        {
                            Log.i("修改密码完成", "FHZ");
                            dialog.dismiss();
                            Toast toast=Toast.makeText(getActivity().getApplicationContext(),"修改成功,请重新登陆!",Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            Intent intent = new Intent(getActivity(),Login.class);
                            startActivity(intent);

                        }
//                        else {
//                            Toast.makeText(OneSelfView.this.getActivity(),"修改密码失败，请稍后再试！",Toast.LENGTH_SHORT).show();
//                        }
                    }catch (Exception e)
                    {
                        Log.i("修改密码返回值：异常", "FHZ");
                    }
                }else{
                    Toast.makeText(OneSelfView.this.getActivity(),"服务端验证出错，请联系管理员",Toast.LENGTH_SHORT).show();
                }

            }else if(msg.what==ThreadAndHandlerLabel.GetOneSelf){
                String  jsonStr=msg.obj.toString();
                if(msg.obj!=null&& !jsonStr.equals("anyType{}"))
                {
                    Log.i(" 获取个人信息返回值："+msg.obj,"FHZ");

                    try{
                        UserInfoResultParams userInfoResultParams=JsonEntity.ParseJsonForUserInfoResult(jsonStr);
                        Log.i("accessresult:"+userInfoResultParams.AccessResult,"FHZ");
                        if(userInfoResultParams!=null)
                        {
//                            String curPerson=GsonUtil.t2Json2(userInfoResultParams);
//                            mCache.put(LocalDataLabel.CurPerson,curPerson);
//                            personInfoEntity=userInfoResultParams;
                            Log.i(" 获取个人信息返回值保存","FHZ");
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
                        Log.i(" 获取个人信息返回值异常：","FHZ");
                    }
                }else{
                    Toast.makeText(OneSelfView.this.getActivity(),"没有当前用户的数据",Toast.LENGTH_SHORT).show();
                }

            }else if(msg.what==ThreadAndHandlerLabel.LogOut)
            {
                String  jsonStr=msg.obj.toString();
                  if(msg.obj!=null&& !jsonStr.equals("anyType{}"))
                  {
                      try{
                          LogOutResult logOutResult=JsonEntity.ParseJsonForLogOutResult(jsonStr);
                          if(logOutResult!=null)
                          {
                              if(logOutResult.LogoutResult==0)
                              {
                                  Intent intent = new Intent(getActivity(),Login.class);
                                  startActivity(intent);
                              }
                          }
                      }catch (Exception e){}
                  }else{
                      Toast.makeText(OneSelfView.this.getActivity(),"退出异常",Toast.LENGTH_SHORT).show();
                  }
            }  if (msg.what == ThreadAndHandlerLabel.GetAllStaff)
        {
            if(msg.obj!=null)
            {
                Log.i("获取团队成员返回值：" + msg.obj, "FHZ");
                String jsonStr = msg.obj.toString();
                    /*解析Json*/
                try {
                    UserInTeamResult result= JsonEntity.ParseJsonForUserInTeamResult(jsonStr);
                    if(result!=null)
                    {
                        if(result.AccessResult==0)
                        {
                            // result.teamUsers
                            // List<UserInTeam> userList=result.teamUsers;
                            //存缓存
                            Reservoir.putAsync(LocalDataLabel.AllUserInTeam, result.teamUsers, new ReservoirPutCallback() {
                                @Override
                                public void onSuccess() {
                                }

                                @Override
                                public void onFailure(Exception e) {
                                       Log.i("缓存 alluserinteam 异常","FHZ");
                                }
                            });
                        }
                    }
                }catch (Exception e){}
            }else{
                Toast.makeText(OneSelfView.this.getActivity(), "服务端验证出错，请联系管理员", Toast.LENGTH_SHORT).show();
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
        try {
            Reservoir.init(OneSelfView.this.getActivity(), 4096);
        } catch (Exception e) {
            e.printStackTrace();
        }
        View rootView = inflater.inflate(R.layout.oneself_view, container, false);
        init(rootView);
       // initCache();
        setData();
       setListener();
        return rootView;
    }

    private  void init(View  rootView)
    {
        imgBusiness=new ImgBusiness();
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
        person_img=(ImageView)rootView.findViewById(R.id.person_img);
        try {
            if (Reservoir.contains(LocalDataLabel.UserID)) {
                UIDEntity entity = Reservoir.get(LocalDataLabel.UserID, UIDEntity.class);
                UID = entity.UID;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String imgname=UID+".png";
        Bitmap bitmap=imgBusiness.setImg(imgname);
        person_img.setImageBitmap(bitmap);
        List<TeamEntity> teamList=null;
        try{
            Log.i("label try","FHZ");
            if(Reservoir.contains(LocalDataLabel.Label))
            {
                Log.i("label","FHZ");

                Type resultType = new TypeToken<List<TeamEntity>>() {
                }.getType();
                teamList = Reservoir.get(LocalDataLabel.Label, resultType);
//                label=Reservoir.get(LocalDataLabel.Label,IdentifyResult.class);
                Log.i("label","FHZ");
            }
        }catch (Exception e)
        {
            Log.i("label excep","FHZ");
            e.printStackTrace();
        }
        if(teamList!=null)
        {
            teamName_tex.setText(teamList.get(0).TeamName);
            teamlevel_tex.setText(String.valueOf(teamList.get(0).TeamLeave));
            TeamID=teamList.get(0).TeamID;
        }else
        {
            teamName_tex.setText("无团队");
            teamlevel_tex.setText("");
            TeamID="";
        }
    }



    private  void setData()
    {
//        personInfoEntity=(UserInfoResultParams)mCache.getAsObject(LocalDataLabel.CurPerson);
//        if(personInfoEntity==null)
//        {
//            //缓存中未获取到用户个人信息 从服务加载
//            OneSelfBusiness oneSelfBusiness=new OneSelfBusiness();
//            oneSelfBusiness.GetOneSelf(handler,mCache);
//        }
//        else
//        {
//            personname_tex.setText(personInfoEntity.Name);
//            valuesum_tex.setText(String.valueOf(personInfoEntity.TotalScore));
//            monthsum_tex.setText(String.valueOf(personInfoEntity.TotalMonth));
//        }

      boolean isCache=setCache();
        if(!isCache)
        {
            setService();
        }
    }
    private  boolean  setCache()
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
            personname_tex.setText(personInfoEntity.Name);
            Log.i(String.valueOf(personInfoEntity.TotalScore),"FHZ");
            valuesum_tex.setText(String.valueOf(personInfoEntity.TotalScore));
            monthsum_tex.setText(String.valueOf(personInfoEntity.TotalMonth));
            return  true;
        }
        Log.i("personInfoEntity null", "FHZ");
        return false;
    }
    private  void setService()
    {
        //缓存中未获取到用户个人信息 从服务加载
            Log.i("getService", "FHZ");
            OneSelfBusiness oneSelfBusiness=new OneSelfBusiness();
            oneSelfBusiness.GetOneSelf(handler);
    }
    private void setListener()
    {
        personInfoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonInfo.class);
                //startActivity(intent);
                startActivityForResult(intent,1);
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
                LoginBusiness loginBusiness=new LoginBusiness();
                loginBusiness.LogOut(handler);

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
                TextView teamName = (TextView) view.findViewById(R.id.TeamName_tex);
                TextView teamLevel = (TextView) view.findViewById(R.id.TeamLevel_tex);
                TextView teamId = (TextView) view.findViewById(R.id.TeamID_tex);
                // checkBox.setChecked(true);
                teamName_tex.setText(teamName.getText());
                teamlevel_tex.setText(teamLevel.getText());


                //把当前选择的团队置顶 重新存缓存
                List<TeamEntity> teamList = null;
                try {
                    Log.i("label try", "FHZ");
                    if (Reservoir.contains(LocalDataLabel.Label)) {
                        Log.i("label", "FHZ");

                        Type resultType = new TypeToken<List<TeamEntity>>() {
                        }.getType();
                        teamList = Reservoir.get(LocalDataLabel.Label, resultType);
                        Log.i("label", "FHZ");
                    }
                } catch (Exception e) {
                    Log.i("label excep", "FHZ");
                    e.printStackTrace();
                }
                if (teamList != null) {
                    TeamEntity curTeam = null;
                    for (TeamEntity team : teamList) {
                        if (team.TeamID.equals(teamId.getText().toString().trim())) {
                            curTeam = team;
                            teamList.remove(team);
                        }
                    }
                    teamList.add(0, curTeam);
                }
                OtherBusiness other = new OtherBusiness();
                other.GetAllStaffForTeam(handler, teamList.get(0).TeamID);
                     /*存储团队信息*/
                Reservoir.putAsync(LocalDataLabel.Label, teamList, new ReservoirPutCallback() {
                    @Override
                    public void onSuccess() {
                        Log.i("更改后团队信息保存完毕", "FHZ");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        e.printStackTrace();
                    }
                });

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
        List<TeamEntity> teamList=null;
        try{
            Log.i("label try","FHZ");
            if(Reservoir.contains(LocalDataLabel.Label))
            {
                Log.i("label","FHZ");

                Type resultType = new TypeToken<List<TeamEntity>>() {
                }.getType();
                teamList = Reservoir.get(LocalDataLabel.Label, resultType);
//                label=Reservoir.get(LocalDataLabel.Label,IdentifyResult.class);
                Log.i("label","FHZ");
            }
        }catch (Exception e)
        {
            Log.i("label excep","FHZ");
            e.printStackTrace();
        }
        if(teamList!=null)
        {
            for (TeamEntity team: teamList)
            {
                map.put("teamName", team.TeamName);
                map.put("teamLevel", team.TeamLeave);
                map.put("teamId",team.TeamID);
                list.add(map);
            }
        }
//        map.put("teamName", "杉石科技");
//        map.put("teamLevel", "员工");
//        map.put("teamId","1122");
//        list.add(map);
//
//        map=new HashMap<String,Object>();
//        map.put("teamName", "杉石科技");
//        map.put("teamLevel", "经理");
//        map.put("teamId","2233");
//        list.add(map);
//
//        map=new HashMap<String,Object>();
//        map.put("teamName", "杉石科技");
//        map.put("teamLevel", "项目总监");
//        map.put("teamId","3344");
//        list.add(map);
    return  list;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
         //  setCache();
            setData();
        }
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
                        String userPass="";
                        String inputPass=dialog.getoldpass().toString().trim();
                        String newPass=dialog.getNewPass().toString().trim();
                        String newMD5= MD5.getMD5(newPass);
                        String inputMD5= MD5.getMD5(inputPass);
                          AccAndPwd accAndPwd = null;//(AccAndPwd) mCache.getAsObject(LocalDataLabel.AccAndPwd);
                        try{
                            Log.i("USER try","FHZ");
                            if(Reservoir.contains(LocalDataLabel.AccAndPwd))
                            {
                                Log.i("USER","FHZ");
                                accAndPwd=Reservoir.get(LocalDataLabel.AccAndPwd, AccAndPwd.class);
                                Log.i("USER","FHZ");
                            }
                        }catch (Exception e){
                            Log.i("USER excep","FHZ");
                            e.printStackTrace();
                        }
                        if(accAndPwd!=null)
                        {
                            Log.i("accpwd exit","FHZ");
                            userPass=accAndPwd.Password;
                            if(!userPass.equals(inputMD5))
                            {
                                Toast toast=Toast.makeText(getActivity().getApplicationContext(),"原密码不正确!",Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER,0,0);
                                toast.show();
                                dialog.dismiss();
                            }else{
                                //与服务器交互
                                LoginBusiness loginBusiness=new LoginBusiness();
                                loginBusiness.ChangePWD(handler,mCache,newMD5);
                            }
                        }else{
                            Log.i("accpwd is null","FHZ");
                            Toast toast=Toast.makeText(getActivity().getApplicationContext(),"获取缓存失败，请检查内存后重新登录!",Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER,0,0);
                            toast.show();
                            dialog.dismiss();
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
