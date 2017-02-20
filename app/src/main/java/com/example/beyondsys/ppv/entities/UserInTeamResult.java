package com.example.beyondsys.ppv.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by s on 2017/2/17.
 */
public class UserInTeamResult implements Serializable {
    public  UserInTeamResult()
    {
        teamUsers = null;
        AccessResult = -3;
    }

    public List<UserInTeam> teamUsers;
    public int AccessResult;
}
