package com.dungeon.master.ipl.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dungeon.master.ipl.model.Match;
import com.dungeon.master.ipl.model.UserContest;
import com.dungeon.master.ipl.model.UserMatch;

public interface UserMatchRepository extends JpaRepository<UserMatch, Long> {

    List<UserMatch> getByMatch(Match match);
    
    List<UserMatch> getByUserContest(UserContest userContest);
    
    @Transactional
    void deleteByUserContestAndMatch(UserContest userContest, Match match);
}
