package cn.gshkb.pdf.html;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;

public class HtmlToPdf {

    public static void convert() throws IOException, DocumentException {
        Document document = new Document(PageSize.LETTER);
        PdfWriter.getInstance(document, new FileOutputStream("D:\\file-type\\tem-html2.pdf"));
        document.open();
        HTMLWorker htmlWorker = new HTMLWorker(document);
        String line = null;
        StringBuilder text = new StringBuilder();
        //用来计数
        int tmpCount = 0;
        //开始行
        int begin = 50;
        //结束行
        int end = 100;
        BufferedReader sb = new BufferedReader(new FileReader("D:/file-type/机构管理.html"));
        /*try  {
            //这里使用sb.readLine()将一行文件的内容赋予line，注意：在出现sb.readLine()的时候不管你是用来做判断还是赋值，这行内容都已经从流消失。
            while ((line = sb.readLine()) != null) {
                //++=+1
                tmpCount++;
                //如果行数大于等于开始行并且小于等于结束行
                if (tmpCount >= begin && tmpCount <= end) {
                    //用词对象将这行的内容存起来，并加上换行符
                    text.append(line).append("");
                }
            }
            //关闭流

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            sb.close();
        }*/

        htmlWorker.parse(sb);
        document.close();
    }

    public static void main(String[] args) throws IOException, DocumentException {
        convert();
    }
}
