package org.launchcode.dispatcher.controllers;

import org.launchcode.dispatcher.models.User;
import org.launchcode.dispatcher.repositories.UserRepository;
import org.launchcode.dispatcher.services.HomeDelegationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HomeDelegationService homeDelegationService;

    @GetMapping("")
    public String delegateUserBasedOnRole(Principal principal) {
        User currentUser = userRepository.findByUsername(principal.getName());
        return homeDelegationService.getHomeAction(currentUser);
    }

    @GetMapping("dispatcher")
    public String dispatcher() {
        return "dispatcher";
    }

    @GetMapping("fieldTech")
    public String fieldTech() {
        return "fieldTech";
    }



}
