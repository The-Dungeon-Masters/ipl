package com.dungeon.master.ipl.repository;

import com.dungeon.master.ipl.model.UserRecharge;
import com.dungeon.master.ipl.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UsersRechargeRepository extends JpaRepository<UserRecharge, Long> {
    public List<UserRecharge> findByUser(Users user);
}
