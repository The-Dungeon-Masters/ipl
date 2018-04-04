package com.dungeon.master.ipl.dto;

import java.util.Date;

import javax.persistence.Entity;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.dungeon.master.ipl.util.JsonStringType;

@TypeDefs({@TypeDef(name = "json", typeClass = JsonStringType.class)})
@Entity
public class UserRechargeDto {
    long userId;
    float rechargePoints;
    String rechargedBy;
    String comment;
    Date rechargeTime;

    public UserRechargeDto() {
    }

    public UserRechargeDto(long userId, float rechargePoints, String rechargedBy, String comment, Date rechargeTime) {
        this.userId = userId;
        this.rechargePoints = rechargePoints;
        this.rechargedBy = rechargedBy;
        this.comment = comment;
        this.rechargeTime = rechargeTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public float getRechargePoints() {
        return rechargePoints;
    }

    public void setRechargePoints(float rechargePoints) {
        this.rechargePoints = rechargePoints;
    }

    public String getRechargedBy() {
        return rechargedBy;
    }

    public void setRechargedBy(String rechargedBy) {
        this.rechargedBy = rechargedBy;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(Date rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    @Override
    public String toString() {
        return "UserRechargeDto{" +
                "userId=" + userId +
                ", rechargePoints=" + rechargePoints +
                ", rechargedBy='" + rechargedBy + '\'' +
                ", comment='" + comment + '\'' +
                ", rechargeTime=" + rechargeTime +
                '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((comment == null) ? 0 : comment.hashCode());
        result = prime * result + Float.floatToIntBits(rechargePoints);
        result = prime * result + ((rechargeTime == null) ? 0 : rechargeTime.hashCode());
        result = prime * result + ((rechargedBy == null) ? 0 : rechargedBy.hashCode());
        result = prime * result + (int) (userId ^ (userId >>> 32));
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
        UserRechargeDto other = (UserRechargeDto) obj;
        if (comment == null) {
            if (other.comment != null)
                return false;
        } else if (!comment.equals(other.comment))
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
        if (userId != other.userId)
            return false;
        return true;
    }
}
