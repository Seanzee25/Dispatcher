package org.launchcode.dispatcher.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("workOrders")
public class WorkOrderController {

    @GetMapping("")
    public String displayWorkOrders(Model model) {
        // TODO: Return list of work orders for current business
        return "workOrders";
    }
}
