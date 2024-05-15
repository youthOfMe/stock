package com.chenhai.stock.service;

import com.chenhai.stock.pojo.domain.InnerMarketDomain;
import com.chenhai.stock.vo.res.R;

import java.util.List;

public interface StockService {

    /**
     * 获取国内大盘最新数据
     * @return
     */
    R<List<InnerMarketDomain>> getInnerMarketInfo();
}
