package cn.gshkb.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.IOException;

public class Watermark extends PdfPageEventHelper {

   // Font FONT = new Font(Font.FontFamily.HELVETICA, 30, Font.BOLD, new GrayColor(0.95f));
    BaseFont font = BaseFont.createFont("C:/Windows/Fonts/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
    Font FONT = new Font(font, 20, Font.BOLD, new GrayColor(0.95f));
    private String waterCont;//水印内容
    public Watermark() throws IOException, DocumentException {

    }
    public Watermark(String waterCont) throws IOException, DocumentException {
        this.waterCont = waterCont;
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        for(int i=0 ; i<5; i++) {
            for(int j=0; j<5; j++) {
                ColumnText.showTextAligned(writer.getDirectContentUnder(),
                        Element.ALIGN_LEFT,
                        new Phrase(this.waterCont == null ? "HELLO WORLD" : this.waterCont, FONT),
                        (50.5f+i*350),
                        (40.0f+j*150),
                        writer.getPageNumber() % 2 == 1 ? 45 : -45);
            }
        }
    }

}
