package com.chenhai.stock.service.impl;

import com.chenhai.stock.pojo.entity.StockMarketIndexInfo;
import com.chenhai.stock.pojo.vo.StockInfoConfig;
import com.chenhai.stock.service.StockTimerTaskService;
import com.chenhai.stock.utils.DateTimeUtil;
import com.chenhai.stock.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class StockTimerTaskServiceImpl implements StockTimerTaskService {

    @Autowired
    private StockInfoConfig stockInfoConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IdWorker idWorker;

    @Override
    public void getInnerMarketInfo() {
        // 1. 阶段1: 采集原始数据
        // 1.1 组装url地址
        String url = stockInfoConfig.getMarketUrl() + String.join(",", stockInfoConfig.getInner());
        // 1.2 维护请求投, 添加防盗链和用户标识
        HttpHeaders headers = new HttpHeaders();
        // 防盗链
        headers.add("Referer", "https://finance.sina.com.cn/stock/");
        // 用户客户端标识
        headers.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
        // 维护http请求实体对象
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        // 发起请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        int statusCodeValue = responseEntity.getStatusCodeValue();
        if (statusCodeValue != 200) {
            // 当前请求失败
            log.error("当前时间点: {}, 采集数据失败, http状态码: {}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), statusCodeValue);
            // 其他: 发送邮件 企业微信 顶顶等给相关运营人员提醒
            return;
        }
        // 获取js格式数据
        String jsData = responseEntity.getBody();
        log.info("当前时间点: {}, 采集原始数据内容: {}", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), jsData);

        // 2. 阶段2: java正则解析原始数据
        // 2.1 定义正则表达式
        String reg = "var hq_str_(.+)=\"(.+)\"";
        // 2.2 表达式编译
        Pattern pattern = Pattern.compile(reg);
        // 2. 匹配字符串
        Matcher matcher = pattern.matcher(jsData);
        ArrayList<StockMarketIndexInfo> entities = new ArrayList<>();
        while (matcher.find()) {
            // 1. 获取大盘的编码
            String marketCode = matcher.group(1);
            // 2. 获取其他信息
            String otherInfo = matcher.group(2);
            // 将other字符串以逗号分割, 获取大片的详细信息
            String[] splitArr = otherInfo.split(",");
            // 大盘名称
            String marketName = splitArr[0];
            // 获取当前大盘的开盘点数
            BigDecimal openPoint = new BigDecimal(splitArr[1]);
            // 前收盘点
            BigDecimal preClosePoint = new BigDecimal(splitArr[2]);
            // 获取大盘的当前点数
            BigDecimal curPoint = new BigDecimal(splitArr[3]);
            // 获取大盘最高点
            BigDecimal maxPoint = new BigDecimal(splitArr[4]);
            // 获取大盘最低点
            BigDecimal minPoint = new BigDecimal(splitArr[5]);
            // 获取成交量
            Long tradeAmt = Long.valueOf(splitArr[8]);
            // 获取成交金额
            BigDecimal tradeVol = new BigDecimal(splitArr[9]);
            // 时间
            Date curTime = DateTimeUtil.getDateTimeWithoutSecond(splitArr[30] + " " + splitArr[31]).toDate();
            // 3. 阶段3: 解析数据封装实体entity
            StockMarketIndexInfo stockMarketIndexInfo = StockMarketIndexInfo.builder()
                    .id(idWorker.nextId())
                    .marketCode(marketCode)
                    .marketName(marketName)
                    .preClosePoint(preClosePoint)
                    .openPoint(openPoint)
                    .curPoint(curPoint)
                    .minPoint(minPoint)
                    .maxPoint(maxPoint)
                    .tradeAmount(tradeAmt)
                    .tradeVolume(tradeVol)
                    .curTime(curTime)
                    .build();

            log.info("解析大盘数据完成！");
            // 4. 阶段4: 调用mybatis批量入库
        }
    }
}
