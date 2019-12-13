package com.api.Timesheets.services;

import com.api.Timesheets.models.User;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

  User save(User user);
  List<User> findAll();
  void delete(Integer id);

  public Optional<User> resetPassword(String email);
}
