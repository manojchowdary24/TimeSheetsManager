package com.api.Timesheets.services;

import com.api.Timesheets.models.UpdatePasswordDTO;
import com.api.Timesheets.models.User;
import com.api.Timesheets.models.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    User save(User user);

    List<User> findAll();

    void delete(Integer id);

    public Optional<User> resetPassword(String email);

    Optional<User> updatePassword(UpdatePasswordDTO updatePasswordDTO);

    void createUser(UserDTO userDTO);
}
