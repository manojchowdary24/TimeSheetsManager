package com.api.Timesheets.controllers;

import static com.api.Timesheets.utils.CookieUtils.TOKEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.api.Timesheets.ExceptionHandlers.GlobalException;
import com.api.Timesheets.models.AuthResponse;
import com.api.Timesheets.models.LoginRequest;
import com.api.Timesheets.models.UpdatePasswordDTO;
import com.api.Timesheets.models.UserDTO;
import com.api.Timesheets.services.AdminService;
import com.api.Timesheets.services.UserService;
import com.api.Timesheets.utils.CookieUtils;
import com.api.Timesheets.utils.JWTUtil;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private UserService userService;

  @Autowired private JWTUtil jwtUtil;

  @Autowired private AdminService adminService;

  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(
      @Valid @RequestBody LoginRequest loginRequest,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));
    String token = jwtUtil.generateToken(authentication);
    response.addHeader("Set-Cookie", CookieUtils.createCookie(TOKEN, token).toString());
//    response.addHeader(CookieUtils.createCookie(TOKEN, token));

    return ResponseEntity.ok(new AuthResponse("Bearer " + token));
  }

  @PostMapping(path = "/{email}/resetPassword", produces = APPLICATION_JSON_VALUE)
  public void resetPassword(@PathVariable String email) {
    userService
        .resetPassword(email)
        .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND.value(), "User not found."));
  }

  @PostMapping(path = "/{email}/updatePassword")
  public ResponseEntity<?> updatePassword(
      @RequestBody UpdatePasswordDTO updatePasswordDTO, @PathVariable String email) {
    userService
        .updatePassword(updatePasswordDTO, email)
        .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND.value(), "User not found."));
    return ResponseEntity.ok("Password Updated Successfully");
  }

  @PostMapping(path = "/requestAccess")
  public HttpEntity<?> requestAccess(@RequestBody UserDTO userDTO) {
    return adminService.createUser(userDTO);
  }

  @PostMapping(path = "/logout")
  public void logout(HttpServletRequest request, HttpServletResponse response) {
    List<Cookie> cookies = CookieUtils.expireCookies(request);
    for (Cookie cookie : cookies) {
      response.addCookie(cookie);
    }
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }
  }

  @GetMapping("/me")
  public UserDetails getCurrentUserDetails(HttpServletRequest request) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = null;
    if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
      username =(String) auth.getPrincipal();
    } else {
      throw new GlobalException(
          HttpStatus.FORBIDDEN.value(), "Unable to Obtain user login details, please login again");
    }
    return userService.loadUserByUsername(username);
  }
}
