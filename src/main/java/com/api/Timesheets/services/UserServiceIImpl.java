package com.api.Timesheets.services;


import com.api.Timesheets.models.User;
import com.api.Timesheets.repositories.UserRepo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "userService")
class UserServiceImpl implements UserDetailsService, UserService {

  @Autowired
  private UserRepo userDao;

  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    User user = userDao.findByUsername(userId);
    if(user == null){
      throw new UsernameNotFoundException("Invalid username or password.");
    }
    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
  }

  public List findAll() {
    List list = new ArrayList<>();
    userDao.findAll().iterator().forEachRemaining(list::add);
    return list;
  }

  @Override
  public void delete(Integer id) {
    userDao.deleteById(id);
  }

  @Override
  public User save(User user) {
    return userDao.save(user);
  }
}
