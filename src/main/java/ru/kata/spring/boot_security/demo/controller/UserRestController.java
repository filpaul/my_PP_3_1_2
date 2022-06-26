package ru.kata.spring.boot_security.demo.controller;

import org.hibernate.tool.hbm2ddl.ImportSqlCommandExtractorInitiator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") int id) {
        return userService.getUserById(id);
    }

    @PostMapping()
    public User addUser(@RequestBody User user, @RequestParam(value = "inputRoles", required = false) Integer[] inputRoles){
        Set<Role> temp = new HashSet<>();

        for(Integer i: inputRoles) {
            temp.add(roleService.getRoleById(i));
        }
        user.setRoleSet(temp);
        userService.addUser(user);
        return userService.getUserByEmail(user.getEmail());
    }

    @PatchMapping()
    public User updateUser(@RequestBody User user, @RequestParam(value = "inputRoles", required = false) Integer[] inputRoles){
        Set<Role> temp = new HashSet<>();

        for(Integer i: inputRoles) {
            temp.add(roleService.getRoleById(i));
        }
        user.setRoleSet(temp);
        userService.updateUser(user);
        return user;
    }

    @GetMapping("/auth")
    public User getAuthUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.getUserByEmail(userDetails.getUsername());
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") int id) {
        userService.removeUserById(id);
    }


}
