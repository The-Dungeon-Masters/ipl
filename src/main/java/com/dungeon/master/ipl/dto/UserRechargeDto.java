package com.dungeon.master.ipl.dto;

import com.dungeon.master.ipl.model.Users;
import com.dungeon.master.ipl.util.JsonStringType;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

@TypeDefs({@TypeDef(name = "json", typeClass = JsonStringType.class)})
@Entity
public class UserRechargeDto {
    long userId;
    int rechargePoints;
    String rechargedBy;
    String comment;
    Date rechargeTime;

    public UserRechargeDto() {
    }

    public UserRechargeDto(long userId, int rechargePoints, String rechargedBy, String comment, Date rechargeTime) {
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

    public int getRechargePoints() {
        return rechargePoints;
    }

    public void setRechargePoints(int rechargePoints) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRechargeDto that = (UserRechargeDto) o;

        if (userId != that.userId) return false;
        if (rechargePoints != that.rechargePoints) return false;
        if (rechargedBy != null ? !rechargedBy.equals(that.rechargedBy) : that.rechargedBy != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        return rechargeTime != null ? rechargeTime.equals(that.rechargeTime) : that.rechargeTime == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + rechargePoints;
        result = 31 * result + (rechargedBy != null ? rechargedBy.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (rechargeTime != null ? rechargeTime.hashCode() : 0);
        return result;
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
}
