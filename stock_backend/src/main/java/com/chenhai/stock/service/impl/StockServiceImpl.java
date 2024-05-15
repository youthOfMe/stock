package com.chenhai.stock.service.impl;

import com.chenhai.stock.pojo.domain.InnerMarketDomain;
import com.chenhai.stock.service.StockService;
import com.chenhai.stock.vo.res.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 股票服务的实现
 */
@Service
@Slf4j
public class StockServiceImpl implements StockService {

    /**
     * 获取国内大盘最新数据
     * @return
     */
    @Override
    public R<List<InnerMarketDomain>> getInnerMarketInfo() {
        return null;
    }
}
