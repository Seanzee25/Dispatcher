package org.launchcode.dispatcher.controllers.admin;

import org.launchcode.dispatcher.models.Role;
import org.launchcode.dispatcher.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("admin/role")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("")
    public String viewRoles(Model model) {
        model.addAttribute("roles", roleRepository.findAll());
        return "roles/displayRoles";
    }

    @GetMapping("add")
    public String displayAddRoleForm(Model model) {
        model.addAttribute(new Role());
        return "roles/addRoleForm";
    }

    @PostMapping("add")
    public String processAddRoleForm(@ModelAttribute @Valid Role role, Errors errors,
                                     Model model) {

        if(errors.hasErrors()) {
            return "roles/addRoleForm";
        }

        roleRepository.save(role);
        return "redirect:";
    }

    @GetMapping("remove/{roleId}")
    public String removeRole(@PathVariable long roleId) {
        Role role = roleRepository.getOne(roleId);
        if(role != null) {
            roleRepository.delete(role);
        }
        return "redirect:admin/role";
    }
}
