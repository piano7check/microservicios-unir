package com.unir.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class OrdersApplication {

  @LoadBalanced
  @Bean
  public RestTemplate restTemplate() {

    return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
  }

  public static void main(String[] args) {
    SpringApplication.run(OrdersApplication.class, args);
  }

}
