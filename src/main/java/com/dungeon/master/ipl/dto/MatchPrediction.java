package com.dungeon.master.ipl.dto;

import java.util.List;

import javax.persistence.Entity;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.dungeon.master.ipl.util.JsonStringType;

@TypeDefs({@TypeDef(name = "json", typeClass = JsonStringType.class)})
@Entity
public class MatchPrediction {

    private long userId;
    private long matchId;
    private List<ContestPrediction> contestPredictions; 

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }
    public List<ContestPrediction> getContestPredictions() {
        return contestPredictions;
    }

    public void setContestPredictions(List<ContestPrediction> contestPredictions) {
        this.contestPredictions = contestPredictions;
    }
}
