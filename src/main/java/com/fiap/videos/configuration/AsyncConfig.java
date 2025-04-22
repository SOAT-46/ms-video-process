package com.fiap.videos.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class AsyncConfig {

    @Bean
    public Executor taskExecutor() {
        return Executors.newFixedThreadPool(10);
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}