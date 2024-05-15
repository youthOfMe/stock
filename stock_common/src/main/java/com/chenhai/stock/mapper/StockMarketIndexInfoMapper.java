package com.chenhai.stock.mapper;

import com.chenhai.stock.pojo.domain.InnerMarketDomain;
import com.chenhai.stock.pojo.entity.StockMarketIndexInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author 20406
* @description 针对表【stock_market_index_info(国内大盘数据详情表)】的数据库操作Mapper
* @createDate 2024-05-15 13:16:02
* @Entity com.chenhai.stock.pojo.entity.StockMarketIndexInfo
*/
public interface StockMarketIndexInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockMarketIndexInfo record);

    int insertSelective(StockMarketIndexInfo record);

    StockMarketIndexInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockMarketIndexInfo record);

    int updateByPrimaryKey(StockMarketIndexInfo record);

    /**
     * 根据指定时间点查询指定大盘的对应的数据
     * @param curDate 指定的时间
     * @param marketCodes 大盘编码集合
     * @return
     */
    List<InnerMarketDomain> getMarketInfo(@Param("curDate") Date curDate, @Param("marketCodes") List<String> marketCodes);

    /**
     * 统计指定日期范围内 指定大盘每分钟的成交量流水表
     * @param openDate 起始时间 - 一般是开盘时间
     * @param endDate 截止时间 - 一般与openDate同一天
     * @param marketCodes 大盘编码集合
     * @return
     */
    List<Map> getSumAmtInfo(@Param("openDate") Date openDate, @Param("endDate") Date endDate, @Param("marketCodes") List<String> marketCodes);
}
