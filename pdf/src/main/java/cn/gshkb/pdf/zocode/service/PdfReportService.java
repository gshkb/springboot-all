package cn.gshkb.pdf.zocode.service;

import cn.gshkb.pdf.MyHeaderFooter;
import cn.gshkb.pdf.Watermark;
import cn.gshkb.pdf.utils.PdfUtil;
import cn.gshkb.pdf.zocode.dto.PdfDto;
import cn.gshkb.pdf.zocode.dto.StudyDto;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;

@Service
public class PdfReportService {

    // 定义全局的字体静态变量
    private static Font titlefont;
    private static Font headfont;
    private static Font keyfont;
    private static Font textfont;
    private static Font nickfont;
    // 最大宽度
    private static int maxWidth = 520;

    // 静态代码块
    static {
        try {
            // 不同字体（这里定义为同一种字体：包含不同字号、不同style）
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            titlefont = new Font(bfChinese, 16, Font.BOLD);
            headfont = new Font(bfChinese, 14, Font.BOLD);
            keyfont = new Font(bfChinese, 10, Font.BOLD);
            textfont = new Font(bfChinese, 10, Font.NORMAL);
            nickfont = new Font(bfChinese, 20, Font.BOLD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // main测试
    public  void export(HttpServletResponse response) throws Exception {
        try {
            // 1.新建document对象
            Document document = new Document(PageSize.A4);// 建立一个Document对象

            // 2.建立一个书写器(Writer)与document对象关联
           // File file = new File("D:\\file-type\\tem-xxxx.pdf");

           // file.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(document,bos);
            Watermark wm = new Watermark("lano");

            writer.setPageEvent(wm);// 水印

            writer.setPageEvent(new MyHeaderFooter());// 页眉/页脚

            // 3.打开文档
            document.open();
            document.addTitle("Title@PDF");// 标题
            document.addAuthor("Author@hkb");// 作者
            document.addSubject("Subject@tmp");// 主题
            document.addKeywords("Keywords@lano");// 关键字
            document.addCreator("Creator@hkb");// 创建者
            wm.onEndPage(writer, document);
            // 4.向文档中添加内容
            generatePDF(document);

            // 5.关闭文档
            document.close();

            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("car_bill.pdf", "UTF-8"));

            //用流写出
            try {
                /* 用流将数据写给前端 */
                OutputStream os = response.getOutputStream();
                os.write(bos.toByteArray());
                os.flush();
                os.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }

    }



    // 生成PDF文件
    public void generatePDF(Document document) throws Exception {

        PdfDto pdfDto = new PdfDto();
        pdfDto.setName("诸葛小飞");
        pdfDto.setGender("男");
        pdfDto.setIdCard("6227231992090172987");
        pdfDto.setBirthday("1992-09-29");
        pdfDto.setNation("汉族");
        pdfDto.setPolitical("党员");
        pdfDto.setNativePlace("浙江省，象山县");
        pdfDto.setRegisteredResidence("浙江省，杭州市，拱墅区湖墅南路大唐新村11-1-111");

        pdfDto.setAddress("浙江省，杭州市，拱墅区,湖墅南路大唐新村11-1-111");
        pdfDto.setPhone("13575785566");
        pdfDto.setMailbox("32332212@qq.com");

        ArrayList<StudyDto> studyDtos = new ArrayList<StudyDto>();
        for (int i = 0; i < 6; i++) {
            StudyDto studyDto = new StudyDto();
            studyDto.setSchoolName("浙江理工大学" + i);
            studyDto.setSchoolTime("2016.09-2020.06");
            studyDto.setPosition("本科。计算机科学");
            studyDto.setText("担任班长一职，2次获得校级三好学生");
            studyDtos.add(studyDto);
        }
        pdfDto.setStudyList(studyDtos);

        ArrayList<String> imgList = new ArrayList<String>();
        imgList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1645002211,801474244&fm=26&gp=0.jpg");
        imgList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1645002211,801474244&fm=26&gp=0.jpg");
        imgList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1645002211,801474244&fm=26&gp=0.jpg");
        imgList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1645002211,801474244&fm=26&gp=0.jpg");
        imgList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1645002211,801474244&fm=26&gp=0.jpg");
        imgList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1645002211,801474244&fm=26&gp=0.jpg");
        imgList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1645002211,801474244&fm=26&gp=0.jpg");
        imgList.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1645002211,801474244&fm=26&gp=0.jpg");
        pdfDto.setImgList(imgList);

        // 头像
        Image image = Image.getInstance("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2057588226,2402156864&fm=11&gp=0.jpg");
        image.setAlignment(Image.ALIGN_CENTER);
        image.scalePercent(20); //依照比例缩放
        document.add(image);

        // 段落一 昵称
        Paragraph paragraph0 = new Paragraph(pdfDto.getName(), nickfont);
        paragraph0.setAlignment(1); //设置文字居中 0靠左   1，居中     2，靠右
        paragraph0.setIndentationLeft(12); //设置左缩进
        paragraph0.setIndentationRight(12); //设置右缩进
        paragraph0.setFirstLineIndent(24); //设置首行缩进
        paragraph0.setLeading(20f); //行间距
        paragraph0.setSpacingBefore(5f); //设置段落上空白
        paragraph0.setSpacingAfter(10f); //设置段落下空白
        document.add(paragraph0);

        // 段落一
        Paragraph paragraph1 = new Paragraph("基本信息", titlefont);
        paragraph1.setAlignment(0); //设置文字居中 0靠左   1，居中     2，靠右
        paragraph1.setIndentationLeft(12); //设置左缩进
        paragraph1.setIndentationRight(12); //设置右缩进
        paragraph1.setFirstLineIndent(24); //设置首行缩进
        paragraph1.setLeading(20f); //行间距
        paragraph1.setSpacingBefore(5f); //设置段落上空白
        paragraph1.setSpacingAfter(10f); //设置段落下空白
        document.add(paragraph1);

        // 直线
        Paragraph p1 = new Paragraph();
        p1.add(new Chunk(new LineSeparator()));
        document.add(p1);

        // 表格 3 * 2
        PdfPTable table = PdfUtil.getPdfPTable01(2,maxWidth);

        table.addCell(createCell("性别：" + pdfDto.getGender(), textfont));
        table.addCell(createCell("身份证号：" + pdfDto.getIdCard(), textfont));
        table.addCell(createCell("出生日期：" + pdfDto.getBirthday(), textfont));
        table.addCell(createCell("民族：" + pdfDto.getNation(), textfont));
        table.addCell(createCell("政治面貌：" + pdfDto.getPolitical(), textfont));
        table.addCell(createCell("最高学历：" + pdfDto.getHighestEducation(), textfont));
        document.add(table);

        // 表格 1 * 2
        PdfPTable table2 = createTable(1, 1);
        table2.addCell(createCell("籍贯：" + pdfDto.getNativePlace(), textfont));
        table2.addCell(createCell("户籍所在地：" + pdfDto.getRegisteredResidence(), textfont));
        document.add(table2);

        // 段落二
        Paragraph paragraph2 = new Paragraph("联系方式", titlefont);
        paragraph2.setAlignment(0); //设置文字居中 0靠左   1，居中     2，靠右
        paragraph2.setIndentationLeft(12); //设置左缩进
        paragraph2.setIndentationRight(12); //设置右缩进
        paragraph2.setFirstLineIndent(24); //设置首行缩进
        paragraph2.setLeading(20f); //行间距
        paragraph2.setSpacingBefore(5f); //设置段落上空白
        paragraph2.setSpacingAfter(10f); //设置段落下空白
        document.add(paragraph2);

        // 直线二
        Paragraph p2 = new Paragraph();
        p2.add(new Chunk(new LineSeparator()));
        document.add(p2);

        // 表格 3 * 1
        PdfPTable table3 = createTable(1, 1);
        table3.addCell(createCell("地址：" + pdfDto.getAddress(), textfont));
        table3.addCell(createCell("手机：" + pdfDto.getPhone(), textfont));
        table3.addCell(createCell("邮箱：" + pdfDto.getMailbox(), textfont));
        document.add(table3);

        // 段落三
        Paragraph paragraph3 = new Paragraph("联系方式", titlefont);
        paragraph3.setAlignment(0); //设置文字居中 0靠左   1，居中     2，靠右
        paragraph3.setIndentationLeft(12); //设置左缩进
        paragraph3.setIndentationRight(12); //设置右缩进
        paragraph3.setFirstLineIndent(24); //设置首行缩进
        paragraph3.setLeading(20f); //行间距
        paragraph3.setSpacingBefore(5f); //设置段落上空白
        paragraph3.setSpacingAfter(10f); //设置段落下空白
        document.add(paragraph3);
        // 直线二
        Paragraph p3 = new Paragraph();
        p3.add(new Chunk(new LineSeparator()));
        document.add(p3);


        if (!CollectionUtils.isEmpty(pdfDto.getStudyList())) {
            for (StudyDto studyDto : pdfDto.getStudyList()) {
                PdfPTable table4 = createTable(2, 1);
                table4.addCell(createCell(studyDto.getSchoolName(), textfont));
                table4.addCell(createCell(studyDto.getSchoolTime(), textfont));
                document.add(table4);

                PdfPTable table5 = createTable(1, 1);
                table5.addCell(createCell(studyDto.getPosition(), textfont));
                table5.addCell(createCell(studyDto.getText(), textfont));
                table5.setSpacingAfter(10f);
                document.add(table5);
            }
        }

        // 段落四
        Paragraph paragraph4 = new Paragraph("我的证书展柜", titlefont);
        paragraph4.setAlignment(0); //设置文字居中 0靠左   1，居中     2，靠右
        paragraph4.setIndentationLeft(12); //设置左缩进
        paragraph4.setIndentationRight(12); //设置右缩进
        paragraph4.setFirstLineIndent(24); //设置首行缩进
        paragraph4.setLeading(20f); //行间距
        paragraph4.setSpacingBefore(5f); //设置段落上空白
        paragraph4.setSpacingAfter(10f); //设置段落下空白
        document.add(paragraph4);
        // 循环判断插入图片，一页插入四张
        if (!CollectionUtils.isEmpty(pdfDto.getImgList())) {
            for (String path : pdfDto.getImgList()) {
                PdfPTable table4 = createTable(1, 1);
                Image image1 = Image.getInstance(path);
                image.setAlignment(Image.ALIGN_CENTER);
                image.scalePercent(20); //依照比例缩放
                table4.addCell(image1);
                document.add(table4);

            }
        }


    }


/**------------------------创建表格单元格的方法start----------------------------*/
    /**
     * 创建单元格(指定字体)
     *
     * @param value
     * @param font
     * @return
     */
    public PdfPCell createCell(String value, Font font) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(0);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    /**
     * 创建单元格（指定字体、水平..）
     *
     * @param value
     * @param font
     * @param align
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并）
     *
     * @param value
     * @param font
     * @param align
     * @param colspan
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align, int colspan) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并、设置单元格内边距）
     *
     * @param value
     * @param font
     * @param align
     * @param colspan
     * @param boderFlag
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align, int colspan, boolean boderFlag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        cell.setPadding(3.0f);
        cell.disableBorderSide(-1);
        if (!boderFlag) {
            cell.setBorder(0);
            cell.setPaddingTop(15.0f);
            cell.setPaddingBottom(8.0f);
        } else if (boderFlag) {
            cell.setBorder(0);
            cell.setPaddingTop(0.0f);
            cell.setPaddingBottom(15.0f);
        }
        return cell;
    }

    /**
     * 创建单元格（指定字体、水平..、边框宽度：0表示无边框、内边距）
     *
     * @param value
     * @param font
     * @param align
     * @param borderWidth
     * @param paddingSize
     * @param flag
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align, float[] borderWidth, float[] paddingSize, boolean flag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        cell.setBorderWidthLeft(borderWidth[0]);
        cell.setBorderWidthRight(borderWidth[1]);
        cell.setBorderWidthTop(borderWidth[2]);
        cell.setBorderWidthBottom(borderWidth[3]);
        cell.setPaddingTop(paddingSize[0]);
        cell.setPaddingBottom(paddingSize[1]);
        if (flag) {
            cell.setColspan(2);
        }
        return cell;
    }
/**------------------------创建表格单元格的方法end----------------------------*/


/**--------------------------创建表格的方法start------------------- ---------*/
    /**
     * 创建默认列宽，指定列数、水平(居中、右、左)的表格
     *
     * @param colNumber
     * @param align
     * @return
     */
    public PdfPTable createTable(int colNumber, int align) {
        PdfPTable table = new PdfPTable(colNumber);
        try {
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(align);
            table.getDefaultCell().setBorder(-1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    /**
     * 创建指定列宽、列数的表格
     *
     * @param widths
     * @return
     */
    public PdfPTable createTable(float[] widths) {
        PdfPTable table = new PdfPTable(widths);
        try {
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(-1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    /**
     * 创建空白的表格
     *
     * @return
     */
    public PdfPTable createBlankTable() {
        PdfPTable table = new PdfPTable(1);
        table.getDefaultCell().setBorder(0);
        table.addCell(createCell("", keyfont));
        table.setSpacingAfter(20.0f);
        table.setSpacingBefore(20.0f);
        return table;
    }

    /**
     * --------------------------创建表格的方法end-----------------------------
     */

    public static boolean imgToPdf(String imgFilePath, String pdfFilePath) throws IOException {
        File file = new File(imgFilePath);
        if (file.exists()) {
            Document document = new Document();
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(pdfFilePath);
                PdfWriter.getInstance(document, fos);

// 添加PDF文档的某些信息，比如作者，主题等等
                document.addAuthor("arui");
                document.addSubject("test pdf.");
// 设置文档的大小
                document.setPageSize(PageSize.A4);
// 打开文档
                document.open();
// 写入一段文字
//document.add(new Paragraph("JUST TEST ..."));
// 读取一个图片
                Image image = Image.getInstance(imgFilePath);
                float imageHeight = image.getScaledHeight();
                float imageWidth = image.getScaledWidth();
                int i = 0;
                while (imageHeight > 500 || imageWidth > 500) {
                    image.scalePercent(100 - i);
                    i++;
                    imageHeight = image.getScaledHeight();
                    imageWidth = image.getScaledWidth();
                    System.out.println("imageHeight->" + imageHeight);
                    System.out.println("imageWidth->" + imageWidth);
                }

                image.setAlignment(Image.ALIGN_CENTER);
//      //设置图片的绝对位置
//  image.setAbsolutePosition(0, 0);
//  image.scaleAbsolute(500, 400);
// 插入一个图片
                document.add(image);
            } catch (DocumentException de) {
                System.out.println(de.getMessage());
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
            document.close();
            fos.flush();
            fos.close();
            return true;
        } else {
            return false;
        }
    }
}
