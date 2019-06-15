package org.launchcode.dispatcher.controllers;

import org.launchcode.dispatcher.models.Invite;
import org.launchcode.dispatcher.models.User;
import org.launchcode.dispatcher.repositories.InviteRepository;
import org.launchcode.dispatcher.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("invites")
public class InviteController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InviteRepository inviteRepository;

    @GetMapping("")
    public String displayInvites(Model model, Principal principal) {
        User currentUser = userRepository.findByUsername(principal.getName());
        model.addAttribute("invites", currentUser.getInvites());
        return "invites";
    }

    @GetMapping("accept/{inviteId}")
    public String acceptInvite(@PathVariable long inviteId, Principal principal) {
        Invite invite = inviteRepository.getOne(inviteId);
        User currentUser = invite.getUser();
        if(currentUser.getBusiness() == null) {
            currentUser.setBusiness(invite.getBusiness());
            userRepository.save(currentUser);
        }
        inviteRepository.deleteAll(currentUser.getInvites());
        return "redirect:/";
    }

    @GetMapping("reject/{inviteId}")
    public String rejectInvite(@PathVariable long inviteId) {
        Invite invite = inviteRepository.getOne(inviteId);
        inviteRepository.delete(invite);
        return "redirect:/invites";
    }
}
