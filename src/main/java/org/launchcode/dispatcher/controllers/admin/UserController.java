package org.launchcode.dispatcher.controllers.admin;

import org.launchcode.dispatcher.models.Role;
import org.launchcode.dispatcher.models.User;
import org.launchcode.dispatcher.repositories.RoleRepository;
import org.launchcode.dispatcher.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("")
    public String viewUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users/displayUsers";
    }

    @GetMapping("/add")
    public String displayAddUserForm(Model model) {
        model.addAttribute(new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "users/addUserForm";
    }

    @PostMapping("/add")
    public String processAddUserForm(@ModelAttribute @Valid User user, Errors errors,
                                     Model model, @RequestParam long[] roleIds) {
        if(errors.hasErrors()) {
            return "users/addUserForm";
        }

        List<Role> roles = new ArrayList<>();
        for(long id : roleIds) {
            roles.add(roleRepository.getOne(id));
        }
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:";
    }

    @GetMapping("/remove/{id}")
    public String removeUser(@PathVariable long id) {
        User user = userRepository.getOne(id);
        if(user != null) {
            userRepository.delete(user);
        }
        return "redirect:/admin/user";
    }
}
