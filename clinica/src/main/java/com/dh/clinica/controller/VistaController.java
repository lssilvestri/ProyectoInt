package com.dh.clinica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VistaController {
    @GetMapping("/")
    public String login(Model model) {
        model.addAttribute("content", "pages/user.login");
        return "layouts/site";
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("content", "pages/home");
        return "layouts/home";
    }

    @GetMapping("/user-create")
    public String createUser(Model model) {
        model.addAttribute("content", "pages/user.create");
        return "layouts/home";
    }

    @GetMapping("/user-modify")
    public String modifyUser(Model model) {
        model.addAttribute("content", "pages/user.modify");
        return "layouts/home";
    }

    @GetMapping("/user-dashboard")
    public String userDashboard(Model model) {
        model.addAttribute("content", "pages/user.dashboard");
        return "layouts/home";
    }

    @GetMapping("/ticket-dashboard")
    public String ticketDashboard(Model model) {
        model.addAttribute("content", "pages/ticket.dashboard");
        return "layouts/home";
    }
}