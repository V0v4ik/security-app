package com.volodymyr.studying.controller;

import com.volodymyr.studying.model.Role;
import com.volodymyr.studying.model.User;
import com.volodymyr.studying.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String showLRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping
    public String registerNewUser(@Validated @ModelAttribute("user") User user) {
        User userByUsername = userRepository.findByUsername(user.getUsername());
        User userByEmail = userRepository.findByEmail(user.getEmail());
        if (userByUsername != null || userByEmail != null) {
            throw new RuntimeException("User already exist");
        }
        user.setRole(Role.ROLE_USER);
        user.setActive(true);
        userRepository.save(user);
        return "redirect:/login";
    }
}
