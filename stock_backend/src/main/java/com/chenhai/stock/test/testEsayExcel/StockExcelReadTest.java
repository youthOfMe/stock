package com.chenhai.stock.test.testEsayExcel;


import com.alibaba.excel.EasyExcel;
import com.chenhai.stock.pojo.domain.StockUpdownDomain;

public class StockExcelReadTest {
    public static void main(String[] args) {
        String fileName = "D:\\java项目\\单体项目\\今日指数\\自主项目\\stock_parent\\testFile\\test.xlsx";
        EasyExcel.read(fileName, StockUpdownDomain.class, new StockReadListener()).sheet().doRead();
    }
}
