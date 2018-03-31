package com.dungeon.master.ipl.dto;

import com.dungeon.master.ipl.model.UserContest;
import com.dungeon.master.ipl.model.Users;
import com.dungeon.master.ipl.util.JsonStringType;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Entity;
import java.util.List;

@TypeDefs({@TypeDef(name = "json", typeClass = JsonStringType.class)})
@Entity
public class UserDto {
    Users user;
    List<Long> contests;

    public UserDto() {
    }

    public UserDto(Users user, List<Long> contests) {
        this.user = user;
        this.contests = contests;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<Long> getContests() {
        return contests;
    }

    public void setContests(List<Long> contests) {
        this.contests = contests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        if (user != null ? !user.equals(userDto.user) : userDto.user != null) return false;
        return contests != null ? contests.equals(userDto.contests) : userDto.contests == null;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (contests != null ? contests.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "user=" + user +
                ", contests=" + contests +
                '}';
    }
}
