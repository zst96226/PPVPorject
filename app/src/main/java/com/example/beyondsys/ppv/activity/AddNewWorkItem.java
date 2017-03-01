package com.example.beyondsys.ppv.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.anupcowkur.reservoir.Reservoir;
import com.anupcowkur.reservoir.ReservoirPutCallback;
import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.bussiness.OtherBusiness;
import com.example.beyondsys.ppv.bussiness.WorkItemBusiness;
import com.example.beyondsys.ppv.entities.AddWorkItemResult;
import com.example.beyondsys.ppv.entities.IdentifyResult;
import com.example.beyondsys.ppv.entities.LocalDataLabel;
import com.example.beyondsys.ppv.entities.TeamEntity;
import com.example.beyondsys.ppv.entities.ThreadAndHandlerLabel;
import com.example.beyondsys.ppv.entities.UIDEntity;
import com.example.beyondsys.ppv.entities.UserInTeam;
import com.example.beyondsys.ppv.entities.UserInTeamResult;
import com.example.beyondsys.ppv.entities.WorkItemEntity;
import com.example.beyondsys.ppv.tools.InputScoreDialog;
import com.example.beyondsys.ppv.tools.JsonEntity;
import com.example.beyondsys.ppv.tools.ListPopupWindowAdapter;
import com.example.beyondsys.ppv.tools.PopupMenuForWorkItem;
import com.example.beyondsys.ppv.tools.Tools;
import com.example.beyondsys.ppv.tools.ValidaService;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;

public class AddNewWorkItem extends Activity {

    ImageView back;
    private LinearLayout inputScore_layout,ok_layout;
    private InputScoreDialog dialog;
    private TextView input_score,show_score;
    ArrayList<String> listID =new ArrayList<String>();// new String[]{"空","张三", "李四", "王五", "赵六"};
    ArrayList<String> list =new ArrayList<String>();
    ArrayList< String> typeList=new ArrayList<String>();//new String[]{"事项","任务"};
    ArrayList< String> statusList= new ArrayList<String>();
    EditText input_Name,input_des;
    private  TextView  input_AssignedTo, input_Head, input_Checker,
            input_CloseTime,input_type,input_status;
    ListPopupWindow AssignedTo_pop, Head_pop, Checker_pop,Type_pop;
    private Context mContext;
    private ListPopupWindowAdapter mListPopupWindowAdapter;
    private String UID;
    private double SumScale;
    private Handler handler=new Handler()
    {
        public void handleMessage(Message msg) {
            if (msg.what == ThreadAndHandlerLabel.GetAllStaff)
            {
                if(msg.obj!=null)
                {
                    Log.i("获取团队成员返回值：" + msg.obj, "FHZ");
                    String jsonStr = msg.obj.toString();
                    /*解析Json*/
                    try {
                        UserInTeamResult  result= JsonEntity.ParseJsonForUserInTeamResult(jsonStr);
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
                                      //  setCache();
                                    }

                                    @Override
                                    public void onFailure(Exception e) {

                                    }
                                });
//                            if(userList!=null&&userList.size()!=0)
//                            {
//                                for (UserInTeam user: userList)
//                                {
//                                    list.add(user.UserName);
//                                }
//                            }
                            }
                        }
                    }catch (Exception e){}
                }else{
                    Toast.makeText(AddNewWorkItem.this, "服务端验证出错，请联系管理员", Toast.LENGTH_SHORT).show();
                }
            }else if(msg.what==ThreadAndHandlerLabel.AddWorkItem)
            {
                //未完成
                String jsonStr = msg.obj.toString();
                if(msg.obj!=null&& !jsonStr.equals("anyType{}"))
                {
                    Log.i("添加工作项返回值：" + msg.obj, "FHZ");
                    try{
                        int flag=Integer.parseInt(msg.obj.toString());
                        if(flag==0)
                        {
                            Toast.makeText(AddNewWorkItem.this, "新建成功", Toast.LENGTH_SHORT).show();
                            setResult(1);
                            finish();
                        }else{
                            Toast.makeText(AddNewWorkItem.this, "新建失败", Toast.LENGTH_SHORT).show();
                        }
//                        AddWorkItemResult addWorkItemResult =JsonEntity.ParseJsonForAddResult(jsonStr);
//                        if(addWorkItemResult!=null)
//                        {
//                            if(addWorkItemResult.result==0)
//                            {
//                                Toast.makeText(AddNewWorkItem.this, "新建成功", Toast.LENGTH_SHORT).show();
//                            }
//                        }
                    }catch (Exception e){}

                }else{
                    Toast.makeText(AddNewWorkItem.this, "服务端验证出错，请联系管理员", Toast.LENGTH_SHORT).show();
                }

            }else if (msg.what == ThreadAndHandlerLabel.CallAPIError) {
                Toast.makeText(AddNewWorkItem.this, "请求失败，请检查网络连接", Toast.LENGTH_SHORT).show();
            } else if (msg.what == ThreadAndHandlerLabel.LocalNotdata) {
                Toast.makeText(AddNewWorkItem.this, "读取缓存失败，请检查内存重新登录", Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_work_item);

        InitView();
        setCache();
        initDate();
        Listener();


        SetPopWinForChecker();
        setPopWinForType();
    }

    private void InitView() {
        mContext=this;
        back = (ImageView) this.findViewById(R.id.anwi_back);
        inputScore_layout = (LinearLayout) findViewById(R.id.inputScore_layout);
        input_score = (TextView) findViewById(R.id.input_score);
        show_score = (TextView) findViewById(R.id.showScore_tex);
        input_AssignedTo = (TextView) findViewById(R.id.input_AssignedTo);
        input_Head = (TextView) findViewById(R.id.input_Head);
        input_Checker = (TextView) findViewById(R.id.input_Checker);
        input_CloseTime=(TextView)findViewById(R.id.input_endtime);
        input_type=(TextView) findViewById(R.id.input_Type);
        ok_layout=(LinearLayout) findViewById(R.id.ok_layout);
        input_Name=(EditText) findViewById(R.id.input_titlename);
        input_status=(TextView) findViewById(R.id.input_state);
        input_des=(EditText)findViewById(R.id.input_des);
        list.add("空");
        listID.add("");
        typeList.add("事项");
        typeList.add("任务");
        statusList.add("已作废");
        statusList.add("新建未指派");
        statusList.add("指派待确认");
        statusList.add("确认待开始");
        statusList.add("进行中");
        statusList.add("提交待确认");
        statusList.add("确认待测试");
        statusList.add("迭代中");
        statusList.add("迭代完待结束");
        statusList.add("完成");
        setStatus();
        input_type.setText(typeList.get(0));



    }

    private  void initDate()
    {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //实际上是传递ID过来，根据ID在缓存中取实体类对象，或从服务器取
        String FID=bundle.getString("FatherID").trim();
        String FType=bundle.getString("FatherType").trim();
        if(!FID.isEmpty())
        {
            input_type.setText(typeList.get(0));
        }else {
            if (FType.equals(typeList.get(1)))
            {
                input_type.setText(typeList.get(1));
            }
        }
        try {
            if (Reservoir.contains(LocalDataLabel.UserID)) {
                UIDEntity entity = Reservoir.get(LocalDataLabel.UserID, UIDEntity.class);
                UID = entity.UID;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  boolean setCache()
    {
        Log.i(" userinteam  SetCAche","aa");
        try
        {
            if(Reservoir.contains(LocalDataLabel.AllUserInTeam))
            {
                Log.i(" userinteam  SetCAche","aa");
                Type resultType = new TypeToken<List<UserInTeam>>() {
                }.getType();
                List<UserInTeam> entityList = Reservoir.get(LocalDataLabel.AllUserInTeam, resultType);
                if(entityList!=null&&entityList.size()!=0)
                {
                    for (UserInTeam user: entityList)
                    {
                        list.add(user.UserName);
                        listID.add(user.UserID);
                        Log.i(" userinteam", "aa");
                    }
                    return  true;
                }
            }
        }catch (Exception e){}
        return  false;
    }
    private  void setService() {
        List<TeamEntity> label=null;
        try{
            Log.i("label try","FHZ");
            if(Reservoir.contains(LocalDataLabel.Label))
            {
                Log.i("label","FHZ");
                Type resultType = new TypeToken<List<TeamEntity>>() {
                }.getType();
                label = Reservoir.get(LocalDataLabel.Label, resultType);
                //  label=Reservoir.get(LocalDataLabel.Label,IdentifyResult.class);
                Log.i("label","FHZ");
            }
        }catch (Exception e)
        {
            Log.i("label excep","FHZ");
            e.printStackTrace();
        }
        if(label!=null)
        {
            String TeamID=label.get(0).TeamID;
            Log.i(" userinteam  SETservice", "FHZ");

            return ;
        }
        //获取团队信息失败
        Toast.makeText(AddNewWorkItem.this, "读取团队信息失败！", Toast.LENGTH_SHORT).show();
    }

    protected void showDatePickDlg() {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewWorkItem.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                 monthOfYear=monthOfYear+1;
                String dateStr="",monStr="",dayStr="";
                if(monthOfYear<10)
                {
                    monStr="0" + monthOfYear;
                }else{
                    monStr=String.valueOf(monthOfYear);
                }
                if(dayOfMonth<10)
                {
                    dayStr="0"+dayOfMonth;
                }else{
                    dayStr=String.valueOf(dayOfMonth);
                }
                dateStr=year + "-" + monStr + "-" +dayStr;
                Log.i(dateStr+"dateStr","FHZ");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String nowStr=sdf.format(new  java.util.Date());
                Log.i(nowStr+"nowStr","FHZ");
                Date nowTime=null,dateTime=null;
                try {
                  nowTime =sdf.parse(nowStr);
                  dateTime=sdf.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(nowTime.getTime()>=dateTime.getTime())
                {
                    input_CloseTime.setText("");
                }else{
                    input_CloseTime.setText(dateStr);
                }

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    private WorkItemEntity submitEntity() {
        String Name,Assigned2,Belong2, Checker,Description,BID, FID,ID,ClosingTime;
        int  Status,Category,TheTimeStamp;
        double  BusinessValue;
        ID= Tools.GetGUID();
        if(input_CloseTime.getText().toString().trim().equals("")||input_CloseTime.getText().toString().trim().isEmpty())
        {
            Toast.makeText(AddNewWorkItem.this, "结束日期要大于今天！", Toast.LENGTH_SHORT).show();
            return     null;
        }else{
            ClosingTime=input_CloseTime.getText().toString().trim();
        }
//        ClosingTime="2017-01-02";
        Name=input_Name.getText().toString().trim();
        boolean checkName= ValidaService.isTitleLength(Name);
        if(!checkName)
        {
            Toast.makeText(AddNewWorkItem.this, "标题在2~50字符之间！", Toast.LENGTH_SHORT).show();
            return     null;
        }

        if(input_type.getText().toString().equals(typeList.get(0)))
        {
            Category=0;
        }
        else
        {
            Category=1;
        }
        if(input_AssignedTo.getText().toString().trim().equals("空")||input_AssignedTo.getText().toString().trim().isEmpty())
        {
            Assigned2="";
        }else{
            Assigned2=listID.get(list.indexOf(input_AssignedTo.getText().toString().trim()));
        }
        if(input_Head.getText().toString().trim().equals("空")||input_Head.getText().toString().trim().isEmpty())
        {
            if(input_AssignedTo.getText().toString().trim().equals("空")||input_AssignedTo.getText().toString().trim().isEmpty())
            {
                Belong2="";
            }else{
                Toast.makeText(AddNewWorkItem.this, "指派后负责人不能为空！", Toast.LENGTH_SHORT).show();
                return     null;
            }
        }else{
            Belong2=listID.get(list.indexOf(input_Head.getText().toString().trim()));
        }

        Checker=input_Checker.getText().toString().trim();
        if(input_Checker.getText().toString().trim().equals("空")||input_Checker.getText().toString().trim().isEmpty())
        {

            Checker="";
        }else{
            Checker=listID.get(list.indexOf(input_Checker.getText().toString().trim()));
        }

        Description=input_des.getText().toString().trim();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //传递FID过来
        String FatherID=bundle.getString("FatherID").trim();
        if(FatherID.isEmpty())
        {
            FID="";
        }else{
            FID=FatherID;
        }

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
        if(teamList!=null&&!teamList.isEmpty())
        {
            BID=teamList.get(0).TeamID;
        }else{
            BID="";
        }
        Status=0;
        if(input_status.getText().equals(statusList.get(0)))
        {
            Status=0;
        }else
        {
            Status=1;
        }
        TheTimeStamp=1;
        //难度和分数未完成
        if(!show_score.getText().toString().equals("点击估算分值"))
        {
            BusinessValue=Double.valueOf(show_score.getText().toString().trim());
        } else{
            BusinessValue=0.00;
        }
        WorkItemEntity workItem=new WorkItemEntity();
        workItem.TheTimeStamp=TheTimeStamp;
        workItem.Assigned2=Assigned2;
        workItem.Belong2=Belong2;
        workItem.BID=BID;
        workItem.BusinessValue=BusinessValue;
        workItem.Checker=Checker;
        workItem.Category=Category;
        workItem.ClosingTime=ClosingTime;
        workItem.Modifier=UID;
        workItem.CreateTime="";
        workItem.Creater=UID;
        workItem.FID=FID;
        workItem.HardScale=SumScale;
        workItem.Description=Description;
        workItem.ID=ID;
        workItem.Name=Name;
        workItem.Status=Status;
        workItem.Remark=input_score.getText().toString().trim();
        return  workItem;
    }

    private  void setStatus() {
        input_AssignedTo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    String  asssigned=input_AssignedTo.getText().toString().trim();
                    if(asssigned.isEmpty()||asssigned.equals("空")||asssigned.equals(""))
                    {
                        input_status.setText(statusList.get(1));
                    }else
                    {
                        input_status.setText(statusList.get(2));
                    }
                }
            }
        });
    }

    private void Listener() {

        ok_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //对输入信息判断
                //拼装要提交的WorkItemEntity
                //调用服务提交
                //handler中判断提交结果

                WorkItemEntity workItem= submitEntity();
                if(workItem!=null)
                {
                    WorkItemBusiness workItemBusiness=new WorkItemBusiness();
                    workItemBusiness.AddWorkItem(handler,workItem);
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        show_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  showDialog();
                estimateValue();
            }
        });
        input_CloseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickDlg();
            }
        });
        input_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();
                //实际上是传递ID过来，根据ID在缓存中取实体类对象，或从服务器取
                String FID = bundle.getString("FatherID").trim();
                String FType = bundle.getString("FatherType").trim();
                if (!FID.isEmpty()) {
                    if (FType != typeList.get(1)) {
                        setPopWinForType();
                        Type_pop.show();
                    }
                }

            }
        });
//        input_type.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                final int DRAWABLE_RIGHT = 2;
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (event.getX() >= (v.getWidth() - ((EditText) v)
//                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//                        Intent intent = getIntent();
//                        Bundle bundle = intent.getExtras();
//                        //实际上是传递ID过来，根据ID在缓存中取实体类对象，或从服务器取
//                        String FID = bundle.getString("FatherID").trim();
//                        String FType = bundle.getString("FatherType").trim();
//                        if (!FID.isEmpty()) {
//                            if (FType != typeList.get(1)) {
//                                Type_pop.setAnchorView(v);
//                                Type_pop.show();
//                            }
//                        }
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });
        input_type.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();
                //传递FID过来
                String FID = bundle.getString("FatherID").trim();
                String FType = bundle.getString("FatherType").trim();
                if (FID == null) {
                    input_type.setText(typeList.get(0));
                } else {
                    if (FType == typeList.get(1)) {
                        input_type.setText(typeList.get(1));
                    } else {
                        String inputstr = input_type.getText().toString();
                        int strlen = inputstr.length();
                        for (int i = 0; i < typeList.size(); i++) {
                            if (typeList.get(i).length() > strlen) {
                                String str = typeList.get(i).substring(0, strlen);
                                if (str.equals(inputstr)) {
                                    input_type.setText(typeList.get(i));
                                    // input_type.setSelection(typeList.get(i).length());
                                }
                            }
                        }
                    }
                }
//                for (String str: typeList)
//                {
//                    if(input_type.getText().toString().trim().equals(str))
//                    {
//                        return;
//                    }else
//                    {
//                        input_type.setText("");
//                    }
//                }
            }
        });
        input_AssignedTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("click", "FHZ");
                SetPopWinForAssignedTo();
                //AssignedTo_pop.setAnchorView(v);
                AssignedTo_pop.show();
                Log.i("click SHOW", "FHZ");
            }
        });
//        input_AssignedTo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(!hasFocus)
//                {
//                    if(input_AssignedTo.getText().toString().trim().equals("")||input_AssignedTo.getText().toString().trim().equals("空"))
//                    {
//                        input_status.setText(statusList.get(1));
//                    }else{
//                        input_status.setText(statusList.get(2));
//                    }
//                }
//            }
//        });
//        input_AssignedTo.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                final int DRAWABLE_RIGHT = 2;
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (event.getX() >= (v.getWidth() - ((EditText) v)
//                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//                        AssignedTo_pop.setAnchorView(v);
//                        AssignedTo_pop.show();
//
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });

        input_AssignedTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(input_AssignedTo.getText().toString().trim().equals("")||input_AssignedTo.getText().toString().trim().equals("空"))
                {
                    input_status.setText(statusList.get(1));
                }else{
                    input_status.setText(statusList.get(2));
                }
                String inputstr = input_AssignedTo.getText().toString();
                int strlen = inputstr.length();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).length() > strlen) {
                        String str = list.get(i).substring(0, strlen);
                        if (str.equals(inputstr)) {
                            input_AssignedTo.setText(list.get(i));
                           // input_AssignedTo.setSelection(list.get(i).length());
                        }
                    }
                }
//                for (String str: list)
//                {
//                    if(input_AssignedTo.getText().toString().trim().equals(str))
//                    {
//                        return;
//                    }else
//                    {
//                        input_AssignedTo.setText("");
//                    }
//                }
            }
        });
        input_Head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetPopWinForHead();
                Head_pop.show();
            }
        });
//        input_Head.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                final int DRAWABLE_RIGHT = 2;
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (event.getX() >= (v.getWidth() - ((EditText) v)
//                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//                        Head_pop.setAnchorView(v);
//                        Head_pop.show();
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });
        input_Head.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String inputstr = input_Head.getText().toString();
                int strlen = inputstr.length();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).length() > strlen) {
                        String str = list.get(i).substring(0, strlen);
                        if (str.equals(inputstr)) {
                            input_Head.setText(list.get(i));
                          //  input_Head.setSelection(list.get(i).length());
                        }
                    }
                }
//                for (String str: list)
//                {
//                    if( input_Head.getText().toString().trim().equals(str))
//                    {
//                        return;
//                    }else
//                    {
//                        input_Head.setText("");
//                    }
//                }
            }
        });
        input_Checker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetPopWinForChecker();
                Checker_pop.show();
            }
        });
//        input_Checker.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                final int DRAWABLE_RIGHT = 2;
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (event.getX() >= (v.getWidth() - ((EditText) v)
//                            .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
//                        Checker_pop.setAnchorView(v);
//                        Checker_pop.show();
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });
        input_Checker.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String inputstr = input_Checker.getText().toString();
                int strlen = inputstr.length();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).length() > strlen) {
                        String str =list.get(i).substring(0, strlen);
                        if (str.equals(inputstr)) {
                            input_Checker.setText(list.get(i));
                           // input_Checker.setSelection(list.get(i).length());
                        }
                    }
                }
//                for (String str: list)
//                {
//                    if(input_Checker.getText().toString().trim().equals(str))
//                    {
//                        return;
//                    }else
//                    {
//                        input_Checker.setText("");
//                    }
//                }
            }
        });
    }

//    private void showDialog() {
//        dialog = new InputScoreDialog(AddNewWorkItem.this);
//        dialog.setOkListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //确定键
//                Log.e("qqwwok", "qqww");
//
//                    Log.e("qqww调用1", "qqww");
//                    inputShow();
//
//                //提示输入有误
//                dialog.dismiss();
//            }
//        });
//        dialog.setCancelListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }

    private void estimateValue() {
        Intent estimate = new Intent(AddNewWorkItem.this, EstimateValueActivity.class);
        Log.e("esti", "qqww");
        startActivityForResult(estimate, 1);
    }

//    private boolean inputCheck() {
//        //对各价值估算各输入框进行输入验证
//        return true;
//    }

//    private void inputShow() {
//        //将合法输入填充到控件上
//        input_score.setText(dialog.getStepDetail());
//    }

    private void SetPopWinForAssignedTo() {
        AssignedTo_pop = new ListPopupWindow( mContext);
        //自定义Adapter
        mListPopupWindowAdapter=new ListPopupWindowAdapter(list, mContext);
        AssignedTo_pop.setAdapter(mListPopupWindowAdapter);
        //mListPopupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_launcher));
        AssignedTo_pop.setWidth(input_AssignedTo.getWidth());
        AssignedTo_pop.setHeight(200);
//        AssignedTo_pop.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
        AssignedTo_pop.setAnchorView(input_AssignedTo);
        AssignedTo_pop.setModal(true);
        AssignedTo_pop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = list.get(position);
                input_AssignedTo.setText(item);
                // input_AssignedTo.setSelection(item.length());
                AssignedTo_pop.dismiss();
            }
        });
    }
    private  void setPopWinForType() {
        Type_pop=new ListPopupWindow(this);
        mListPopupWindowAdapter=new ListPopupWindowAdapter(typeList, mContext);
        Type_pop.setAdapter(mListPopupWindowAdapter);
        //mListPopupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_launcher));
        Type_pop.setWidth( Type_pop.getWidth());
        Type_pop.setHeight(200);
//        Type_pop.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, typeList));
        Type_pop.setAnchorView(input_type);
        Type_pop.setModal(true);
        Type_pop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item=typeList.get(position);
                input_type.setText(item);
              //  input_type.setSelection(item.length());
                Type_pop.dismiss();
            }
        });
    }
    private void SetPopWinForHead() {
        Head_pop = new ListPopupWindow(this);
        mListPopupWindowAdapter=new ListPopupWindowAdapter(list, mContext);
        Head_pop.setAdapter(mListPopupWindowAdapter);
        //mListPopupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_launcher));
        Head_pop.setWidth( Head_pop.getWidth());
        Head_pop.setHeight(200);
//        Head_pop.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
        Head_pop.setAnchorView(input_Head);
        Head_pop.setModal(true);
        Head_pop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item =list.get(position);
                input_Head.setText(item);
              //  input_Head.setSelection(item.length());
                Head_pop.dismiss();
            }
        });
    }

    private void SetPopWinForChecker() {
        Checker_pop = new ListPopupWindow(this);
        mListPopupWindowAdapter=new ListPopupWindowAdapter(list, mContext);
        Checker_pop.setAdapter(mListPopupWindowAdapter);
        //mListPopupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_launcher));
        Checker_pop.setWidth(  Checker_pop.getWidth());
        Checker_pop.setHeight(200);
//        Checker_pop.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
        Checker_pop.setAnchorView(input_Checker);
        Checker_pop.setModal(true);
        Checker_pop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = list.get(position);
                input_Checker.setText(item);
                // input_Checker.setSelection(item.length());
                Checker_pop.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    input_score.setText(data.getStringExtra("stepDetail"));
                    SumScale=Double.valueOf(data.getStringExtra("sumScale"));
                    //分值计算接口
//                   List<Map<String,Object>> valueParam=( List<Map<String,Object>>)data.getSerializableExtra("valueParam");
                  // Log.i("1MAX:" + valueParam.get(0).get("max"),"FHZ");
                    String value=data.getStringExtra("valueParam");
                    show_score.setText(value);
                } else {
                    input_score.setText("点击估算分值");
                }
                break;
            default:
                break;
        }
    }

}
