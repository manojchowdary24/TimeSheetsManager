package com.api.Timesheets.controllers;

import com.api.Timesheets.models.User;
import com.api.Timesheets.repositories.UserRepo;
import com.api.Timesheets.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class MainController {

  @Autowired
  private UserRepo repo;

  @GetMapping("users")
  public List<User> getUsers(){
    return repo.findAll();
  }

  @Autowired
  private UserService userService;

  @GetMapping
//  @PreAuthorize("hasRole('ADMIN')")
  public List listUser(){
    return userService.findAll();
  }

}
