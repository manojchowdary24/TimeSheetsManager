//package com.api.Timesheets.security;
//
//import com.google.common.base.Predicate;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
//import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
//import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import java.util.Arrays;
//
//import static com.api.Timesheets.utils.CookieUtils.TOKEN;
//
//public class BearerCookiesTokenExtractor implements TokenExtractor {
//
//    private final BearerTokenExtractor tokenExtractor = new BearerTokenExtractor();
//
//    @Override
//    public Authentication extract(HttpServletRequest request) {
//        Authentication authentication = tokenExtractor.extract(request);
//        if (authentication == null) {
//            authentication = Arrays.stream(request.getCookies())
//                    .filter(isValidTokenCookie())
//                    .findFirst()
//                    .map(cookie -> new PreAuthenticatedAuthenticationToken(cookie.getValue(), ""))
//                    .orElse(null);
//        }
//        return authentication;
//    }
//
//    private Predicate<Cookie> isValidTokenCookie() {
//        return cookie -> cookie.getName().equals(TOKEN);
//    }
//}
