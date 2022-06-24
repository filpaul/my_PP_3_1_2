package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String showAllUsers(Model model, Principal principal) {
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("userLogin", userService.getUserByEmail(principal.getName()));
        model.addAttribute("allRoles", roleService.findAllRoles());
        model.addAttribute("newUser", new User());
        return "Admin";
    }

    @PostMapping("/new")
    public String saveUser(@ModelAttribute("newUser") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/admin";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("editUser") User user, Principal principal) {
        if (!userService.getUserByEmail(principal.getName()).getPassword().startsWith("$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }
    @DeleteMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}

