package com.example.beyondsys.ppv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.entities.ValueDetailEntity;
import com.example.beyondsys.ppv.entities.WorkValueEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhsht on 2017/1/13.工作价值
 */
public class WorkValueView extends Fragment {
    //、、
    private  ListView listView ;
    private View rootView;
    private  LinearLayout sort_layout,filter_layout;
    private RelativeLayout topme_layout;
    private CheckBox topme_che;
    private ImageView sort_img;
    private TextView sort_tex,filter_tex;
   // private WorkValueEntity valueEntity;
    private final  static int sortup=1;
    private  final  static  int sortdown=-1;
    private  int sortFlag=1;
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
                } else {
                    filter_tex.setText(R.string.filter_by_currentmonth);
                }
            }
        });
        topme_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (topme_che.isChecked()) {
                    topme_che.setChecked(false);

                } else {
                    topme_che.setChecked(true);
                }
            }
        });
    }
    private void setAdapter()
    {
        SimpleAdapter adapter =new SimpleAdapter(this.getActivity(),getData() ,R.layout.valueliststyle ,  new String[]{"personImg","personName","valueSum","monthSum"},
                new int[]{R.id.person_img  ,R.id.personname_tex  ,R.id.valuesum_tex ,R.id .monthsum_tex  }) ;
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
        List<WorkValueEntity> entityList=getEntities(sortFlag);
        for (WorkValueEntity valueEntity:entityList) {
            Map<String, Object> map = new HashMap<String, Object>();
           String  MonthCount= valueEntity.MonthCount+"个月";
            map.put("personImg",  R.drawable.person );
            map.put("personName", valueEntity.Name);
            map.put("valueSum",  valueEntity.ScoreCount);
            map.put("monthSum", valueEntity.MonthCount);
            list.add(map);
        }
        return list;
    }

    private  List<WorkValueEntity> getEntities(int sort)
    {
        List<WorkValueEntity> entityList=new ArrayList<WorkValueEntity>();
        for (int i=0;i<10;i++) {
            WorkValueEntity    valueEntity = new WorkValueEntity();
            // valueEntity.IMGTarget="";
            valueEntity.BID = "BID" + i;
            valueEntity.ID = "ID" + i;
            valueEntity.Name = "Name" + i;
            valueEntity.Status = i;
            valueEntity.ScoreCount = (5-i) * 100;
            valueEntity.MonthCount = i;
            entityList.add(valueEntity);
            Log.e(entityList.get(i).ScoreCount+"","qq");
        }
        if(sort==sortdown)
        {
            Collections.sort(entityList, new ValueComparator());
        }
        else
        {
            Collections.sort(entityList,new ValueComparator());
            Collections.reverse(entityList);
        }
         Log.e(entityList.get(4).ScoreCount+"", "qq");
        return  entityList;
    }
    // 自定义比较器：按价值排序
    static class ValueComparator implements Comparator {
        public int compare(Object object1, Object object2) {// 实现接口中的方法
           WorkValueEntity p1 = (WorkValueEntity) object1; // 强制转换
            WorkValueEntity p2 = (WorkValueEntity) object2;
            return new Double(p1.ScoreCount).compareTo(new Double(p2.ScoreCount));
        }
    }

    private  void topme()
    {

    }
}
