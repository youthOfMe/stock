package com.chenhai.stock.service.impl;

import com.chenhai.stock.mapper.StockMarketIndexInfoMapper;
import com.chenhai.stock.mapper.StockRtInfoMapper;
import com.chenhai.stock.pojo.domain.InnerMarketDomain;
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

import java.util.Date;
import java.util.List;

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
}
