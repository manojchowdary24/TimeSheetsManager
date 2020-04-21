//package com.api.Timesheets.security;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//
//@Configuration
//@EnableResourceServer
//public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//    private static final String RESOURCE_ID = "resource_id";
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) {
//        resources.tokenExtractor(new CustomTokenExtractor());
//        resources.resourceId(RESOURCE_ID).stateless(false);
////        resources.tokenExtractor(new BearerCookiesTokenExtractor());
////        resources.authenticationEntryPoint(new InvalidTokenEntryPoint());
//    }
//
////    @Override
////    public void configure(HttpSecurity http) throws Exception {
////
////        http
////                .requestMatchers()
////                .antMatchers("/auth/login", "/oauth/authorize")
////                .and()
////                .authorizeRequests()
////                .antMatchers("/auth/login").permitAll()
//////                .and().authorizeRequests()
//////                .antMatchers("/users/**").hasRole("ADMIN")
////                .anyRequest()
////                .authenticated()
////                .and()
////                .formLogin()
////        ;
////        http
////                .cors()
////                .and()
////                .sessionManagement()
////                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////                .and()
////                .cors()
////                .disable()
////                .formLogin()
////                .disable()
////                .httpBasic()
////                .disable();
////                .authorizeRequests()
////                .antMatchers("/auth/login",
////                        "/",
////                        "/error",
////                        "/favicon.ico",
////                        "/**/*.png",
////                        "/**/*.gif",
////                        "/**/*.svg",
////                        "/**/*.jpg",
////                        "/**/*.html",
////                        "/**/*.css",
////                        "/**/*.js")
////                .permitAll()
////                .anyRequest()
////                .authenticated()
////                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
////    }
//
//}