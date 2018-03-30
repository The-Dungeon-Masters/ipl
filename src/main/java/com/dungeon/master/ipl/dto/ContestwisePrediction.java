package com.dungeon.master.ipl.dto;

import java.util.List;

import com.dungeon.master.ipl.model.Contest;
import com.dungeon.master.ipl.model.Match;

public class ContestwisePrediction {

    private Contest contest;
    private List<TeamwiseUsers> teamUsers;
    private float winningPoints;
    
    public Contest getContest() {
        return contest;
    }
    public void setContest(Contest contest) {
        this.contest = contest;
    }
    public List<TeamwiseUsers> getTeamUsers() {
        return teamUsers;
    }
    public void setTeamUsers(List<TeamwiseUsers> teamUsers) {
        this.teamUsers = teamUsers;
    }
    public float getWinningPoints() {
        return winningPoints;
    }
    public void setWinningPoints(float winningPoints) {
        this.winningPoints = winningPoints;
    }
    
}
