package com.aac.optics.ims.analysisfile;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
public class AnalysisImsFileApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnalysisImsFileApplication.class, args);
    }
}
