package com.dungeon.master.ipl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dungeon.master.ipl.model.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {

    // Users findByName(String name);
}
