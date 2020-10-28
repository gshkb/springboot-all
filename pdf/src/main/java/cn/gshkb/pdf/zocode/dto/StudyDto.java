package cn.gshkb.pdf.zocode.dto;

import lombok.Data;

@Data
public class StudyDto {

    /**
     * 学校名称
     */
    private String schoolName;
    /**
     * 在校时间 例如，2016。09-2020.06
     */
    private String schoolTime;
    /**
     * 学位.专业
     */
    private String position;
    /**
     * 履历内容
     */
    private String text;
}
