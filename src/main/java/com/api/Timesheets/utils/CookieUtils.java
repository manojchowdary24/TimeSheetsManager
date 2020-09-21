package com.api.Timesheets.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public final class CookieUtils {

  public static final String SESSION = "sessionKey";
  public static final String USER = "userName";
  public static final String TOKEN = "token";

  private CookieUtils() {}

  public static Cookie findPingCookie(Cookie[] cookies) {
    if (cookies == null) {
      return null;
    }

    for (Cookie cookie : cookies) {
      if (cookie.getName().equals(SESSION)) {
        return cookie;
      }
    }

    return null;
  }

  public static Optional<String> getTokenFromRequest(HttpServletRequest request) {
    final Cookie[] cookies = request.getCookies();
    if (cookies == null) {
      return Optional.empty();
    }
    System.out.println("Cookies length ====== "+ cookies.length);
    return Arrays.stream(cookies)
        .peek(x -> System.out.println(x.getName()+" ==== "+ x.getValue() + " ===== "+ x.getDomain()+" ===== "+x.getSecure()))
        .filter(cookie -> cookie.getName().equals(TOKEN))
        .findFirst()
        .map(Cookie::getValue);
  }

  public static List<Cookie> expireCookies(HttpServletRequest request) {
    final Cookie[] cookies = request.getCookies();
    if (cookies == null) {
      return Collections.emptyList();
    }
    return Arrays.stream(cookies)
    .map(CookieUtils::expireCookie)
    .collect(Collectors.toList());
  }

  public static Cookie expireCookie(Cookie cookie){
    cookie = new Cookie(cookie.getName(), null);
    cookie.setMaxAge(0);
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    cookie.setSecure(true);

    return cookie;
  }

  public static Cookie createCookie(String cookieKey, String cookieValue) {

    Cookie cookie = new Cookie(cookieKey, cookieValue);
    cookie.setMaxAge(-1);
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setDomain("https://timesheets-manager.netlify.app");

    return cookie;
  }
}
