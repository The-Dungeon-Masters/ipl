package com.dungeon.master.ipl.dto;

import javax.persistence.Entity;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.dungeon.master.ipl.util.JsonStringType;

@TypeDefs({@TypeDef(name = "json", typeClass = JsonStringType.class)})
@Entity
public class UserBoardDto implements Comparable<UserBoardDto>{
    long userId;
    String userName;
    float netPoints;
    float totalPoints;
    float rechargePoints;
    
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public float getNetPoints() {
        return netPoints;
    }
    public void setNetPoints(float netPoints) {
        this.netPoints = netPoints;
    }
    public float getTotalPoints() {
        return totalPoints;
    }
    public void setTotalPoints(float totalPoints) {
        this.totalPoints = totalPoints;
    }
    public float getRechargePoints() {
        return rechargePoints;
    }
    public void setRechargePoints(float rechargePoints) {
        this.rechargePoints = rechargePoints;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(netPoints);
        result = prime * result + Float.floatToIntBits(rechargePoints);
        result = prime * result + Float.floatToIntBits(totalPoints);
        result = prime * result + (int) (userId ^ (userId >>> 32));
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
        UserBoardDto other = (UserBoardDto) obj;
        if (Float.floatToIntBits(netPoints) != Float.floatToIntBits(other.netPoints))
            return false;
        if (Float.floatToIntBits(rechargePoints) != Float.floatToIntBits(other.rechargePoints))
            return false;
        if (Float.floatToIntBits(totalPoints) != Float.floatToIntBits(other.totalPoints))
            return false;
        if (userId != other.userId)
            return false;
        if (userName == null) {
            if (other.userName != null)
                return false;
        } else if (!userName.equals(other.userName))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "UserBoardDto [userId=" + userId + ", userName=" + userName + ", netPoints=" + netPoints
                + ", totalPoints=" + totalPoints + ", rechargePoints=" + rechargePoints + "]";
    }
    @Override
    public int compareTo(UserBoardDto arg0) {
        if(this.getNetPoints() > arg0.getNetPoints())
            return -1;
        else if(this.getNetPoints() < arg0.getNetPoints())
            return 1;
        return 0;
    }

}
