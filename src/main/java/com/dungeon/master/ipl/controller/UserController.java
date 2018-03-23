package com.dungeon.master.ipl.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dungeon.master.ipl.model.Users;
import com.dungeon.master.ipl.service.RepositoriesService;

@RestController
@RequestMapping
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private RepositoriesService repositoriesService;

    @GetMapping(path = "/getusers", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public List<Users> getAllConsumers() {
        LOGGER.info(repositoriesService.getAllUsers().toString());
        return repositoriesService.getAllUsers();
    }
}
