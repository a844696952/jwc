package com.yice.edu.cn.common.dto.jy;

import com.yice.edu.cn.common.pojo.jy.collectivePlan.JyPrepareLessonsDiscuss;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.JyPrepareLessonsDiscussReply;

import java.util.List;

/**
 * @Author: keyusong
 * @Date: 2018/12/21 9:34
 * 评论和回复
 */
public class DiscussAndReply {

    //给前端评论框一个默认值，用于控制评论框的开合
    public static final int SIGN_FALSE = 0;

    private JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss;
    private List<JyPrepareLessonsDiscussReply> list;
    private int sign;

    public JyPrepareLessonsDiscuss getJyPrepareLessonsDiscuss() {
        return jyPrepareLessonsDiscuss;
    }

    public void setJyPrepareLessonsDiscuss(JyPrepareLessonsDiscuss jyPrepareLessonsDiscuss) {
        this.jyPrepareLessonsDiscuss = jyPrepareLessonsDiscuss;
    }

    public List<JyPrepareLessonsDiscussReply> getList() {
        return list;
    }

    public void setList(List<JyPrepareLessonsDiscussReply> list) {
        this.list = list;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }
}
