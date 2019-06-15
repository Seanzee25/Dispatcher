package org.launchcode.dispatcher.services;

import org.launchcode.dispatcher.models.User;
import org.springframework.stereotype.Service;

@Service
public class HomeDelegationService {

    public String getHomeAction(User user) {
        String homeAction = "redirect:/";
        String roleName = user.getRoles().iterator().next().getName();

        if(roleName.equals("ROLE_BUSINESS_OWNER")) {
            if(user.getBusiness() == null) {
                homeAction = "redirect:/business/addBusiness";
            } else {
                homeAction = "redirect:/employees";
            }
        } else if(user.getBusiness() != null) {
            if(roleName.equals("ROLE_DISPATCHER")) {
                homeAction = "redirect:/workOrders";
            } else if(roleName.equals("ROLE_FIELD_TECH")) {
                homeAction = "redirect:/jobs";
            }
        } else {
            homeAction = "redirect:/invites";
        }


        return homeAction;
    }
}
