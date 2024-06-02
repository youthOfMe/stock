package com.chenhai.stock.controller;

import com.chenhai.stock.pojo.domain.InnerMarketDomain;
import com.chenhai.stock.pojo.domain.Stock4MinuteDomain;
import com.chenhai.stock.pojo.domain.StockBlockRtInfoDomain;
import com.chenhai.stock.pojo.domain.StockUpdownDomain;
import com.chenhai.stock.service.StockService;
import com.chenhai.stock.vo.res.PageResult;
import com.chenhai.stock.vo.res.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(value = "/api/quot", tags = {"定义股票相关接口控制器"})
@RestController
@RequestMapping("/api/quot")
public class StockController {

    @Autowired
    private StockService stockService;

    /**
     * 获取国内大盘最新的数据
     * @return
     */
    @ApiOperation(value = "获取国内大盘最新数据", notes = "获取国内大盘最新的数据", httpMethod = "GET")
    @GetMapping("/index/all")
    public R<List<InnerMarketDomain>> getInnerMarketInfo() {
        return stockService.getInnerMarketInfo();
    }

    /**
     * 获取国内板块指数数据
     * @return
     */
    @ApiOperation(value = "获取国内板块指数数据", notes = "获取国内板块指数数据", httpMethod = "GET")
    @GetMapping("/sector/all")
    public R<List<StockBlockRtInfoDomain>> getStockBlockRtInfo() {
        return stockService.getStockBlockRtInfo();
    }

    /**
     * 分页查询最新的股票交易数据
     * @param page 当前页
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "分页查询最新的股票数据", notes = "分页查询最新的股票交易数据", httpMethod = "GET")
    @GetMapping("/stock/all")
    public R<PageResult<StockUpdownDomain>> getStockInfoByPage(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                               @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize) {
        return stockService.getStockInfoByPage(page, pageSize);
    }

    /**
     * 统计最新股票交易日每分钟的涨跌停的股票数量
     * @return
     */
    @ApiOperation(value = "统计最新股票交易日每分钟的涨跌停的股票数量", notes = "统计最新股票交易日每分钟的涨跌停的股票数量", httpMethod = "GET")
    @GetMapping("/stock/updown/count")
    public R<Map<String, List>> getStockUpDownCount() {
        return stockService.getStockUpDownCount();
    }

    /**
     * 统计大盘T日和T-1日每分钟交易两的统计
     * @return
     */
    @ApiOperation(value = "统计大盘T日和T-1日每分钟交易量的统计", notes = "统计大盘T日和T-1日每分钟交易量的统计", httpMethod = "GET")
    @GetMapping("/stock/tradeAmt")
    public R<Map<String, List>> getComparedStockTradeAmt() {
        return stockService.getComparedStockTradeAmt();
    }

    /**
     * 统计最新交易时间点下股票 (A股) 在各个涨幅区间的数量
     * @return
     */
    @GetMapping("/stock/updown")
    public R<Map> getIncreaseRangeInfo() {
        return stockService.getIncreaseRangeInfo();
    }

    /**
     * 胡哦去指定股票T日的分时数据
     * @param stockCode
     * @return
     */
    @ApiOperation(value = "获取指定股票T日的分时数据", notes = "胡哦去指定股票T日的分时数据", httpMethod = "GET")
    @GetMapping("/stock/screen/time-sharing")
    public R<List<Stock4MinuteDomain>> getStockScreenTimeSharing(@RequestParam(value = "code", required = true) String stockCode) {
        return stockService.getStockScreenTimeSharing(stockCode);
    }

}
