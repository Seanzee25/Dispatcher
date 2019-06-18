package org.launchcode.dispatcher.controllers;

import org.launchcode.dispatcher.models.Customer;
import org.launchcode.dispatcher.models.User;
import org.launchcode.dispatcher.repositories.CustomerRepository;
import org.launchcode.dispatcher.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;

@Controller
@RequestMapping("customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String index(Model model, Principal principal) {
        User currentUser = userRepository.findByUsername(principal.getName());
        Collection<Customer> customers = customerRepository.findAllByBusiness(currentUser.getBusiness());

        model.addAttribute("customers", customers);

        return "customers";
    }

    @GetMapping("add")
    public String displayAddCustomerForm(Model model) {

        model.addAttribute(new Customer());
        return "addCustomer";
    }

    @PostMapping("add")
    public String processAddCustomerForm(@ModelAttribute @Valid Customer newCustomer,
                                         Errors errors, Model model, Principal principal) {
        if(errors.hasErrors()) {
            model.addAttribute(newCustomer);
            return "addCustomer";
        }

        User currentUser = userRepository.findByUsername(principal.getName());
        newCustomer.setBusiness(currentUser.getBusiness());
        customerRepository.save(newCustomer);
        return "redirect:/customers";
    }
}
