package com.example.beyondsys.ppv.activity;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.dataaccess.ACache;
import com.example.beyondsys.ppv.entities.WorkItemEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

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
    private  final static int aboutme=2;
    private final static int  assignme=1;
    private final static int undo=3;
    private final static int  progress=4;
    private final static int done=5;
    private final static int  cancel=6;
    private  int flag=1;



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
    wi_s_one_txt.setTextColor(getActivity().getResources().getColor(R.color.Gray));
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
                wi_s_one_txt.setTextColor(getActivity().getResources().getColor(R.color.text));
                if (assignme == flag) {
                    wi_s_one_txt.setText(R.string.wi_s_one_txt_2);
                    flag = aboutme;
                    SetList();
                } else {
                    wi_s_one_txt.setText(R.string.wi_s_one_txt);
                    flag = assignme;
                    SetList();
                }
            }
        });


        undo_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdefault();
                undo_tex.setTextColor(getActivity().getResources().getColor(R.color.text));
                flag = undo;
                SetList();
            }
        });

        progress_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdefault();
                proing_tex.setTextColor(getActivity().getResources().getColor(R.color.text));
                flag = progress;
                SetList();
            }
        });

        done_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdefault();
                done_tex.setTextColor(getActivity().getResources().getColor(R.color.text));
                flag = done;
                SetList();
            }
        });

        cancel_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdefault();
                cancel_tex.setTextColor(getActivity().getResources().getColor(R.color.text));
                flag = cancel;
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
        //根据flag获取各状态事务对象列表
        Log.e(flag+"","qq");

       List<WorkItemEntity> entityList=getEntities(flag);
        for (WorkItemEntity workItemEntity:entityList)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("workimg", R.drawable.work_item);
            map.put("workId",workItemEntity.ID);
            map.put("workName", workItemEntity.Name);
            map.put("workState", R.drawable.img_done);
            map.put("endingTime", workItemEntity.ClosingTime);
            map.put("workValue", workItemEntity.BusinessValue);
            map.put("strartTime", workItemEntity.CreateTime);
            list.add(map);
          WorkItemEntity hasEntity=(WorkItemEntity)  mCache.getAsObject(workItemEntity.ID.toString().trim());
            if(hasEntity==null)
            {
                mCache.put(workItemEntity.ID.toString().trim(), workItemEntity);
            }
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
    private  List<WorkItemEntity> getEntities(int flag)
    {
        List<WorkItemEntity> entityList=new ArrayList<WorkItemEntity>();
                switch (flag)
        {
            case assignme:
                for(int i=0;i<10;i++)
                {
                    WorkItemEntity workItemEntity=new WorkItemEntity();
                    workItemEntity.BID="BID"+i;
                    workItemEntity.ID="ID"+i;
                    workItemEntity.FID="FID"+i;
                    workItemEntity.Name="Name"+i;
                    workItemEntity.Description="Des"+i;
                    workItemEntity.Category=i;
                    workItemEntity.Status=i;
                    workItemEntity.Assigned2="Assigned"+i;
                    workItemEntity.Belong2="Belong"+i;
                    workItemEntity.Checker="Check"+i;
                    workItemEntity.Creater="Creater"+i;
                    workItemEntity.CreateTime="Createtime"+i;
                    workItemEntity.ClosingTime="ClosingTime"+i;
                    workItemEntity.Modifier="Modifier"+i;
                    workItemEntity.ModifyTime="ModifiyTime"+i;
                    workItemEntity.BusinessValue=i;
                    workItemEntity.BasicScore=i;
                    workItemEntity.CheckedScore=i;
                    workItemEntity.HardScale=i;
                    workItemEntity.Remark="Remark"+i;
                    entityList.add(workItemEntity);

                    Log.e(entityList.get(i).Name.toString(), "qq");
                }
                //往缓存中存列表
                break;
            case aboutme:
               //   entityList.clear();
                break;
            case undo:
                //entityList.clear();
                break;
            case progress:
              //  entityList.clear();
                break;
            case done:
             //   entityList.clear();
                break;
            case cancel:
               // entityList.clear();
                break;
            default:
              //  entityList.clear();
                    break;
        }

        return  entityList;
    }
}
