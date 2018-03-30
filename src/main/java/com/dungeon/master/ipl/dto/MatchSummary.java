package com.dungeon.master.ipl.dto;

import java.util.List;

import com.dungeon.master.ipl.model.Match;

public class MatchSummary {

    private List<ContestwisePrediction> predictions;
    private Match match;

    public List<ContestwisePrediction> getPredictions() {
        return predictions;
    }
    public void setPredictions(List<ContestwisePrediction> predictions) {
        this.predictions = predictions;
    }
    public Match getMatch() {
        return match;
    }
    public void setMatch(Match match) {
        this.match = match;
    }
    
}
