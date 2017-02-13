package com.example.beyondsys.ppv.entities;

import java.io.Serializable;

/**
 * Created by s on 2017/2/13.
 */
public class WorkValueResultEntity  implements Serializable{
    public  String Score;
    public  int AccessResult;
    public  WorkValueResultEntity(){
        Score=null;
        AccessResult=-3;
    }
}
