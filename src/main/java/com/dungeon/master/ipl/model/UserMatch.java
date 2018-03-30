package com.dungeon.master.ipl.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.dungeon.master.ipl.util.JsonStringType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author patilna
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@TypeDefs({@TypeDef(name = "json", typeClass = JsonStringType.class)})
@Entity
@Table(name = "user_matches")
public class UserMatch implements Serializable {

    private static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_contest_id")
    private UserContest userContest;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "match_id")
    private Match match;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private Team team;
    
    @Column(nullable = false)
    private int points;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserContest getUserContest() {
        return userContest;
    }

    public void setUserContest(UserContest userContest) {
        this.userContest = userContest;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    
}
