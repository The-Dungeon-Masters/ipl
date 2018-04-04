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
    float rechargePoints;
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

    public float getRechargePoints() {
        return rechargePoints;
    }

    public void setRechargePoints(float rechargePoints) {
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((comments == null) ? 0 : comments.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + Float.floatToIntBits(rechargePoints);
        result = prime * result + ((rechargeTime == null) ? 0 : rechargeTime.hashCode());
        result = prime * result + ((rechargedBy == null) ? 0 : rechargedBy.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
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
        UserRecharge other = (UserRecharge) obj;
        if (comments == null) {
            if (other.comments != null)
                return false;
        } else if (!comments.equals(other.comments))
            return false;
        if (id != other.id)
            return false;
        if (Float.floatToIntBits(rechargePoints) != Float.floatToIntBits(other.rechargePoints))
            return false;
        if (rechargeTime == null) {
            if (other.rechargeTime != null)
                return false;
        } else if (!rechargeTime.equals(other.rechargeTime))
            return false;
        if (rechargedBy == null) {
            if (other.rechargedBy != null)
                return false;
        } else if (!rechargedBy.equals(other.rechargedBy))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }
}
