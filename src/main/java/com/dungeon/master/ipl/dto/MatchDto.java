package com.dungeon.master.ipl.dto;

import javax.persistence.Entity;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.dungeon.master.ipl.util.JsonStringType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@TypeDefs({@TypeDef(name = "json", typeClass = JsonStringType.class)})
@Entity
public class MatchDto{
    
    private long matchId;
    private long winningTeamId;
    
    public long getMatchId() {
        return matchId;
    }
    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }
    public long getWinningTeamId() {
        return winningTeamId;
    }
    public void setWinningTeamId(long winningTeamId) {
        this.winningTeamId = winningTeamId;
    }
}
