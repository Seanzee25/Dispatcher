package org.launchcode.dispatcher.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminMenuController {

    @GetMapping("")
    public String displayMenu() {
        return "admin/adminMenu";
    }
}
