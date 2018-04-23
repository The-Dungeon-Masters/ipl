package com.dungeon.master.ipl.dto;

import java.util.Date;

public class UsersPrediction implements Comparable<UsersPrediction>{

    private long id;
    private String matchName;
    private Date time;
    private String predictedTeam;
    private String winningTeam;
    private float points;
    private String contest;
    
    public String getMatchName() {
        return matchName;
    }
    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    public String getPredictedTeam() {
        return predictedTeam;
    }
    public void setPredictedTeam(String predictedTeam) {
        this.predictedTeam = predictedTeam;
    }
    public String getWinningTeam() {
        return winningTeam;
    }
    public void setWinningTeam(String winningTeam) {
        this.winningTeam = winningTeam;
    }
    public float getPoints() {
        return points;
    }
    public void setPoints(float points) {
        this.points = points;
    }
    public String getContest() {
        return contest;
    }
    public void setContest(String contest) {
        this.contest = contest;
    }
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((contest == null) ? 0 : contest.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((matchName == null) ? 0 : matchName.hashCode());
        result = prime * result + Float.floatToIntBits(points);
        result = prime * result + ((predictedTeam == null) ? 0 : predictedTeam.hashCode());
        result = prime * result + ((time == null) ? 0 : time.hashCode());
        result = prime * result + ((winningTeam == null) ? 0 : winningTeam.hashCode());
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
        UsersPrediction other = (UsersPrediction) obj;
        if (contest == null) {
            if (other.contest != null)
                return false;
        } else if (!contest.equals(other.contest))
            return false;
        if (id != other.id)
            return false;
        if (matchName == null) {
            if (other.matchName != null)
                return false;
        } else if (!matchName.equals(other.matchName))
            return false;
        if (Float.floatToIntBits(points) != Float.floatToIntBits(other.points))
            return false;
        if (predictedTeam == null) {
            if (other.predictedTeam != null)
                return false;
        } else if (!predictedTeam.equals(other.predictedTeam))
            return false;
        if (time == null) {
            if (other.time != null)
                return false;
        } else if (!time.equals(other.time))
            return false;
        if (winningTeam == null) {
            if (other.winningTeam != null)
                return false;
        } else if (!winningTeam.equals(other.winningTeam))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "UsersPrediction [id=" + id + ", matchName=" + matchName + ", time=" + time + ", predictedTeam="
                + predictedTeam + ", winningTeam=" + winningTeam + ", points=" + points + ", contest=" + contest + "]";
    }
    @Override
    public int compareTo(UsersPrediction o) {
        return o.getTime().compareTo(this.getTime());
    }
    
    }
