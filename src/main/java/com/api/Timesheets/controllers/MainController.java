package com.api.Timesheets.controllers;

import com.api.Timesheets.models.User;
import com.api.Timesheets.models.UserDTO;
import com.api.Timesheets.repositories.UserRepo;
import com.api.Timesheets.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/")
public class MainController {

  @Autowired private UserRepo repo;

  @Autowired private UserService userService;

  @GetMapping
  //  @PreAuthorize("hasRole('ADMIN')")
  public List listUser() {
    return userService.findAll();
  }

  @GetMapping("users")
  public List<User> getUsers() {
    return repo.findAll();
  }

  @PostMapping("createUser")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
    userService.createUser(userDTO);
    return ResponseEntity.ok("User created successfully");
  }
}
