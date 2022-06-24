package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.model.User;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserServiceImpl userService;
    @Autowired
    public UserDetailServiceImpl(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found!", email));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), user.getAuthorities());
    }
}
