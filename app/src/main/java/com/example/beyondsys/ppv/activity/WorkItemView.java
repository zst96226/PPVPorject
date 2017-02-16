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

import com.anupcowkur.reservoir.Reservoir;
import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.bussiness.WorkItemBusiness;
import com.example.beyondsys.ppv.dataaccess.ACache;
import com.example.beyondsys.ppv.entities.IdentifyResult;
import com.example.beyondsys.ppv.entities.LocalDataLabel;
import com.example.beyondsys.ppv.entities.TeamEntity;
import com.example.beyondsys.ppv.entities.ThreadAndHandlerLabel;
import com.example.beyondsys.ppv.entities.UserLoginResultEntity;
import com.example.beyondsys.ppv.entities.WorkItemEntity;
import com.example.beyondsys.ppv.entities.WorkItemResultEntity;
import com.example.beyondsys.ppv.entities.WorkItemResultParams;
import com.example.beyondsys.ppv.tools.GsonUtil;
import com.example.beyondsys.ppv.tools.JsonEntity;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
    private LinearLayout wi_s_one, undo_layout, progress_layout, done_layout, cancel_layout;
    private TextView wi_s_one_txt, undo_tex, proing_tex, done_tex, cancel_tex;
    // private WorkItemEntity workItemEntity;
    private List<WorkItemResultParams> workItemEntityList=null;
    private final static int aboutme = 1;
    private final static int assignme = 0;
    private final static int undo = 0;
    private final static int progress = 1;
    private final static int done = 2;
    private final static int cancel = 3;
    private int reflag = 0, stflag = 0;
    SimpleAdapter adapter;

    private Handler threadHander = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == ThreadAndHandlerLabel.GetWorkItem) {
                if (msg.obj != null) {
                    Log.i("返回值：" + msg.obj, "FHZ");
                    String jsonStr = msg.obj.toString();
                    if (!jsonStr.equals("anyType{}"))
                    {
                        WorkItemResultEntity entity=JsonEntity.ParseJsonForWorkItemResult(jsonStr);
                        switch (entity.AccessResult)
                        {
                            case 0:
                                Log.i("获取工作项成功","FHZ");
                                workItemEntityList=entity.WorkItemList;
                                adapter.notifyDataSetChanged();
                                break;
                            case 1:
                                Toast.makeText(WorkItemView.this.getActivity(), "请求失败，请重新尝试", Toast.LENGTH_SHORT).show();
                                break;
                            case -3:
                                Toast.makeText(WorkItemView.this.getActivity(), "服务端验证出错，请联系管理员", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                    else
                    {
                        Toast.makeText(WorkItemView.this.getActivity(), "服务端验证出错，请联系管理员", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(WorkItemView.this.getActivity(), "服务端验证出错，请联系管理员", Toast.LENGTH_SHORT).show();
                }
            } else if (msg.what == ThreadAndHandlerLabel.CallAPIError) {
                Toast.makeText(WorkItemView.this.getActivity(), "请求失败，请检查网络连接", Toast.LENGTH_SHORT).show();
            } else if (msg.what == ThreadAndHandlerLabel.LocalNotdata) {
                Toast.makeText(WorkItemView.this.getActivity(), "读取缓存失败，请检查内存重新登录", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.workitem_view, container, false);

        try {
            Reservoir.init(getActivity(), 2048);
        } catch (Exception e) {
            e.printStackTrace();
        }

        IninView();

        SetList();

        Listener();

//        SetList();

        GetCacheOrService();

        return rootView;
    }

    private void IninView() {
        mCache = ACache.get(getActivity());
        listView = (ListView) rootView.findViewById(R.id.workitem_list);
        wi_s_one = (LinearLayout) rootView.findViewById(R.id.wi_s_one);
        wi_s_one_txt = (TextView) rootView.findViewById(R.id.wi_s_one_txt);
        undo_layout = (LinearLayout) rootView.findViewById(R.id.wi_s_pro);
        progress_layout = (LinearLayout) rootView.findViewById(R.id.wi_s_proing);
        done_layout = (LinearLayout) rootView.findViewById(R.id.wi_s_complete);
        cancel_layout = (LinearLayout) rootView.findViewById(R.id.wi_s_invalid);
        undo_tex = (TextView) rootView.findViewById(R.id.undo_tex);
        proing_tex = (TextView) rootView.findViewById(R.id.proing_tex);
        done_tex = (TextView) rootView.findViewById(R.id.done_tex);
        cancel_tex = (TextView) rootView.findViewById(R.id.cancel_tex);
    }

    private void SetList() {
        adapter = new SimpleAdapter(this.getActivity(), getData(), R.layout.workitemliststyle, new String[]{"workimg", "workId", "workName", "workValue", "workState", "strartTime", "endingTime"},
                new int[]{R.id.work_img, R.id.workid_tex, R.id.workname_tex, R.id.workvalue_tex, R.id.work_state_img, R.id.strarttime_tex, R.id.endtime_tex});
        listView.setAdapter(adapter);
    }

    private void setdefault() {
        wi_s_one_txt.setTextColor(getActivity().getResources().getColor(R.color.text));
        undo_tex.setTextColor(getActivity().getResources().getColor(R.color.Gray));
        proing_tex.setTextColor(getActivity().getResources().getColor(R.color.Gray));
        done_tex.setTextColor(getActivity().getResources().getColor(R.color.Gray));
        cancel_tex.setTextColor(getActivity().getResources().getColor(R.color.Gray));
    }

    private void Listener() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WorkItemDetail.class);
                TextView ItemName_tex = (TextView) view.findViewById(R.id.workname_tex);
                TextView ItemId_tex = (TextView) view.findViewById(R.id.workid_tex);
                //实际上是传递ID过去
                intent.putExtra("ItemID", ItemId_tex.getText().toString().trim());
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
//                    SetList();
                    GetCacheOrService();
                } else {
                    wi_s_one_txt.setText(R.string.wi_s_one_txt);
                    reflag = assignme;
//                    SetList();
                    GetCacheOrService();
                }
            }
        });


        undo_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdefault();
                undo_tex.setTextColor(getActivity().getResources().getColor(R.color.text));
                stflag = undo;
//                SetList();
                GetCacheOrService();
            }
        });

        progress_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdefault();
                proing_tex.setTextColor(getActivity().getResources().getColor(R.color.text));
                stflag = progress;
//                SetList();
                GetCacheOrService();
            }
        });

        done_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdefault();
                done_tex.setTextColor(getActivity().getResources().getColor(R.color.text));
                stflag = done;
//                SetList();
                GetCacheOrService();
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

    private void GetCacheOrService() {
        /*判断缓存是否存在*/
        try {
            if (Reservoir.contains(LocalDataLabel.WorkItemList + reflag + stflag)) {
                Type resultType = new TypeToken<List<WorkItemResultParams>>() {
                }.getType();
                try {
                    workItemEntityList = Reservoir.get(LocalDataLabel.WorkItemList + reflag + stflag, resultType);
                } catch (Exception e) {
                    //failure
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (workItemEntityList!=null) {
            /*本地有缓存，先显示缓存，然后读取网络数据，刷新*/
            SetList();
            workItemEntityList.clear();
            List<TeamEntity> entity = null;
            String TeamID= null;
            try {
                if (Reservoir.contains(LocalDataLabel.Label)) {
                    Type resultType = new TypeToken<List<TeamEntity>>() {}.getType();
                    entity = Reservoir.get(LocalDataLabel.Label, resultType);
                    TeamID = entity.get(0).TeamID;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            UserLoginResultEntity userLoginResultEntity = null;
            try {
                if (Reservoir.contains(LocalDataLabel.Proof)) {
                    userLoginResultEntity = Reservoir.get(LocalDataLabel.Proof, UserLoginResultEntity.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (userLoginResultEntity!=null) {
                WorkItemBusiness workItemBusiness = new WorkItemBusiness();
                workItemBusiness.GetWorkItem(threadHander, TeamID, stflag, reflag, 1, userLoginResultEntity.TicketID);
            }
            else{
                Toast.makeText(WorkItemView.this.getActivity(), "读取缓存失败，请检查内存重新登录", Toast.LENGTH_SHORT).show();
            }
        } else {
            /*本地无缓存，读取网络数据，显示*/
            List<TeamEntity> entity = null;
            String TeamID= null;
            try {
                if (Reservoir.contains(LocalDataLabel.Label)) {
                    Type resultType = new TypeToken<List<TeamEntity>>() {}.getType();
                    entity = Reservoir.get(LocalDataLabel.Label, resultType);
                    TeamID = entity.get(0).TeamID;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            UserLoginResultEntity userLoginResultEntity = null;
            try {
                if (Reservoir.contains(LocalDataLabel.Proof)) {
                    userLoginResultEntity = Reservoir.get(LocalDataLabel.Proof, UserLoginResultEntity.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (userLoginResultEntity!=null) {
                WorkItemBusiness workItemBusiness = new WorkItemBusiness();
                workItemBusiness.GetWorkItem(threadHander, TeamID, stflag, reflag, 1, userLoginResultEntity.TicketID);
            }
            else{
                Toast.makeText(WorkItemView.this.getActivity(), "读取缓存失败，请检查内存重新登录", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void RefUI()
    {
        if (workItemEntityList.size()>0)
        {
            SetList();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
    }

    private List<Map<String, Object>> getData() {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.clear();
        List<WorkItemResultParams> entityList = workItemEntityList;
        if (entityList == null) {
            return list;
        }
        for (WorkItemResultParams workItemEntity : entityList) {
            Map<String, Object> map = new HashMap<String, Object>();
            //根据类别不同图片不同
            map.put("workimg", R.drawable.work_item);
            map.put("workId", workItemEntity.WorkID);
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
}
