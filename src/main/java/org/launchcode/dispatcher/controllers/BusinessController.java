package org.launchcode.dispatcher.controllers;

import org.launchcode.dispatcher.models.Business;
import org.launchcode.dispatcher.models.User;
import org.launchcode.dispatcher.repositories.BusinessRepository;
import org.launchcode.dispatcher.repositories.RoleRepository;
import org.launchcode.dispatcher.repositories.UserRepository;
import org.launchcode.dispatcher.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("business")
public class BusinessController {

    @Autowired
    private BusinessRepository businessRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;

//    @GetMapping("")
//    public String displayOwnerPage() {
//        return "businessOwner/ownerMenuPage";
//    }

    @GetMapping("addBusiness")
    public String displayAddBusinessForm(Model model) {
        model.addAttribute(new Business());
        return "businessOwner/addBusinessPage";
    }

    @PostMapping("addBusiness")
    public String processAddBusinessForm(@ModelAttribute @Valid Business business,
                                         Errors errors, Model model, Principal principal) {
        if(errors.hasErrors()) {
            return "addBusiness";
        }
        User currentUser = userRepository.findByUsername(principal.getName());
        business.setOwner(currentUser);
        businessRepository.save(business);
        currentUser.setBusiness(business);
        userRepository.save(currentUser);

        return "redirect:/employees";
    }

    @GetMapping("addEmployee")
    public String displayAddEmployeeForm() {
        return "businessOwner/addEmployeePage";
    }

    @PostMapping("addEmployee")
    public String processAddEmployeeForm(Model model, @RequestParam String username) {
        List<User> usersWithoutBusiness = userRepository.findByBusinessIsNull();
        model.addAttribute("users", usersWithoutBusiness);
        return "businessOwner/addEmployeePage";
    }

    @GetMapping("addEmployee/{userId}")
    public String addEmployeeToBusiness(@PathVariable long userId, Principal principal) {
        User currentUser = userRepository.findByUsername(principal.getName());
        User userToAdd = userRepository.getOne(userId);
        Business currentBusiness = currentUser.getBusiness();
        currentBusiness.getEmployees().add(userToAdd);
        businessRepository.save(currentBusiness);
        return "redirect:/businessOwner";
    }

    @GetMapping("editEmployees")
    public String editEmployees(Model model, Principal principal) {
        User currentUser = userRepository.findByUsername(principal.getName());
        Business currentBusiness = currentUser.getBusiness();
        Collection<User> employees = currentBusiness.getEmployees();
        model.addAttribute("employees", employees);
        return "businessOwner/editEmployeesPage";
    }

    @GetMapping("editEmployee/{userId}")
    public String displayEditEmployeeForm(@PathVariable long userId, Model model) {
        User user = userRepository.getOne(userId);
        model.addAttribute("employee", user);
        model.addAttribute("roles", roleRepository.findAll());
        return "businessOwner/editSingleEmployeePage";
    }

    @PostMapping("editEmployee/*")
    public String processEditEmployeeForm(@ModelAttribute @Valid User employee,
                                          Errors errors,
                                          @RequestParam long employeeId,
                                          @RequestParam long[] roles) {
        if(errors.hasErrors()) {
            return "businessOwner/editSingleEmployeePage";
        }
        User editedUser = userRepository.getOne(employeeId);
//        userService.setUserRole(editedUser, roles);
        userRepository.save(editedUser);
        return "redirect:/businessOwner";
    }



}
