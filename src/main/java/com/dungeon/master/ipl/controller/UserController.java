package com.dungeon.master.ipl.controller;

import java.util.List;

import com.dungeon.master.ipl.dto.UserChangePassword;
import com.dungeon.master.ipl.dto.UserDto;
import com.dungeon.master.ipl.model.Contest;
import com.dungeon.master.ipl.service.UserContestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dungeon.master.ipl.model.Users;
import com.dungeon.master.ipl.service.RepositoriesService;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private RepositoriesService repositoriesService;

    @Autowired
    private UserContestService userContestService;

    @PostMapping(value = "/adduser", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public void addUser(@RequestBody UserDto user) {
        repositoriesService.saveUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable long id) {
        repositoriesService.deleteUser(id);
    }

    @GetMapping(path = "/getall", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> getAllUsers() {
        return repositoriesService.getAllUsers();
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
    public void changePassword(@RequestBody UserChangePassword newPassword) {
        repositoriesService.changePassword(newPassword);
    }

    @GetMapping(path = "/getcontests/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public List<Contest> getUserContest(@PathVariable long id) {
        return userContestService.getUserContest(id);
    }
}
