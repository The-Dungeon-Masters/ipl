package com.dungeon.master.ipl.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dungeon.master.ipl.dto.ContestPrediction;
import com.dungeon.master.ipl.dto.ContestwisePrediction;
import com.dungeon.master.ipl.dto.MatchDto;
import com.dungeon.master.ipl.dto.MatchPrediction;
import com.dungeon.master.ipl.dto.MatchSummaryDto;
import com.dungeon.master.ipl.dto.MatchViewDto;
import com.dungeon.master.ipl.dto.UsersPrediction;
import com.dungeon.master.ipl.model.Contest;
import com.dungeon.master.ipl.model.Match;
import com.dungeon.master.ipl.model.Team;
import com.dungeon.master.ipl.model.UserContest;
import com.dungeon.master.ipl.model.UserMatch;
import com.dungeon.master.ipl.model.Users;
import com.dungeon.master.ipl.repository.MatchRepository;
import com.dungeon.master.ipl.repository.TeamRepository;
import com.dungeon.master.ipl.repository.UserContestRepository;
import com.dungeon.master.ipl.repository.UserMatchRepository;
import com.dungeon.master.ipl.repository.UsersRepository;
import com.dungeon.master.ipl.service.CurrentUserDetailsService;
import com.dungeon.master.ipl.util.DateUtils;
import com.dungeon.master.ipl.util.PointsException;

@RestController
@RequestMapping("/matches")
public class MatchController {

    @Autowired
    private MatchRepository matchRepository;
    
    @Autowired
    private TeamRepository teamRepository;
    
    @Autowired
    private UsersRepository usersRepository;
    
    @Autowired
    private CurrentUserDetailsService currentUserDetailsService;
    
    @Autowired
    private UserContestRepository userContestRepository;
    
    @Autowired
    private UserMatchRepository userMatchRepository;
    
    @GetMapping(produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public List<MatchViewDto> getAllMatches() throws ParseException, IOException {
        
        return transformToMatchViewDto(matchRepository.findAll());
        
    }
    
    private List<MatchViewDto> transformToMatchViewDto(List<Match> matches){
        List<MatchViewDto> matchDtos = new ArrayList<>();
        for(Match match:matches){
            MatchViewDto matchDto = new MatchViewDto();
            matchDto.setId(match.getId());
            matchDto.setName(match.getTeam1().getFullName() + " vs " + match.getTeam2().getFullName());
            matchDto.setTeam1(match.getTeam1());
            matchDto.setTeam2(match.getTeam2());
            matchDto.setStartTime(match.getStartTime());
            matchDto.setVenue(match.getVenue());
            matchDtos.add(matchDto);
        }
        return matchDtos;
    }
    
    @GetMapping(path = "/todaysMatches", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public List<MatchViewDto> getTodaysMatches() throws ParseException, IOException {
        
        List<Match> allMatches = matchRepository.findAll();
        List<Match> todaysMatches = allMatches.stream().
                filter(match -> DateUtils.isSameDay(match.getStartTime(), Calendar.getInstance().getTime()))
                .collect(Collectors.toList());
        return transformToMatchViewDto(todaysMatches);
    }
    
    @GetMapping(path = "/upcomingMatches", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public List<MatchViewDto> getUpcomingMatches() throws ParseException, IOException {
        
        List<Match> allMatches = matchRepository.findAll();
        List<Match> upcomingMatches = allMatches.stream().
                filter(match -> DateUtils.isFutureDay(match.getStartTime(), Calendar.getInstance().getTime()))
                .collect(Collectors.toList());
        return transformToMatchViewDto(upcomingMatches);
    }
    
    @GetMapping(path = "/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public Match getById(@PathVariable("id") Long id) throws ParseException, IOException {
        
        return matchRepository.findOne(id);
    }
    
    @GetMapping(path = "/myMatchPrediction/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public MatchPrediction getMyMatchPrediction(@PathVariable("id") Long id) throws ParseException, IOException {
        MatchPrediction prediction = new MatchPrediction();
        prediction.setMatchId(id);
        long loggedInUserId = currentUserDetailsService.getLoggedInUser().getUserId();
        Match match = matchRepository.findOne(id);
        List<UserMatch> userMatches = userMatchRepository.getByMatch(match);
        List<ContestPrediction> cPredictions = new ArrayList<ContestPrediction>();
        for(UserMatch userMatch:userMatches){
            UserContest userContest = userMatch.getUserContest();
            if(userContest.getUser().getUserId() == loggedInUserId){
                ContestPrediction cPred = new ContestPrediction();
                cPred.setContestId(userContest.getContest().getId());
                cPred.setTeamId(userMatch.getTeam().getId());
                cPredictions.add(cPred);
            }
        }
        prediction.setContestPredictions(cPredictions);
        return prediction;
    }
    
    @GetMapping(path = "/myPredictions", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public List<UsersPrediction> getMyPredictions() throws ParseException, IOException {
        long loggedInUserId = currentUserDetailsService.getLoggedInUser().getUserId();
        List<UsersPrediction> predictions = new ArrayList<>();
        List<UserContest> userContests = userContestRepository.findByUser(usersRepository.findOne(loggedInUserId));
        List<UserMatch> userMatches = new ArrayList<>();
        for(UserContest userContest:userContests){
            userMatches.addAll(userMatchRepository.getByUserContest(userContest));
        }
        for(UserMatch userMatch:userMatches){
            Match match = userMatch.getMatch();
            UsersPrediction prediction = new UsersPrediction();
            prediction.setMatchName(match.getTeam1().getName() + " vs " + match.getTeam2().getName());
            prediction.setContest(userMatch.getUserContest().getContest().getType());
            prediction.setPoints(userMatch.getPoints());
            prediction.setPredictedTeam(userMatch.getTeam().getName());
            prediction.setTime(match.getStartTime());
            prediction.setWinningTeam(match.getStatus());
            predictions.add(prediction);
        }
        return predictions;
    }
    
    @GetMapping(path = "/matchSummary/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public MatchSummaryDto getMatchSummary(@PathVariable("id") Long id) throws ParseException, IOException {
        long loggedInUserId = currentUserDetailsService.getLoggedInUser().getUserId();
        
        Match match = matchRepository.findOne(id);
        List<UserMatch> usersMatches = userMatchRepository.getByMatch(match);
        
        //filter out based on logged in users contest
        List<UserContest> userContests = userContestRepository.findByUser(usersRepository.findOne(loggedInUserId));
        
        Iterator<UserMatch> it = usersMatches.iterator();
        
        if(userContests.size() == 1 && userContests.get(0).getContest().getType().equalsIgnoreCase("lunch")){
            //logged in user has opted for only Lunch..filter out others
            while(it.hasNext()){
                UserMatch userMatch = it.next();
                if(!userMatch.getUserContest().getContest().getType().equalsIgnoreCase("lunch")){
                    it.remove();
                }
            }
        }else{
            //user has opted other than lunch
            boolean lunchOpted = false;
            for(UserContest userContest:userContests){
                if(userContest.getContest().getType().equalsIgnoreCase("lunch")){
                    lunchOpted = true;
                    break;
                }
            }
            if(!lunchOpted){
                //lunch not opted...filter out lunch
                while(it.hasNext()){
                    UserMatch userMatch = it.next();
                    if(userMatch.getUserContest().getContest().getType().equalsIgnoreCase("lunch")){
                        it.remove();
                    }
                }
            }
        }
        
        //decide winning points
        Map<Contest, Float> contestwisePoints = new HashMap<>();
        if(StringUtils.isNotEmpty(match.getStatus())){
            //match result is out
            for(UserMatch usersMatch:usersMatches){
                Contest contest = usersMatch.getUserContest().getContest();
                if(!contestwisePoints.containsKey(contest)){
                    if(usersMatch.getPoints() > 0){
                        contestwisePoints.put(contest, usersMatch.getPoints());
                    }
                }
            }
        }
        
        //Map<Contest, Map<Team, List<User>>>
        Map<Contest, Map<Team, List<String>>> contestwiseUsersMap = new HashMap<>();
        
        for(UserMatch useMatch:usersMatches){
            Contest contest = useMatch.getUserContest().getContest();
            Team team = useMatch.getTeam();
            Map<Team, List<String>> teamwiseUsers;
            
            if(contestwiseUsersMap.containsKey(contest)){
                teamwiseUsers = contestwiseUsersMap.get(contest);
            }else{
                teamwiseUsers = new HashMap<>();
            }
            
            List<String> teamUsers;
            if(teamwiseUsers.containsKey(team)){
                teamUsers = teamwiseUsers.get(team);
            }else{
                teamUsers = new ArrayList<>();
            }
            
            teamUsers.add(useMatch.getUserContest().getUser().getUserName());
            teamwiseUsers.put(team, teamUsers);
            contestwiseUsersMap.put(contest, teamwiseUsers);
        }
        
        List<ContestwisePrediction> predictions = new ArrayList<>();
        for(Contest contest:contestwiseUsersMap.keySet()){
            ContestwisePrediction prediction = new ContestwisePrediction();
            prediction.setContestName(contest.getType());
            if(contestwisePoints.containsKey(contest)){
                prediction.setWinningPoints(contestwisePoints.get(contest));
            }
            Map<Team, List<String>> teamwiseUsersMap = contestwiseUsersMap.get(contest);
            
            for(Team team:teamwiseUsersMap.keySet()){
                List<String> teamwiseUsers = teamwiseUsersMap.get(team); 
                if(team.getName().equalsIgnoreCase(match.getTeam1().getName())){
                    prediction.setTeam1Users(teamwiseUsers);
                }else if(team.getName().equalsIgnoreCase(match.getTeam2().getName())){
                    prediction.setTeam2Users(teamwiseUsers);
                }
            }
            predictions.add(prediction);
        }
        MatchSummaryDto matchSummaryDto = new MatchSummaryDto();
        matchSummaryDto.setTeam1(match.getTeam1().getName());
        matchSummaryDto.setTeam2(match.getTeam2().getName());
        matchSummaryDto.setWinner(match.getStatus());
        matchSummaryDto.setContestwisePredictions(predictions);
        return matchSummaryDto;
    }
    
    @Transactional
    @PostMapping(value = "/predictMatch", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public MatchPrediction predictMatch(@RequestBody MatchPrediction prediction) throws Exception {
        
        long matchId = prediction.getMatchId();
        long loggedInUserId = currentUserDetailsService.getLoggedInUser().getUserId();
        Users user = usersRepository.findOne(loggedInUserId);
        float usersTotalPts = user.getPoints();
        List<UserContest> userContests = userContestRepository.findByUser(user);
        
        for(ContestPrediction cPrediction:prediction.getContestPredictions()){
            
            UserContest userContest = userContests.stream()
                .filter(uContest -> uContest.getContest().getId().equals(cPrediction.getContestId()))
                .findFirst().orElse(null);
            
            if(userContest.getContest().getPoints() > usersTotalPts){
                throw new PointsException("insufficient points..");
            }else{
                usersTotalPts = usersTotalPts - userContest.getContest().getPoints();
            }
            
            UserMatch userMatch = new UserMatch();
            userMatch.setUserContest(userContest);
            userMatch.setMatch(matchRepository.findOne(matchId));
            userMatch.setTeam(teamRepository.findOne(cPrediction.getTeamId()));
            
            userMatchRepository.save(userMatch);
        }
        
        return prediction;
        
    }
    
    @Transactional
    @PutMapping(value = "/predictMatch", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public MatchPrediction updateMatchPrediction(@RequestBody MatchPrediction prediction) throws Exception {
        
        Match match = matchRepository.findOne(prediction.getMatchId());
        
        long loggedInUserId = currentUserDetailsService.getLoggedInUser().getUserId();
        List<UserContest> userContests = userContestRepository.findByUser(usersRepository.findOne(loggedInUserId));
        for(UserContest userContest:userContests){
            userMatchRepository.deleteByUserContestAndMatch(userContest, match);
        }
        
        return predictMatch(prediction);
        
    }
    
    @PutMapping(value = "/updateMatch", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public void updateMatchResult(@RequestBody MatchDto matchDto) throws Exception {
        
        Match match = matchRepository.findOne(matchDto.getMatchId());
        String winner = null;
        if(match.getTeam1().getId() == matchDto.getWinningTeamId()){
            winner = match.getTeam1().getName(); 
        }else if(match.getTeam2().getId() == matchDto.getWinningTeamId()){
            winner = match.getTeam2().getName();
        }else{
            throw new Exception("Unknown winning team id...");
        }
        match.setStatus(winner);
        matchRepository.save(match);
        
        List<UserMatch> usersMatches = userMatchRepository.getByMatch(match);
        Map<Contest, List<UserMatch>> map = new HashMap<>();
        for(UserMatch userMatch:usersMatches){
            Contest contest = userMatch.getUserContest().getContest();
            List<UserMatch> userMatches;
            if(map.containsKey(contest)){
                userMatches = map.get(contest);
            }else{
                userMatches = new ArrayList<>();
            }
            userMatches.add(userMatch);
            map.put(contest, userMatches);
        }
        
        float userPoints;
        for(Contest contest:map.keySet()){
            if(contest.getType().equalsIgnoreCase("lunch")){
                //we'll need do something
            }else if(contest.getType().contains("Points")){
                List<UserMatch> userMatches = map.get(contest);
                List<UserMatch> winners = userMatches.stream()
                        .filter(userMatch -> userMatch.getTeam().getId() == matchDto.getWinningTeamId())
                        .collect(Collectors.toList());
                List<UserMatch> loosers = userMatches.stream()
                        .filter(userMatch -> userMatch.getTeam().getId() != matchDto.getWinningTeamId())
                        .collect(Collectors.toList());
                float looserPoints = loosers.size() * contest.getPoints();
                int winnerCount = winners.size();
                for(UserMatch looserMatch:loosers){
                    looserMatch.setPoints(-contest.getPoints());
                    Users user = looserMatch.getUserContest().getUser();
                    user.setPoints(user.getPoints() - contest.getPoints());
                    usersRepository.save(user);
                }
                for(UserMatch winnerMatch:winners){
                    winnerMatch.setPoints(looserPoints/winnerCount);
                    Users user = winnerMatch.getUserContest().getUser();
                    user.setPoints(user.getPoints() + (looserPoints/winnerCount));
                    usersRepository.save(user);
                }
            }
        }
        userMatchRepository.save(usersMatches);
        
    }

}
