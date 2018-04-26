package com.dungeon.master.ipl.dto;

import java.util.TreeSet;

import javax.persistence.Entity;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.dungeon.master.ipl.util.JsonStringType;

@TypeDefs({@TypeDef(name = "json", typeClass = JsonStringType.class)})
@Entity
public class MatchwiseLunchPoints{
    
    private long userId;
    private String userName;
    private int matchMissCount;
    private float adjustment;
    private int winMatchesCount;
    private TreeSet<MatchDetails> matchDetails;
    
    public class MatchDetails implements Comparable<MatchDetails>{
        private String match;
        private long matchId;
        private String prediction;
        private int points;
        public String getMatch() {
            return match;
        }
        public void setMatch(String match) {
            this.match = match;
        }
        public long getMatchId() {
            return matchId;
        }
        public void setMatchId(long matchId) {
            this.matchId = matchId;
        }
        public String getPrediction() {
            return prediction;
        }
        public void setPrediction(String prediction) {
            this.prediction = prediction;
        }
        public int getPoints() {
            return points;
        }
        public void setPoints(int points) {
            this.points = points;
        }
        @Override
        public int compareTo(MatchDetails o) {
            if(o.getMatchId() > this.getMatchId())
                return -1;
            else if(o.getMatchId() < this.getMatchId())
                return 1;
            return 0;
        }
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getMatchMissCount() {
        return matchMissCount;
    }

    public void setMatchMissCount(int matchMissCount) {
        this.matchMissCount = matchMissCount;
    }

    public float getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(float adjustment) {
        this.adjustment = adjustment;
    }

    public TreeSet<MatchDetails> getMatchDetails() {
        return matchDetails;
    }

    public void setMatchDetails(TreeSet<MatchDetails> matchDetails) {
        this.matchDetails = matchDetails;
    }

    public int getWinMatchesCount() {
        return winMatchesCount;
    }

    public void setWinMatchesCount(int winMatchesCount) {
        this.winMatchesCount = winMatchesCount;
    }

}
