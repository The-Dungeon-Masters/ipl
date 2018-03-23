package com.dungeon.master.ipl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dungeon.master.ipl.model.Users;
import com.dungeon.master.ipl.repository.UsersRepository;

@Service
public class RepositoriesService {

    @Autowired
    private UsersRepository usersRepository;

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }
}
