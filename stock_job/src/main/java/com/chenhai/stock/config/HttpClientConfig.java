package com.chenhai.stock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 定义http客户端工具Bean
 */
public class HttpClientConfig {

    /**
     * 定义Http客户端Bean
     * @return
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
