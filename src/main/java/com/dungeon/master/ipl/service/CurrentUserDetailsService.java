package com.dungeon.master.ipl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dungeon.master.ipl.model.CurrentUser;
import com.dungeon.master.ipl.model.Users;

@Service
public class CurrentUserDetailsService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentUserDetailsService.class);

    @Autowired
    RepositoriesService repositoriesService;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Users user = repositoriesService.getUserByName(username);
        return new CurrentUser(user);
    }
    
    public Users getLoggedInUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            
            Object principal = auth.getPrincipal();
            
            return repositoriesService.getUserByName(String.valueOf(principal));
        }
         
        return null;
    }
}