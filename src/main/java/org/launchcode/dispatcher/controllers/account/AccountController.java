package org.launchcode.dispatcher.controllers.account;

import org.launchcode.dispatcher.models.Role;
import org.launchcode.dispatcher.models.User;
import org.launchcode.dispatcher.repositories.RoleRepository;
import org.launchcode.dispatcher.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("register")
public class AccountController {

    @Autowired
    private UserRepository userRepository;
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
        boolean userExists = usernameExists(user.getUsername());
        if(errors.hasErrors() || userExists) {
            if(userExists) {
                model.addAttribute("usernameExists", "Username already in use.");
            }
            model.addAttribute("roles", roleRepository.findAll());
            return "register";
        }

        String unencryptedPassword = user.getPassword();
        encryptUserPasswordAndSetRoles(user, roleIds);

        User savedUser = userRepository.save(user);
        loginUser(request, savedUser.getUsername(), unencryptedPassword);
        return "redirect:";
    }

    private void encryptUserPasswordAndSetRoles(User user, long[] roleIds) {
        encryptUserPassword(user);
        setUserRoles(user, roleIds);
    }

    private void encryptUserPassword(User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
    }

    private void setUserRoles(User user, long[] roleIds) {
        List<Role> roles = new ArrayList<>();
        for(long roleId : roleIds) {
            roles.add(roleRepository.getOne(roleId));
        }
        user.setRoles(roles);
    }

    private void loginUser(HttpServletRequest request, String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    private boolean usernameExists(String username) {
        User user = userRepository.findByUsername(username);
        return user != null;
    }
}
