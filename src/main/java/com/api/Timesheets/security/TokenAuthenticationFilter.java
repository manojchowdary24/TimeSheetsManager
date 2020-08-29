package com.api.Timesheets.security;

import com.api.Timesheets.services.UserService;
import com.api.Timesheets.utils.CookieUtils;
import com.api.Timesheets.utils.JWTUtil;
import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

  private static final String BEARER = "Bearer ";

  @Autowired private JWTUtil jwtUtil;

  @Autowired private UserService userDetails;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = CookieUtils.getTokenFromRequest(request).orElse(null);
    LOGGER.info("token From cookies ====={}",token);
    if (token == null) {
      token = request.getHeader("Authorization");
      if (Strings.isNullOrEmpty(token) || !token.startsWith(BEARER)) {
        filterChain.doFilter(request, response);
        return;
      }
    }
    String finalToken = token.replace(BEARER, "");
    Claims claims = jwtUtil.getDetailsFromTokenAndValidate(finalToken);
    String userName = claims.getSubject();
    UserDetails details = userDetails.loadUserByUsername(userName);
    Authentication auth =
        new UsernamePasswordAuthenticationToken(userName, null, details.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(auth);
    filterChain.doFilter(request, response);
  }
}
