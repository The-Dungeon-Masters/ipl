package com.dungeon.master.ipl.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dungeon.master.ipl.dto.ContestPrediction;
import com.dungeon.master.ipl.dto.ContestwisePrediction;
import com.dungeon.master.ipl.dto.MatchPrediction;
import com.dungeon.master.ipl.dto.MatchSummary;
import com.dungeon.master.ipl.dto.TeamwiseUsers;
import com.dungeon.master.ipl.dto.UserDto;
import com.dungeon.master.ipl.model.Contest;
import com.dungeon.master.ipl.model.Match;
import com.dungeon.master.ipl.model.Team;
import com.dungeon.master.ipl.model.UserContest;
import com.dungeon.master.ipl.model.UserMatch;
import com.dungeon.master.ipl.model.Users;
import com.dungeon.master.ipl.repository.ContestRepository;
import com.dungeon.master.ipl.repository.MatchRepository;
import com.dungeon.master.ipl.repository.TeamRepository;
import com.dungeon.master.ipl.repository.UserContestRepository;
import com.dungeon.master.ipl.repository.UserMatchRepository;
import com.dungeon.master.ipl.service.RepositoriesService;

@RestController
@RequestMapping("/matches")
public class MatchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private MatchRepository matchRepository;
    
    @Autowired
    private RepositoriesService repositoriesService;

    @Autowired
    private TeamRepository teamRepository;
    
    @Autowired
    private ContestRepository contestRepository;
    
    @Autowired
    private UserContestRepository userContestRepository;
    
    @Autowired
    private UserMatchRepository userMatchRepository;
    
    @GetMapping(path = "/todaysMatches", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public List<Match> getTodaysMatches() throws ParseException, IOException {
        
        List<Match> allMatches = matchRepository.findAll();
        List<Match> todaysMatches = allMatches.stream().
                filter(match -> DateUtils.isSameDay(match.getStartTime(), Calendar.getInstance().getTime()))
                .collect(Collectors.toList());
        return todaysMatches;
    }
    
    @GetMapping(path = "/matchSummary", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public MatchSummary getMatchSummary() throws ParseException, IOException {
        
        Match match = matchRepository.findOne(1L);
        List<UserMatch> usersMatches = userMatchRepository.getByMatch(match);
        
        //Map<Contest, Map<Team, List<User>>>
        Map<Contest, Map<Team, List<Users>>> contestwiseUsersMap = new HashMap<>();
        
        for(UserMatch useMatch:usersMatches){
            Contest contest = useMatch.getUserContest().getContest();
            Team team = useMatch.getTeam();
            Map<Team, List<Users>> teamwiseUsers;
            
            if(contestwiseUsersMap.containsKey(contest)){
                teamwiseUsers = contestwiseUsersMap.get(contest);
            }else{
                teamwiseUsers = new HashMap<>();
            }
            
            List<Users> teamUsers;
            if(teamwiseUsers.containsKey(team)){
                teamUsers = teamwiseUsers.get(team);
            }else{
                teamUsers = new ArrayList<>();
            }
            
            teamUsers.add(useMatch.getUserContest().getUser());
            teamwiseUsers.put(team, teamUsers);
            contestwiseUsersMap.put(contest, teamwiseUsers);
        }
        
        List<ContestwisePrediction> predictions = new ArrayList<>();
        for(Contest contest:contestwiseUsersMap.keySet()){
            ContestwisePrediction prediction = new ContestwisePrediction();
            prediction.setContest(contest);
            Map<Team, List<Users>> teamwiseUsersMap = contestwiseUsersMap.get(contest);
            List<TeamwiseUsers> teamwiseUsers = new ArrayList<>();
            for(Team team:teamwiseUsersMap.keySet()){
                TeamwiseUsers teamUser = new TeamwiseUsers();
                teamUser.setTeam(team);
                teamUser.setUsers(teamwiseUsersMap.get(team));
                teamwiseUsers.add(teamUser);
            }
            prediction.setTeamUsers(teamwiseUsers);
            predictions.add(prediction);
        }
        MatchSummary matchSummary = new MatchSummary();
        matchSummary.setMatch(match);
        matchSummary.setPredictions(predictions);
        return matchSummary;
    }
    
    @PostMapping(value = "/predictMatch", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public void addUser(@RequestBody MatchPrediction prediction) {
        
        UserDto userDto = repositoriesService.getUser(prediction.getUserId());
        long matchId = prediction.getMatchId();
        
        for(ContestPrediction cPrediction:prediction.getContestPredictions()){
            UserContest userContest = new UserContest(userDto.getUser(), contestRepository.findOne(cPrediction.getContestId()));
            
            UserMatch userMatch = new UserMatch();
            userMatch.setUserContest(userContest);
            userMatch.setMatch(matchRepository.findOne(matchId));
            userMatch.setTeam(teamRepository.findOne(cPrediction.getTeamId()));
            
            userMatchRepository.save(userMatch);
        }
    }

}
