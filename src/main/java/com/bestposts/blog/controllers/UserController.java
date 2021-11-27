package com.bestposts.blog.controllers;

import com.bestposts.blog.models.Users;
import com.bestposts.blog.repos.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UsersRepo usersRepo;

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {

        model.addAttribute("user", new Users());
        return "signup";
    }

    @PostMapping("/signup")
    public String regUser(@RequestParam String email, @RequestParam String firstName, @RequestParam String lastName,
                          @RequestParam String password, Model model) {
        Users user = new Users(email, firstName, lastName, password);
        usersRepo.save(user);
        return "redirect:/main";
    }
}
