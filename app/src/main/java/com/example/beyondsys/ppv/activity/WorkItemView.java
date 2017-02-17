package com.example.beyondsys.ppv.activity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.GetChars;
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


    private ListView listView;
    private View rootView;
    private LinearLayout wi_s_one, undo_layout, progress_layout, done_layout, cancel_layout;
    private TextView wi_s_one_txt, undo_tex, proing_tex, done_tex, cancel_tex;
    // private WorkItemEntity workItemEntity;
    private List<WorkItemResultParams> workItemEntityList = null;
    private final static int aboutme = 1;
    private final static int assignme = 0;
    private final static int undo = 0;
    private final static int progress = 1;
    private final static int done = 2;
    private final static int cancel = 3;
    private int reflag = 0, stflag = 0;
    SimpleAdapter adapter;

    private String TKID ="";
    private String TeamID = "";

    private Handler threadHander = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == ThreadAndHandlerLabel.GetWorkItem) {
                if (msg.obj != null) {
                    Log.i("返回值：" + msg.obj, "FHZ");
                    String jsonStr = msg.obj.toString();
                    if (!jsonStr.equals("anyType{}")) {
                        WorkItemResultEntity entity = JsonEntity.ParseJsonForWorkItemResult(jsonStr);
                        switch (entity.AccessResult) {
                            case 0:
                                Log.i("获取工作项成功", "FHZ");
                                workItemEntityList = entity.WorkItemList;
                                if (workItemEntityList != null) {
                                    SetList();
                                }
                                break;
                            case 1:
                                Toast.makeText(WorkItemView.this.getActivity(), "请求失败，请重新尝试", Toast.LENGTH_SHORT).show();
                                break;
                            case -3:
                                Toast.makeText(WorkItemView.this.getActivity(), "服务端验证出错，请联系管理员", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } else {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.workitem_view, container, false);
        try {
            Reservoir.init(WorkItemView.this.getActivity(), 4096);
        } catch (Exception e) {
            e.printStackTrace();
        }
        IninView();

        Listener();

        GetDataForCache();

        GetDataForService();

        return rootView;
    }

    private void IninView() {
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
        undo_tex.setTextColor(getActivity().getResources().getColor(R.color.text));
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
                    GetDataForService();
                } else {
                    wi_s_one_txt.setText(R.string.wi_s_one_txt);
                    reflag = assignme;
                    GetDataForService();
                }
            }
        });


        undo_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdefault();
                undo_tex.setTextColor(getActivity().getResources().getColor(R.color.text));
                stflag = undo;
                GetDataForService();
            }
        });

        progress_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdefault();
                proing_tex.setTextColor(getActivity().getResources().getColor(R.color.text));
                stflag = progress;
                GetDataForService();
            }
        });

        done_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdefault();
                done_tex.setTextColor(getActivity().getResources().getColor(R.color.text));
                stflag = done;
                GetDataForService();
            }
        });

        cancel_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdefault();
                cancel_tex.setTextColor(getActivity().getResources().getColor(R.color.text));
                stflag = cancel;
                GetDataForService();
            }
        });
    }

    private void GetDataForService() {
        if (TKID.equals("")) {
            Toast.makeText(WorkItemView.this.getActivity(), "读取缓存失败，请检查内存重新登录", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(WorkItemView.this.getContext(), Login.class);
            startActivity(intent);
            this.getActivity().finish();
        }
        else {
            WorkItemBusiness workItemBusiness = new WorkItemBusiness();
            workItemBusiness.GetWorkItem(threadHander, TeamID, stflag, reflag, 1, TKID);
        }
    }

    private void GetDataForCache() {
        try {
            if (Reservoir.contains(LocalDataLabel.Proof)) {
                UserLoginResultEntity userLoginResultEntity = Reservoir.get(LocalDataLabel.Proof, UserLoginResultEntity.class);
                TKID = userLoginResultEntity.TicketID;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (Reservoir.contains(LocalDataLabel.Label)) {
                Type resultType = new TypeToken<List<TeamEntity>>() {
                }.getType();
                List<TeamEntity> entity = Reservoir.get(LocalDataLabel.Label, resultType);
                TeamID = entity.get(0).TeamID;
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            if(workItemEntity.Category==0)
            {
                map.put("workimg", R.drawable.b);
            }else{
                map.put("workimg", R.drawable.t);
            }
            map.put("workId", workItemEntity.WorkID);
            map.put("workName", workItemEntity.WorkName);
            //根据状态不同图片不同
            switch (workItemEntity.Status)
            {
                case 0:
                    map.put("workState", R.drawable.status0);
                    break;
                case 1:
                    map.put("workState", R.drawable.status1);
                    break;
                case 2:
                    map.put("workState", R.drawable.status2);
                    break;
                case 3:
                    map.put("workState", R.drawable.status3);
                    break;
                case 4:
                    map.put("workState", R.drawable.status4);
                     break;
                case 5:
                    map.put("workState", R.drawable.status5);
                    break;
                case 6:
                    map.put("workState", R.drawable.status6);
                    break;
                case 7:
                    map.put("workState", R.drawable.status7);
                    break;
               default:
                   map.put("workState", R.drawable.status0);
                   break;
            }
            map.put("endingTime", workItemEntity.EndTime);
           // map.put("workState", R.drawable.img_done);
            map.put("endingTime", workItemEntity.EndTime.substring(0,10));
            map.put("workValue", workItemEntity.Workscore);
            map.put("strartTime", workItemEntity.StartTime.substring(0,10));
            list.add(map);
        }
        return list;
    }
}
