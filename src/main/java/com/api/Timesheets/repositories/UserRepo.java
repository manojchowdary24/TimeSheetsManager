package com.api.Timesheets.repositories;

import com.api.Timesheets.enums.Role;
import com.api.Timesheets.models.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

  Optional<User> findByUsernameIgnoreCase(String userName);

  Optional<User> findByEmail(String email);

  @Query("from User u join u.roles r where r.name=:roleName")
  List<User> findByPermissionsSet(Role roleName);

  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
