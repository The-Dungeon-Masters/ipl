package com.dungeon.master.ipl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dungeon.master.ipl.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

    // Users findByName(String name);
}
