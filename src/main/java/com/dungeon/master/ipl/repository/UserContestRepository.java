package com.dungeon.master.ipl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dungeon.master.ipl.model.UserContest;

public interface UserContestRepository extends JpaRepository<UserContest, Long> {

}
