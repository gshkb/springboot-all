package cn.gshkb.pdf.zocode.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PdfDto {

    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String gender;
    /**
     * 身份证
     */
    private String idCard;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 民族
     */
    private String nation;
    /**
     * 政治面貌
     */
    private String political;
    /**
     * 最高学历
     */
    private String highestEducation;
    /**
     * 籍贯
     */
    private String nativePlace;
    /**
     * 户籍所在地
     */
    private String registeredResidence;
    /**
     * 地址
     */
    private String address;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 邮箱
     */
    private String mailbox;

    /**
     * 教育经历
     */
    private List<StudyDto> studyList;
    /**
     * 证书柜 -存图片url
     */
    private List<String> imgList;
}
