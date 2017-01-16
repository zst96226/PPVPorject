package com.example.beyondsys.ppv.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.tools.PageAdapter;

import java.util.ArrayList;

public class MainPPVActivity extends FragmentActivity implements View.OnClickListener {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ppv);

        initView();

        initData();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.view_page);
        workitem = (LinearLayout) findViewById(R.id.btn_workitem);
        workvalue = (LinearLayout) findViewById(R.id.btn_workvalue);
        worknone = (LinearLayout) findViewById(R.id.btn_worknone);
        oneself = (LinearLayout) findViewById(R.id.btn_oneself);

        img_workitem=(ImageView)findViewById(R.id.img_workitem);
        img_workvalue=(ImageView)findViewById(R.id.img_workvalue);
        img_worknone=(ImageView)findViewById(R.id.img_worknone);
        img_oneself=(ImageView)findViewById(R.id.img_oneself);

        txt_workitem=(TextView)findViewById(R.id.txt_workitem);
        txt_workvalue=(TextView)findViewById(R.id.txt_workvalue);
        txt_worknone=(TextView)findViewById(R.id.txt_worknone);
        txt_oneself=(TextView)findViewById(R.id.txt_oneself);

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

        ImageView Add_btn=(ImageView)this.findViewById(R.id.title_btn);
        Add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainPPVActivity.this,AddNewWorkItem.class);
                startActivity(intent);
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
                viewPager.setCurrentItem(0);
                txt_workitem.setTextColor(this.getResources().getColor(R.color.Yellow));
                txt_workvalue.setTextColor(this.getResources().getColor(R.color.White));
                txt_worknone.setTextColor(this.getResources().getColor(R.color.White));
                txt_oneself.setTextColor(this.getResources().getColor(R.color.White));
            case 0:
                workitem.setSelected(true);
                txt_workitem.setTextColor(this.getResources().getColor(R.color.Yellow));
                txt_workvalue.setTextColor(this.getResources().getColor(R.color.White));
                txt_worknone.setTextColor(this.getResources().getColor(R.color.White));
                txt_oneself.setTextColor(this.getResources().getColor(R.color.White));
                break;
            case R.id.btn_workvalue:
                viewPager.setCurrentItem(1);
                txt_workitem.setTextColor(this.getResources().getColor(R.color.White));
                txt_workvalue.setTextColor(this.getResources().getColor(R.color.Yellow));
                txt_worknone.setTextColor(this.getResources().getColor(R.color.White));
                txt_oneself.setTextColor(this.getResources().getColor(R.color.White));
            case 1:
                workvalue.setSelected(true);
                txt_workitem.setTextColor(this.getResources().getColor(R.color.White));
                txt_workvalue.setTextColor(this.getResources().getColor(R.color.Yellow));
                txt_worknone.setTextColor(this.getResources().getColor(R.color.White));
                txt_oneself.setTextColor(this.getResources().getColor(R.color.White));
                break;
            case R.id.btn_worknone:
                viewPager.setCurrentItem(2);
                txt_workitem.setTextColor(this.getResources().getColor(R.color.White));
                txt_workvalue.setTextColor(this.getResources().getColor(R.color.White));
                txt_worknone.setTextColor(this.getResources().getColor(R.color.Yellow));
                txt_oneself.setTextColor(this.getResources().getColor(R.color.White));
            case 2:
                worknone.setSelected(true);
                txt_workitem.setTextColor(this.getResources().getColor(R.color.White));
                txt_workvalue.setTextColor(this.getResources().getColor(R.color.White));
                txt_worknone.setTextColor(this.getResources().getColor(R.color.Yellow));
                txt_oneself.setTextColor(this.getResources().getColor(R.color.White));
                break;
            case R.id.btn_oneself:
                viewPager.setCurrentItem(3);
                txt_workitem.setTextColor(this.getResources().getColor(R.color.White));
                txt_workvalue.setTextColor(this.getResources().getColor(R.color.White));
                txt_worknone.setTextColor(this.getResources().getColor(R.color.White));
                txt_oneself.setTextColor(this.getResources().getColor(R.color.Yellow));
            case 3:
                oneself.setSelected(true);
                txt_workitem.setTextColor(this.getResources().getColor(R.color.White));
                txt_workvalue.setTextColor(this.getResources().getColor(R.color.White));
                txt_worknone.setTextColor(this.getResources().getColor(R.color.White));
                txt_oneself.setTextColor(this.getResources().getColor(R.color.Yellow));
                break;
            default:
                break;
        }
    }
}
