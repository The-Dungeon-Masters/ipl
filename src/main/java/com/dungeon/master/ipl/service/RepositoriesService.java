package com.dungeon.master.ipl.service;

import java.util.ArrayList;
import java.util.List;

import com.dungeon.master.ipl.dto.UserChangePassword;
import com.dungeon.master.ipl.dto.UserDto;
import com.dungeon.master.ipl.model.Contest;
import com.dungeon.master.ipl.util.IplConstants;
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
        usersRepository.save(userDto.getUser());
        userContestService.saveUserContest(userDto);
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

    private String getEncryptedPassword(String plainPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(IplConstants.PASSWORD_STRENGTH);
        return passwordEncoder.encode(plainPassword);
    }
}
