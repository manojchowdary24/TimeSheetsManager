//package com.api.Timesheets.security;
//
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//import static com.api.Timesheets.utils.CookieUtils.TOKEN;
////import static org.apache.logging.log4j.util.Strings.EMPTY;
//
//public class InvalidTokenEntryPoint implements AuthenticationEntryPoint {
//
//    public static final String CONTEXT_PATH = "/";
//
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
////        log.info("Invalid token used. Destroying cookie and session and redirecting to home page");
//        request.getSession().invalidate(); //Destroys the DefaultOAuth2ClientContext that keeps the invalid token
//        response.addCookie(createEmptyCookie());
//    }
//
//    private Cookie createEmptyCookie() {
//        final Cookie cookie = new Cookie(TOKEN, null);
//        cookie.setMaxAge(0);
//        cookie.setHttpOnly(true);
//        cookie.setPath(CONTEXT_PATH);
//        return cookie;
//    }
//}
