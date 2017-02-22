package com.example.beyondsys.ppv.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.anupcowkur.reservoir.Reservoir;
import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.bussiness.WorkItemBusiness;
import com.example.beyondsys.ppv.dataaccess.ACache;
import com.example.beyondsys.ppv.entities.ChildWorkItemEntity;
import com.example.beyondsys.ppv.entities.LocalDataLabel;
import com.example.beyondsys.ppv.entities.SubWorkItemParams;
import com.example.beyondsys.ppv.entities.TeamEntity;
import com.example.beyondsys.ppv.entities.ThreadAndHandlerLabel;
import com.example.beyondsys.ppv.entities.UserLoginResultEntity;
import com.example.beyondsys.ppv.entities.WorkDetailResult;
import com.example.beyondsys.ppv.entities.WorkItemChildResultParams;
import com.example.beyondsys.ppv.entities.WorkItemContextentity;
import com.example.beyondsys.ppv.entities.WorkItemEntity;
import com.example.beyondsys.ppv.entities.WorkItemResultParams;
import com.example.beyondsys.ppv.entities.WorkValueEntity;
import com.example.beyondsys.ppv.tools.GsonUtil;
import com.example.beyondsys.ppv.tools.JsonEntity;
import com.example.beyondsys.ppv.tools.PopupMenuForWorkItem;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkItemDetail extends AppCompatActivity {

    String TKID = "";
    String TeamID = "";
    String WorkID = "";
    List<SubWorkItemParams> chid = new ArrayList<>();

    ListPopupWindow Assign_pop, Checker_pop, Head_pop, Status_pop;
    ArrayList<String> Userlist = new ArrayList<String>();
    ArrayList<String> Statrlist = new ArrayList<>();
    ImageView back;
    ImageView wid_show_chid;
    ImageView returen;
    ImageView menu;
    ImageView work_img, work_status;
    LinearLayout main_workitem;
    ListView child_list;
    private EditText name_edt, assign2_edt, checker_edt, status_edt, Head_edt, value_edt, closingtime_edt, des_edt;
    private TextView wid_workname, wid_workvalue, starttime_tex, endtime_tex, creater_tex, creatertime_tex, modifier_tex, modifytime_tex;
    private RelativeLayout del_layout;
    private Button del_ok, del_cancel;
    private boolean isdel = false;
    String[] typeList = new String[]{"事项", "任务"};
    private String CurItemId = "", CurItemType = typeList[0];
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == ThreadAndHandlerLabel.GetWorkItemContext) {
                if (msg.obj != null) {
                    try {
                        WorkItemContextentity Result = JsonEntity.ParseJsonForWorkItemContextentity(msg.obj.toString());
                        if (Result != null) {
                            switch (Result.AccessResult) {
                                case 0:
                                    /*获得详细信息*/
                                    List<WorkDetailResult> list = Result.WorkDetailsOutputParams;
                                    if (list != null) {
                                        /*显示工作详细信息*/
                                        ShowWorkContext(list.get(0));
                                    }
                                    break;
                                case -1:
                                    Toast.makeText(WorkItemDetail.this, "请求失败，请重新尝试", Toast.LENGTH_SHORT).show();
                                    break;
                                case -3:
                                    Toast.makeText(WorkItemDetail.this, "服务端验证出错，请联系管理员", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    } catch (Exception e) {
                    }
                } else {
                    Toast.makeText(WorkItemDetail.this, "没有获取到当前工作项信息", Toast.LENGTH_SHORT).show();
                }
            } else if (msg.what == ThreadAndHandlerLabel.GetChildWorkItem) {
                if (msg.obj != null) {
                    Log.i("工作子项返回值:" + msg.obj.toString(), "zst_test");
                    try {
                        ChildWorkItemEntity entity = JsonEntity.ParseJsonForChildWorkItemEntity(msg.obj.toString());
                        if (entity != null) {
                            switch (entity.AccessResult) {
                                case 0:
                                    /*获得当前工作项子项详细信息*/
                                    chid = entity.WorkSubItemParams;
                                    if (chid != null) {
                                        /*显示子项*/
                                        SetList();
                                    }
                                    break;
                                case -1:
                                    Toast.makeText(WorkItemDetail.this, "请求失败，请重新尝试", Toast.LENGTH_SHORT).show();
                                    break;
                                case -3:
                                    Toast.makeText(WorkItemDetail.this, "服务端验证出错，请联系管理员", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } else {
                            Toast.makeText(WorkItemDetail.this, "没有获取到当前工作项信息", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                    }
                } else {
                    Toast.makeText(WorkItemDetail.this, "没有获取到当前工作项信息", Toast.LENGTH_SHORT).show();
                }
            } else if (msg.what == ThreadAndHandlerLabel.CallAPIError) {
                Toast.makeText(WorkItemDetail.this, "请求网络失败，请检查网络连接", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_item_detail);
        try {
            Reservoir.init(this, 4096);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initView();

        GetDataForCache();

        ShowWorkItem();

        GetDataForService();

        SetPermissions();

        Listener();

        SetPopAdapter();
    }

    /*UI绑定*/
    private void initView() {
        back = (ImageView) this.findViewById(R.id.wid_back);
        wid_show_chid = (ImageView) findViewById(R.id.wid_show_chid);
        menu = (ImageView) findViewById(R.id.anwi_menu);
        main_workitem = (LinearLayout) findViewById(R.id.main_workitem);
        child_list = (ListView) findViewById(R.id.wid_list);
        returen = (ImageView) findViewById(R.id.wid_return);
        work_status = (ImageView) findViewById(R.id.work_state_img);
        work_img = (ImageView) findViewById(R.id.work_img);
        name_edt = (EditText) findViewById(R.id.wid_workname_edt);
        assign2_edt = (EditText) findViewById(R.id.wid_Assigned2_edt);
        checker_edt = (EditText) findViewById(R.id.wid_Checker_edt);
        status_edt = (EditText) findViewById(R.id.wid_Status_edt);
        del_layout = (RelativeLayout) findViewById(R.id.del_choose_layout);
        del_ok = (Button) findViewById(R.id.del_ok);
        del_cancel = (Button) findViewById(R.id.del_cancel);
        Head_edt = (EditText) findViewById(R.id.wid_Head_edt);
        wid_workname = (TextView) findViewById(R.id.wid_workname);
        wid_workvalue = (TextView) findViewById(R.id.wid_workvalue);
        starttime_tex = (TextView) findViewById(R.id.starttime_tex);
        endtime_tex = (TextView) findViewById(R.id.endtime_tex);
        creater_tex = (TextView) findViewById(R.id.wid_Creater_txt);
        creatertime_tex = (TextView) findViewById(R.id.wid_CreateTime_txt);
        modifier_tex = (TextView) findViewById(R.id.wid_Modifier_txt);
        modifytime_tex = (TextView) findViewById(R.id.wid_ModifyTime_txt);
        value_edt = (EditText) findViewById(R.id.wid_Value_edt);
        closingtime_edt = (EditText) findViewById(R.id.wid_Value_edt);
        des_edt = (EditText) findViewById(R.id.wid_Description_edt);
    }

    /*获取跳转传递信息*/
    private void ShowWorkItem() {
        Intent intent = getIntent();
        WorkItemResultParams WorkItem = (WorkItemResultParams) intent.getSerializableExtra("Item");
        if (WorkItem.Category == 0) {
            work_img.setImageResource(R.drawable.t);
        } else {
            work_img.setImageResource(R.drawable.b);
        }
        WorkID = WorkItem.WorkID;
        wid_workname.setText(WorkItem.WorkName);
        wid_workvalue.setText(WorkItem.Workscore + "");
        switch (WorkItem.Status) {
            case 0:
                work_status.setImageResource(R.drawable.status0);
                break;
            case 1:
                work_status.setImageResource(R.drawable.status1);
                break;
            case 2:
                work_status.setImageResource(R.drawable.status2);
                break;
            case 3:
                work_status.setImageResource(R.drawable.status3);
                break;
            case 4:
                work_status.setImageResource(R.drawable.status4);
                break;
            case 5:
                work_status.setImageResource(R.drawable.status5);
                break;
            case 6:
                work_status.setImageResource(R.drawable.status6);
                break;
            case 7:
                work_status.setImageResource(R.drawable.status7);
                break;
            default:
                work_status.setImageResource(R.drawable.status0);
                break;
        }
        starttime_tex.setText(WorkItem.StartTime.substring(0, 10));
        endtime_tex.setText(WorkItem.EndTime.substring(0, 10));
    }

    /*显示工作详细信息*/
    private void ShowWorkContext(WorkDetailResult result) {
        name_edt.setText(result.WorkName);
        assign2_edt.setText(result.AssignerName);
        checker_edt.setText(result.CheckerName);
        //Head_edt.setText(result.Nam);
        creater_tex.setText(result.Creater);
        creatertime_tex.setText(result.CreateTime.toString());
        modifier_tex.setText(result.ModifierName);
        modifytime_tex.setText(result.ModifyTime.toString());
        status_edt.setText(result.Status);
        value_edt.setText(result.BusinessValue + "");
        closingtime_edt.setText(result.Deadline.toString());
        des_edt.setText(result.Description);
    }

    /*事件监听相关*/
    private void Listener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        main_workitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (child_list.getVisibility() == View.GONE) {
                    child_list.setVisibility(View.VISIBLE);
                    SetList();
                    wid_show_chid.setImageResource(R.drawable.arrow_up_float);
                } else {
                    child_list.setVisibility(View.GONE);
                    wid_show_chid.setImageResource(R.drawable.arrow_down_float);
                }
            }
        });
        child_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //有子项则  可点击切换数据
                Log.e("dianji", "tag");
                TextView itemId = (TextView) view.findViewById(R.id.workid_tex);
                if (itemId != null) {
                    CurItemId = itemId.getText().toString().trim();
                }
                if (isdel == true) {
                    //判断选择框是否可见 选中
                    CheckBox checkBox = (CheckBox) view.findViewById(R.id.child_che);
                    if (checkBox != null) {
                        if (checkBox.isChecked()) {
                            checkBox.setChecked(false);
                        } else {
                            checkBox.setChecked(true);
                        }
                    }
                    return;
                }
            }
        });
        del_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //检测是否有选中项，则删除
                if (child_list.getVisibility() == View.GONE) {
                    child_list.setVisibility(View.VISIBLE);
                    SetList();
                    wid_show_chid.setImageResource(R.drawable.arrow_up_float);
                }
                for (int i = 0; i < child_list.getCount(); i++) {
                    View view = child_list.getChildAt(i);
                    CheckBox checkBox = (CheckBox) view.findViewById(R.id.child_che);
                    ImageView img = (ImageView) view.findViewById(R.id.child_img);
                    img.setVisibility(View.VISIBLE);
                    checkBox.setVisibility(View.GONE);
                }
                isdel = false;
                del_layout.setVisibility(View.GONE);
            }
        });
        returen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //有父项则
                //从缓存中取当前对象，若FID不为空，则显示父项内容

            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final PopupMenuForWorkItem popWindow = new PopupMenuForWorkItem(WorkItemDetail.this);
                popWindow.showPopupWindow(findViewById(R.id.anwi_menu));
                popWindow.add_child.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        //do something you need here
                        //跳转添加子项界面
                        Intent intent = new Intent(WorkItemDetail.this, AddNewWorkItem.class);
                        startActivity(intent);
                        intent.putExtra("FatherID", CurItemId);
                        intent.putExtra("FatherType", "");
                        popWindow.showPopupWindow(findViewById(R.id.anwi_menu));
                    }
                });
                popWindow.del_child.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // do something before signing out
                        //删除选中的子项
                        if (isdel != false) {
                            isdel = false;
                            del_layout.setVisibility(View.GONE);
                            if (child_list.getVisibility() == View.GONE) {
                                child_list.setVisibility(View.VISIBLE);
                                SetList();
                                wid_show_chid.setImageResource(R.drawable.arrow_up_float);
                            }
                            for (int i = 0; i < child_list.getCount(); i++) {
                                View view = child_list.getChildAt(i);
                                CheckBox checkBox = (CheckBox) view.findViewById(R.id.child_che);
                                ImageView img = (ImageView) view.findViewById(R.id.child_img);
                                img.setVisibility(View.VISIBLE);
                                checkBox.setVisibility(View.GONE);
                            }
                            popWindow.showPopupWindow(findViewById(R.id.anwi_menu));
                            return;
                        }
                        Log.e("isdel=false", "aa");
                        isdel = true;
                        if (child_list.getVisibility() == View.GONE) {
                            Log.e("list gone", "aa");
                            child_list.setVisibility(View.VISIBLE);
                            SetList();
                            wid_show_chid.setImageResource(R.drawable.arrow_up_float);
                        }
                        Log.e("list for", "aa");
                        child_list.setVisibility(View.VISIBLE);
                        for (int i = 0; i < child_list.getCount(); i++) {
                            View view = child_list.getChildAt(i);
                            Log.e("list view i", "aa");
                            CheckBox checkBox = (CheckBox) view.findViewById(R.id.child_che);
                            Log.e("list che i", "aa");
                            ImageView img = (ImageView) view.findViewById(R.id.child_img);
                            Log.e("list img i", "aa");
                            img.setVisibility(View.GONE);
                            checkBox.setVisibility(View.VISIBLE);
                            Log.e("list for i", "aa");
                        }
                        Log.e("list for end", "aa");
                        del_layout.setVisibility(View.VISIBLE);
                        Log.e("dellayout", "aa");
                        popWindow.showPopupWindow(findViewById(R.id.anwi_menu));

                    }
                });
                popWindow.del_father.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // do something you need here
                        // 该项状态置为废除
                        status_edt.setText("废除");
                        popWindow.showPopupWindow(findViewById(R.id.anwi_menu));
                    }
                });
                popWindow.submit.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // do something you need here
                        // 提交修改结果
                        popWindow.showPopupWindow(findViewById(R.id.anwi_menu));
                    }
                });
                popWindow.change_status.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // do something you need here
                        // 改变状态，进入下一状态
                        popWindow.showPopupWindow(findViewById(R.id.anwi_menu));
                    }
                });
            }
        });
        assign2_edt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getX() >= (v.getWidth() - ((EditText) v)
                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Assign_pop.show();
                        return true;
                    }
                }
                return false;
            }
        });
        checker_edt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getX() >= (v.getWidth() - ((EditText) v)
                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Checker_pop.show();
                        return true;
                    }
                }
                return false;
            }
        });
        Head_edt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getX() >= (v.getWidth() - ((EditText) v)
                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Head_pop.show();
                        return true;
                    }
                }
                return false;
            }
        });
        status_edt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getX() >= (v.getWidth() - ((EditText) v)
                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Status_pop.show();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    /*权限设置*/
    private void SetPermissions() {
    }

    /*设置子项List样式*/
    private void SetList() {
        SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.child_list_item, new String[]{"workImg", "workId", "workName", "workValue", "workState", "strartTime", "endingTime"},
                new int[]{R.id.work_img, R.id.workid_tex, R.id.workname_tex, R.id.workvalue_tex, R.id.work_state_img, R.id.strarttime_tex, R.id.endtime_tex});
        child_list.setAdapter(adapter);
    }

    /*设置子项List数据*/
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        List<SubWorkItemParams> subWorkItemParamses = chid;
        if (subWorkItemParamses != null && subWorkItemParamses.size() != 0) {
            for (SubWorkItemParams subItem : subWorkItemParamses) {
                if (subItem.WorkType == 0) {
                    map.put("workimg", R.drawable.b);
                } else {
                    map.put("workimg", R.drawable.t);
                }
                //根据状态不同图片不同
                switch (subItem.Status) {
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
                map.put("workId", subItem.WorkID);
                map.put("workName", subItem.WorkName);
                map.put("endingTime", subItem.EndTime);
                map.put("workValue", subItem.Score);
                map.put("strartTime", subItem.StartTime);
                list.add(map);
            }

        }
        return list;
    }

    /*获取本地数据*/
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

    /*获取服务端数据*/
    private void GetDataForService() {
        if (WorkID.equals("") || TKID.equals("")) {
            Toast.makeText(WorkItemDetail.this, "读取缓存失败，请检查内存重新登录", Toast.LENGTH_SHORT).show();
            /*清除其余活动中Activity以及全部缓存显示登录界面*/
        } else {
            /*获取当前项详细信息*/
            WorkItemBusiness business = new WorkItemBusiness();
            business.GetWorkItemContent(handler, WorkID, TKID);
            /*获取子项*/
            business.GetChildWorkItem(handler, WorkID, TKID, 1);
        }

    }

    /*设置下拉菜单相关*/
    private void SetPopAdapter() {
        Assign_pop = new ListPopupWindow(this);
        Assign_pop.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Userlist));
        Assign_pop.setAnchorView(assign2_edt);
        Assign_pop.setModal(true);
        Assign_pop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = Userlist.get(position);
                assign2_edt.setText(item);
                assign2_edt.setSelection(item.length());
                Assign_pop.dismiss();
            }
        });
        Checker_pop = new ListPopupWindow(this);
        Checker_pop.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Userlist));
        Checker_pop.setAnchorView(checker_edt);
        Checker_pop.setModal(true);
        Checker_pop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = Userlist.get(position);
                checker_edt.setText(item);
                checker_edt.setSelection(item.length());
                Checker_pop.dismiss();
            }
        });
        Head_pop = new ListPopupWindow(this);
        Head_pop.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Userlist));
        Head_pop.setAnchorView(Head_edt);
        Head_pop.setModal(true);
        Head_pop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = Userlist.get(position);
                Head_edt.setText(item);
                Head_edt.setSelection(item.length());
                Head_pop.dismiss();
            }
        });
        Status_pop = new ListPopupWindow(this);
        Status_pop.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Statrlist));
        Status_pop.setAnchorView(status_edt);
        Status_pop.setModal(true);
        Status_pop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = Statrlist.get(position);
                status_edt.setText(item);
                status_edt.setSelection(item.length());
                Status_pop.dismiss();
            }
        });
    }
}
