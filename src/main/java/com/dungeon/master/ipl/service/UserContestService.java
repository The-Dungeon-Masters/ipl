package com.dungeon.master.ipl.service;

import com.dungeon.master.ipl.dto.UserDto;
import com.dungeon.master.ipl.model.Contest;
import com.dungeon.master.ipl.model.UserContest;
import com.dungeon.master.ipl.model.Users;
import com.dungeon.master.ipl.repository.ContestRepository;
import com.dungeon.master.ipl.repository.UserContestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserContestService {

    @Autowired
    private UserContestRepository userContestRepository;

    @Autowired
    private ContestRepository contestRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserContestService.class);

    @Transactional
    public void saveUserContest(UserDto userDto) {
        for (Long contestId : userDto.getContests()) {
            Contest contest = contestRepository.getOne(contestId);
            UserContest userContest = new UserContest(userDto.getUser(), contest);
            userContestRepository.save(userContest);
        }
    }

    public List<Contest> getUserContest(long id) {
        Users user = new Users();
        user.setUserId(id);
        List<Contest> contests = new ArrayList<>();
        List<UserContest> userContestList = userContestRepository.findByUser(user);
        for (UserContest uc : userContestList) {
            contests.add(uc.getContest());
        }
        return contests;
    }

    public List<Users> getContestUsers(long id) {
        Contest contest = contestRepository.findOne(id);
        List<UserContest> userContestList = userContestRepository.findByContest(contest);
        List<Users> userList = new ArrayList<>();
        for (UserContest uc : userContestList) {
            userList.add(uc.getUser());
        }
        return userList;
    }
}
