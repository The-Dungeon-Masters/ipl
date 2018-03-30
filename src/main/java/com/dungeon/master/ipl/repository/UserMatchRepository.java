package com.dungeon.master.ipl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dungeon.master.ipl.model.Match;
import com.dungeon.master.ipl.model.UserMatch;

public interface UserMatchRepository extends JpaRepository<UserMatch, Long> {

    List<UserMatch> getByMatch(Match match);
}
