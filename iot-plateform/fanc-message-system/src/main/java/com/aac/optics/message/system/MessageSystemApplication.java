package com.aac.optics.message.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication(scanBasePackages="com.aac.optics.message.system.*")
@EnableDiscoveryClient
@EnableCircuitBreaker
public class MessageSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessageSystemApplication.class, args);
    }
}