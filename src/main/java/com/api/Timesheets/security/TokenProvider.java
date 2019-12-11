package com.api.Timesheets.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenProvider {

  static final String CLIENT_ID = "timesheets-management-client";
  static final String CLIENT_SECRET = "timesheets-management-secret";
  static final String GRANT_TYPE_PASSWORD = "password";

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private ObjectMapper objectMapper;


  public OauthTokenResponse getToken(String userName,String password) throws Exception {
    final String urlWithParms =
        "http://localhost:8080/oauth/token" + "?username=" + userName
            + "&grant_type="+ GRANT_TYPE_PASSWORD
            + "&password=" + password
            + "&client_id=" +CLIENT_ID
            +"&client_secret="+CLIENT_SECRET;
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
