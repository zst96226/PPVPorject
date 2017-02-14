package com.example.beyondsys.ppv.activity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.bussiness.WorkItemBusiness;
import com.example.beyondsys.ppv.dataaccess.ACache;
import com.example.beyondsys.ppv.entities.LocalDataLabel;
import com.example.beyondsys.ppv.entities.TeamEntity;
import com.example.beyondsys.ppv.entities.ThreadAndHandlerLabel;
import com.example.beyondsys.ppv.entities.UserLoginResultEntity;
import com.example.beyondsys.ppv.entities.WorkItemEntity;
import com.example.beyondsys.ppv.entities.WorkItemResultEntity;
import com.example.beyondsys.ppv.entities.WorkItemResultParams;
import com.example.beyondsys.ppv.tools.GsonUtil;
import com.example.beyondsys.ppv.tools.JsonEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.logging.LogRecord;

/**
 * Created by zhsht on 2017/1/12.工作项页面
 */
public class WorkItemView extends Fragment {
    /*本地缓存操作对象*/
    ACache mCache = null;
   private ListView listView;
    private View rootView;
    private LinearLayout wi_s_one,undo_layout,progress_layout,done_layout,cancel_layout;
    private TextView  wi_s_one_txt,undo_tex,proing_tex,done_tex,cancel_tex;
   // private WorkItemEntity workItemEntity;
    private   List<WorkItemResultParams> workItemEntityList;
    private  final static int aboutme=1;
    private final static int  assignme=0;
    private final static int undo=0;
    private final static int  progress=1;
    private final static int done=2;
    private final static int  cancel=3;
    private  int reflag=0,stflag=0;

    private Handler threadHander=new Handler(){
        public void handleMessage(Message msg)
        {
          if(msg.what== ThreadAndHandlerLabel.GetWorkItem)
          {
              if(msg.obj!=null)
              {
                  Log.i("返回值："+msg.obj,"FHZ");
                  String jsonStr = msg.obj.toString();
                  try{
                      WorkItemResultEntity entity=JsonEntity.ParseJsonForWorkItemResult(jsonStr);
                      if(entity!=null)
                      {
                          if(entity.AccessResult==0)
                          {
                              String str=entity.WorkItemList;
                              List<WorkItemResultParams> entityList=JsonEntity.ParseJsonForworkItemResultParamsList(str);
                              if(entityList!=null&&(!entityList.isEmpty()))
                              {
                                  workItemEntityList=entityList;
                              }
                          }else
                          {
                              Toast.makeText(WorkItemView.this.getActivity(), "获取返回数据出错！", Toast.LENGTH_SHORT).show();
                          }
                      }
//                    List< WorkItemResultEntity> entityList=JsonEntity.ParseJsonForWorkItemListResult(jsonStr);
//                      if(entityList!=null&&(!entityList.isEmpty()))
//                      {
//                          //  //将结果实体类转化成工作项实体类
//                        //  workItemEntityList     entityList;
//                      }else{
//                          Toast.makeText(WorkItemView.this.getActivity(), "没有当前状态的数据", Toast.LENGTH_SHORT).show();
//                      }
                  }catch (Exception e){}
              }else
              {
                  Toast.makeText(WorkItemView.this.getActivity(), "服务端验证出错，请联系管理员", Toast.LENGTH_SHORT).show();
              }
          }else if (msg.what == ThreadAndHandlerLabel.CallAPIError) {
              Toast.makeText(WorkItemView.this.getActivity(), "请求失败，请检查网络连接", Toast.LENGTH_SHORT).show();
          } else if (msg.what == ThreadAndHandlerLabel.LocalNotdata) {
              Toast.makeText(WorkItemView.this.getActivity(), "读取缓存失败，请检查内存重新登录", Toast.LENGTH_SHORT).show();
                /*清除其余活动中Activity以及全部缓存显示登录界面*/
          }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.workitem_view, container, false);

        IninView();

        SetList();

        Listener();

        return rootView;
    }

    private void IninView(){
        mCache = ACache.get(getActivity());
        listView=(ListView)rootView.findViewById(R.id.workitem_list);
        wi_s_one=(LinearLayout)rootView.findViewById(R.id.wi_s_one);
        wi_s_one_txt=(TextView)rootView.findViewById(R.id.wi_s_one_txt);
        undo_layout=(LinearLayout)rootView.findViewById(R.id.wi_s_pro);
        progress_layout=(LinearLayout)rootView.findViewById(R.id.wi_s_proing);
        done_layout=(LinearLayout)rootView.findViewById(R.id.wi_s_complete);
        cancel_layout=(LinearLayout)rootView.findViewById(R.id.wi_s_invalid);
        undo_tex=(TextView)rootView.findViewById(R.id.undo_tex);
        proing_tex=(TextView)rootView.findViewById(R.id.proing_tex);
        done_tex=(TextView)rootView.findViewById(R.id.done_tex);
        cancel_tex=(TextView)rootView.findViewById(R.id.cancel_tex);
    }

    private void SetList(){
        SimpleAdapter adapter =new SimpleAdapter(this.getActivity(),getData() ,R.layout.workitemliststyle,  new String[]{"workimg","workId","workName","workValue","workState","strartTime","endingTime"},
                new int[]{R.id.work_img,R.id.workid_tex,R.id .workname_tex ,R.id.workvalue_tex ,R.id .work_state_img ,R.id .strarttime_tex ,R.id.endtime_tex}) ;
        listView.setAdapter(adapter);
    }

    private  void setdefault()
    {
        wi_s_one_txt.setTextColor(getActivity().getResources().getColor(R.color.text));
        undo_tex.setTextColor(getActivity().getResources().getColor(R.color.Gray));
        proing_tex.setTextColor(getActivity().getResources().getColor(R.color.Gray));
        done_tex.setTextColor(getActivity().getResources().getColor(R.color.Gray));
        cancel_tex.setTextColor(getActivity().getResources().getColor(R.color.Gray));
    }

    private void Listener(){

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WorkItemDetail.class);
                TextView ItemName_tex=(TextView)view.findViewById(R.id.workname_tex);
                TextView ItemId_tex=(TextView)view.findViewById(R.id.workid_tex);
                //实际上是传递ID过去
                intent.putExtra("ItemID",ItemId_tex.getText().toString().trim());
               // intent.putExtra("ItemName",ItemName_tex.getText().toString().trim());
                startActivity(intent);
            }
        });
        wi_s_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdefault();
                if (assignme == reflag) {
                    wi_s_one_txt.setText(R.string.wi_s_one_txt_2);
                    reflag = aboutme;
                    SetList();
                } else {
                    wi_s_one_txt.setText(R.string.wi_s_one_txt);
                    reflag = assignme;
                    SetList();
                }
            }
        });


        undo_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdefault();
                undo_tex.setTextColor(getActivity().getResources().getColor(R.color.text));
                stflag = undo;
                SetList();
            }
        });

        progress_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdefault();
                proing_tex.setTextColor(getActivity().getResources().getColor(R.color.text));
               stflag = progress;
                SetList();
            }
        });

        done_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdefault();
                done_tex.setTextColor(getActivity().getResources().getColor(R.color.text));
                stflag = done;
                SetList();
            }
        });

        cancel_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdefault();
                cancel_tex.setTextColor(getActivity().getResources().getColor(R.color.text));
                stflag = cancel;
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.clear();
        Log.e(stflag + reflag + "", "qq");
       List<WorkItemResultParams> entityList=getEntities(reflag,stflag);
        if(entityList==null)
        {
            return list;
        }
        //存缓存
        String workItemArray=GsonUtil.getGson().toJson(entityList);
        mCache.put(LocalDataLabel.WorkItemList+reflag+stflag,workItemArray);
        for (WorkItemResultParams workItemEntity:entityList)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            //根据类别不同图片不同
            map.put("workimg", R.drawable.work_item);
            map.put("workId",workItemEntity.WorkID);
            map.put("workName", workItemEntity.WorkName);
            //根据状态不同图片不同
            map.put("workState", R.drawable.img_done);
            map.put("endingTime", workItemEntity.EndTime);
            map.put("workValue", workItemEntity.Workscore);
            map.put("strartTime", workItemEntity.StartTime);
            list.add(map);
        }
        return list;
    }
//    private  List<Map<String, Object>> entities2maps( List<WorkItemEntity> entityList)
//    {
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        for (WorkItemEntity workItemEntity:entityList)
//        {
//
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("workimg", R.drawable.work_item);
//            map.put("workName", workItemEntity.Name);
//            map.put("workState", R.drawable.img_done);
//            map.put("endingTime", workItemEntity.ClosingTime);
//            map.put("workValue", workItemEntity.BusinessValue);
//            map.put("strartTime", workItemEntity.CreateTime);
//            list.add(map);
//        }
//       return list;
//   }
    private  List<WorkItemResultParams> getEntities(int reflag,int stflag)
    {
        //根据flag获取各状态事务对象列表
        WorkItemBusiness workItemBusiness=new WorkItemBusiness();
        //从缓存中取TeamID
        String TeamID="";
        String jsonarr=mCache.getAsString(LocalDataLabel.Label);
        if(jsonarr!=null)
        {
            try {
                List<TeamEntity> teamEntityList=JsonEntity.ParsingJsonForTeamList(jsonarr);
                if(teamEntityList!=null&&(!teamEntityList.isEmpty()))
                {
                    TeamID=teamEntityList.get(0).TeamID;
                }
            }catch (Exception e){}
        }
        workItemBusiness.GetWorkItem(threadHander,TeamID,stflag,reflag,1,mCache);
//原代码已保存
        return  workItemEntityList;
    }
}
