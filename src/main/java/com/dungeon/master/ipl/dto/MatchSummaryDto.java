package com.dungeon.master.ipl.dto;

import java.util.List;

public class MatchSummaryDto {
    private String winner;
    private String team1;
    private String team2;
    private List<ContestwisePrediction> contestwisePredictions;
    public String getWinner() {
        return winner;
    }
    public void setWinner(String winner) {
        this.winner = winner;
    }
    public String getTeam1() {
        return team1;
    }
    public void setTeam1(String team1) {
        this.team1 = team1;
    }
    public String getTeam2() {
        return team2;
    }
    public void setTeam2(String team2) {
        this.team2 = team2;
    }
    public List<ContestwisePrediction> getContestwisePredictions() {
        return contestwisePredictions;
    }
    public void setContestwisePredictions(List<ContestwisePrediction> contestwisePredictions) {
        this.contestwisePredictions = contestwisePredictions;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((contestwisePredictions == null) ? 0 : contestwisePredictions.hashCode());
        result = prime * result + ((team1 == null) ? 0 : team1.hashCode());
        result = prime * result + ((team2 == null) ? 0 : team2.hashCode());
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
        MatchSummaryDto other = (MatchSummaryDto) obj;
        if (contestwisePredictions == null) {
            if (other.contestwisePredictions != null)
                return false;
        } else if (!contestwisePredictions.equals(other.contestwisePredictions))
            return false;
        if (team1 == null) {
            if (other.team1 != null)
                return false;
        } else if (!team1.equals(other.team1))
            return false;
        if (team2 == null) {
            if (other.team2 != null)
                return false;
        } else if (!team2.equals(other.team2))
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
        return "MatchSummaryDto [winner=" + winner + ", team1=" + team1 + ", team2=" + team2
                + ", contestwisePredictions=" + contestwisePredictions + "]";
    }
    

    
    
}
