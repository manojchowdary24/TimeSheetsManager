package com.api.Timesheets.controllers;

import com.api.Timesheets.models.User;
import com.api.Timesheets.repositories.UserRepo;
import com.api.Timesheets.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

  @Autowired private UserRepo repo;

  @Autowired private UserService userService;

  @GetMapping
  @PreAuthorize("hasRole('USER')")
  public List<User> listUser() {


    return userService.findAll();
  }

}
