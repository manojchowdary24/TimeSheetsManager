package com.api.Timesheets.services;

import static com.api.Timesheets.config.ModelToHtmlConverter.REQUEST_ACCESS_USER_HTML_TEMPLATE;
import static com.api.Timesheets.config.ModelToHtmlConverter.RESET_PASSWORD_USER_HTML_TEMPLATE;
import static com.api.Timesheets.utils.GlobalConstants.UPDATE_PASSWORD_URL;
import static com.api.Timesheets.utils.JWTUtil.generateTempToken;

import com.api.Timesheets.ExceptionHandlers.GlobalException;
import com.api.Timesheets.config.ModelToHtmlConverter;
import com.api.Timesheets.models.EmailDTO;
import com.api.Timesheets.models.UpdatePasswordDTO;
import com.api.Timesheets.models.User;
import com.api.Timesheets.models.UserDTO;
import com.api.Timesheets.models.UserDetailsImpl;
import com.api.Timesheets.repositories.UserRepo;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService implements UserDetailsService {

  @Value("${reset.password.token.expiration.days}")
  private Integer tokenExpirationDays;

  @Autowired
  private UserRepo userDao;

  @Autowired
  private AdminService adminService;

  @Autowired private BCryptPasswordEncoder passwordEncoder;

  private String appUrl;
  @Autowired private ModelToHtmlConverter modelToHtmlConverter;
  @Autowired private EmailService emailService;

  @Value("${app.root.url}")
  public void setAppUrl(String appUrl) {
    this.appUrl = appUrl;
  }

  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    User user = userDao.findByUsernameIgnoreCase(userId).orElseThrow(
        ()-> new UsernameNotFoundException("Invalid username or password.")
    );
    return UserDetailsImpl.build(user);
  }

  public List<User> findAll() {
    return userDao.findAll();
  }

  public void delete(Integer id) {
    userDao.deleteById(id);
  }

  public Optional<User> resetPassword(String email) {
    return userDao
        .findByEmail(email)
        .map(
            user -> {
              String token = generateTempToken();
              user.setChangePasswordRequired(true);
              user.setResetPasswordToken(passwordEncoder.encode(token));
              user.setTokenExpDate(LocalDate.now().plusDays(tokenExpirationDays).toDate());
              user.setActive(false);
              sendResetPasswordEmail(user, token, tokenExpirationDays);
              userDao.save(user);
              return user;
            });
  }

  public Optional<User> updatePassword(UpdatePasswordDTO updatePasswordDTO, String email) {
    return userDao
        .findByEmail(email)
        .map(
            usr -> {
              if (isEligible(updatePasswordDTO, usr)) {
                usr.setChangePasswordRequired(false);
                usr.setActive(true);
                usr.setPassword(passwordEncoder.encode(updatePasswordDTO.getNewPassword()));
                usr.setResetPasswordToken(null);
                usr.setTokenExpDate(null);
                userDao.save(usr);
              } else {
                throw new GlobalException(
                    HttpStatus.BAD_REQUEST.value(), "Unable to update the password");
              }
              return usr;
            });
  }

  private boolean isEligible(UpdatePasswordDTO updatePasswordDTO, User usr) {
    return usr.getChangePasswordRequired()
        && passwordEncoder.matches(updatePasswordDTO.getToken(), usr.getResetPasswordToken())
        && new LocalDate(usr.getTokenExpDate()).isAfter(LocalDate.now());
  }

  private void sendResetPasswordEmail(User user, String token, Integer tokenExpirationDays) {
    try {
      String updatePasswordURL = appUrl + UPDATE_PASSWORD_URL + user.getEmail();
      ImmutableMap<String, Object> model =
          ImmutableMap.of(
              "user", user,
              "token", token,
              "expirationDays", tokenExpirationDays,
              "appUrl", updatePasswordURL);
      String html = modelToHtmlConverter.convert(RESET_PASSWORD_USER_HTML_TEMPLATE, model);
      EmailDTO emailDTO =
          EmailDTO.builder()
              .content(html)
              .recepient(user.getEmail())
              .subject("Timesheets Manager - Reset Password")
              .build();
      emailService.sendEmail(emailDTO);
    } catch (Exception e) {
      throw new RuntimeException("Error sending email for reset password of user.", e);
    }
  }

  public void requestAccess(UserDTO userDTO) {
    User user = User.fromUserDTO(userDTO);
    user.setActive(false);
    userDao.save(user);
    User adminUser = adminService.getAdmin();
    try {
      if (adminUser != null && StringUtils.isNotEmpty(adminUser.getEmail())) {
        ImmutableMap<String, Object> model =
            ImmutableMap.of(
                "user", user,
                "expirationDays", tokenExpirationDays,
                "appUrl", appUrl);
        //            Will Use once we have the Template ready
        //            String html = modelToHtmlConverter.convert(REQUEST_ACCESS_USER_HTML_TEMPLATE,
        // model);
        EmailDTO emailDTO =
            EmailDTO.builder()
                .content(REQUEST_ACCESS_USER_HTML_TEMPLATE)
                .recepient(adminUser.getEmail())
                .subject("Timesheets Manager - Access Request")
                .build();
        emailService.sendEmail(emailDTO);
      }
    } catch (Exception e) {
      throw new RuntimeException(
          "Error sending email for access request of user." + user.getFirstName(), e);
    }
  }
}
