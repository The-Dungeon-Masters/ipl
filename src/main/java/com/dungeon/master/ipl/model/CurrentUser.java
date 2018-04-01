package com.dungeon.master.ipl.model;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class CurrentUser extends User {
    private Users user;

    public CurrentUser(Users user) {
        super(user.getUserName(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getUserType().toString()));

        this.user = user;
    }

    public Users getUser() {
        return user;
    }

    public Long getId() {
        return user.getUserId();
    }

    public String getRole() {
        return user.getUserType().toString();
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "user=" + user +
                "} " + super.toString();
    }
}
