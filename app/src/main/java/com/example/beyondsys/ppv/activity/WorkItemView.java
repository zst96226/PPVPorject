package com.example.beyondsys.ppv.activity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.anupcowkur.reservoir.ReservoirPutCallback;
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
public class WorkItemView extends Fragment  implements SwipeRefreshLayout.OnRefreshListener{


    private ListView listView;
    private View rootView;
    private LinearLayout wi_s_one, undo_layout, progress_layout, done_layout, cancel_layout;
    private TextView wi_s_one_txt, undo_tex, proing_tex, done_tex, cancel_tex;
    // private WorkItemEntity workItemEntity;
    private List<WorkItemResultParams> workItemEntityList = new ArrayList<>();
    private final static int aboutme = 1;
    private final static int assignme = 0;
    private final static int undo = 0;
    private final static int progress = 1;
    private final static int done = 2;
    private final static int cancel = 3;
    private int reflag = 0, stflag = 0;
    SimpleAdapter adapter;
    private SwipeRefreshLayout mSwipeLayout;
    private String TKID = "";
    private String TeamID = "";

    private Handler threadHander = new Handler() {
        public void handleMessage(Message msg) {
            // 停止刷新
            mSwipeLayout.setRefreshing(false);
            if (msg.what == ThreadAndHandlerLabel.GetWorkItem) {
                if (msg.obj != null) {

                    Log.i("返回值：" + msg.obj, "FHZ");
                    String jsonStr = msg.obj.toString();
                    if (!jsonStr.equals("anyType{}")) {
                        WorkItemResultEntity entity = JsonEntity.ParseJsonForWorkItemResult(jsonStr);
                        System.out.println("获取工作项json:" + jsonStr);
                        switch (entity.AccessResult) {
                            case 0:
                                Log.i("获取工作项成功", "FHZ");
                                workItemEntityList.clear();

                                workItemEntityList = entity.WorkItemList;
                                if (workItemEntityList != null) {
                                    if (reflag == 0 && stflag == 0) {
                                        try {
                                            Reservoir.putAsync(LocalDataLabel.WorkItemList, entity.WorkItemList, new ReservoirPutCallback() {
                                                @Override
                                                public void onSuccess() {
                                                    SetList();
                                                }

                                                @Override
                                                public void onFailure(Exception e) {
                                                    e.printStackTrace();
                                                }
                                            });
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        SetList();
                                    }
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
        GetDataForCache();

        IninView();

        ShowData();

        Listener();

        return rootView;
    }

    private void ShowData() {
        if (workItemEntityList!=null) {
            SetList();
            GetDataForService();
        } else {
            GetDataForService();
        }
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
        mSwipeLayout = (SwipeRefreshLayout)  rootView.findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeLayout.setDistanceToTriggerSync(200);// 设置手指在屏幕下拉多少距离会触发下拉刷新
        mSwipeLayout.setProgressBackgroundColor(R.color.refresh);
        mSwipeLayout.setSize(SwipeRefreshLayout.LARGE);
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

    private  void statusColor(int flag){
        switch (flag)
        {
            case 0:undo_tex.setTextColor(getActivity().getResources().getColor(R.color.text));
                break;
            case 1: proing_tex.setTextColor(getActivity().getResources().getColor(R.color.text));
                break;
            case 2: done_tex.setTextColor(getActivity().getResources().getColor(R.color.text));
                break;
            case 3: cancel_tex.setTextColor(getActivity().getResources().getColor(R.color.text));
                break;
            default:undo_tex.setTextColor(getActivity().getResources().getColor(R.color.text));
                break;

        }
    }

    private void Listener() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WorkItemDetail.class);
                TextView ItemName_tex = (TextView) view.findViewById(R.id.workname_tex);
                TextView ItemId_tex = (TextView) view.findViewById(R.id.workid_tex);
                String Id = ItemId_tex.getText().toString().trim();
                for (WorkItemResultParams entity : workItemEntityList) {
                    if (entity.WorkID.equals(Id)) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Item", entity);
                        bundle.putInt("status",stflag);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }

            }
        });
        wi_s_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setdefault();
                if (assignme == reflag) {
                    wi_s_one_txt.setText(R.string.wi_s_one_txt_2);
                   // undo_tex.setTextColor(getActivity().getResources().getColor(R.color.text));
                    reflag = aboutme;
                    statusColor(stflag);
                  //  stflag=undo;
                    GetDataForService();
                } else {
                    wi_s_one_txt.setText(R.string.wi_s_one_txt);
                  //  undo_tex.setTextColor(getActivity().getResources().getColor(R.color.text));
                    reflag = assignme;
                    statusColor(stflag);
                   // stflag=undo;
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
        } else {
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
                if(entity!=null&&entity.size()!=0)
                {
                    TeamID = entity.get(0).TeamID;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (Reservoir.contains(LocalDataLabel.WorkItemList)) {
                Type resultType = new TypeToken<List<WorkItemResultParams>>() {
                }.getType();
                workItemEntityList = Reservoir.get(LocalDataLabel.WorkItemList, resultType);
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
            if (workItemEntity.Category == 0) {
                map.put("workimg", R.drawable.b);
            } else {
                map.put("workimg", R.drawable.t);
            }
            map.put("workId", workItemEntity.WorkID);
            map.put("workName", workItemEntity.WorkName);
            //根据状态不同图片不同
            switch (workItemEntity.Status) {
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
                case 8:
                    map.put("workState", R.drawable.status8);
                    break;
                case 9:
                    map.put("workState", R.drawable.status9);
                    break;
                default:
                    map.put("workState", R.drawable.status0);
                    break;
            }
            map.put("endingTime", workItemEntity.EndTime);
            // map.put("workState", R.drawable.img_done);
            map.put("endingTime", workItemEntity.EndTime.substring(0, 10));
            map.put("workValue", workItemEntity.Workscore);
            map.put("strartTime", workItemEntity.StartTime.substring(0, 10));
            list.add(map);
        }
        return list;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GetDataForService();
                // 停止刷新
              //  mSwipeLayout.setRefreshing(false);
            }
        },1000); // 5秒后发送消息，停止刷新
    }
}
