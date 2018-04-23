package com.dungeon.master.ipl.dto;

import java.util.Date;

public class UsersMatchwisePoints implements Comparable<UsersMatchwisePoints>{
    
    private String userName;
    private long matchId;
    private String matchName;
    private String winner;
    private Date time;
    private float points;
    private String contestName;
    
    
    @Override
    public int compareTo(UsersMatchwisePoints o) {
        if(o.getPoints() > this.getPoints())
            return 1;
        else if(o.getPoints() < this.getPoints())
            return -1;
        return 0;
    }


    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public long getMatchId() {
        return matchId;
    }


    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }


    public String getMatchName() {
        return matchName;
    }


    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }


    public String getWinner() {
        return winner;
    }


    public void setWinner(String winner) {
        this.winner = winner;
    }


    public Date getTime() {
        return time;
    }


    public void setTime(Date time) {
        this.time = time;
    }


    public float getPoints() {
        return points;
    }


    public void setPoints(float points) {
        this.points = points;
    }


    public String getContestName() {
        return contestName;
    }


    public void setContestName(String contestName) {
        this.contestName = contestName;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((contestName == null) ? 0 : contestName.hashCode());
        result = prime * result + (int) (matchId ^ (matchId >>> 32));
        result = prime * result + ((matchName == null) ? 0 : matchName.hashCode());
        result = prime * result + Float.floatToIntBits(points);
        result = prime * result + ((time == null) ? 0 : time.hashCode());
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
        result = prime * result + ((winner == null) ? 0 : winner.hashCode());
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
        UsersMatchwisePoints other = (UsersMatchwisePoints) obj;
        if (contestName == null) {
            if (other.contestName != null)
                return false;
        } else if (!contestName.equals(other.contestName))
            return false;
        if (matchId != other.matchId)
            return false;
        if (matchName == null) {
            if (other.matchName != null)
                return false;
        } else if (!matchName.equals(other.matchName))
            return false;
        if (Float.floatToIntBits(points) != Float.floatToIntBits(other.points))
            return false;
        if (time == null) {
            if (other.time != null)
                return false;
        } else if (!time.equals(other.time))
            return false;
        if (userName == null) {
            if (other.userName != null)
                return false;
        } else if (!userName.equals(other.userName))
            return false;
        if (winner == null) {
            if (other.winner != null)
                return false;
        } else if (!winner.equals(other.winner))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "UsersMatchwisePoints [userName=" + userName + ", matchId=" + matchId + ", matchName=" + matchName
                + ", winner=" + winner + ", time=" + time + ", points=" + points + ", contestName=" + contestName + "]";
    }

}
