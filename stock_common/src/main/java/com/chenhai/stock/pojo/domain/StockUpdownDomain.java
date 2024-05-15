package com.chenhai.stock.pojo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockUpdownDomain {

    private String code;

    private String name;

    private BigDecimal preClosePrice;

    private BigDecimal tradePrice;

    private BigDecimal increace;

    private BigDecimal upDown;

    private BigDecimal amplitude;

    private Long tradeAmt;

    private BigDecimal tradeVol;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm") // SpringMVC支持的注解 -》 json格式数据
    private Date curDate;
}
