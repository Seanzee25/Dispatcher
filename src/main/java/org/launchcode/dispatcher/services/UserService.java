package org.launchcode.dispatcher.services;

import org.launchcode.dispatcher.models.Role;
import org.launchcode.dispatcher.models.User;
import org.launchcode.dispatcher.repositories.RoleRepository;
import org.launchcode.dispatcher.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean userExists(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        return existingUser != null;
    }

    public void setUserRole(User user, long roleId) {
        Role role = roleRepository.getOne(roleId);
        Collection<Role> roleCollection = new ArrayList<>();
        roleCollection.add(role);
        user.setRoles(roleCollection);
    }

    public void registerAndLoginUser(User user, HttpServletRequest request) {
        String unencryptedPassword = user.getPassword();
        registerUser(user);
        loginUser(request, user.getUsername(), unencryptedPassword);
    }

    private void registerUser(User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }

    private void loginUser(HttpServletRequest request, String username,
                              String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
