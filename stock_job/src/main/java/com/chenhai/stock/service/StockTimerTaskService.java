package com.chenhai.stock.service;

/**
 * 定义股票采集数据的服务接口
 */
public interface StockTimerTaskService {

    /**
     * 获取国内大盘实时数据
     */
    void getInnerMarketInfo();
}
