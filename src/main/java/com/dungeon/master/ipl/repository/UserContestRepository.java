package com.dungeon.master.ipl.repository;

import com.dungeon.master.ipl.model.Contest;
import com.dungeon.master.ipl.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dungeon.master.ipl.model.UserContest;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserContestRepository extends JpaRepository<UserContest, Long> {
    public List<UserContest> findByUser(Users user);
    public void deleteByUser(Users user);
    public List<UserContest> findByContest(Contest contest);
}
