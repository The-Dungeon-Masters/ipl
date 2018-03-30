package com.dungeon.master.ipl.dto;

import java.util.List;

import com.dungeon.master.ipl.model.Team;
import com.dungeon.master.ipl.model.Users;

public class TeamwiseUsers {

    private Team team;
    private List<Users> users;
    
    public Team getTeam() {
        return team;
    }
    public void setTeam(Team team) {
        this.team = team;
    }
    public List<Users> getUsers() {
        return users;
    }
    public void setUsers(List<Users> users) {
        this.users = users;
    }
    
    
}
