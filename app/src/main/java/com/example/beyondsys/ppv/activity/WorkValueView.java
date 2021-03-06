package com.example.beyondsys.ppv.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
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
import android.widget.Toast;

import com.anupcowkur.reservoir.Reservoir;
import com.anupcowkur.reservoir.ReservoirPutCallback;
import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.bussiness.ImgBusiness;
import com.example.beyondsys.ppv.bussiness.OneSelfBusiness;
import com.example.beyondsys.ppv.bussiness.WorkItemBusiness;
import com.example.beyondsys.ppv.bussiness.WorkValueBusiness;
import com.example.beyondsys.ppv.dataaccess.ACache;
import com.example.beyondsys.ppv.entities.APIEntity;
import com.example.beyondsys.ppv.entities.AccAndPwd;
import com.example.beyondsys.ppv.entities.IdentifyResult;
import com.example.beyondsys.ppv.entities.LocalDataLabel;
import com.example.beyondsys.ppv.entities.PersonInfoEntity;
import com.example.beyondsys.ppv.entities.TeamEntity;
import com.example.beyondsys.ppv.entities.ThreadAndHandlerLabel;
import com.example.beyondsys.ppv.entities.UIDEntity;
import com.example.beyondsys.ppv.entities.UserInfoResultParams;
import com.example.beyondsys.ppv.entities.UserLoginResultEntity;
import com.example.beyondsys.ppv.entities.ValueDetailEntity;
import com.example.beyondsys.ppv.entities.WorkItemResultEntity;
import com.example.beyondsys.ppv.entities.WorkValueEntity;
import com.example.beyondsys.ppv.entities.WorkValueResultEntity;
import com.example.beyondsys.ppv.entities.WorkValueResultParams;
import com.example.beyondsys.ppv.tools.GsonUtil;
import com.example.beyondsys.ppv.tools.JsonEntity;
import com.example.beyondsys.ppv.tools.ListSort;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.File;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zhsht on 2017/1/13.工作价值
 */
public class WorkValueView extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private String TKID = "";
    private String TeamID = "";
    private String UID = "";
    private ListView listView;
    private View rootView;
    private LinearLayout sort_layout, filter_layout;
    private RelativeLayout topme_layout;
    private CheckBox topme_che;
    private ImageView sort_img;
    private TextView sort_tex, filter_tex;
    private List<WorkValueResultParams> valueEntityList = null;
    private UserInfoResultParams curPersonEntity = null;
   // File file;
    private ImgBusiness imgBusiness;
    private SwipeRefreshLayout mSwipeLayout;

    private final static int sortup = 1;
    private final static int sortdown = 0;
    private final static int curmonth = 0;
    private final static int hismonth = 1;
    private int sortFlag = 1, staFlag = 0;//默认升序排列，当前月
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // 停止刷新
            mSwipeLayout.setRefreshing(false);
            if (msg.what == ThreadAndHandlerLabel.GetWorkValue) {
                String jsonStr = msg.obj.toString();
                if (msg.obj != null && !jsonStr.equals("anyType{}")) {
                    Log.i("123","价值返回值;" + msg.obj );
                    try{
                        WorkValueResultEntity entity = JsonEntity.ParseJsonForWorkValueResult(jsonStr);
                        Log.i("价值返回值;" + entity.AccessResult + " " + entity.Score.size(), "FHZ");
                        if (entity != null) {
                            switch (entity.AccessResult) {
                                case 0:
                                    if (entity.Score != null) {
                                        valueEntityList = entity.Score;
                                        setAdapter();
                                    }
                                    break;
                                case 1:
                                    Toast.makeText(WorkValueView.this.getActivity(), "请求失败，请重新尝试", Toast.LENGTH_SHORT).show();
                                    break;
                                case -3:
                                    Toast.makeText(WorkValueView.this.getActivity(), "服务端验证出错，请联系管理员", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    }catch (Exception e)
                    {
                        Log.i("价值返回值;异常","FHZ");
                    }
                } else {
                    Toast.makeText(WorkValueView.this.getActivity(), "服务端验证出错，请联系管理员", Toast.LENGTH_SHORT).show();
                }
            } else if (msg.what == ThreadAndHandlerLabel.CallAPIError) {
                Toast.makeText(WorkValueView.this.getActivity(), "请求失败，请检查网络连接", Toast.LENGTH_SHORT).show();
            } else if (msg.what == ThreadAndHandlerLabel.LocalNotdata) {
                Toast.makeText(WorkValueView.this.getActivity(), "读取缓存失败，请检查内存重新登录", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.workvalue_view, container, false);
        try {
            Reservoir.init(WorkValueView.this.getActivity(), 4096);
        } catch (Exception e) {
            e.printStackTrace();
        }
        init();
        //setAdapter();


        GetDataForCache();

        GetDataForService();

        setListenter();

        return rootView;
    }

    private void init() {
       imgBusiness=new ImgBusiness();
        listView = (ListView) rootView.findViewById(R.id.value_list);
        sort_layout = (LinearLayout) rootView.findViewById(R.id.wv_sort);
        filter_layout = (LinearLayout) rootView.findViewById(R.id.wv_filter);
        topme_layout = (RelativeLayout) rootView.findViewById(R.id.topme_layout);
        topme_che = (CheckBox) rootView.findViewById(R.id.topme_che);
        sort_img = (ImageView) rootView.findViewById(R.id.sort_img);
        sort_tex = (TextView) rootView.findViewById(R.id.wv_sort_txt);
        filter_tex = (TextView) rootView.findViewById(R.id.wv_filter_txt);
        mSwipeLayout = (SwipeRefreshLayout)  rootView.findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeLayout.setDistanceToTriggerSync(400);// 设置手指在屏幕下拉多少距离会触发下拉刷新
        mSwipeLayout.setProgressBackgroundColor(R.color.refresh);
        mSwipeLayout.setSize(SwipeRefreshLayout.LARGE);
    }

    private void setListenter() {
        Log.e("qqww1", "qqww");
        sort_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("qqww2", "qqww");
                if (sort_tex.getText().toString().equals(getResources().getString(R.string.sortup))) {
                    Log.e("qqww3", "qqww");
                    sort_img.setImageResource(R.drawable.sort_down);
                    sort_tex.setText(R.string.sortdown);
                    //降排序函数；
                    sortFlag = sortdown;
                    setAdapter();
                } else {
                    sort_img.setImageResource(R.drawable.sort_up);
                    //  sort_img.setImageDrawable(setImg("123"));
                    sort_tex.setText(R.string.sortup);
                    //升排序函数；
                    sortFlag = sortup;
                    setAdapter();
                }
            }
        });
        filter_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (filter_tex.getText().toString().equals(getResources().getString(R.string.filter_by_currentmonth))) {
                    filter_tex.setText(R.string.filter_by_all);
                    staFlag = hismonth;
                    GetDataForService();
                } else {
                    filter_tex.setText(R.string.filter_by_currentmonth);
                    staFlag = curmonth;
                    GetDataForService();
                }
            }
        });
        topme_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (topme_che.isChecked()) {
                    topme_che.setChecked(false);
                    setAdapter();
//                    GetDataForService();
                } else {
                    topme_che.setChecked(true);
                    setAdapter();
//                    GetDataForService();
                }

            }
        });
    }

    private void setAdapter() {
        SimpleAdapter adapter = new SimpleAdapter(this.getActivity(), getData(), R.layout.valueliststyle,
                new String[]{"personImg", "personId", "personName", "valueSum", "monthSum"},
                new int[]{R.id.person_img, R.id.personid_tex, R.id.personname_tex, R.id.valuesum_tex, R.id.monthsum_tex});
        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if (view instanceof ImageView && data instanceof Bitmap) {
                    ImageView i = (ImageView) view;
                    if(data!=null)
                    {
                        i.setImageBitmap((Bitmap) data);
                    }else{
                        i.setImageResource(R.drawable.unknow);
                    }

                    return true;
                }
                return false;
            }
        });
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String personId = "";
                Intent intent = new Intent(getActivity(), PersonValueDetail.class);
                TextView person = (TextView) view.findViewById(R.id.personid_tex);
                personId = person.getText().toString();

                for (WorkValueResultParams workValue: valueEntityList)
                {
                    if(workValue.UserID.equals(personId))
                    {
                        Bundle bundle = new Bundle();
                        intent.putExtra("personId", personId);
                        bundle.putSerializable("Item", workValue);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
//
//                intent.putExtra("sortFlag",sortFlag);
//                intent.putExtra("staFlag",staFlag);


            }
        });
    }

    private void GetDataForCache() {
        try {
            if (Reservoir.contains(LocalDataLabel.UserID)) {
                UIDEntity entity = Reservoir.get(LocalDataLabel.UserID, UIDEntity.class);
                UID = entity.UID;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    }

    private void GetDataForService() {
        if (TKID.equals("")) {
            Toast.makeText(WorkValueView.this.getActivity(), "读取缓存失败，请检查内存重新登录", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(WorkValueView.this.getContext(), Login.class);
            startActivity(intent);
            this.getActivity().finish();
        } else {
            WorkValueBusiness workValueBusiness = new WorkValueBusiness();
            workValueBusiness.GetWorkValue(handler, TeamID, staFlag, 1, TKID);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
    }

//    private Bitmap setImg(String ImageName) {
//        File fileDir;
//        Bitmap bitmap = null;
//        // Drawable drawable=null;
//        String path = Environment.getExternalStorageDirectory() + "/listviewImg/";// 文件目录
//        fileDir = new File(path);
//        if (!fileDir.exists()) {
//            Log.i("exit", "qq");
//            fileDir.mkdirs();
//        }
//        String picurl = APIEntity.ImagePath + ImageName;
//        file = new File(fileDir, ImageName);
//        if (!file.exists()) {// 如果本地图片不存在则从网上下载
//            ImgBusiness imgBusiness = new ImgBusiness();
//            imgBusiness.downloadImg(picurl, ImageName);
//        } else {// 图片存在则填充到view上
//            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//            // drawable =new BitmapDrawable(bitmap);
//        }
//        return bitmap;
//    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.clear();
        List<WorkValueResultParams> entityList = valueEntityList;
        if (entityList == null || entityList.isEmpty()) {
            return list;
        }
        if (topme_che.isChecked()) {
            /*从List中找到当前用户*/
            WorkValueResultParams os_entity = null;
            for (WorkValueResultParams valueEntity : entityList) {
                if (valueEntity.UserID.equals(UID)) {
                    os_entity = valueEntity;
                }
            }
            List<WorkValueResultParams> listvalue = new ArrayList<>();
            if (os_entity!=null) {
            /*从List中移除自己*/
                for (WorkValueResultParams valueEntity : entityList) {
                    if (!valueEntity.UserID.equals(os_entity.UserID)) {
                        listvalue.add(valueEntity);
                    }
                }
            }
            else
            {
                listvalue=entityList;
            }
            /*按照条件赋值显示*/
            if (sortFlag == sortdown) {
                /*对剩下的数据排序*/
                listvalue = ListSort.DownSort(listvalue);
                /*把当前用户加到第一个*/
                if (os_entity!=null) {
                     listvalue.add(0, os_entity);
                }
                for (WorkValueResultParams valueEntity : listvalue) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    //个人图片 图片用ID命名
//                  Bitmap bitmap = setImg(valueEntity.IMGTarget);
                    String imgname=valueEntity.UserID+".png";
                    Bitmap bitmap = imgBusiness.setImg(imgname);
                    if(bitmap!=null)
                    {
                        map.put("personImg", bitmap);
                    }else{
                        map.put("personImg", R.drawable.unknow);
                    }
                    // map.put("personImg", bitmap);
                    map.put("personId", valueEntity.UserID);
                    map.put("personName", valueEntity.Name);
                    map.put("valueSum", String.valueOf(valueEntity.BasicScore + valueEntity.CheckedScore));
                    map.put("monthSum", valueEntity.Month + "个月");
                    list.add(map);
                }
            } else {
                /*对剩下的数据排序*/
                listvalue = ListSort.UpSort(listvalue);
                /*把当前用户加到第一个*/
                if (os_entity!=null) {
                    listvalue.add(0, os_entity);
                }
                for (WorkValueResultParams valueEntity : listvalue) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    //个人图片 图片用ID命名
//                  Bitmap bitmap = setImg(valueEntity.IMGTarget);
                    String imgname=valueEntity.UserID+".png";
                    Bitmap bitmap = imgBusiness.setImg(imgname);
                    if(bitmap!=null)
                    {
                        map.put("personImg", bitmap);
                    }else{
                        map.put("personImg", R.drawable.unknow);
                    }
                   // map.put("personImg", bitmap);
                    map.put("personId", valueEntity.UserID);
                    map.put("personName", valueEntity.Name);
                    map.put("valueSum", String.valueOf(valueEntity.BasicScore + valueEntity.CheckedScore));
                    map.put("monthSum", valueEntity.Month + "个月");
                    list.add(map);
                }
            }
        } else {
            Log.i("我置顶未选中","VS");
            if (sortFlag == sortdown) {
                Log.i("降序","VS");
                for (WorkValueResultParams valueEntity : ListSort.DownSort(entityList)) {
                    Log.i("排序完成","VS");
                    Map<String, Object> map = new HashMap<String, Object>();
                    //个人图片 图片用ID命名
//                  Bitmap bitmap = setImg(valueEntity.IMGTarget);
                    String imgname=valueEntity.UserID+".png";
                    Bitmap bitmap = imgBusiness.setImg(imgname);
                    if(bitmap!=null)
                    {
                        map.put("personImg", bitmap);
                    }else{
                        map.put("personImg", R.drawable.unknow);
                    }
                    // map.put("personImg", bitmap);
                    map.put("personId", valueEntity.UserID);
                    map.put("personName", valueEntity.Name);
                    map.put("valueSum", String.valueOf(valueEntity.BasicScore + valueEntity.CheckedScore));
                    map.put("monthSum", valueEntity.Month + "个月");
                    list.add(map);
                }
            } else {
                Log.i("VS",entityList.size()+"");
                for (WorkValueResultParams valueEntity : ListSort.UpSort(entityList)) {
                    Log.i("排序完成","VS");
                    Map<String, Object> map = new HashMap<String, Object>();
                    //个人图片 图片用ID命名
//                  Bitmap bitmap = setImg(valueEntity.IMGTarget);
                    String imgname=valueEntity.UserID+".png";
                    Bitmap bitmap = imgBusiness.setImg(imgname);
                    if(bitmap!=null)
                    {
                        map.put("personImg", bitmap);
                    }else{
                        map.put("personImg", R.drawable.unknow);
                    }
                    // map.put("personImg", bitmap);
                    map.put("personId", valueEntity.UserID);
                    map.put("personName", valueEntity.Name);
                    map.put("valueSum", String.valueOf(valueEntity.BasicScore + valueEntity.CheckedScore));
                    map.put("monthSum", valueEntity.Month + "个月");
                    list.add(map);
                }
            }
        }
        Reservoir.putAsync(LocalDataLabel.WorkValueList, entityList, new ReservoirPutCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(Exception e) {

            }
        });
        return list;
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GetDataForService();

            }
        }, 1000); // 5秒后发送消息，停止刷新
    }
}
