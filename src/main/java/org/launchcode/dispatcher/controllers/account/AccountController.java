package org.launchcode.dispatcher.controllers.account;

import org.launchcode.dispatcher.models.User;
import org.launchcode.dispatcher.repositories.RoleRepository;
import org.launchcode.dispatcher.repositories.UserRepository;
import org.launchcode.dispatcher.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("register")
public class AccountController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("")
    public String displayAccountRegisterForm(Model model) {
        model.addAttribute(new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "register";
    }

    @PostMapping("")
    @Transactional
    public String createAccountAndLogin(@ModelAttribute @Valid User user,
                                        Errors errors, Model model,
                                        @RequestParam long[] roleIds,
                                        HttpServletRequest request) {

        if(errors.hasErrors() || userService.userExists(user)) {
            if(userService.userExists(user)) {
                model.addAttribute("usernameExists", "Username already in use.");
            }
            model.addAttribute("roles", roleRepository.findAll());
            return "register";
        }

        userService.setUserRoles(user, roleIds);
        userService.registerAndLoginUser(user, request);

        return "redirect:";
    }
}
