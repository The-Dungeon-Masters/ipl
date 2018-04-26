package com.dungeon.master.ipl.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
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

import com.dungeon.master.ipl.dto.MatchwiseLunchPoints;
import com.dungeon.master.ipl.dto.UserBoardDto;
import com.dungeon.master.ipl.dto.UserChangePassword;
import com.dungeon.master.ipl.dto.UserDto;
import com.dungeon.master.ipl.dto.UserRechargeDto;
import com.dungeon.master.ipl.model.Contest;
import com.dungeon.master.ipl.model.Match;
import com.dungeon.master.ipl.model.UserContest;
import com.dungeon.master.ipl.model.UserMatch;
import com.dungeon.master.ipl.model.Users;
import com.dungeon.master.ipl.repository.MatchRepository;
import com.dungeon.master.ipl.repository.UserContestRepository;
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
    private MatchRepository matchRepository;
    
    @Autowired
    private UserContestService userContestService;
    
    @Autowired
    private UsersRepository usersRepository;
    
    @Autowired
    private UserContestRepository userContestRepository;
    
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
    
    private boolean checkUserHasPointsContest(){
        Users loggedInUser = currentUserDetailsService.getLoggedInUser();
        List<UserContest> userContests = userContestRepository.findByUser(loggedInUser);
        
        return userContests.stream()
                                    .anyMatch(uContest -> uContest.getContest().getType().contains("Points"));
    }
    
    private boolean checkUserHasLunchContest(){
        Users loggedInUser = currentUserDetailsService.getLoggedInUser();
        List<UserContest> userContests = userContestRepository.findByUser(loggedInUser);
        
        return userContests.stream()
                                    .anyMatch(uContest -> uContest.getContest().getType().equalsIgnoreCase("Lunch"));
    }
    
    @GetMapping(path = "/getLunchUserBoard", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public List<UserBoardDto> getLunchUserBoard() {
        List<UserBoardDto> usersBoard = new ArrayList<>();
        if(!checkUserHasLunchContest())
            return usersBoard;
        
        List<Match> matches = matchRepository.findAll().parallelStream()
                                .filter(match -> !StringUtils.isEmpty(match.getStatus()))
                                .collect(Collectors.toList());
        
        Collections.sort(matches);
        
        List<Users> allUsers = usersRepository.findAll();
        //get all predictions
        List<UserMatch> allUsersMatches = userMatchRepository.findAll();
        
        for(Users user:allUsers){
            long userId = user.getUserId();
            //filter out only points users
            List<Contest> contests = userContestService.getUserContest(userId);
            if(!CollectionUtils.isEmpty(contests) 
                    && !contests.stream().anyMatch(contest -> contest.getType().equalsIgnoreCase("Lunch"))){
                continue;
            }
            
            //filter out user's lunch matches
            List<UserMatch> usersLunchMatches = allUsersMatches.stream().filter(
                        userMatch -> userMatch.getUserContest().getContest().getType().equalsIgnoreCase("Lunch")
                    &&  userMatch.getUserContest().getUser().getUserId() == userId).collect(Collectors.toList());
            
            float matchCountB4UserStarted = 0;
            float matchMissCount = 0;
            float matchLooseCount = 0;
            boolean userStartedPlaying = false;
            
            for(Match match:matches){
                if(!userStartedPlaying){
                    //check if user started playing 
                    userStartedPlaying = usersLunchMatches.stream()
                            .anyMatch(uMatch -> uMatch.getMatch().getId().equals(match.getId()));
                }
                if(!userStartedPlaying){
                    //user has still not started plyaing
                    matchCountB4UserStarted++;
                }else{
                    //user has started playing
                    UserMatch uMatch = usersLunchMatches.stream().filter(um -> um.getMatch().getId().equals(match.getId())).findFirst().orElse(null);
                    if(uMatch == null){
                        //user forgot to predict
                        matchMissCount++;
                    }else{
                        if(!uMatch.getTeam().getName().equalsIgnoreCase(match.getStatus())){
                            matchLooseCount++;
                        }
                    }
                }
            }
            
            //calculate points
            float totalPts = (((matchLooseCount + matchMissCount) + (matchCountB4UserStarted/2) ) * 10);
            UserBoardDto userBoard = new UserBoardDto();
            userBoard.setUserId(userId);
            userBoard.setUserName(user.getUserName());
            userBoard.setTotalPoints( totalPts * -1  );
            usersBoard.add(userBoard);
        }
        
        return usersBoard;
    }
    
    @GetMapping(path = "/getMatchwiseUserLunchPoints/{userId}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public MatchwiseLunchPoints getMatchwiseUserLunchPoints(@PathVariable("userId") long userId) {
        
        MatchwiseLunchPoints matchwisePoints = new MatchwiseLunchPoints();
        TreeSet<MatchwiseLunchPoints.MatchDetails> matchDetails = new TreeSet<>();
        matchwisePoints.setMatchDetails(matchDetails);
        
        if(!checkUserHasLunchContest())
            return matchwisePoints;
        
        List<Match> matches = matchRepository.findAll().parallelStream()
                                .filter(match -> !StringUtils.isEmpty(match.getStatus()))
                                .collect(Collectors.toList());
        
        Collections.sort(matches);
        
        //get all predictions
        List<UserMatch> allUsersMatches = userMatchRepository.findAll();
        
        //filter out user's lunch matches
        List<UserMatch> usersLunchMatches = allUsersMatches.stream().filter(
                    userMatch -> userMatch.getUserContest().getContest().getType().equalsIgnoreCase("Lunch")
                &&  userMatch.getUserContest().getUser().getUserId() == userId).collect(Collectors.toList());
        
        float matchCountB4UserStarted = 0;
        boolean userStartedPlaying = false;
        int winMatchesCount = 0;
        
        for(Match match:matches){
            if(!userStartedPlaying){
                //check if user started playing 
                userStartedPlaying = usersLunchMatches.stream()
                        .anyMatch(uMatch -> uMatch.getMatch().getId().equals(match.getId()));
            }
            if(!userStartedPlaying){
                //user has still not started playing
                matchCountB4UserStarted++;
            }else{
                //user has started playing
                MatchwiseLunchPoints.MatchDetails matchDet = matchwisePoints.new MatchDetails();
                UserMatch uMatch = usersLunchMatches.stream().filter(um -> um.getMatch().getId().equals(match.getId())).findFirst().orElse(null);
                String prediction = "";
                int points = 0;
                if(uMatch == null){
                    //user forgot to predict
                    points = -10;
                }else{
                    prediction = uMatch.getTeam().getName();
                    if(!uMatch.getTeam().getName().equalsIgnoreCase(match.getStatus())){
                        //user lost
                        points = -10;
                    }else{
                        //user won
                        winMatchesCount++;
                        points = 0;
                    }
                }
                matchDet.setMatch(match.getTeam1().getName() + " vs " + match.getTeam2().getName());
                matchDet.setMatchId(match.getId());
                matchDet.setPrediction(prediction);
                matchDet.setPoints(points);
                matchDetails.add(matchDet);
            }
        }
        
        matchwisePoints.setUserId(userId);
        matchwisePoints.setMatchMissCount((int)matchCountB4UserStarted);
        matchwisePoints.setAdjustment(matchCountB4UserStarted*-5);
        matchwisePoints.setWinMatchesCount(winMatchesCount);
        
        return matchwisePoints;
    }
    
    @GetMapping(path = "/getUserBoard", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public List<UserBoardDto> getUserBoard() {
        List<UserBoardDto> usersBoard = new ArrayList<>();

        if(!checkUserHasPointsContest())
            return usersBoard;
        
        List<Users> allUsers = usersRepository.findAll();
        
        
        //get all predictions
        List<UserMatch> allUsersMatches = userMatchRepository.findAll();
        
        for(Users user:allUsers){
            
            //filter out only lunch users
            List<Contest> contests = userContestService.getUserContest(user.getUserId());
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
