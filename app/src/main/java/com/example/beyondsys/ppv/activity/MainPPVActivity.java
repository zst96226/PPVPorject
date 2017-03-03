package com.example.beyondsys.ppv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anupcowkur.reservoir.Reservoir;
import com.anupcowkur.reservoir.ReservoirPutCallback;
import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.bussiness.OtherBusiness;
import com.example.beyondsys.ppv.entities.LocalDataLabel;
import com.example.beyondsys.ppv.entities.TeamEntity;
import com.example.beyondsys.ppv.entities.ThreadAndHandlerLabel;
import com.example.beyondsys.ppv.entities.UserInTeam;
import com.example.beyondsys.ppv.entities.UserInTeamResult;
import com.example.beyondsys.ppv.tools.JsonEntity;
import com.example.beyondsys.ppv.tools.PageAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainPPVActivity extends FragmentActivity implements View.OnClickListener {

    ImageView Add_btn;
    private ArrayList<Fragment> views = new ArrayList<Fragment>();
    private ViewPager viewPager;
    //下方标签区域
    private LinearLayout workitem;
    private LinearLayout workvalue;
    private LinearLayout worknone;
    private LinearLayout oneself;
    //标签区图片
    private ImageView img_workitem;
    private ImageView img_workvalue;
    private ImageView img_worknone;
    private ImageView img_oneself;
    //标签区文字
    private TextView txt_workitem;
    private TextView txt_workvalue;
    private TextView txt_worknone;
    private TextView txt_oneself;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == ThreadAndHandlerLabel.GetAllStaff) {
                if (msg.obj != null) {
                    final String jsonStr = msg.obj.toString();
                    System.out.print("全部人员："+jsonStr);
                    /*解析Json*/
                    try {
                        UserInTeamResult result = JsonEntity.ParseJsonForUserInTeamResult(jsonStr);
                        if (result != null) {
                            if (result.AccessResult == 0) {
                                Reservoir.putAsync(LocalDataLabel.AllUserInTeam, result.teamUsers, new ReservoirPutCallback() {
                                    @Override
                                    public void onSuccess() {
                                        Log.i("cache",jsonStr);
                                    }

                                    @Override
                                    public void onFailure(Exception e) {

                                    }
                                });
                            }
                        }
                    } catch (Exception e) {
                    }
                } else {
                    Toast.makeText(MainPPVActivity.this, "服务端验证出错，请联系管理员", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ppv);


        GetAllUserForTeam();

        initView();

        initData();

    }

    public   void GetAllUserForTeam() {
        List<TeamEntity> label = null;
        try {
            if (Reservoir.contains(LocalDataLabel.Label)) {
                Type resultType = new TypeToken<List<TeamEntity>>() {
                }.getType();
                label = Reservoir.get(LocalDataLabel.Label, resultType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (label != null) {
            String TeamID="";
            if(label.size()>0)
            {
                TeamID= label.get(0).TeamID;
            }
            OtherBusiness other = new OtherBusiness();
            other.GetAllStaffForTeam(handler, TeamID);
        }
    }


    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.view_page);
        workitem = (LinearLayout) findViewById(R.id.btn_workitem);
        workvalue = (LinearLayout) findViewById(R.id.btn_workvalue);
        worknone = (LinearLayout) findViewById(R.id.btn_worknone);
        oneself = (LinearLayout) findViewById(R.id.btn_oneself);

        img_workitem = (ImageView) findViewById(R.id.img_workitem);
        img_workvalue = (ImageView) findViewById(R.id.img_workvalue);
        img_worknone = (ImageView) findViewById(R.id.img_worknone);
        img_oneself = (ImageView) findViewById(R.id.img_oneself);

        txt_workitem = (TextView) findViewById(R.id.txt_workitem);
        txt_workvalue = (TextView) findViewById(R.id.txt_workvalue);
        txt_worknone = (TextView) findViewById(R.id.txt_worknone);
        txt_oneself = (TextView) findViewById(R.id.txt_oneself);

        workitem.setOnClickListener(this);
        workvalue.setOnClickListener(this);
        worknone.setOnClickListener(this);
        oneself.setOnClickListener(this);

        workitem.setSelected(true);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                changeTab(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Add_btn = (ImageView) this.findViewById(R.id.title_btn);
        Add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPPVActivity.this, AddNewWorkItem.class);
                intent.putExtra("FatherID", "");
                intent.putExtra("FatherType", "");
               // startActivity(intent);
                startActivityForResult(intent,1);
            }
        });
    }

    private void initData() {
        Fragment tab_workitem = new WorkItemView();
        Fragment tab_workvalue = new WorkValueView();
        Fragment tab_worknone = new WorkNoneView();
        Fragment tab_oneself = new OneSelfView();
        views.add(tab_workitem);
        views.add(tab_workvalue);
        views.add(tab_worknone);
        views.add(tab_oneself);

        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(), views));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_ppv, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        changeTab(v.getId());
    }

    private void changeTab(int id) {
        workitem.setSelected(false);
        switch (id) {
            case R.id.btn_workitem:
                Add_btn.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(0);
                SetDefault();
                txt_workitem.setTextColor(this.getResources().getColor(R.color.text));
                img_workitem.setImageResource(R.drawable.workitem_yes);
            case 0:
                Add_btn.setVisibility(View.VISIBLE);
                workitem.setSelected(true);
                SetDefault();
                txt_workitem.setTextColor(this.getResources().getColor(R.color.text));
                img_workitem.setImageResource(R.drawable.workitem_yes);
                break;
            case R.id.btn_workvalue:
                Add_btn.setVisibility(View.GONE);
                viewPager.setCurrentItem(1);
                SetDefault();
                txt_workvalue.setTextColor(this.getResources().getColor(R.color.text));
                img_workvalue.setImageResource(R.drawable.workvalue_yes);
            case 1:
                Add_btn.setVisibility(View.GONE);
                workvalue.setSelected(true);
                SetDefault();
                txt_workvalue.setTextColor(this.getResources().getColor(R.color.text));
                img_workvalue.setImageResource(R.drawable.workvalue_yes);
                break;
            case R.id.btn_worknone:
                Add_btn.setVisibility(View.GONE);
                viewPager.setCurrentItem(2);
                SetDefault();
                txt_worknone.setTextColor(this.getResources().getColor(R.color.text));
                img_worknone.setImageResource(R.drawable.none_yes);
            case 2:
                Add_btn.setVisibility(View.GONE);
                worknone.setSelected(true);
                SetDefault();
                txt_worknone.setTextColor(this.getResources().getColor(R.color.text));
                img_worknone.setImageResource(R.drawable.none_yes);
                break;
            case R.id.btn_oneself:
                Add_btn.setVisibility(View.GONE);
                viewPager.setCurrentItem(3);
                SetDefault();
                txt_oneself.setTextColor(this.getResources().getColor(R.color.text));
                img_oneself.setImageResource(R.drawable.oneself_yes);
            case 3:
                Add_btn.setVisibility(View.GONE);
                oneself.setSelected(true);
                SetDefault();
                txt_oneself.setTextColor(this.getResources().getColor(R.color.text));
                img_oneself.setImageResource(R.drawable.oneself_yes);
                break;
            default:
                break;
        }
    }

    private void SetDefault() {
        txt_workitem.setTextColor(this.getResources().getColor(R.color.Gray));
        txt_workvalue.setTextColor(this.getResources().getColor(R.color.Gray));
        txt_worknone.setTextColor(this.getResources().getColor(R.color.Gray));
        txt_oneself.setTextColor(this.getResources().getColor(R.color.Gray));

        img_workitem.setImageResource(R.drawable.workitem_no);
        img_workvalue.setImageResource(R.drawable.workvalue_no);
        img_worknone.setImageResource(R.drawable.none_no);
        img_oneself.setImageResource(R.drawable.oneself_no);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            if(resultCode==1)
            {
                //刷新
            }
        }
    }
}
