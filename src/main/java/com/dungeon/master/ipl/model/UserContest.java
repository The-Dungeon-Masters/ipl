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

/**
 * @author patilna
 */
@TypeDefs({@TypeDef(name = "json", typeClass = JsonStringType.class)})
@Entity
@Table(name = "contest_users")
public class UserContest implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public UserContest(){
        
    }
    
    public UserContest(Users user, Contest contest){
        this.user = user;
        this.contest = contest;
    }

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contest_id")
    private Contest contest;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Users user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
