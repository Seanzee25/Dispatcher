package org.launchcode.dispatcher.controllers;

import org.launchcode.dispatcher.models.Business;
import org.launchcode.dispatcher.models.Invite;
import org.launchcode.dispatcher.models.User;
import org.launchcode.dispatcher.repositories.InviteRepository;
import org.launchcode.dispatcher.repositories.UserRepository;
import org.launchcode.dispatcher.searchFilters.EmployeeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

import static org.launchcode.dispatcher.repositories.specifications.EmployeeSpecifications.byBusiness;
import static org.launchcode.dispatcher.repositories.specifications.EmployeeSpecifications.byFilter;

@Controller
@RequestMapping("employees")
public class EmployeeController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InviteRepository inviteRepository;

    @GetMapping("")
    public String displayEmployeesPage(Model model, Principal principal) {
        User currentUser = userRepository.findByUsername(principal.getName());
        Business business = currentUser.getBusiness();

        model.addAttribute("filter", new EmployeeFilter());
        model.addAttribute("employees",
                userRepository.findAll(byBusiness(business), Sort.by("username")));
        model.addAttribute("pendingInvites", business.getInvites());

        return "employees";
    }

    @PostMapping("")
    public String displayFilteredEmployees(Model model, Principal principal,
                                           @ModelAttribute EmployeeFilter filter) {

        User currentUser = userRepository.findByUsername(principal.getName());
        Business business = currentUser.getBusiness();

        model.addAttribute("filter", filter);
        model.addAttribute("employees",
                            userRepository.findAll(byBusiness(business).and(byFilter(filter)),
                                                   Sort.by("username")));

        return "employees";
    }

    @GetMapping("addEmployee")
    public String displayAddEmployeeForm() {
        return "addEmployee";
    }

    @PostMapping("addEmployee")
    public String searchAvaiableEmployees(Model model, @RequestParam String username) {
        Collection<User> availableUsers = userRepository.findAllByUsernameAndBusinessIsNull(username);
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
