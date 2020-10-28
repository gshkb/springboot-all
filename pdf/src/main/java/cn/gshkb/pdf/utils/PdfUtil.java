package cn.gshkb.pdf.utils;


import cn.gshkb.pdf.Watermark;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;


import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class PdfUtil {


    /**
     * @param templatePath pdf模板路径
     */
    // 利用模板生成pdf
    public static void fillTemplate(Map<String, Object> data, HttpServletResponse response, String templatePath) {
        PdfReader reader = null;

        PdfStamper pdfStamper = null;

        ByteArrayOutputStream bos = null;

        try {
            //设置字体
            String font = "C:\\Windows\\Fonts\\Microsoft-YaHei.ttf";
            //  String font =   "C:/Windows/Fonts/simhei.ttf";

            reader = new PdfReader(templatePath);
            bos = new ByteArrayOutputStream();
            pdfStamper = new PdfStamper(reader, bos);
            AcroFields acroFields = pdfStamper.getAcroFields();

            //使用中文字体 使用 AcroFields填充值的不需要在程序中设置字体，在模板文件中设置字体为中文字体 Adobe 宋体 std L
            // BaseFont bfChinese = BaseFont.createFont(font, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            //设置编码格式
            acroFields.addSubstitutionFont(bfChinese);
            // 遍历data 给pdf表单表格赋值
            for (String key : data.keySet()) {
                if (data.get(key) != null) {
                    String s1 = data.get(key).toString();
                    acroFields.setField(key, s1);
                }
            }

            Watermark wm = new Watermark("lano");
            // 动态生成段落
            PdfWriter writer = pdfStamper.getWriter();
         //   writer.setPageEvent(wm);// 水印

         //   writer.setPageEvent(new MyHeaderFooter());// 页眉/页脚
           // writer.getPdfDocument();

            // 如果为false那么生成的PDF文件还能编辑，一定要设为true
            pdfStamper.setFormFlattening(true);
            pdfStamper.close();

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

            // 用文件流写出

//            String savePath = "文件保存路径";
//            FileOutputStream fos = new FileOutputStream(savePath);
//
//            fos.write(bos.toByteArray());
//            fos.flush();
//            fos.close();


        } catch (IOException | DocumentException e) {
            //  log.error("读取文件异常");
            //  throw new HwBusinessException(BasicCode.ERROR, "生成PDF失败！");
            e.printStackTrace();
        } finally {
            try {
                bos.close();
                reader.close();
            } catch (IOException e) {
                //    log.error("关闭流异常");
                e.printStackTrace();
            }
        }
    }

    /**
     * 插入文本
     *
     * @param ps
     * @param s
     */
    public static void insertText(PdfStamper ps, AcroFields s) {
        List<AcroFields.FieldPosition> list = s.getFieldPositions("CONNECT_NAME");
        Rectangle rect = list.get(0).position;


        PdfContentByte cb = ps.getOverContent(1);
        PdfPTable table = new PdfPTable(1);
        float tatalWidth = rect.getRight() - rect.getLeft() - 1;
        table.setTotalWidth(tatalWidth);


        PdfPCell cell = new PdfPCell(new Phrase(CreateChunk("测试")));
        cell.setFixedHeight(rect.getTop() - rect.getBottom() - 1);
        cell.setBorderWidth(0);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setLeading(0, (float) 1.1);


        table.addCell(cell);
        table.writeSelectedRows(0, -1, rect.getLeft(), rect.getTop(), cb);
    }


    /**
     * 创建Chunk
     *
     */
    public static Chunk CreateChunk(String content) {
        BaseFont bfChinese;
        Chunk chunk = null;
        try {
            bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
            Font fontChinese = new Font(bfChinese, 10 * 4 / 3);
            chunk = new Chunk(content, fontChinese);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return chunk;
    }

    /**
     * 段落样式获取
     */
    public static Paragraph getParagraph (String content, Font font, Integer alignment){
        Paragraph paragraph = new Paragraph(content,font) ;
        if (alignment != null && alignment >= 0){
            paragraph.setAlignment(alignment);
        }
        return paragraph ;
    }
    /**
     * 图片样式
     */
    public static Image getImage (String imgPath, float width, float height) throws Exception {
        Image image = Image.getInstance(imgPath);
        image.setAlignment(Image.MIDDLE);
        if (width > 0 && height > 0){
            image.scaleAbsolute(width, height);
        }
        return image ;
    }
    /**
     * 表格生成
     */
    public static PdfPTable getPdfPTable01 (int numColumns, float totalWidth) throws Exception {
        // 表格处理
        PdfPTable table = new PdfPTable(numColumns);
        // 设置表格宽度比例为%100
        table.setWidthPercentage(100);
        // 设置宽度:宽度平均
        table.setTotalWidth(totalWidth);
        // 锁住宽度
        table.setLockedWidth(true);
        // 设置表格上面空白宽度
        table.setSpacingBefore(10f);
        // 设置表格下面空白宽度
        table.setSpacingAfter(10f);
        // 设置表格默认为无边框
        table.getDefaultCell().setBorder(0);
        table.setPaddingTop(50);
        table.setSplitLate(false);
        return table ;
    }
    /**
     * 表格内容
     */
    public static PdfPCell getPdfPCell (Phrase phrase){
        PdfPCell pdfPCell = new PdfPCell(phrase);
        pdfPCell.setBorder(0);
        return new PdfPCell (phrase) ;
    }
    /**
     * 表格内容带样式
     */
    public static void addTableCell (PdfPTable dataTable, Font font, List<String> cellList){
        for (String content:cellList) {
            dataTable.addCell(getParagraph(content,font,-1));
        }
    }
}