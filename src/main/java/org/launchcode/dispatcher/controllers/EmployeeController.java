package org.launchcode.dispatcher.controllers;

import org.launchcode.dispatcher.models.Business;
import org.launchcode.dispatcher.models.Invite;
import org.launchcode.dispatcher.models.User;
import org.launchcode.dispatcher.repositories.InviteRepository;
import org.launchcode.dispatcher.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

@Controller
@RequestMapping("employees")
public class EmployeeController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InviteRepository inviteRepository;

    @GetMapping("")
    public String displayEmployeesPage(Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        Business business = user.getBusiness();
        Collection<User> employees = business.getEmployees();
        model.addAttribute("employees", employees);
        model.addAttribute("pendingInvites", business.getInvites());
        return "employees";
    }

    @GetMapping("addEmployee")
    public String displayAddEmployeeForm() {
        return "addEmployee";
    }

    @PostMapping("addEmployee")
    public String searchAvaiableEmployees(Model model, @RequestParam String username) {
        Collection<User> availableUsers = userRepository.findByUsernameAndBusinessIsNull(username);
        model.addAttribute("availableUsers", availableUsers);
        return "addEmployee";
    }

    @GetMapping("addEmployee/sendInvite/{userId}")
    public String sendInvite(@PathVariable long userId, Principal principal) {
        User currentUser = userRepository.findByUsername(principal.getName());
        User userToInvite = userRepository.getOne(userId);
        Business currentBusiness = currentUser.getBusiness();
        if(userToInvite.getBusiness() == null) {
            Invite newInvite = new Invite();
            newInvite.setBusiness(currentBusiness);
            newInvite.setUser(userToInvite);
            inviteRepository.save(newInvite);
        }

        return "redirect:/employees";
    }
}
