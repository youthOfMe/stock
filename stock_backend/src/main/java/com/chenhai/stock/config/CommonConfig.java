package com.chenhai.stock.config;

import com.chenhai.stock.pojo.vo.StockInfoConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 定义公共配置bean
 */
@Configuration
@EnableConfigurationProperties({StockInfoConfig.class}) // 开启对象的相关配置
public class CommonConfig {

    /**
     * 定义密码加密的Bean对象
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
