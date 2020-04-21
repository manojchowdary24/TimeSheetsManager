package com.api.Timesheets.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

public final class CookieUtils {

    public static final String SESSION = "sessionKey";
    public static final String USER = "userName";
    public static final String TOKEN = "token";

    private CookieUtils() {
    }

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
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(TOKEN))
                .findFirst()
                .map(Cookie::getValue);
    }

    public static Cookie expireCookie(String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        return cookie;
    }

    public static Cookie createCookie(String cookieKey, String cookieValue) {

        Cookie cookie = new Cookie(cookieKey, cookieValue);
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);

        return cookie;
    }

}
