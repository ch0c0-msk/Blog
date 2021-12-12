package com.bestposts.blog.controllers;

import com.bestposts.blog.models.CustomUserDetailsService;
import com.bestposts.blog.models.Posts;
import com.bestposts.blog.models.Users;
import com.bestposts.blog.repos.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private CustomUserDetailsService userDetailsService;



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

    @GetMapping("/profile/{id}")
    public String showProfile(@PathVariable(value = "id") Integer id, Model model, Principal principal)
    {

        Integer curUserId = null;
        Users user = usersRepo.findById(id).orElseThrow();
        if (principal != null) {
           curUserId = ((Users) userDetailsService.loadUserByUsername(principal.getName())).getId();
        }
        model.addAttribute("user",user);
        model.addAttribute("curUserId",curUserId);
        return "profile";
    }

    @GetMapping("/profile/{id}/edit")
    public String editProfile(@PathVariable(value = "id") Integer id, Model model, Principal principal) {

        Optional<Users> user = usersRepo.findById(id);
        Integer currId = ((Users) userDetailsService.loadUserByUsername(principal.getName())).getId();
        model.addAttribute("curUsrId",currId);

        if ( (user.get()).getId() == currId) {

            ArrayList<Users> res = new ArrayList<>();
            user.ifPresent(res::add);
            model.addAttribute("user", res);
            return "editUser";
        }

        return "redirect:/main";
    }

    @PostMapping("/profile/{id}/edit")
    public String updateProfile(@PathVariable(value = "id") Integer id, @RequestParam String grup, @RequestParam Integer age,
                                @RequestParam String city, @RequestParam String about, Model model, Principal principal) {

        Users user = usersRepo.findById(id).orElseThrow();
        Integer currId = ((Users) userDetailsService.loadUserByUsername(principal.getName())).getId();

        if (user.getId() == currId) {

            user.setGrup(grup);
            user.setAge(age);
            user.setCity(city);
            user.setAbout(about);
            usersRepo.save(user);
            return "redirect:/main";
        }

        return "redirect:/main";
    }
}
