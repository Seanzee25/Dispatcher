package org.launchcode.dispatcher.controllers;

import org.launchcode.dispatcher.models.User;
import org.launchcode.dispatcher.models.WorkOrder;
import org.launchcode.dispatcher.models.WorkOrderStatus;
import org.launchcode.dispatcher.repositories.UserRepository;
import org.launchcode.dispatcher.repositories.WorkOrderRepository;
import org.launchcode.dispatcher.searchFilters.WorkOrderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static org.launchcode.dispatcher.repositories.specifications.WorkOrderSpecifications.byExample;
import static org.launchcode.dispatcher.repositories.specifications.WorkOrderSpecifications.byUser;

@Controller
@RequestMapping("jobs")
public class JobController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkOrderRepository workOrderRepository;

    @GetMapping("")
    public String index(Model model, Principal principal) {
        User currentUser = userRepository.findByUsername(principal.getName());
        model.addAttribute("filter", new WorkOrderFilter());
        model.addAttribute("workOrders",
                workOrderRepository.findAll(byUser(currentUser), Sort.by("date", "startTime")));
        return "jobs";
    }

    @PostMapping("")
    public String displayFilteredJobs(Model model, Principal principal,
                                      @ModelAttribute WorkOrderFilter filter) {
        User currentUser = userRepository.findByUsername(principal.getName());
        model.addAttribute("filter", filter);
        model.addAttribute("workOrders",
                workOrderRepository.findAll(byUser(currentUser).and(byExample(filter)), Sort.by("date", "startTime")));
        return "jobs";
    }

    @GetMapping("start/{workOrderId}")
    public String startJob(@PathVariable long workOrderId) {
        // TODO: Be sure to test security of start and finish tasks. Maybe others as well.
        WorkOrder workOrder = workOrderRepository.getOne(workOrderId);
        workOrder.setStatus(WorkOrderStatus.STARTED);
        workOrderRepository.save(workOrder);

        return "redirect:/jobs";
    }

    @GetMapping("/finish/{workOrderId}")
    public String finishJob(@PathVariable long workOrderId) {
        WorkOrder workOrder = workOrderRepository.getOne(workOrderId);
        workOrder.setStatus(WorkOrderStatus.FINISHED);
        workOrderRepository.save(workOrder);

        return "redirect:/jobs";
    }
}
