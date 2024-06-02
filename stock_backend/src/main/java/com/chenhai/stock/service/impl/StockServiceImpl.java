package com.chenhai.stock.service.impl;

import com.chenhai.stock.mapper.StockBlockRtInfoMapper;
import com.chenhai.stock.mapper.StockMarketIndexInfoMapper;
import com.chenhai.stock.mapper.StockRtInfoMapper;
import com.chenhai.stock.pojo.domain.InnerMarketDomain;
import com.chenhai.stock.pojo.domain.StockBlockRtInfoDomain;
import com.chenhai.stock.pojo.domain.StockUpdownDomain;
import com.chenhai.stock.pojo.vo.StockInfoConfig;
import com.chenhai.stock.service.StockService;
import com.chenhai.stock.utils.DateTimeUtil;
import com.chenhai.stock.vo.res.PageResult;
import com.chenhai.stock.vo.res.R;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 股票服务的实现
 */
@Service
@Slf4j
public class StockServiceImpl implements StockService {

    @Autowired
    private StockInfoConfig stockInfoConfig;

    @Autowired
    private StockMarketIndexInfoMapper stockMarketIndexInfoMapper;

    @Autowired
    private StockRtInfoMapper stockRtInfoMapper;

    @Autowired
    private StockBlockRtInfoMapper stockBlockRtInfoMapper;

    @Autowired

    /**
     * 获取国内大盘最新数据
     * @return
     */
    @Override
    public R<List<InnerMarketDomain>> getInnerMarketInfo() {
        // 1. 获取股票最新的交易时间点(精确到分钟, 秒和毫秒设置为0)
        // mock data 等后续完成job工程 再将代码删除即可
        Date curDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        curDate = DateTime.parse("2022-01-02 09:32:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 2. 获取大盘编码集合
        List<String> mCodes = stockInfoConfig.getInner();
        // 3. 调用mapper查询数据
        List<InnerMarketDomain> data = stockMarketIndexInfoMapper.getMarketInfo(curDate, mCodes);

        // 4. 封装被响应
        return R.ok(data);
    }

    /**
     * 获取国内板块指数数据
     * @return
     */
    @Override
    public R<List<StockBlockRtInfoDomain>> getStockBlockRtInfo() {
        // 1. 获取股票最新的交易时间点(精确到分钟, 秒和毫秒设置为0)
        // mock data 等后续完成job工程 再将代码删除即可
        Date curDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        curDate = DateTime.parse("2021-12-21 14:30:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 调用mapper接口获取数据
        List<StockBlockRtInfoDomain> stockBlockRtInfos = stockBlockRtInfoMapper.getStcokBlockRtInfo(curDate);


        return R.ok(stockBlockRtInfos);
    }

    /**
     * 分页查询最新的股票交易数据
     * @param page 当前页
     * @param pageSize 每页大小
     * @return 返回值
     */
    @Override
    public R<PageResult<StockUpdownDomain>> getStockInfoByPage(Integer page, Integer pageSize) {
        // 1. 获取股票最新的交易时间点(精确到分钟, 秒和毫秒设置为0)
        // mock data 等后续完成job工程 再将代码删除即可
        Date curDate = DateTimeUtil.getLastDate4Stock(DateTime.now()).toDate();
        curDate = DateTime.parse("2022-01-02 09:32:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        // 2. 设置PageHelper分页参数
        PageHelper.startPage(page, pageSize);
        // 3. 调用mapper进行查询
        List<StockUpdownDomain> pageData = stockRtInfoMapper.getStockInfoByTime(curDate);
        // 4. 去封装为PageResult对象
        PageInfo<StockUpdownDomain> pageInfo = new PageInfo<>(pageData);
        PageResult<StockUpdownDomain> pageResult = new PageResult<>(pageInfo);

        // 5. 响应数据
        return R.ok(pageResult);
    }

    /**
     * 统计最新股票交易日内每分钟的涨跌停的股票数量
     * @return
     */
    @Override
    public R<Map<String, List>> getStockUpDownCount() {
        // 1. 获取股票最新的交易时间点(精确到分钟, 秒和毫秒设置为0)
        // mock data 等后续完成job工程 再将代码删除即可
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        curDateTime = DateTime.parse("2022-01-06 14:25:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        Date endDate = curDateTime.toDate();
        // 2. 获取最新交易时间点对应的开盘时间点
        Date startDate = DateTimeUtil.getOpenDate(curDateTime).toDate();
        // 3. 统计涨停数据
        List<Map> upList = stockRtInfoMapper.getStockUpdownCount(startDate, endDate, 1);
        // 4. 统计跌停数据
        List<Map> downList = stockRtInfoMapper.getStockUpdownCount(startDate, endDate, 0);
        // 5. 组装数据
        HashMap<String, List> info = new HashMap<>();
        info.put("upList", upList);
        info.put("downList", downList);

        // 6. 响应数据
        return R.ok(info);
    }

    /**
     * 统计大盘T日和T-1日每分钟交易量的统计
     * @return
     */
    @Override
    public R<Map<String, List>> getComparedStockTradeAmt() {
        // 1. 获取股票最新的交易时间点(精确到分钟, 秒和毫秒设置为0)
        // mock data 等后续完成job工程 再将代码删除即可
        DateTime tEndDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        tEndDateTime = DateTime.parse("2022-01-03 14:40:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        Date tEndDate = tEndDateTime.toDate();
        // 开盘时间
        Date tStartDate = DateTimeUtil.getOpenDate(tEndDateTime).toDate();
        // 2. 获取T-1日的时间范围
        DateTime preTEndDateTime = DateTimeUtil.getPreviousTradingDay(tEndDateTime);
        // mock data
        preTEndDateTime = DateTime.parse("2022-01-02 14:40:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        Date preTEndDate = preTEndDateTime.toDate();
        // 开盘时间
        Date tPreStartDate = DateTimeUtil.getOpenDate(preTEndDateTime).toDate();
        // 3. 调用mapper查询
        // 3.1 统计T日
        List<Map> tData = stockMarketIndexInfoMapper.getSumAmtInfo(tStartDate, tEndDate, stockInfoConfig.getInner());
        // 3.2 统计T-1日
        List<Map> preTData = stockMarketIndexInfoMapper.getSumAmtInfo(tPreStartDate, preTEndDate, stockInfoConfig.getInner());
        // 4. 组装数据
        HashMap<String, List> info = new HashMap<>();
        info.put("amtList", tData);
        info.put("yesAmtList", preTData);

        // 5.响应数据
        return R.ok(info);
    }

    /**
     * 统计最新交易时间点下股票（A股）在各个涨幅区间的数量
     * @return
     */
    @Override
    public R<Map> getIncreaseRangeInfo() {
        // 1. 获取当前最新的股票交易时间点
        DateTime curDateTime = DateTimeUtil.getLastDate4Stock(DateTime.now());
        // mock data
        curDateTime = DateTime.parse("2022-01-06 09:55:00", DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
        Date curDate = curDateTime.toDate();
        // 2. 调用mapper获取数据
        List<Map> infos = stockRtInfoMapper.getIncreaceRangeInfoByDate(curDate);
        // 获取有序的涨幅区间标题集合
        List<String> upDownRange = stockInfoConfig.getUpDownRange();
        // 将顺序的涨幅区间的元素转换为Map对象即可
        List<Map<String, Object>> allInfos = new ArrayList<>();
        for (String title : upDownRange) {
            Map<String, Object> tmp = null;
            for (Map info : infos) {
                if(info.containsValue(title)) {
                    tmp = info;
                    break;
                }
            }
            if (tmp == null) {
                // 不存在, 则进行补齐
                tmp = new HashMap<>();
                tmp.put("count", 0);
                tmp.put("title", title);
            }
            allInfos.add(tmp);
        }

        // 3. 组装数据
        HashMap<String, Object> data = new HashMap<>();
        data.put("time", curDateTime.toString("yyyy-MM-dd HH:mm:ss"));
        data.put("infos", allInfos);

        // 4. 响应
        return R.ok(data);
    }


}
