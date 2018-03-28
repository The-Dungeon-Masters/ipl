package com.dungeon.master.ipl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dungeon.master.ipl.model.Users;
import com.dungeon.master.ipl.repository.UsersRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RepositoriesService {

    @Autowired
    private UsersRepository usersRepository;

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public Users getUser(long id) {
        return usersRepository.findOne(id);
    }

    @Transactional
    public void deleteUser(long id) {
        usersRepository.delete(id);
    }

    @Transactional
    public void saveUser(Users user) {
        usersRepository.save(user);
    }

    @Transactional
    public void updateUser(Users user) {
        Users existingUser = usersRepository.getOne(user.getUserId());
        existingUser.setUserName(user.getUserName());
        existingUser.setEmail(user.getEmail());
    }
}
