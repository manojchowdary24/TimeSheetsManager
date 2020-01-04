package com.api.Timesheets.utils;

import javax.servlet.http.Cookie;

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

        return cookie;
    }

}
