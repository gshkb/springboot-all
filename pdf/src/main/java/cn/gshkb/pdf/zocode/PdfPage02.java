package cn.gshkb.pdf.zocode;

import cn.gshkb.pdf.Watermark;
import cn.gshkb.pdf.demo.PdfFontUtil;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PdfPage02 {

    // 基础配置
    private static String PDF_SITE = "D:\\file-type\\浙学码ss.pdf";
    private static String FONT = "C:/Windows/Fonts/simhei.ttf";
    private static String PAGE_TITLE = "PDF文件";

    private static String PRODUCT_NAME = "\n商户名称：浙江蓝奥教育科技有限公司";
    private static String H1 = "\n基本信息";
    private static String H2 = "\n联系方式";
    private static String H3 = "\n教育经历";

    // 基础样式
    private static Font TITLE_FONT = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, 20, Font.BOLD);
    private static Font NODE_FONT = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, 15, Font.BOLD);
    private static Font BLOCK_FONT = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, 13, Font.BOLD, BaseColor.BLACK);
    private static Font INFO_FONT = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, 12, Font.NORMAL, BaseColor.BLACK);
    private static Font CONTENT_FONT = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);


    private static void createPdfPage() throws Exception {
        // 创建文档
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(PDF_SITE));
        document.open();

        Watermark wm = new Watermark("浙江电大");
        // 水印
        writer.setPageEvent(wm);

        // 报告标题
        document.add(PdfFontUtil.getParagraph(PAGE_TITLE, TITLE_FONT, 1));
        document.add(PdfFontUtil.getParagraph(PRODUCT_NAME, INFO_FONT, -1));
        document.add(PdfFontUtil.getParagraph(getCreateDate(), INFO_FONT, -1));

        // 报告内容
        // 段落标题 + 报表图
        document.add(PdfFontUtil.getParagraph(H1, BLOCK_FONT, -1));
        // 设置图片宽高

        // 数据表格1
       // document.add(PdfFontUtil.getParagraph("\n· 数据详情\n\n", BLOCK_FONT, -1));
        PdfPTable dataTable = PdfFontUtil.getPdfPTable01(2, 400);
        // 设置表格
        List<List<String>> tableDataList = getTableDataForPerson();

        for (List<String> tableData : tableDataList) {
            PdfFontUtil.addTableCell(dataTable, CONTENT_FONT, tableData);
        }
        document.add(dataTable);

        // 数据表格2
        PdfPTable dataTable2 = PdfFontUtil.getPdfPTable01(1, 400);
        // 设置表格
        List<List<String>> tableDataList2 = getTableDataPerson();

        for (List<String> tableData : tableDataList2) {
            PdfFontUtil.addTableCell(dataTable2, CONTENT_FONT, tableData);
        }
        document.add(dataTable2);


        document.newPage();
        document.close();
        writer.close();
    }

    private static List<List<String>> getTableDataForPerson() {
        List<List<String>> tableDataList = new ArrayList<>();

        List<String> tableData1 = new ArrayList<>();
        tableData1.add("性别：" + "男");
        tableData1.add("省份证号：" + "622723******1410");
        tableDataList.add(tableData1);

        List<String> tableData2 = new ArrayList<>();
        tableData2.add("出生日期：" + "1992-10-04");
        tableData2.add("民族：" + "汉");
        tableDataList.add(tableData2);

        return tableDataList;
    }

    private static List<List<String>> getTableDataPerson() {
        List<List<String>> tableDataList = new ArrayList<>();

        List<String> tableData1 = new ArrayList<>();
        tableData1.add("籍贯：" + "浙江省象山县");
        tableDataList.add(tableData1);

        List<String> tableData2 = new ArrayList<>();
        tableData2.add("户籍所在地：" + "浙江省，杭州市，拱墅区，湖墅南路大唐新村11-1-111");
        tableDataList.add(tableData2);

        return tableDataList;
    }

    private static List<List<String>> getTableDataForTel() {
        List<List<String>> tableDataList = new ArrayList<>();

        List<String> tableData1 = new ArrayList<>();
        tableData1.add("性别：" + "男");
        tableData1.add("省份证号：" + "622723******1410");
        tableDataList.add(tableData1);

        List<String> tableData2 = new ArrayList<>();
        tableData2.add("出生日期：" + "1992-10-04");
        tableData2.add("民族：" + "汉");
        tableDataList.add(tableData2);

        List<String> tableData3 = new ArrayList<>();
        tableData3.add("籍贯：" + "浙江省象山县");
        tableData3.add("户籍所在地：" + "浙江省，杭州市，拱墅区，湖墅南路大唐新村11-1-111");
        tableDataList.add(tableData2);
        return tableDataList;
    }

    private static String getCreateDate() {
        return "\n生成时间：2020-01-15\n\n";
    }


    public static void main(String[] args) throws Exception {
        createPdfPage();
    }
}
