package cn.gshkb.pdf.zocode.controller;

import cn.gshkb.pdf.utils.PdfUtil;
import cn.gshkb.pdf.zocode.dto.PdfDto;
import cn.gshkb.pdf.zocode.service.PdfReportService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class PdfController {

    @Autowired
    private PdfReportService pdfReportService;

    @GetMapping("/exportPdf")
    public void exportPdf(HttpServletResponse response){
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


        ObjectMapper mapper = new ObjectMapper();

       // Convert POJO to Map
        Map<String, Object> map =
                mapper.convertValue(pdfDto, new TypeReference<Map<String, Object>>() {});
        PdfUtil.fillTemplate(map,response,"D:\\file-type\\tem-copy.pdf");
    }


    @GetMapping("/exportPdf1")
    public void exportPdf1(HttpServletResponse response) throws Exception {
        pdfReportService.export(response);
    }
}
