package com.dungeon.master.ipl.service;

import com.dungeon.master.ipl.model.CurrentUser;
import com.dungeon.master.ipl.model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserDetailsService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentUserDetailsService.class);

    @Autowired
    RepositoriesService repositoriesService;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        LOGGER.debug("Authenticating user with email={}", username);
        Users user = repositoriesService.getUserByName(username);
        return new CurrentUser(user);
    }
}