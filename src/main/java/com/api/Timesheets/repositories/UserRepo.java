package com.api.Timesheets.repositories;

import com.api.Timesheets.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

  Optional<User> findByUsernameIgnoreCase(String userName);

  Optional<User> findByEmail(String email);

  User findByPermissionsSet(String role);

  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
