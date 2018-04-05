package com.dungeon.master.ipl.model;

import java.io.Serializable;
import java.util.Date;

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

/**
 * @author patilna
 */
@TypeDefs({@TypeDef(name = "json", typeClass = JsonStringType.class)})
@Entity
@Table(name = "matches")
public class Match implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "match_id", updatable = false, nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_one_id")
    private Team team1;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_two_id")
    private Team team2;
    
    @Column(name = "status", nullable = true)
    private String status;
    
    @Column(name = "start_time", nullable = false)
    private Date startTime;
    
    @Column(name = "venue", nullable = false)
    private String venue;
    
    public Long getId() {
        return id;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

}
