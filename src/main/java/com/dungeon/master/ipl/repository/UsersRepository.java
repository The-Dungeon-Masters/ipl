package com.dungeon.master.ipl.repository;

import com.dungeon.master.ipl.model.UserContest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dungeon.master.ipl.model.Users;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UsersRepository extends JpaRepository<Users, Long> {

    // Users findByName(String name);
    public Users findByUserName(String userName);
}
