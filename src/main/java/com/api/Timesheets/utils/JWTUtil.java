package com.api.Timesheets.utils;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class JWTUtil {

  private final String SECRET_KEY = "jhbsdfgsdvhaishgvsvdsvbkadslvjdfvads";

  public String generateToken(Authentication auth) {
    return Jwts.builder()
        .claim("authorities", auth.getAuthorities())
        .setSubject(auth.getName())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(1)))
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
        .compact();
  }

  public Claims getDetailsFromTokenAndValidate(String token) {
    Claims body = null;
    try {
      Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
      body = claimsJws.getBody();
      if (!validateToken(body)) {
        throw new IllegalStateException(String.format("Token Might be expired", token));
      }
    } catch (JwtException e) {
      throw new IllegalStateException(String.format("Token cannot be trusted", token));
    }
    return body;
  }

  private Boolean validateToken(Claims claims) {
    Boolean result = false;
    if (claims.getExpiration().after(new Date())) {
      result = true;
    }
    return result;
  }
}
