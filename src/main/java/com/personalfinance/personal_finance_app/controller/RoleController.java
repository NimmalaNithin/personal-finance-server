package com.personalfinance.personal_finance_app.controller;

import com.personalfinance.personal_finance_app.model.entity.Role;
import com.personalfinance.personal_finance_app.model.entity.User;
import com.personalfinance.personal_finance_app.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }

    @PostMapping("/")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        Role savedRole = roleService.save(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRole);
    }
}
