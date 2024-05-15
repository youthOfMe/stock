package com.chenhai.stock.service;

import com.chenhai.stock.pojo.domain.InnerMarketDomain;
import com.chenhai.stock.pojo.domain.StockUpdownDomain;
import com.chenhai.stock.vo.res.PageResult;
import com.chenhai.stock.vo.res.R;

import java.util.List;

public interface StockService {

    /**
     * 获取国内大盘最新数据
     * @return
     */
    R<List<InnerMarketDomain>> getInnerMarketInfo();

    /**
     * 分页查询最新的股票交易数据
     * @param page 当前页
     * @param pageSize 每页大小
     * @return 返回值
     */
    R<PageResult<StockUpdownDomain>> getStockInfoByPage(Integer page, Integer pageSize);
}
