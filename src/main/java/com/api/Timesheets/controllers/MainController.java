package com.api.Timesheets.controllers;

import com.api.Timesheets.models.User;
import com.api.Timesheets.repositories.UserRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RequestMapping(value = "/api/")
public class MainController {

  @Autowired
  private UserRepo repo;

  @GetMapping("users")
  public List<User> getUsers(){
    return repo.findAll();
  }

}
