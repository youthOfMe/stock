package com.chenhai.stock.service;

import com.chenhai.stock.pojo.domain.InnerMarketDomain;
import com.chenhai.stock.pojo.domain.StockUpdownDomain;
import com.chenhai.stock.vo.res.PageResult;
import com.chenhai.stock.vo.res.R;

import java.util.List;
import java.util.Map;

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

    /**
     * 统计最新股票交易日内每分钟内的涨跌停的股票的数量
     * @return
     */
    R<Map<String, List>> getStockUpDownCount();

    /**
     * 统计大盘T日和T-1日每分钟交易量的统计
     * @return
     */
    R<Map<String, List>> getComparedStockTradeAmt();

    /**
     * 统计最新交易时间点下股票 (A股) 在各个涨幅区间的数量
     * @return
     */
    R<Map> getIncreaseRangeInfo();
}
