package com.chenhai.stock.mapper;

import com.chenhai.stock.pojo.domain.StockBlockRtInfoDomain;
import com.chenhai.stock.pojo.entity.StockBlockRtInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* @author 20406
* @description 针对表【stock_block_rt_info(股票板块详情信息表)】的数据库操作Mapper
* @createDate 2024-06-01 16:00:20
* @Entity com.chenhai.stock.pojo.entity.StockBlockRtInfo
*/
public interface StockBlockRtInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockBlockRtInfo record);

    int insertSelective(StockBlockRtInfo record);

    StockBlockRtInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(StockBlockRtInfo record);

    int updateByPrimaryKey(StockBlockRtInfo record);

    /**
     * 获取国内板块指数数据
     * @param curDate
     */
    List<StockBlockRtInfoDomain> getStcokBlockRtInfo(@Param("curDate") Date curDate);
}
