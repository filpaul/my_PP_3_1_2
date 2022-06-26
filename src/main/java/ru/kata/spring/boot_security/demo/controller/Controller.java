package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.HashSet;
import java.util.Set;

@org.springframework.stereotype.Controller
public class Controller {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public Controller(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String getUsers(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute("user") User user, Model model) {
        model.addAttribute("authUserInfo", userService.getUserByEmail(userDetails.getUsername()));
        model.addAttribute("usersInfo", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("roleAdmin", roleService.getRoleByRoleName("ROLE_ADMIN"));
        return "adminPage";
    }

    @GetMapping("/user")
    public String userGetPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("authUserInfo", userService.getUserByEmail(userDetails.getUsername()));
        model.addAttribute("roleAdmin", roleService.getRoleByRoleName("ROLE_ADMIN"));
        return "user";
    }

    @PostMapping("/admin")
    public String createUser(@ModelAttribute("user") User user, @RequestParam(value = "inputRoles", required = false) Integer[] inputRoles) {
        Set<Role> temp = new HashSet<>();

        for(Integer i: inputRoles) {
            temp.add(roleService.getRoleById(i));
        }
        user.setRoleSet(temp);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/admin/{id}")
    public String updateUser(@ModelAttribute("user") User user,
                             @PathVariable("id") int id,
                             @RequestParam(value = "inputRoles", required = false) Integer[] inputRoles) {
        Set<Role> temp = new HashSet<>();

        for(Integer i: inputRoles) {
            temp.add(roleService.getRoleById(i));
        }
        user.setRoleSet(temp);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String removeUser(@PathVariable("id") int id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("/")
    public String indexPage() {
        return "redirect:/login";
    }


}
