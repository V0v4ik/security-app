package com.volodymyr.studying.controller;

import com.volodymyr.studying.exceptions.NoSuchUserException;
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
@RequestMapping("/login")
public class LoginController {

    //TODO login works without my help??=)=)

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String showLoginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping
    public String loginUser(@Validated @ModelAttribute("user") User user) {
        User userByUsername = userRepository.findByUsername(user.getUsername());
        if (userByUsername == null) {
            throw new NoSuchUserException("User doesn't exist");
        }
        return "redirect:/home";
    }
}
