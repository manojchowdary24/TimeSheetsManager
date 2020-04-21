package com.api.Timesheets.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource(name = "userService")
    private UserDetailsService userDetailsService;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

//    protected SecurityContextRestorerFilter buildSecurityContextRestorerFilter() throws Exception {
//        List<String> pathsToSkip = Arrays.asList("/auth/login",
//                "/",
//                "/error",
//                "/favicon.ico",
//                "/**/*.png",
//                "/**/*.gif",
//                "/**/*.svg",
//                "/**/*.jpg",
//                "/**/*.html",
//                "/**/*.css",
//                "/**/*.js");
//        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, "/users/**");
//        SecurityContextRestorerFilter filter
//                = new SecurityContextRestorerFilter(tokenExtractor, matcher);
//        filter.setAuthenticationManager(authenticationManagerBean());
//        return filter;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/user/**").hasRole("ADMIN")
//                .antMatchers("/anonymous*").anonymous()
//                .antMatchers("/login*").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/")
//                .loginProcessingUrl("/auth/login")
//                .defaultSuccessUrl("/", true);
                //.failureUrl("/login.html?error=true")
//                .failureHandler(authenticationFailureHandler())
//                .and()
//                .logout()
//                .logoutUrl("/perform_logout")
//                .deleteCookies("JSESSIONID")
//                .logoutSuccessHandler(logoutSuccessHandler());

//        http.authorizeRequests().antMatchers("/", "/auth/login", "/**/*.html",
//                "/**/*.css",
//                "/**/*.js").permitAll()
////                .and().authorizeRequests()
////                .antMatchers("/users/**").hasRole("ADMIN")
//                .anyRequest().authenticated();
//        http
////                .csrf().disable()
//                .requestMatchers()
//                .antMatchers("/auth/login", "/oauth/authorize","/")
//                .and()
//                .authorizeRequests()
//                .antMatchers("/auth/login","/").permitAll()
//                .and().authorizeRequests()
//                .antMatchers("/users/**").hasRole("ADMIN")
////                .antMatchers("/")
//                .anyRequest()
//                .permitAll();

//        http
//                .authorizeRequests()
//                .antMatchers("/auth/**",
//                        "/",
//                        "/error",
//                        "/favicon.ico",
//                        "/**/*.png",
//                        "/**/*.gif",
//                        "/**/*.svg",
//                        "/**/*.jpg",
//                        "/**/*.html",
//                        "/**/*.css",
//                        "/**/*.js")
//                .permitAll()
//                .anyRequest()
//                .authenticated();
//
        http
                .csrf().disable()

                // We don't need CSRF for JWT based authentication
//                .exceptionHandling()
//              .authenticationEntryPoint(this.authenticationEntryPoint)

//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

//                .anonymous().disable()
                .authorizeRequests()
//              .antMatchers(
                .antMatchers("/oauth/**","/auth/**",
                        "/",
                        "/error",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/users/**").permitAll().anyRequest().authenticated();
//                .antMatchers().hasRole("ADMIN")
//                .and().authorizeRequests().anyRequest().authenticated();
//                .and()
//                .addFilterBefore(buildSecurityContextRestorerFilter(), UsernamePasswordAuthenticationFilter.class);
        ;
        // Add our custom Token based authentication filter
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenExtractor tokenExtractor() {
        return new CustomTokenExtractor();
    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
}
