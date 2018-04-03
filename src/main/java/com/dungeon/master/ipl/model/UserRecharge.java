package com.dungeon.master.ipl.model;

import com.dungeon.master.ipl.util.JsonStringType;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Date;

@TypeDefs({@TypeDef(name = "json", typeClass = JsonStringType.class)})
@Entity
@Table(name = "user_recharge")
public class UserRecharge {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    Users user;
    @Column(name = "recharge_points", nullable = true)
    int rechargePoints;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recharged_by")
    Users rechargedBy;
    @Column(name = "comments", nullable = true)
    String comments;
    @Column(name = "recharge_time", nullable = false)
    private Date rechargeTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public int getRechargePoints() {
        return rechargePoints;
    }

    public void setRechargePoints(int rechargePoints) {
        this.rechargePoints = rechargePoints;
    }

    public Users getRechargedBy() {
        return rechargedBy;
    }

    public void setRechargedBy(Users rechargedBy) {
        this.rechargedBy = rechargedBy;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(Date rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRecharge that = (UserRecharge) o;

        if (id != that.id) return false;
        if (rechargePoints != that.rechargePoints) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (rechargedBy != null ? !rechargedBy.equals(that.rechargedBy) : that.rechargedBy != null) return false;
        if (comments != null ? !comments.equals(that.comments) : that.comments != null) return false;
        return rechargeTime != null ? rechargeTime.equals(that.rechargeTime) : that.rechargeTime == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + rechargePoints;
        result = 31 * result + (rechargedBy != null ? rechargedBy.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (rechargeTime != null ? rechargeTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserRecharge{" +
                "id=" + id +
                ", user=" + user +
                ", rechargePoints=" + rechargePoints +
                ", rechargedBy=" + rechargedBy +
                ", comments='" + comments + '\'' +
                ", rechargeTime=" + rechargeTime +
                '}';
    }
}
