package com.api.Timesheets.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  static final String AUTHORIZATION_CODE = "authorization_code";
  static final String REFRESH_TOKEN = "refresh_token";
  static final String IMPLICIT = "implicit";
  static final String SCOPE_READ = "read";
  static final String SCOPE_WRITE = "write";
  static final String TRUST = "trust";
  static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1*60*60;
  static final int FREFRESH_TOKEN_VALIDITY_SECONDS = 6*60*60;

  @Value("${timesheets-management.clientId}")
  private String clientId;

  @Value("${timesheets-management.clientSecret}")
  private String clientSecret;

  @Value("${timesheets-management.grant-type}")
  private String grantType;

  @Autowired
  private TokenStore tokenStore;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private BCryptPasswordEncoder encoder;

  @Override
  public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {

    configurer
        .inMemory()
        .withClient(clientId)
        .secret(encoder.encode(clientSecret))
        .authorizedGrantTypes(grantType, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT)
        .scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
        .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS).
        refreshTokenValiditySeconds(FREFRESH_TOKEN_VALIDITY_SECONDS);
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.tokenStore(tokenStore)
        .authenticationManager(authenticationManager);
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
    oauthServer.allowFormAuthenticationForClients();
  }
}