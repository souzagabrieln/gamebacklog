package com.gabriel.gamebacklog.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.gabriel.gamebacklog.model.User;
import com.gabriel.gamebacklog.model.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/register")
    public String formRegisterUser(Model model) {
        model.addAttribute("user", new User());
        return "registeruser";
    }

    @PostMapping("/user/register")
    public String registerUser(@ModelAttribute User user) {
        user.setRole("ROLE_USER");
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user/list")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.listAll());
        return "userlist";
    }

    @GetMapping("/user/{id}/edit")
    public String formEditUser(@PathVariable("id") UUID id, Model model) {

        User user = userService.findById(id);

        model.addAttribute("user", user);

        return "edituser";
    }

    @PostMapping("/user/{id}/edit")
    public String updateUser(@PathVariable("id") UUID id,
                            @ModelAttribute User user) {

        user.setId(id);

        userService.save(user);

        return "redirect:/user/list";
    }

    @PostMapping("/user/{id}/delete")
    public String deleteUser(@PathVariable("id") UUID id) {
        userService.deleteById(id);
        return "redirect:/user/list";
    }

}
