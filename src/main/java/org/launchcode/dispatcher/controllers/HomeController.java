package org.launchcode.dispatcher.controllers;

import org.launchcode.dispatcher.models.Role;
import org.launchcode.dispatcher.models.User;
import org.launchcode.dispatcher.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

@Controller
public class HomeController {

    @GetMapping("")
    public String index() {
        return "index";
    }

    @GetMapping("businessOwner")
    public String businessOwner() {
        return "businessOwner";
    }

    @GetMapping("dispatcher")
    public String dispatcher() {
        return "dispatcher";
    }

    @GetMapping("fieldTech")
    public String fieldTech() {
        return "fieldTech";
    }

    @GetMapping("login")
    public String displayLoginPage() {
        return "login";
    }

    @RequestMapping("logout-success")
    public String successfulLogout(Model model) {
        model.addAttribute("logout", "logout");
        return "login";
    }

    @Autowired
    UserRepository userRepository;

    @RequestMapping("test")
    @ResponseBody
    public String test(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        Collection<Role> roles = user.getRoles();
        Collection<String> roleNames = new ArrayList<>();
        for(Role role : roles) {
            roleNames.add(role.getName());
        }
        return roleNames.toString();
    }

}
