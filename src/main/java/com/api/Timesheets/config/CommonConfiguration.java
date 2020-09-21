package com.api.Timesheets.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CommonConfiguration {

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  /**
   * Solve the problem:
   * There was an unexpected error (type=Internal Server Error, status=500).
   * An invalid domain [.xxx.com] was specified for this cookie
   *
   * @return
   */
//  @Bean
//  public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
//    return (factory) -> factory.addContextCustomizers(
//        (context) -> context.setCookieProcessor(new LegacyCookieProcessor()));
//  }
}
