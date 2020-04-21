//package com.api.Timesheets.security;
//
//import com.google.common.base.Predicate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
//import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
//import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Arrays;
//
//import static com.api.Timesheets.utils.CookieUtils.TOKEN;
//
//public class SecurityContextRestorerFilter extends AbstractAuthenticationProcessingFilter {
//    private final TokenExtractor tokenExtractor;
//
//    @Autowired
//    public SecurityContextRestorerFilter(TokenExtractor tokenExtractor, RequestMatcher matcher) {
//        super(matcher);
//        this.tokenExtractor = tokenExtractor;
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//            throws AuthenticationException, IOException, ServletException {
//        Authentication authentication = tokenExtractor.extract(request);
//        if (authentication == null) {
//            authentication = Arrays.stream(request.getCookies())
//                    .filter(isValidTokenCookie())
//                    .findFirst()
//                    .map(cookie -> new PreAuthenticatedAuthenticationToken(cookie.getValue(), ""))
//                    .orElseGet(null);
//        }
//        return authentication;
//
////        String tokenPayload = request.getHeader("WebSecurityConfig.JWT_TOKEN_HEADER_PARAM");
//////        RawAccessJwtToken token = new RawAccessJwtToken(tokenExtractor.extract(tokenPayload));
////        return getAuthenticationManager().authenticate(" ");
//    }
//
//    private Predicate<Cookie> isValidTokenCookie() {
//        return cookie -> cookie.getName().equals(TOKEN);
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
//                                            Authentication authResult) throws IOException, ServletException {
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(authResult);
//        SecurityContextHolder.setContext(context);
//        chain.doFilter(request, response);
//    }
//
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//                                              AuthenticationException failed) throws IOException, ServletException {
//        SecurityContextHolder.clearContext();
////        failureHandler.onAuthenticationFailure(request, response, failed);
//    }
//}
