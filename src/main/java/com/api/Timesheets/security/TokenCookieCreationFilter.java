//package com.api.Timesheets.security;
//
//import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
//import org.springframework.security.oauth2.client.OAuth2ClientContext;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class TokenCookieCreationFilter extends OncePerRequestFilter {
//
//    public static final String ACCESS_TOKEN_COOKIE_NAME = "token";
//    private final UserInfoRestTemplateFactory userInfoRestTemplateFactory;
//
//    public TokenCookieCreationFilter(UserInfoRestTemplateFactory userInfoRestTemplateFactory) {
//        this.userInfoRestTemplateFactory = userInfoRestTemplateFactory;
//    }
//
//    @Override
//    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
//        try {
//            final OAuth2ClientContext oAuth2ClientContext = userInfoRestTemplateFactory.getUserInfoRestTemplate().getOAuth2ClientContext();
//            final OAuth2AccessToken authentication = oAuth2ClientContext.getAccessToken();
//            if (authentication != null && authentication.getExpiresIn() > 0) {
//                System.out.println("Authentication is not expired: expiresIn="+ authentication.getExpiresIn());
//                final Cookie cookieToken = createCookie(authentication.getValue(), authentication.getExpiresIn());
//                response.addCookie(cookieToken);
//                System.out.println("Cookied added: name="+ cookieToken.getName());
//            }
//        } catch (final Exception e) {
//            System.out.println("Error while extracting token for cookie creation"+ e);
//        }
//        filterChain.doFilter(request, response);
//    }
//
//    private Cookie createCookie(final String content, final int expirationTimeSeconds) {
//        final Cookie cookie = new Cookie(ACCESS_TOKEN_COOKIE_NAME, content);
//        cookie.setMaxAge(expirationTimeSeconds);
//        cookie.setHttpOnly(true);
//        cookie.setPath("/");
//        return cookie;
//    }
//}