//package com.api.Timesheets.UserTest;
//
//import com.api.Timesheets.controllers.MainController;
//import com.api.Timesheets.models.LoginRequest;
//import org.junit.Assert;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.web.client.RestTemplate;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class LoginPageTest {
//
//    @InjectMocks
//    private MainController controller;
//
//    @LocalServerPort
//    int randomServerPort;
//
//    @Test
//    @Ignore
//    public void testUserEndpointWithCookie() throws URISyntaxException {
//
//        RestTemplate restTemplate = new RestTemplate();
//        final String baseUrl = "http://localhost:" + randomServerPort + "/auth/login";
//        URI uri = new URI(baseUrl);
//        LoginRequest loginRequest = new LoginRequest("jeff", "password");
//
//
//        HttpEntity<LoginRequest> request = new HttpEntity<>(loginRequest);
//
//        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
//
////        HttpHeaders headers = new HttpHeaders();
////        System.out.println(result.getHeaders().get("Set-Cookie").get(1));
////        headers.set("X-COM-PERSIST", result.getHeaders().get("Set-Cookie").get(1));
////        headers.set("X-COM-LOCATION", "USA");
//        HttpEntity entity = new HttpEntity(result.getHeaders());
//
//        final String baseUrl2 = "http://localhost:" + randomServerPort + "/users/user";
//        URI uri2 = new URI(baseUrl2);
//        ResponseEntity<String> response = restTemplate.exchange(uri2, HttpMethod.GET, entity, String.class);
//
//        //Verify request succeed
//        Assert.assertEquals(201, result.getStatusCodeValue());
//    }
//}
