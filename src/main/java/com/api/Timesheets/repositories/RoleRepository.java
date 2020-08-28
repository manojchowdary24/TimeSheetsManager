package com.api.Timesheets.repositories;

import com.api.Timesheets.enums.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<com.api.Timesheets.models.Role, Long> {
  Optional<com.api.Timesheets.models.Role> findByName(Role name);
}
