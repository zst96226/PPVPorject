package com.example.beyondsys.ppv.entities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Created by s on 2017/2/14.
 */
public class IdentifyResult implements Serializable {
    public IdentifyResult()
    {
        Team = null;
        AccessResult = -3;
        UID = null;
    }

    public List<TeamEntity> Team;
    public int AccessResult;
    public String UID;
}
