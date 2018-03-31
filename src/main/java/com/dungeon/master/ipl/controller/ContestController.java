package com.dungeon.master.ipl.controller;

import com.dungeon.master.ipl.model.Contest;
import com.dungeon.master.ipl.model.Users;
import com.dungeon.master.ipl.service.ContestService;
import com.dungeon.master.ipl.service.UserContestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contest")
public class ContestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContestController.class);
    @Autowired
    private ContestService contestService;

    @Autowired
    private UserContestService userContestService;

    @PostMapping(value = "/addcontest", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public void addContest(@RequestBody Contest contest) {
        contestService.saveContest(contest);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteContest(@PathVariable long id) {
        contestService.deleteContest(id);
    }

    @GetMapping(path = "/getall", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public List<Contest> getAllContests() {
        return contestService.getAllContests();
    }

    @GetMapping(path = "/get/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public Contest getContest(@PathVariable("id") Long id) {
        return contestService.getContest(id);
    }

    @PutMapping("/update")
    public void updateContest(@RequestBody Contest contest) {
        contestService.updateContest(contest);
    }

    @GetMapping(path = "/getusers/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public List<Users> getUserContest(@PathVariable long id) {
        return userContestService.getContestUsers(id);
    }
}
