package com.yice.edu.cn.common.pojo.dm.msg;

import lombok.Data;

/**
 * @author dengfengfeng
 */
@Data
public class StudentMsg {

    private String studentId;
    private String studentName;
    private String studentImg;
    private String studentNo;
    private String time;
    private int count;
    private DmMsg lastMsg;
}
