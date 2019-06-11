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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class AccountController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("login")
    public String displayLoginRegisterPage(Model model, Principal principal) {
        if(principal != null) {
            return "redirect:/logout";
        }
        model.addAttribute(new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "login";
    }

//    @GetMapping("")
//    public String displayAccountRegisterForm(Model model) {
//        model.addAttribute(new User());
//        model.addAttribute("roles", roleRepository.findAll());
//        return "login";
//    }

    @PostMapping("register")
    @Transactional
    public String createAccountAndLogin(@ModelAttribute @Valid User user,
                                        Errors errors, Model model,
                                        @RequestParam long roleId,
                                        HttpServletRequest request) {

        if(errors.hasErrors() || userService.userExists(user)) {
            if(userService.userExists(user)) {
                model.addAttribute("usernameExists", "Username already in use.");
            }
            model.addAttribute(user);
            model.addAttribute("roles", roleRepository.findAll());
            return "login";
        }

        userService.setUserRole(user, roleId);
        userService.registerAndLoginUser(user, request);

        return "redirect:";
    }

    @GetMapping("logout-success")
    public String successfulLogout(Model model) {
        return "redirect:/login";
    }
}
