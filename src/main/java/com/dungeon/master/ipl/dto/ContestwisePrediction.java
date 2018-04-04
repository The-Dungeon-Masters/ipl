package com.dungeon.master.ipl.dto;

import java.util.List;

public class ContestwisePrediction {

    private String contestName;
    private List<String> team1Users;
    private List<String> team2Users;
    private float winningPoints;
    
    public String getContestName() {
        return contestName;
    }
    public void setContestName(String contestName) {
        this.contestName = contestName;
    }
    public List<String> getTeam1Users() {
        return team1Users;
    }
    public void setTeam1Users(List<String> team1Users) {
        this.team1Users = team1Users;
    }
    public List<String> getTeam2Users() {
        return team2Users;
    }
    public void setTeam2Users(List<String> team2Users) {
        this.team2Users = team2Users;
    }
    public float getWinningPoints() {
        return winningPoints;
    }
    public void setWinningPoints(float winningPoints) {
        this.winningPoints = winningPoints;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((contestName == null) ? 0 : contestName.hashCode());
        result = prime * result + ((team1Users == null) ? 0 : team1Users.hashCode());
        result = prime * result + ((team2Users == null) ? 0 : team2Users.hashCode());
        result = prime * result + Float.floatToIntBits(winningPoints);
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ContestwisePrediction other = (ContestwisePrediction) obj;
        if (contestName == null) {
            if (other.contestName != null)
                return false;
        } else if (!contestName.equals(other.contestName))
            return false;
        if (team1Users == null) {
            if (other.team1Users != null)
                return false;
        } else if (!team1Users.equals(other.team1Users))
            return false;
        if (team2Users == null) {
            if (other.team2Users != null)
                return false;
        } else if (!team2Users.equals(other.team2Users))
            return false;
        if (Float.floatToIntBits(winningPoints) != Float.floatToIntBits(other.winningPoints))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "ContestwisePrediction [contestName=" + contestName + ", team1Users=" + team1Users + ", team2Users="
                + team2Users + ", winningPoints=" + winningPoints + "]";
    }
    
}
