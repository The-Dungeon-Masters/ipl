package com.dungeon.master.ipl.service;
import com.dungeon.master.ipl.model.Contest;
import com.dungeon.master.ipl.repository.ContestRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ContestService {

    @Autowired
    private ContestRepository contestRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContestService.class);

    @Transactional
    public void saveContest(Contest contest) {
        contestRepository.save(contest);
    }

    public List<Contest> getAllContests(){
        return contestRepository.findAll();
    }

    public Contest getContest(long id){
        return contestRepository.findOne(id);
    }

    public void updateContest(Contest contest){
        Contest existingContest = contestRepository.getOne(contest.getId());
        existingContest.setPoints(contest.getPoints());
        existingContest.setType(contest.getType());
        contestRepository.save(existingContest);
    }

    public void deleteContest(long id) {
        contestRepository.delete(id);
    }

}
