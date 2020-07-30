package com.api.Timesheets.repositories;

import com.api.Timesheets.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Integer>{

  User findByUsernameIgnoreCase(String userName);
  Optional<User> findByEmail(String email);

  User findByRole(String role);

}
