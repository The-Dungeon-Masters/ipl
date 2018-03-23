package com.dungeon.master.ipl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dungeon.master.ipl.model.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

    // Users findByName(String name);
}
