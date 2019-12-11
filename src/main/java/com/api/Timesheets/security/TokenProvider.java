package com.api.Timesheets.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class TokenProvider {

  public String createToken(Authentication authentication) {
    return null;
  }
}
