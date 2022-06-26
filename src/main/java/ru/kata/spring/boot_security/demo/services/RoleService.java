package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void addRole(Role role) {
        roleRepository.save(role);
    }

    public void removeRole(int id) {
        roleRepository.deleteById(id);
    }

    public Set<Role> getAllRoles() {
        return new HashSet<>(roleRepository.findAll());
    }

    public Role getRoleById(int id) {
        return roleRepository.findRoleById(id);
    }

    public Role getRoleByRoleName(String roleName) {
        return roleRepository.findRoleByRoleName(roleName);
    }

}
