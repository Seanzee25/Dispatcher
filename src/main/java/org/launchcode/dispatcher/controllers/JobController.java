package org.launchcode.dispatcher.controllers;

import org.launchcode.dispatcher.models.User;
import org.launchcode.dispatcher.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("jobs")
public class JobController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String index(Model model, Principal principal) {
        User currentUser = userRepository.findByUsername(principal.getName());
        model.addAttribute("workOrders", currentUser.getWorkOrders());
        return "jobs";
    }
}
