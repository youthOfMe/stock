package com.chenhai.stock.config;

import com.chenhai.stock.pojo.vo.StockInfoConfig;
import com.chenhai.stock.utils.IdWorker;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定义公共配置bean
 */
@Configuration
@EnableConfigurationProperties({StockInfoConfig.class})
public class CommonConfig {

    /**
     * 配置基于雪花算法生成全局唯一id
     *   参与元算的参数： 时间戳 + 机房id + 机器id + 序列号
     *   保证id唯一
     * @return
     */
    @Bean
    public IdWorker idWorker() {
        // 指定当前为1号机房的2号机器生成
        return new IdWorker(2L, 1L);
    }
}
