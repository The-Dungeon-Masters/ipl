package com.dungeon.master.ipl.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dungeon.master.ipl.dto.UserBoardDto;
import com.dungeon.master.ipl.dto.UserChangePassword;
import com.dungeon.master.ipl.dto.UserDto;
import com.dungeon.master.ipl.dto.UserRechargeDto;
import com.dungeon.master.ipl.model.Contest;
import com.dungeon.master.ipl.model.UserMatch;
import com.dungeon.master.ipl.model.Users;
import com.dungeon.master.ipl.repository.UserMatchRepository;
import com.dungeon.master.ipl.repository.UsersRepository;
import com.dungeon.master.ipl.service.CurrentUserDetailsService;
import com.dungeon.master.ipl.service.RepositoriesService;
import com.dungeon.master.ipl.service.UserContestService;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private RepositoriesService repositoriesService;

    @Autowired
    private UserContestService userContestService;
    
    @Autowired
    private UsersRepository usersRepository;
    
    @Autowired
    private UserMatchRepository userMatchRepository;
    
    @Autowired
    private CurrentUserDetailsService currentUserDetailsService;

    @PostMapping(value = "/adduser", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public Users addUser(@RequestBody UserDto user) {
        long loggedInUserId = currentUserDetailsService.getLoggedInUser().getUserId();
        Users loggedInUser = usersRepository.findOne(loggedInUserId);
        user.getUser().setCreatedBy(loggedInUser.getUserName());
        if(loggedInUser.getUserType().equalsIgnoreCase("admin")){
            return repositoriesService.saveUser(user);
        }
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable long id) {
        repositoriesService.deleteUser(id);
    }

    @GetMapping(path = "/getall", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> getAllUsers() {
        return repositoriesService.getAllUsers();
    }

    @GetMapping(path = "/getloggedinuser", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public UserDto getLoggedInUser() {
        long loggedInUserId = currentUserDetailsService.getLoggedInUser().getUserId();
        Users loggedInUser = usersRepository.findOne(loggedInUserId);
        return repositoriesService.getUser(loggedInUser.getUserName());
    }

    @GetMapping(path = "/get/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public UserDto getUser(@PathVariable("id") Long id) {
        return repositoriesService.getUser(id);
    }

    @PutMapping("/update")
    public void updateCustomer(@RequestBody UserDto user) {
        repositoriesService.updateUser(user);
    }

    @PutMapping("/changepassword")
    public UserChangePassword changePassword(@RequestBody UserChangePassword newPassword) {
        long loggedInUserId = currentUserDetailsService.getLoggedInUser().getUserId();
        repositoriesService.changePassword(loggedInUserId, newPassword);
        return newPassword;
    }

    @GetMapping(path = "/getcontests", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Contest> getUserContest() {
        long loggedInUserId = currentUserDetailsService.getLoggedInUser().getUserId();
        Users loggedInUser = usersRepository.findOne(loggedInUserId);
        logger.info("LoggedIn user: "+loggedInUser);
        return userContestService.getUserContest(loggedInUser.getUserId());
    }

    @GetMapping(path = "/getrechargehistory", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public List<UserRechargeDto> getRechageHistory() {
        long loggedInUserId = currentUserDetailsService.getLoggedInUser().getUserId();
        Users loggedInUser = usersRepository.findOne(loggedInUserId);
        return repositoriesService.getRechageHistory(loggedInUser.getUserId());
    }
    
    @GetMapping(path = "/getUserBoard", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public List<UserBoardDto> getUserBoard() {
        List<Users> allUsers = usersRepository.findAll();
        
        List<UserBoardDto> usersBoard = new ArrayList<>();
        
        long loggedInUserId = currentUserDetailsService.getLoggedInUser().getUserId();
        
        //get all predictions
        List<UserMatch> allUsersMatches = userMatchRepository.findAll();
        
        for(Users user:allUsers){
            
            //filter out only lunch users
            List<Contest> contests = userContestService.getUserContest(loggedInUserId);
            if(!CollectionUtils.isEmpty(contests) 
                    && !contests.stream().anyMatch(contest -> contest.getType().contains("Point"))){
                continue;
            }
            
            //check if user has not played a single match then skip
            if(!allUsersMatches.stream().anyMatch(userMatch -> userMatch.getUserContest().getUser().getUserId() == user.getUserId()))
                continue;
            
            List<UserRechargeDto> recharges = repositoriesService.getRechageHistory(user.getUserId());
            
            if(!CollectionUtils.isEmpty(recharges)){
                float rechargePts = (float)recharges.stream().mapToDouble(recharge -> recharge.getRechargePoints()).sum();
                UserBoardDto dto = new UserBoardDto(); 
                dto.setUserId(user.getUserId());
                dto.setUserName(user.getUserName());
                dto.setRechargePoints(rechargePts);
                dto.setTotalPoints(user.getPoints());
                dto.setNetPoints(user.getPoints() - rechargePts);
                usersBoard.add(dto);
            }
        }
        Collections.sort(usersBoard);
        return usersBoard;
    }

    @PostMapping(value = "/recharge", produces = MediaType.APPLICATION_JSON_VALUE)
    public void doRecharge(@RequestBody UserRechargeDto userRecharge) {
        long loggedInUserId = currentUserDetailsService.getLoggedInUser().getUserId();
        Users loggedInUser = usersRepository.findOne(loggedInUserId);
        userRecharge.setRechargedBy(loggedInUser.getUserName());
        userRecharge.setRechargeTime(Calendar.getInstance().getTime());
        repositoriesService.doRecharge(userRecharge);
    }
}
