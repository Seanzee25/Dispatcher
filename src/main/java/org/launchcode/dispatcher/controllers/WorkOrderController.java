package org.launchcode.dispatcher.controllers;

import org.launchcode.dispatcher.models.User;
import org.launchcode.dispatcher.models.WorkOrder;
import org.launchcode.dispatcher.repositories.RoleRepository;
import org.launchcode.dispatcher.repositories.UserRepository;
import org.launchcode.dispatcher.repositories.WorkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.Date;

@Controller
@RequestMapping("workOrders")
public class WorkOrderController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkOrderRepository workOrderRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("")
    public String displayWorkOrders(Model model, Principal principal) {
        User currentUser = userRepository.findByUsername(principal.getName());
        model.addAttribute("workOrders", currentUser.getBusiness().getWorkOrders());
        return "workOrders";
    }

    @GetMapping("add/{customerId}")
    public String displayAddWorkOrderForm(Model model, Principal principal, @PathVariable long customerId) {
        User currentUser = userRepository.findByUsername(principal.getName());
        WorkOrder workOrder = new WorkOrder();
        workOrder.setDate(new Date());
        model.addAttribute(workOrder);
        model.addAttribute("business", currentUser.getBusiness());
        model.addAttribute("customerId", customerId);
        return "addWorkOrder";
    }

    @PostMapping("add/{customerId}")
    public String processAddWorkOrderForm(@ModelAttribute @Valid WorkOrder newWorkOrder,
                                          Errors errors, Principal principal, Model model,
                                          @PathVariable long customerId) {


        if(errors.hasErrors()) {
            model.addAttribute(newWorkOrder);
            User currentUser = userRepository.findByUsername(principal.getName());
            model.addAttribute("customerId", customerId);
            model.addAttribute("business", currentUser.getBusiness());
            return "addWorkOrder";
        }

        workOrderRepository.save(newWorkOrder);

        return "redirect:/workOrders";
    }

    @GetMapping("assign/{workOrderId}")
    public String displayAssignWorkOrderForm(Principal principal, Model model,
                                             @PathVariable long workOrderId) {
        User currentUser = userRepository.findByUsername(principal.getName());
        Collection<User> technicians = userRepository
                .findAllByRolesAndBusiness(roleRepository.findByName("ROLE_FIELD_TECH"), currentUser.getBusiness());
        model.addAttribute(new WorkOrder());
        model.addAttribute("workOrderId", workOrderId);
        model.addAttribute("technicians", technicians);
        return "assignTechnicians";
    }

    @PostMapping("assign/{workOrderId}")
    public String processAssignWorkOrderForm(@PathVariable long workOrderId,
                                             @ModelAttribute WorkOrder workOrder) {
        WorkOrder workOrderToAssign = workOrderRepository.getOne(workOrderId);
        workOrderToAssign.setTechnicians(workOrder.getTechnicians());
        workOrderRepository.save(workOrderToAssign);
        return "redirect:/workOrders";
    }
}
