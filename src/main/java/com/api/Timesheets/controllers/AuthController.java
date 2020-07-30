package com.api.Timesheets.controllers;


import com.api.Timesheets.ExceptionHandlers.GlobalException;
import com.api.Timesheets.models.AuthResponse;
import com.api.Timesheets.models.LoginRequest;
import com.api.Timesheets.models.UpdatePasswordDTO;
import com.api.Timesheets.models.UserDTO;
import com.api.Timesheets.services.UserService;
import com.api.Timesheets.utils.CookieUtils;
import com.api.Timesheets.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.api.Timesheets.utils.CookieUtils.TOKEN;
import static com.api.Timesheets.utils.CookieUtils.USER;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request,
                                              HttpServletResponse response)
            throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        String token = jwtUtil.generateToken(authentication);

        response.addCookie(CookieUtils.createCookie(USER, authentication.getName()));
        response.addCookie(CookieUtils.createCookie(TOKEN, token));

        return ResponseEntity.ok(new AuthResponse("Bearer " + token));
    }

    @PostMapping(path = "/{email}/resetPassword", produces = APPLICATION_JSON_VALUE)
    public void resetPassword(@PathVariable String email) {
        userService.resetPassword(email).orElseThrow(
                () -> new GlobalException(HttpStatus.NOT_FOUND.value(), "User not found."));
    }

    @PostMapping(path = "/{email}/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO, @PathVariable String email) {
        userService.updatePassword(updatePasswordDTO,email).orElseThrow(
                () -> new GlobalException(HttpStatus.NOT_FOUND.value(), "User not found."));
        return ResponseEntity.ok("Password Updated Successfully");
    }

    @PostMapping(path = "/{email}/requestAccess")
    public ResponseEntity<?> requestAccess(@RequestBody UserDTO userDTO) {
        userService.requestAccess(userDTO);
        return ResponseEntity.ok("Access Request Submitted Successfully");
    }
}

