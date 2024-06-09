package com.chenhai.stock.service;

import com.chenhai.stock.pojo.domain.*;
import com.chenhai.stock.vo.res.PageResult;
import com.chenhai.stock.vo.res.R;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface StockService {

    /**
     * 获取国内大盘最新数据
     * @return
     */
    R<List<InnerMarketDomain>> getInnerMarketInfo();

    /**
     * 获取国内板块指数数据
     * @return
     */
    R<List<StockBlockRtInfoDomain>> getStockBlockRtInfo();

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

    /**
     * 取指定股票T日的分时数据
     * @param stockCode
     * @return
     */
    R<List<Stock4MinuteDomain>> getStockScreenTimeSharing(String stockCode);

    /**
     * 统计指定股票的日K线数据
     * @param stockCode
     * @return
     */
    R<List<Stock4EvrDayDomain>> getStockScreenDkLine(String stockCode);

    /**
     * 导出指定页码的最新股票信息
     * @param page 当前页码
     * @param pageSize 每页大小
     * @param response 响应对象
     */
    void exportStockUpDownInfo(Integer page, Integer pageSize, HttpServletResponse response);
}
