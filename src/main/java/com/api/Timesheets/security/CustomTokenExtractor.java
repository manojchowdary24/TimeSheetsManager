package com.api.Timesheets.security;

import com.api.Timesheets.utils.CookieUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import javax.servlet.http.HttpServletRequest;

public class CustomTokenExtractor implements TokenExtractor {
    @Override
    public Authentication extract(HttpServletRequest request) {
        return new PreAuthenticatedAuthenticationToken(CookieUtils.getTokenFromRequest(request),"");
    }

}
