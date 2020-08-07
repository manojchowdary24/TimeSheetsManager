package com.api.Timesheets.services;


import com.api.Timesheets.ExceptionHandlers.GlobalException;
import com.api.Timesheets.config.ModelToHtmlConverter;
import com.api.Timesheets.models.EmailDTO;
import com.api.Timesheets.models.UpdatePasswordDTO;
import com.api.Timesheets.models.User;
import com.api.Timesheets.models.UserDTO;
import com.api.Timesheets.repositories.UserRepo;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.RandomStringUtils;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.api.Timesheets.config.ModelToHtmlConverter.RESET_PASSWORD_USER_HTML_TEMPLATE;
import static com.api.Timesheets.config.ModelToHtmlConverter.REQUEST_ACCESS_USER_HTML_TEMPLATE;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

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
        User user = userDao.findByUsernameIgnoreCase(userId);
        if (user == null) {
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
            user.setTokenExpDate(LocalDate.now().plusDays(tokenExpirationDays).toDate());
            user.setActive(false);
            sendResetPasswordEmail(user, token, tokenExpirationDays);
            userDao.save(user);
            return user;
        });
    }

    @Override
    public Optional<User> updatePassword(UpdatePasswordDTO updatePasswordDTO,String email ) {
        return userDao.findByEmail(email).map(usr -> {
            if (isEligible(updatePasswordDTO, usr)) {
                usr.setChangePasswordRequired(false);
                usr.setActive(true);
                usr.setPassword(passwordEncoder.encode(updatePasswordDTO.getNewPassword()));
                usr.setResetPasswordToken(null);
                usr.setTokenExpDate(null);
                userDao.save(usr);
            }else{
                throw new GlobalException(HttpStatus.BAD_REQUEST.value(), "Unable to update the password");
            }
            return usr;
        });
    }

    private boolean isEligible(UpdatePasswordDTO updatePasswordDTO, User usr) {
        return usr.getChangePasswordRequired() &&
                passwordEncoder.matches(updatePasswordDTO.getToken(), usr.getResetPasswordToken()) &&
                new LocalDate(usr.getTokenExpDate()).isAfter(LocalDate.now());
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

    @Override
    public void createUser(UserDTO userDTO){
        User user = User.fromUserDTO(userDTO);
        user.setActive(true);
        user.setPassword(passwordEncoder.encode("password"));
        user.setChangePasswordRequired(true);
        userDao.save(user);

    }

    @Override
    public void requestAccess(UserDTO userDTO) {
        User user = User.fromUserDTO(userDTO);
        user.setActive(false);
        userDao.save(user);
        User adminUser = userDao.findByPermissionsSet("ROLE_ADMIN");
        try {
            if(adminUser!=null && StringUtils.isNotEmpty(adminUser.getEmail())) {
                ImmutableMap<String, Object> model = ImmutableMap.of(
                        "user", user,
                        "expirationDays", tokenExpirationDays,
                        "appUrl", appUrl);
//            Will Use once we have the Template ready
//            String html = modelToHtmlConverter.convert(REQUEST_ACCESS_USER_HTML_TEMPLATE, model);
                EmailDTO emailDTO = EmailDTO.builder()
                        .content(REQUEST_ACCESS_USER_HTML_TEMPLATE)
                        .recepient(adminUser.getEmail())
                        .subject("Timesheets Manager - Access Request")
                        .build();
                emailService.sendEmail(emailDTO);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error sending email for access request of user." + user.getFirstName(), e);
        }
    }


}
