package com.yice.edu.cn.common.pojo.jy.courseware;

import java.util.regex.Pattern;

/**
 * @author dengfengfeng
 */
public enum FileType {

    /**
     * 图片
     */
    IMAGE("^(jpg|png|jpeg|gif)$"),
    /**
     * 音频
     */
    AUDIO("^(wav|mp3|wma)$"),
    /**
     * 视频
     */
    VIDEO("^(mp4|avi|3gp|mpg|wmv|mpeg|mkv|rmvb)$"),

    /**
     * WORD
     */
    WORD("^(doc|docx)$"),

    /**
     * PDF
     */
    PDF("^(pdf)$"),

    /**
     * PPT
     */
    PPT("^(ppt|pptx)$"),

    /**
     * EXCEL
     */
    EXCEL("^(xls|xlsx)$");

    private String exts;

    public String getExts() {
        return exts;
    }

    FileType(String exts) {
        this.exts = exts;
    }

    public boolean matches(final String ext){
        return Pattern.matches(this.exts, ext);
    }
}
