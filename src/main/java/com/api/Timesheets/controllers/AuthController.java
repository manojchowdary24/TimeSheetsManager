package com.api.Timesheets.controllers;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.api.Timesheets.ExceptionHandlers.GlobalException;
import com.api.Timesheets.models.AuthResponse;
import com.api.Timesheets.models.LoginRequest;
import com.api.Timesheets.security.OauthTokenResponse;
import com.api.Timesheets.security.TokenProvider;
import com.api.Timesheets.services.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private TokenProvider tokenProvider;

  @Autowired
  private UserService userService;

  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
      throws Exception {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getUsername(),
            loginRequest.getPassword()
        )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);

    OauthTokenResponse resp = tokenProvider.getToken(loginRequest.getUsername(),
                loginRequest.getPassword());
    return ResponseEntity.ok(new AuthResponse(resp.getAccessToken()));
  }

  @PostMapping(path = "/{email}/resetPassword", produces = APPLICATION_JSON_VALUE)
  public void resetPassword(@PathVariable String email) {
    userService.resetPassword(email).orElseThrow(
        () -> new GlobalException(HttpStatus.NOT_FOUND.value(), "User not found."));
  }
}

