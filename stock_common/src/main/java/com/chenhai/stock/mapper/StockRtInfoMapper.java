package com.chenhai.stock.mapper;

import com.chenhai.stock.pojo.domain.Stock4EvrDayDomain;
import com.chenhai.stock.pojo.domain.Stock4MinuteDomain;
import com.chenhai.stock.pojo.domain.StockUpdownDomain;
import com.chenhai.stock.pojo.entity.StockRtInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author 20406
* @description 针对表【stock_rt_info(个股详情信息表)】的数据库操作Mapper
* @createDate 2024-05-15 14:07:45
* @Entity com.chenhai.stock.pojo.entity.StockRtInfo
*/
public interface StockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockRtInfo record);

    int insertSelective(StockRtInfo record);

    StockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockRtInfo record);

    int updateByPrimaryKey(StockRtInfo record);

    List<StockUpdownDomain> getStockInfoByTime(@Param("curDate") Date curDate);

    /**
     * 统计指定日期范围内股票涨停或者跌停的数量流水
     * @param startDate 开始时间, 一般指开盘时间
     * @param endDate 截止时间
     * @param flag 约定: 1: 代表统计涨停 0: 跌停
     * @return
     */
    List<Map> getStockUpdownCount(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("flag") int flag);

    /**
     * 统计指定时间点下股票在各个涨跌区间的数量
     * @param curDate
     * @return
     */
    List<Map> getIncreaceRangeInfoByDate(Date curDate);

    /**
     * 根据股票编码查询指定时间范围馁的日K先数据
     * @param openDate
     * @param endDate
     * @param stockCode
     * @return
     */
    List<Stock4MinuteDomain> getStock4MinuteInfo(@Param("startDate") Date openDate, @Param("endDate") Date endDate, @Param("stockCode") String stockCode);

    /**
     * 根据股票编码吗查询指定时间范围内的日K线数据
     * @param startDate
     * @param endDate
     * @param stockCode
     * @return
     */
    List<Stock4EvrDayDomain> getStock4DkLine(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("stockCode") String stockCode);
}
