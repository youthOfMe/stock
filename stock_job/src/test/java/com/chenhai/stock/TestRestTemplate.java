package com.chenhai.stock;

import com.chenhai.stock.service.StockTimerTaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestRestTemplate {

    @Autowired
    private StockTimerTaskService stockTimerTaskService;

    @Test
    public void testGetMarketInfo() {
        stockTimerTaskService.getInnerMarketInfo();
    }
}
