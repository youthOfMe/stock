package com.chenhai.stock.service.impl;

import com.chenhai.stock.pojo.vo.StockInfoConfig;
import com.chenhai.stock.service.StockTimerTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockTimerTaskServiceImpl implements StockTimerTaskService {

    @Autowired
    private StockInfoConfig stockInfoConfig

    @Override
    public void getInnerMarketInfo() {
        // 1. 阶段1: 采集原始数据
        // 1.1 组装url地址
        // String url =
    }
}
