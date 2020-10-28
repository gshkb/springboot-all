package cn.gshkb.pdf.demo;

import cn.gshkb.pdf.Watermark;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PdfPage01 {
    // 基础配置
    private static String PDF_SITE = "D:\\file-type\\PDF页面2020-01-15.pdf" ;
    private static String FONT = "C:/Windows/Fonts/simhei.ttf";
    private static String PAGE_TITLE = "PDF数据导出报告" ;
    // 基础样式
    private static Font TITLE_FONT = FontFactory.getFont(FONT, BaseFont.IDENTITY_H,20, Font.BOLD);
    private static Font NODE_FONT = FontFactory.getFont(FONT, BaseFont.IDENTITY_H,15, Font.BOLD);
    private static Font BLOCK_FONT = FontFactory.getFont(FONT, BaseFont.IDENTITY_H,13, Font.BOLD, BaseColor.BLACK);
    private static Font INFO_FONT = FontFactory.getFont(FONT, BaseFont.IDENTITY_H,12, Font.NORMAL,BaseColor.BLACK);
    private static Font CONTENT_FONT = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

    private static void createPdfPage () throws Exception {
        // 创建文档
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(PDF_SITE));
        document.open();

        Watermark wm = new Watermark("浙江电大");

        writer.setPageEvent(wm);// 水印

        // 报告标题
        document.add(PdfFontUtil.getParagraph(PAGE_TITLE,TITLE_FONT,1)) ;
        document.add(PdfFontUtil.getParagraph("\n商户名称：XXX科技有限公司",INFO_FONT,-1)) ;
        document.add(PdfFontUtil.getParagraph("\n生成时间：2020-01-15\n\n",INFO_FONT,-1)) ;

        // 报告内容
        // 段落标题 + 报表图
        document.add(PdfFontUtil.getParagraph("城市数据分布统计",NODE_FONT,-1)) ;
        document.add(PdfFontUtil.getParagraph("\n· 可视化图表\n\n",BLOCK_FONT,-1)) ;
        // 设置图片宽高
        float documentWidth = document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin();
        float documentHeight = documentWidth / 580 * 320;
        document.add(PdfFontUtil.getImage("D:\\file-type\\热力图.png",documentWidth-80,documentHeight-80)) ;
        // 数据表格
        document.add(PdfFontUtil.getParagraph("\n· 数据详情\n\n",BLOCK_FONT,-1)) ;
        PdfPTable dataTable = PdfFontUtil.getPdfPTable01(4,400) ;
        // 设置表格
        List<String> tableHeadList = tableHead () ;
        List<List<String>> tableDataList = getTableData () ;
        PdfFontUtil.addTableCell(dataTable,CONTENT_FONT,tableHeadList);
        for (List<String> tableData : tableDataList) {
            PdfFontUtil.addTableCell(dataTable,CONTENT_FONT,tableData);
        }
        document.add(dataTable);
        document.add(PdfFontUtil.getParagraph("\n· 报表描述\n\n",BLOCK_FONT,-1)) ;
        document.add(PdfFontUtil.getParagraph("数据报告可以监控每天的推广情况，" +
                "可以针对不同的数据表现进行分析，以提升推广效果。",CONTENT_FONT,-1)) ;
        document.newPage() ;
        document.close();
        writer.close();
    }
    private static List<List<String>> getTableData (){
        List<List<String>> tableDataList = new ArrayList<>() ;
        for (int i = 0 ; i < 3 ; i++){
            List<String> tableData = new ArrayList<>() ;
            tableData.add("浙江"+i) ;
            tableData.add("杭州"+i) ;
            tableData.add("276"+i) ;
            tableData.add("33.3%") ;
            tableDataList.add(tableData) ;
        }
        return tableDataList ;
    }
    private static List<String> tableHead (){
        List<String> tableHeadList = new ArrayList<>() ;
        tableHeadList.add("省份") ;
        tableHeadList.add("城市") ;
        tableHeadList.add("数量") ;
        tableHeadList.add("百分比") ;
        return tableHeadList ;
    }
    public static void main(String[] args) throws Exception {
        createPdfPage () ;
    }
}
