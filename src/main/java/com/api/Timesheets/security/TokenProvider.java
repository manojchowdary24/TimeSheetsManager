package com.api.Timesheets.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenProvider {

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  @Value("${timesheets-management.clientId}")
  private String clientId;

  @Value("${timesheets-management.clientSecret}")
  private String clientSecret;

  @Value("${timesheets-management.grant-type}")
  private String grantType;


  public OauthTokenResponse getToken(String userName,String password) throws Exception {
    final String urlWithParms =
        "http://localhost:8080/oauth/token" + "?username=" + userName
            + "&grant_type="+ grantType
            + "&password=" + password
            + "&client_id=" +clientId
            +"&client_secret="+clientSecret;
    final ResponseEntity<String> response = restTemplate
        .exchange(urlWithParms, HttpMethod.POST,
            null, String.class);
    if (response.getStatusCode().is2xxSuccessful()) {
      try {
        return objectMapper.readValue(response.getBody(), OauthTokenResponse.class);
      } catch (IOException e) {
        throw new Exception();
      }
    } else {
      throw new Exception();
    }

  }

}
