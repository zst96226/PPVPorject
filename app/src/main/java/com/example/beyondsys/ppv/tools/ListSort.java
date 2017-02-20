package com.example.beyondsys.ppv.tools;

import android.util.Log;

import com.example.beyondsys.ppv.entities.WorkValueResultParams;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangb on 2017/2/17.List排序
 */
public class ListSort {

    public static List<WorkValueResultParams> UpSort(List<WorkValueResultParams> list) {
        List<WorkValueResultParams> _list=new ArrayList<>();
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (WorkValueResultParams valueEntity : list) {
            map.put(valueEntity.UserID,(int)valueEntity.BasicScore+(int)valueEntity.CheckedScore);
        }
        List<Map.Entry<String,Integer>> mapList = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
        Collections.sort(mapList, new Comparator<Map.Entry<String, Integer>>() {

            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o1.getValue() - o2.getValue());
            }
        });
        for (Map.Entry<String,Integer> _map : mapList) {
            for (WorkValueResultParams valueEntity : list)  {
                if (_map.getKey().equals(valueEntity.UserID))
                {
                    _list.add(valueEntity);
                }
            }
        }
        return _list;
    }

    public static List<WorkValueResultParams> DownSort(List<WorkValueResultParams> list) {
        List<WorkValueResultParams> _list=new ArrayList<>();
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (WorkValueResultParams valueEntity : list) {
            map.put(valueEntity.UserID,(int)valueEntity.BasicScore+(int)valueEntity.CheckedScore);
        }
        List<Map.Entry<String,Integer>> mapList = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
        Collections.sort(mapList, new Comparator<Map.Entry<String, Integer>>() {

            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        });
        for (Map.Entry<String,Integer> _map : mapList) {
            for (WorkValueResultParams valueEntity : list)  {
                if (_map.getKey().equals(valueEntity.UserID))
                {
                    _list.add(valueEntity);
                }
            }
        }
        return _list;
    }
}
