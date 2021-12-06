package com.bestposts.blog.controllers;

import com.bestposts.blog.models.Users;
import com.bestposts.blog.repos.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersRepo usersRepo;



    @GetMapping("/signup")
    public String signup() {

        return "signUp";
    }

    @PostMapping("/signup")
    public String regUser(@RequestParam String email, @RequestParam String firstName, @RequestParam String lastName,
                          @RequestParam String password, Model model) {
        Users user = new Users();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(passwordEncoder.encode(password));
        usersRepo.save(user);
        return "redirect:/login";
    }
}
