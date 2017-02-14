package com.example.beyondsys.ppv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.bussiness.OneSelfBusiness;
import com.example.beyondsys.ppv.bussiness.WorkValueBusiness;
import com.example.beyondsys.ppv.dataaccess.ACache;
import com.example.beyondsys.ppv.entities.LocalDataLabel;
import com.example.beyondsys.ppv.entities.PersonInfoEntity;
import com.example.beyondsys.ppv.entities.TeamEntity;
import com.example.beyondsys.ppv.entities.ThreadAndHandlerLabel;
import com.example.beyondsys.ppv.entities.UserInfoResultParams;
import com.example.beyondsys.ppv.entities.ValueDetailEntity;
import com.example.beyondsys.ppv.entities.WorkItemResultEntity;
import com.example.beyondsys.ppv.entities.WorkValueEntity;
import com.example.beyondsys.ppv.entities.WorkValueResultEntity;
import com.example.beyondsys.ppv.entities.WorkValueResultParams;
import com.example.beyondsys.ppv.tools.GsonUtil;
import com.example.beyondsys.ppv.tools.JsonEntity;

import org.json.JSONArray;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zhsht on 2017/1/13.工作价值
 */
public class WorkValueView extends Fragment {
    //
    ACache mCache=null;
    private  ListView listView ;
    private View rootView;
    private  LinearLayout sort_layout,filter_layout;
    private RelativeLayout topme_layout;
    private CheckBox topme_che;
    private ImageView sort_img;
    private TextView sort_tex,filter_tex;
    private  List<WorkValueResultParams> valueEntityList=null;
    private UserInfoResultParams curPersonEntity=null;
    private final  static int sortup=1;
    private  final  static  int sortdown=0;
    private  final  static  int curmonth=0;
    private  final  static int  hismonth=1;
    private  int sortFlag=1,staFlag=0;//默认升序排列，当前月
    private Handler handler=new Handler()
    {
        public void handleMessage(Message msg)
        {
            if(msg.what== ThreadAndHandlerLabel.GetWorkValue)
            {
                if(msg.obj!=null)
                {
                    Log.i("价值返回值;"+msg.obj,"FHZ");
                    String jsonStr=msg.obj.toString();
                    try{
                        WorkValueResultEntity entity=JsonEntity.ParseJsonForWorkValueResult(jsonStr);
                        if(entity!=null)
                        {
                            if(entity.AccessResult==0)
                            {
                                String jsonArr=entity.Score;
                                List<WorkValueResultParams> entityList=JsonEntity.ParseJsonForWorkValueParamsList(jsonArr);
                                if(entityList!=null)
                                {
                                    valueEntityList=entityList;
                                }
                            }else{
                                Toast.makeText(WorkValueView.this.getActivity(), "获取返回数据出错！", Toast.LENGTH_SHORT).show();
                            }
                        }
//                      List<WorkValueEntity> entityList= JsonEntity.ParseJsonForWorkValueList(jsonStr);
//                        if(entityList!=null&&(!entityList.isEmpty()))
//                        {
//                            valueEntityList=entityList;
//                        }else {
//                            Toast.makeText(WorkValueView.this.getActivity(),"没有当前状态的数据",Toast.LENGTH_SHORT).show();
//                        }
                    }catch (Exception e){}
                }else{
                    Toast.makeText(WorkValueView.this.getActivity(),"服务端验证出错，请联系管理员",Toast.LENGTH_SHORT).show();
                }
            }else if(msg.what==ThreadAndHandlerLabel.GetOneSelf){
                if(msg.obj!=null)
                {
                    Log.i("当前用户信息返回值："+msg.obj,"FHZ");
                    String  jsonStr=msg.obj.toString();
                    try{
                        UserInfoResultParams userInfoResultParams=JsonEntity.ParseJsonForUserInfoResult(jsonStr);
                        if(userInfoResultParams!=null)
                        {
                            String curPerson=GsonUtil.t2Json2(userInfoResultParams);
                            mCache.put(LocalDataLabel.CurPerson,curPerson);
                            curPersonEntity=userInfoResultParams;
                        }
//                        PersonInfoEntity personInfoEntity=JsonEntity.ParseJsonForPerson(jsonStr);
//                         curPersonEntity=personInfoEntity;
                    }catch (Exception e){}
                }else{
                    Toast.makeText(WorkValueView.this.getActivity(),"没有当前用户的数据",Toast.LENGTH_SHORT).show();
                }
            }else if(msg.what==ThreadAndHandlerLabel.CallAPIError){
                Toast.makeText(WorkValueView.this.getActivity(),"请求失败，请检查网络连接",Toast.LENGTH_SHORT).show();
            }else  if(msg.what==ThreadAndHandlerLabel.LocalNotdata){
                Toast.makeText(WorkValueView.this.getActivity(),"读取缓存失败，请检查内存重新登录",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.workvalue_view, container, false);
         init();
        setAdapter();
        setListenter();
        return rootView;
    }
    private void init()
    {
        mCache=ACache.get(this.getActivity());
        listView=(ListView)rootView.findViewById(R.id.value_list );
        sort_layout=(LinearLayout)rootView.findViewById(R.id.wv_sort);
        filter_layout=(LinearLayout)rootView.findViewById(R.id.wv_filter);
        topme_layout=(RelativeLayout)rootView.findViewById(R.id.topme_layout);
        topme_che=(CheckBox)rootView.findViewById(R.id.topme_che);
        sort_img=(ImageView)rootView.findViewById(R.id.sort_img);
        sort_tex=(TextView)rootView.findViewById(R.id.wv_sort_txt);
        filter_tex=(TextView)rootView.findViewById(R.id.wv_filter_txt);


    }
    private void setListenter()
    {
        Log.e("qqww1","qqww");
      sort_layout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Log.e("qqww2","qqww");
              if(sort_tex.getText().toString().equals(getResources().getString(R.string.sortup)))
              {
                  Log.e("qqww3","qqww");
                  sort_img.setImageResource(R.drawable.sort_down);
                  sort_tex.setText(R.string.sortdown);
                  //降排序函数；
                  sortFlag=sortdown;
                  setAdapter();
              }
            else
              {
                  sort_img.setImageResource(R.drawable.sort_up);
                  sort_tex.setText(R.string.sortup);
                  //升排序函数；
                  sortFlag=sortup;
                  setAdapter();
              }
          }
      });
        filter_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("qqww4", "qqww");
                if (filter_tex.getText().toString().equals(getResources().getString(R.string.filter_by_currentmonth))) {
                    Log.e("qqww5", "qqww");
                    filter_tex.setText(R.string.filter_by_all);
                    setAdapter();
                    staFlag=hismonth;
                } else {
                    filter_tex.setText(R.string.filter_by_currentmonth);
                    setAdapter();
                    staFlag=curmonth;
                }
            }
        });
        topme_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (topme_che.isChecked())
                {
                    topme_che.setChecked(false);
                    Log.e("setAdapter();false","ee");
                    setAdapter();
                } else {
                    topme_che.setChecked(true);
                    Log.e("setAdapter();true", "ee");
                    setAdapter();
                }

            }
        });
    }
    private void setAdapter()
    {
        SimpleAdapter adapter =new SimpleAdapter(this.getActivity(),getData() ,R.layout.valueliststyle ,  new String[]{"personImg","personId","personName","valueSum","monthSum"},
                new int[]{R.id.person_img ,R.id.personid_tex ,R.id.personname_tex  ,R.id.valuesum_tex ,R.id .monthsum_tex  }) ;
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String personName = "";
                Intent intent = new Intent(getActivity(), PersonValueDetail.class);
                TextView person = (TextView) view.findViewById(R.id.personname_tex);
                personName = person.getText().toString();
                intent.putExtra("personName", personName);
                //传递ID
                intent.putExtra("ID", "");
                startActivity(intent);
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
        List<WorkValueResultParams> entityList=getEntities(sortFlag,staFlag);
        if(entityList==null||entityList.isEmpty())
        {
            return list;
        }
        String  valueArray= GsonUtil.getGson().toJson(entityList);
        mCache.put(LocalDataLabel.WorkValueList+sortFlag+staFlag,valueArray);
        OneSelfBusiness oneSelfBusiness=new OneSelfBusiness();
        oneSelfBusiness.GetOneSelf(handler, mCache);
        if(curPersonEntity==null)
        {
            for (WorkValueResultParams valueEntity:entityList) {
                Map<String, Object> map = new HashMap<String, Object>();
                //个人图片
                map.put("personImg",  R.drawable.person );
                map.put("personId", valueEntity.UserID);
                map.put("personName", valueEntity.Name);
                map.put("valueSum",  String.valueOf(valueEntity.BasicScore+valueEntity.CheckedScore));
                map.put("monthSum", valueEntity.Month+"个月");
                list.add(map);
            }
            return list;
        }
        PersonInfoEntity hasEntity=(PersonInfoEntity)mCache.getAsObject(LocalDataLabel.CurPerson);
        if (hasEntity!=null)
        {
            mCache.remove(LocalDataLabel.CurPerson);
        }
        String curPerson=GsonUtil.getGson().toJson(curPersonEntity);
        mCache.put(LocalDataLabel.CurPerson,curPerson);
        if(topme_che.isChecked())
        {
            for (int i=0;i<entityList.size();i++)
            {
                if(entityList.get(i).UserID.equals(curPersonEntity.UserID))
                {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("personImg",  R.drawable.person );
                    map.put("personId",entityList.get(i).UserID);
                    map.put("personName", entityList.get(i).Name);
                    map.put("valueSum", String.valueOf(entityList.get(i).BasicScore+entityList.get(i).CheckedScore));
                    map.put("monthSum", entityList.get(i).Month + "个月");
                    list.add(map);
                    entityList.remove(i);
                }
            }
            for (WorkValueResultParams valueEntity:entityList) {
                Log.e(valueEntity.Name.toString(),"ee");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("personImg",  R.drawable.person );
                map.put("personId", valueEntity.UserID);
                map.put("personName", valueEntity.Name);
                map.put("valueSum", String.valueOf(valueEntity.BasicScore+valueEntity.CheckedScore));
                map.put("monthSum",  valueEntity.Month+"个月");
                list.add(map);
            }
        }
        else
        {
            for (WorkValueResultParams valueEntity:entityList) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("personImg",  R.drawable.person );
                map.put("personId", valueEntity.UserID);
                map.put("personName", valueEntity.Name);
                map.put("valueSum", String.valueOf(valueEntity.BasicScore+valueEntity.CheckedScore));
                map.put("monthSum", valueEntity.Month+"个月");
                list.add(map);
            }
        }
        return list;
    }

//    private  List<Map<String, Object>> topme()
//    {
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//
//        return list;
//    }
    private  List<WorkValueResultParams> getEntities(int sort,int status)
    {
        WorkValueBusiness workValueBusiness=new WorkValueBusiness();
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
        workValueBusiness.GetWorkValue(handler,TeamID,status,1,mCache);
//
//        valueEntityList=new ArrayList<WorkValueEntity>();
//        for (int i=0;i<10;i++) {
//            WorkValueEntity    valueEntity = new WorkValueEntity();
//            // valueEntity.IMGTarget="";
//            valueEntity.BID = "BID" + i;
//            valueEntity.ID = "ID" + i;
//            valueEntity.Name = "Name" + i;
//            valueEntity.Status = i;
//            valueEntity.ScoreCount = (5-i) * 100;
//            valueEntity.MonthCount = i;
//            valueEntityList.add(valueEntity);
//        }
        if(valueEntityList!=null)
        {
            if(sort==sortdown)
            {
                Collections.sort(valueEntityList, new ValueComparator());
            }
            else
            {
                Collections.sort(valueEntityList,new ValueComparator());
                Collections.reverse(valueEntityList);
            }
        }
        return  valueEntityList;
    }

    // 自定义比较器：按价值排序
    static class ValueComparator implements Comparator {
        public int compare(Object object1, Object object2) {// 实现接口中的方法
           WorkValueEntity p1 = (WorkValueEntity) object1; // 强制转换
            WorkValueEntity p2 = (WorkValueEntity) object2;
            return new Double(p1.ScoreCount).compareTo(new Double(p2.ScoreCount));
        }
    }


}
