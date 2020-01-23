package com.api.Timesheets.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;

import static com.api.Timesheets.utils.CookieUtils.TOKEN;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "resource_id";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
//        resources.tokenExtractor(new CustomTokenExtractor());
//        resources.resourceId(RESOURCE_ID).stateless(false);
        resources.tokenExtractor(new BearerCookiesTokenExtractor());
        resources.authenticationEntryPoint(new InvalidTokenEntryPoint());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatcher(withCookieToken())
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .disable()
                .formLogin()
                .disable()
                .httpBasic()
                .disable()
                .authorizeRequests()
                .antMatchers("/auth/**",
                        "/",
                        "/error",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }

    private RequestMatcher withCookieToken() {
        return request -> request.getCookies() != null && Arrays.stream(request.getCookies()).anyMatch(cookie -> cookie.getName().equals(TOKEN));
    }

}