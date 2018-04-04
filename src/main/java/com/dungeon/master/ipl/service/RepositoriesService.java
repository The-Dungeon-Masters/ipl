package com.dungeon.master.ipl.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.dungeon.master.ipl.dto.UserChangePassword;
import com.dungeon.master.ipl.dto.UserDto;
import com.dungeon.master.ipl.dto.UserRechargeDto;
import com.dungeon.master.ipl.model.Contest;
import com.dungeon.master.ipl.model.UserRecharge;
import com.dungeon.master.ipl.repository.UsersRechargeRepository;
import com.dungeon.master.ipl.util.IplConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dungeon.master.ipl.model.Users;
import com.dungeon.master.ipl.repository.UserContestRepository;
import com.dungeon.master.ipl.repository.UsersRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RepositoriesService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    UserContestService userContestService;

    @Autowired
    UserContestRepository userContestRepository;

    @Autowired
    UsersRechargeRepository usersRechargeRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoriesService.class);

    public List<UserDto> getAllUsers() {
        List<UserDto> userDtoList = new ArrayList<>();
        List<Users> allUsers = usersRepository.findAll();
        for (Users user : allUsers) {
            UserDto userDto = new UserDto();
            userDto.setUser(user);
            List<Long> contestList = new ArrayList<>();
            List<Contest> contests = userContestService.getUserContest(user.getUserId());
            for (Contest contest:contests) {
                contestList.add(contest.getId());
            }
            userDto.setContests(contestList);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    public UserDto getUser(long id) {
        Users user = usersRepository.findOne(id);
        UserDto userDto = new UserDto();
        userDto.setUser(user);
        List<Long> contestList = new ArrayList<>();
        List<Contest> contests = userContestService.getUserContest(id);
        for (Contest contest:contests) {
            contestList.add(contest.getId());
        }
        userDto.setContests(contestList);
        return userDto;
    }

    public UserDto getUser(String userName) {
        Users user = usersRepository.findByUserName(userName);
        UserDto userDto = new UserDto();
        userDto.setUser(user);
        List<Long> contestList = new ArrayList<>();
        List<Contest> contests = userContestService.getUserContest(user.getUserId());
        for (Contest contest:contests) {
            contestList.add(contest.getId());
        }
        userDto.setContests(contestList);
        return userDto;
    }

    public Users getUserByName(String name) {
        return usersRepository.findByUserName(name);
    }

    @Transactional
    public void deleteUser(long id) {
        usersRepository.delete(id);
    }

    @Transactional
    public void saveUser(UserDto userDto) {
        userDto.getUser().setPassword(getEncryptedPassword(userDto.getUser().getPassword()));
        Users addedUser = usersRepository.save(userDto.getUser());
        userContestService.saveUserContest(userDto);
        LOGGER.info("Added User : "+addedUser);

        UserRecharge userRecharge = new UserRecharge();
        userRecharge.setUser(addedUser);
        userRecharge.setRechargePoints(addedUser.getPoints());
        userRecharge.setComments("Initail points");
        Users addedByUser = getUserByName(addedUser.getCreatedBy());
        userRecharge.setRechargedBy(addedByUser);
        LOGGER.info("User Recharge: "+userRecharge);
        usersRechargeRepository.save(userRecharge);
    }

    @Transactional
    public void updateUser(UserDto userDto) {
        Users existingUser = usersRepository.getOne(userDto.getUser().getUserId());
        existingUser.setUserName(userDto.getUser().getUserName());
        existingUser.setEmail(userDto.getUser().getEmail());
        usersRepository.save(userDto.getUser());
        userContestRepository.deleteByUser(existingUser);
        saveUser(userDto);
    }

    @Transactional
    public void changePassword(UserChangePassword userChangePassword) {
        Users existingUser = usersRepository.getOne(userChangePassword.getUserId());
        // TODO : Check existing password
        existingUser.setPassword(getEncryptedPassword(userChangePassword.getNewPassword()));
        usersRepository.save(existingUser);
    }

    public List<UserRechargeDto> getRechageHistory(long userId) {
        List<UserRecharge> rechargeList = usersRechargeRepository.findByUser(usersRepository.findOne(userId));
        List<UserRechargeDto> rechargeDtoList = new ArrayList<>();
        for (UserRecharge userRecharge : rechargeList) {
            UserRechargeDto userRechargeDto = new UserRechargeDto(
                    userRecharge.getUser().getUserId(),
                    userRecharge.getRechargePoints(),
                    userRecharge.getRechargedBy().getUserName(),
                    userRecharge.getComments(),
                    userRecharge.getRechargeTime()
            );
            rechargeDtoList.add(userRechargeDto);
        }
        return rechargeDtoList;
    }

    @Transactional
    public void doRecharge(UserRechargeDto userRechargeDto) {
        UserRecharge userRecharge = new UserRecharge();
        Users user = usersRepository.findOne(userRechargeDto.getUserId());
        Users rechargedBy = usersRepository.findByUserName(userRechargeDto.getRechargedBy());
        userRecharge.setUser(user);
        userRecharge.setRechargedBy(rechargedBy);
        userRecharge.setComments(userRechargeDto.getComment());
        userRecharge.setRechargePoints(userRechargeDto.getRechargePoints());
        userRecharge.setRechargeTime(Calendar.getInstance().getTime());
        usersRechargeRepository.save(userRecharge);

        //Update user points
        float newPoints = user.getPoints() + userRechargeDto.getRechargePoints();
        user.setPoints(newPoints);
        usersRepository.save(user);
    }

    private String getEncryptedPassword(String plainPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(IplConstants.PASSWORD_STRENGTH);
        return passwordEncoder.encode(plainPassword);
    }
}
