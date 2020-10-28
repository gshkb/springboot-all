package cn.gshkb.pdf.demo;

import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.util.List;

public class PdfFontUtil {

    private  PdfFontUtil(){}

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
