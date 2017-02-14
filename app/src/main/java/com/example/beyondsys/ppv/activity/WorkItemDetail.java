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

import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.bussiness.WorkItemBusiness;
import com.example.beyondsys.ppv.dataaccess.ACache;
import com.example.beyondsys.ppv.entities.LocalDataLabel;
import com.example.beyondsys.ppv.entities.ThreadAndHandlerLabel;
import com.example.beyondsys.ppv.entities.WorkDetailResult;
import com.example.beyondsys.ppv.entities.WorkItemEntity;
import com.example.beyondsys.ppv.entities.WorkValueEntity;
import com.example.beyondsys.ppv.tools.GsonUtil;
import com.example.beyondsys.ppv.tools.JsonEntity;
import com.example.beyondsys.ppv.tools.PopupMenuForWorkItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkItemDetail extends AppCompatActivity {
    /*本地缓存操作对象*/
    ACache mCache = null;
    ImageView back;
    ImageView wid_show_chid;
    ImageView returen;
    ImageView menu;
    ImageView work_img,work_status;
    LinearLayout main_workitem;
    ListView child_list;
    private WorkDetailResult workItemEntity;
    private TextView item_name,value_tex,starttime_tex,endingtime_tex,creater_tex,creat_time_tex,modifier_tex,modify_time_tex;
    private  EditText name_edt,assign2_edt,checker_edt,status_edt,value_edt,closetime_edt,des_edt;
    private RelativeLayout del_layout;
    private  Button del_ok,del_cancel;
    private boolean isdel=false;
    private  String id="";
    private Handler handler=new Handler()
    {
        public  void  handleMessage(Message msg)
        {
           if(msg.what== ThreadAndHandlerLabel.GetWorkItemContext)
           {
               if(msg.obj!=null)
               {
                 String jsonStr=msg.obj.toString();
                   try{
                       WorkDetailResult workDetailResult=JsonEntity.ParseJsonForWorkDetailResult(jsonStr);
                       if(workDetailResult!=null){
                          if(workDetailResult.AccessResult==0)
                          {
                             String json= GsonUtil.t2Json2(workDetailResult);
                              mCache.put(LocalDataLabel.WorkItemDetail+id,json);
                          }
                       }
                   }catch (Exception e){}
               }else{
                   Toast.makeText(WorkItemDetail.this, "没有获取到当前工作项信息", Toast.LENGTH_SHORT).show();
               }
            }else if (msg.what == ThreadAndHandlerLabel.CallAPIError) {
                Toast.makeText(WorkItemDetail.this, "修改失败，请检查网络连接", Toast.LENGTH_SHORT).show();
            }else if (msg.what == ThreadAndHandlerLabel.LocalNotdata) {
               Toast.makeText(WorkItemDetail.this, "读取缓存失败，请检查内存重新登录", Toast.LENGTH_SHORT).show();
                /*清除其余活动中Activity以及全部缓存显示登录界面*/
           }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_item_detail);

        initView();

        Listener();

        SetData();
    }

    private void initView() {
        mCache = ACache.get(this);
        back = (ImageView) this.findViewById(R.id.wid_back);
        wid_show_chid = (ImageView) findViewById(R.id.wid_show_chid);
        menu = (ImageView) findViewById(R.id.anwi_menu);
        main_workitem = (LinearLayout) findViewById(R.id.main_workitem);
        child_list = (ListView) findViewById(R.id.wid_list);
        returen = (ImageView) findViewById(R.id.wid_return);
        item_name = (TextView) findViewById(R.id.wid_workname);
        value_tex = (TextView) findViewById(R.id.wid_workvalue);
        starttime_tex = (TextView) findViewById(R.id.starttime_tex);
        endingtime_tex = (TextView) findViewById(R.id.endtime_tex);
        work_status=(ImageView)findViewById(R.id.work_state_img);
        work_img=(ImageView)findViewById(R.id.work_img);
        name_edt=(EditText)findViewById(R.id.wid_workname_edt);
        assign2_edt=(EditText)findViewById(R.id.wid_Assigned2_edt);
        checker_edt=(EditText)findViewById(R.id.wid_Checker_edt);
        creater_tex=(TextView)findViewById(R.id.wid_Creater_txt);
        creat_time_tex=(TextView)findViewById(R.id.wid_CreateTime_txt);
        modifier_tex=(TextView)findViewById(R.id.wid_Modifier_txt);
        modify_time_tex=(TextView)findViewById(R.id.wid_ModifyTime_txt);
        status_edt=(EditText)findViewById(R.id.wid_Status_edt);
        value_edt=(EditText)findViewById(R.id.wid_Value_edt);
        closetime_edt=(EditText)findViewById(R.id.wid_ClosingTime_edt);
        des_edt=(EditText)findViewById(R.id.wid_Description_edt);
        del_layout=(RelativeLayout)findViewById(R.id.del_choose_layout);
        del_ok=(Button)findViewById(R.id.del_ok);
        del_cancel=(Button)findViewById(R.id.del_cancel);
//       workItemEntity=new WorkItemEntity();
//        workItemEntity.BID="BID";
//        workItemEntity.ID="ID";
//        workItemEntity.FID="FID";
//        workItemEntity.Name="Name";
//        workItemEntity.Description="Des";
//        workItemEntity.Category=0;
//        workItemEntity.Status=0;
//        workItemEntity.Assigned2="Assigned";
//        workItemEntity.Belong2="Belong";
//        workItemEntity.Checker="Check";
//        workItemEntity.Creater="Creater";
//        workItemEntity.CreateTime="Createtime";
//        workItemEntity.ClosingTime="ClosingTime";
//        workItemEntity.Modifier="Modifier";
//        workItemEntity.ModifyTime="ModifiyTime";
//        workItemEntity.BusinessValue=0;
//        workItemEntity.BasicScore=0;
//        workItemEntity.CheckedScore=0;
//        workItemEntity.HardScale=0;
//        workItemEntity.Remark="Remark";
    }

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
                //有子项则  可展开 点击进入详情
                Log.e("dianji", "tag");
                setChildData();
                if (isdel == true) {
                    //判断选择框是否可见 选中
                    return;
                }
                //正常操作

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
                for (int i=0;i<child_list.getCount();i++)
                {
                    View view=child_list.getChildAt(i);
                    CheckBox checkBox=(CheckBox)view.findViewById(R.id.child_che);
                    ImageView img=(ImageView)view.findViewById(R.id.child_img);
                    img.setVisibility(View.VISIBLE);
                    checkBox.setVisibility(View.GONE);
                }
                isdel=false;
                del_layout.setVisibility(View.GONE);
            }
        });
        del_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                SetData();
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
                        popWindow.showPopupWindow(findViewById(R.id.anwi_menu));
                    }
                });
                popWindow.del_child.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // do something before signing out
                        //删除选中的子项
                        if(isdel!=false)
                        {
                            isdel=false;
                            del_layout.setVisibility(View.GONE);
                            if (child_list.getVisibility() == View.GONE) {
                                child_list.setVisibility(View.VISIBLE);
                                SetList();
                                wid_show_chid.setImageResource(R.drawable.arrow_up_float);
                            }
                            for (int i=0;i<child_list.getCount();i++)
                            {
                                View view=child_list.getChildAt(i);
                                CheckBox checkBox=(CheckBox)view.findViewById(R.id.child_che);
                                ImageView img=(ImageView)view.findViewById(R.id.child_img);
                                img.setVisibility(View.VISIBLE);
                                checkBox.setVisibility(View.GONE);
                            }
                            popWindow.showPopupWindow(findViewById(R.id.anwi_menu));
                            return;
                        }
                        Log.e("isdel=false","aa");
                        isdel=true;
                        if (child_list.getVisibility() == View.GONE) {
                            Log.e("list gone","aa");
                            child_list.setVisibility(View.VISIBLE);
                            SetList();
                            wid_show_chid.setImageResource(R.drawable.arrow_up_float);
                        }
                        Log.e("list for", "aa");
                        child_list.setVisibility(View.VISIBLE);
                        for (int i=0;i<child_list.getCount();i++)
                        {
                            View view=child_list.getChildAt(i);
                            Log.e("list view i", "aa");
                                CheckBox checkBox=(CheckBox)view.findViewById(R.id.child_che);
                                Log.e("list che i", "aa");
                                ImageView img=(ImageView)view.findViewById(R.id.child_img);
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

    }

    private void SetList() {

            SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.child_list_item, new String[]{"workName", "workValue", "workState", "strartTime", "endingTime"},
                    new int[]{R.id.workname_tex, R.id.workvalue_tex, R.id.work_state_img, R.id.strarttime_tex, R.id.endtime_tex});
            child_list.setAdapter(adapter);
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("workName", "事务1");
        map.put("workState", R.drawable.img_done);
        map.put("endingTime", "17-11-22");
        map.put("workValue", "100");
        map.put("strartTime", "17-11-22");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("workName", "事务2");
        map.put("workState", R.drawable.img_pro);
        map.put("endingTime", "17-11-22");
        map.put("workValue", "100");
        map.put("strartTime", "17-11-22");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("workName", "事务3");
        map.put("workState", R.drawable.img_proing);
        map.put("endingTime", "17-11-22");
        map.put("workValue", "100");
        map.put("strartTime", "17-11-22");
        list.add(map);

        return list;
    }

    private void SetData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //实际上是传递ID过来，根据ID在缓存中取实体类对象，或从服务器取
        id=bundle.getString("ItemID").trim();
        WorkDetailResult hasEntity=JsonEntity.ParseJsonForWorkDetailResult(mCache.getAsString(LocalDataLabel.WorkItemDetail+id));//(WorkItemEntity) mCache.getAsObject(LocalDataLabel.WorkItemDetail+id);
        if(hasEntity==null)
        {
           //根据ID从服务器取 并存缓存
            WorkItemBusiness workItemBusiness=new WorkItemBusiness();
            workItemBusiness.GetWorkItemContent(handler,mCache,id);
        }else
        {
             name_edt.setText(hasEntity.WorkName.toString());
            assign2_edt.setText(hasEntity.AssignerName.toString());
            checker_edt.setText(hasEntity.CheckerName.toString());
            creater_tex.setText(hasEntity.Creater.toString());
            creat_time_tex.setText(hasEntity.CreateTime.toString());
            modifier_tex.setText(hasEntity.ModifierName.toString());
            modify_time_tex.setText(hasEntity.ModifyTime.toString());
            status_edt.setText(String.valueOf(hasEntity.Status));
           // value_edt.setText(String.valueOf(hasEntity.AccessResult));
            closetime_edt.setText(hasEntity.Deadline.toString());
            des_edt.setText(hasEntity.Description.toString());

           item_name.setText(hasEntity.WorkName.toString());
            value_tex.setText(String.valueOf(hasEntity.WorkName));
           // work_img.setImageDrawable(??);
            //work_status.setImageDrawable(??);
            starttime_tex.setText(hasEntity.CreateTime.toString());
          //  endingtime_tex.setText(hasEntity.ClosingTime.toString());
        }
    }
    private  void setChildData()
    {

    }
}
