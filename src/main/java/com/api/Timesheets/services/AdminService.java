package com.api.Timesheets.services;

import static com.api.Timesheets.utils.JWTUtil.generateTempToken;

import com.api.Timesheets.ExceptionHandlers.ResourceNotFoundException;
import com.api.Timesheets.enums.Role;
import com.api.Timesheets.models.User;
import com.api.Timesheets.models.UserDTO;
import com.api.Timesheets.repositories.RoleRepository;
import com.api.Timesheets.repositories.UserRepo;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
  @Autowired private UserRepo userRepository;

  @Autowired private RoleRepository roleRepository;

  @Autowired private PasswordEncoder encoder;

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public HttpEntity<? extends Object> createUser(UserDTO userDTO) {
    if (userRepository.existsByUsername(userDTO.getUserName())) {
      return ResponseEntity.badRequest().body("Error: Username is already taken!");
    }

    if (userRepository.existsByEmail(userDTO.getEmail())) {
      return ResponseEntity.badRequest().body("Error: Email is already in use!");
    }
    Set<String> strRoles = userDTO.getRoles();
    Set<com.api.Timesheets.models.Role> roles = new HashSet<>();

    if (strRoles == null) {
      com.api.Timesheets.models.Role userRole =
          roleRepository
              .findByName(Role.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(
          role -> {
            switch (role) {
              case "Admin":
                com.api.Timesheets.models.Role adminRole =
                    roleRepository
                        .findByName(Role.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(adminRole);

                break;
              default:
                com.api.Timesheets.models.Role userRole =
                    roleRepository
                        .findByName(Role.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            }
          });
    }
    User user = User.fromUserDTO(userDTO);
    user.setPassword(encoder.encode(userDTO.getPassword()));
    user.setActive(true);
    user.setResetPasswordToken(encoder.encode(generateTempToken()));
    user.setRoles(roles);
    userRepository.save(user);
    return ResponseEntity.ok("User created successfully");
  }

  public HttpEntity<? extends Object> updateUser(Integer userId, UserDTO userDTO) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
    user.setEmail(userDTO.getEmail());
    user.setActive(userDTO.getActive());
    userRepository.save(user);
    return ResponseEntity.ok("User Updated successfully");
  }
}
