package com.api.Timesheets.services;


import static com.api.Timesheets.config.ModelToHtmlConverter.RESET_PASSWORD_USER_HTML_TEMPLATE;

import com.api.Timesheets.config.ModelToHtmlConverter;
import com.api.Timesheets.models.User;
import com.api.Timesheets.models.EmailDTO;
import com.api.Timesheets.repositories.UserRepo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import com.google.common.collect.ImmutableMap;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service(value = "userService")
class UserServiceImpl implements UserDetailsService, UserService {

  @Value("${reset.password.token.expiration.days}")
  private Integer tokenExpirationDays;

  @Autowired
  private UserRepo userDao;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  private String appUrl;

  @Value("${app.root.url}")
  public void setAppUrl(String appUrl) {
    this.appUrl = appUrl;
  }

  @Autowired
  private ModelToHtmlConverter modelToHtmlConverter;

  @Autowired
  private EmailService emailService;

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
  public Optional<User> resetPassword(String email) {
    return userDao.findByEmail(email).map(user -> {
      String token = generateToken();
      user.setChangePasswordRequired(true);
      user.setResetPasswordToken(passwordEncoder.encode(token));
      user.setTokenExpDate(LocalDate.now().plusDays(tokenExpirationDays));
      sendResetPasswordEmail(user,token,tokenExpirationDays);
        return user;
    });
  }

  private void sendResetPasswordEmail(User user, String token, Integer tokenExpirationDays) {
    try {
      ImmutableMap<String, Object> model = ImmutableMap.of(
          "user", user,
          "token", token,
          "expirationDays", tokenExpirationDays,
          "appUrl", appUrl);
      String html = modelToHtmlConverter.convert(RESET_PASSWORD_USER_HTML_TEMPLATE, model);
      EmailDTO emailDTO = EmailDTO.builder()
          .content(html)
          .recepient(user.getEmail())
          .subject("Timesheets Manager - Reset Password")
          .build();
      emailService.sendEmail(emailDTO);
    } catch (Exception e) {
      throw new RuntimeException("Error sending email for reset password of user.", e);
    }
  }

  @Override
  public User save(User user) {
    return userDao.save(user);
  }

  private String generateToken() {
    return RandomStringUtils.random(12, true, true);
  }
}
