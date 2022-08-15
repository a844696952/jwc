package com.yice.edu.cn.common.pojo.xw.document;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * 发送对象（抄送人）
 *
 */
@Data
public class SendObject {

    private String id;
    private String name;
    private String parentId;
    private String schoolId;
    private Integer type;
    private String imgUrl;
    private String teacherId;
}
