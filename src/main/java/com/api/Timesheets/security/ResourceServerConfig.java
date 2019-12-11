package com.api.Timesheets.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

  private static final String RESOURCE_ID = "resource_id";

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    resources.resourceId(RESOURCE_ID).stateless(false);
  }



//  @Override
//  public void configure(HttpSecurity http) throws Exception {
////    http.
////        anonymous().disable()
////        .authorizeRequests()
////        .antMatchers("/users/**").authenticated()
////        .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
////    http
////        .csrf().disable()
////        .formLogin()
////        .disable()
////        .httpBasic()
////        .disable()
////        .anonymous().disable()
////        .authorizeRequests()
////        .antMatchers("/auth/**", "/oauth2/**")
////        .permitAll()
////        .anyRequest()
////        .authenticated();
//  }

}
