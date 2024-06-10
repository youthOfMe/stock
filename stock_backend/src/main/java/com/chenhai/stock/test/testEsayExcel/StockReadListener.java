package com.chenhai.stock.test.testEsayExcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.chenhai.stock.pojo.domain.StockUpdownDomain;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StockReadListener extends AnalysisEventListener<StockUpdownDomain> {

    /**
     * 当读取到一行数据时, 会调用这个方法, 并将读取到的数据以及上下文信息作为参数传入
     * 可以在这个方法中对读取到的数据进行处理和操作, 处理数据的时候要注意异常错误, 保证读取数据的稳定性
     * @param stockUpdownDomain 每行数据
     * @param analysisContext 上下文数据
     */
    @Override
    public void invoke(StockUpdownDomain stockUpdownDomain, AnalysisContext analysisContext) {
        log.info("解析到一条数据: {}", stockUpdownDomain);
    }

    /**
     * 当每个sheet所有数据读取完毕后, 会调用这个方法, 可以在这个方法中进行一些首尾工作, 比如资源释放, 数据汇总等
     * @param analysisContext 上下文数据
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("sheet={} 所有数据解析完成!", analysisContext.readSheetHolder().getSheetName());
    }

    /**
     * 解析出现异常
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        log.error("解析异常", exception);
        throw exception;
    }
}
