package com.api.Timesheets.controllers;

import com.api.Timesheets.models.User;
import com.api.Timesheets.models.UserDTO;
import com.api.Timesheets.repositories.UserRepo;
import com.api.Timesheets.services.AdminService;
import com.api.Timesheets.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

  @Autowired private UserRepo repo;

  @Autowired private UserService userService;

  @Autowired private AdminService service;

  @GetMapping("/users")
  @PreAuthorize("hasRole('ADMIN')")
  public List<User> listUser() {
    return service.getAllUsers();
  }

  @PostMapping("/createUser")
  @PreAuthorize("hasRole('ADMIN')")
  public HttpEntity<?> createUser(@RequestBody UserDTO userDTO) {
    return service.createUser(userDTO);
  }

  @PostMapping("/updateUser/{userId}")
  @PreAuthorize("hasRole('ADMIN')")
  public HttpEntity<?> updateUser(
      @PathVariable("userId") Integer userId, @RequestBody UserDTO userDTO) {
    return service.updateUser(userId, userDTO);
  }
}
