package com.gl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.gl.entity.User;
import com.gl.service.UserService;

@Controller
public class AuthController {

	@Autowired
	private UserService userService;

	@GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        addUserModelAttribute(model);
        model.addAttribute("status", null);
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(Model model, @ModelAttribute User user) {
        model.addAttribute("status", userService.register(user));
        addUserModelAttribute(model);
        return "register";
    }
    
    @GetMapping("/403")
    public String showAccessDeniedPage() {
        return "403";
    }
    
    private void addUserModelAttribute(Model model) {
        model.addAttribute("user", new User());
    }

}
