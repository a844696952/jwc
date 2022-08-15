package com.yice.edu.cn.common.pojo.jy.resourceCenter;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;

@Component
public class ResourceFileTypeUtil {
    public int setResouceType(String suffix) {
        //文档后缀数组
        String[] docArray = {"doc", "docx", "ppt", "pptx", "txt", "pdf", "xls", "xlsx"};
        //图片后缀数组
        String[] imgeArray = {"jpg", "png", "gif", "bmp","jpeg"};
        //音频后缀数组
        String[] voiceArray = {"mp3"};
        //视频后缀数组
        String[] videoArray = {};

        //返回1为文档类型
        if (ArrayUtils.contains(docArray, suffix)) {
            return 1;
            //返回2为图片类型
        } else if (ArrayUtils.contains(imgeArray, suffix)) {
            return 2;
            //返回3为音频类型
        } else if (ArrayUtils.contains(voiceArray, suffix)) {
            return 3;
            //返回4为视频类型
        } else if (ArrayUtils.contains(videoArray, suffix)) {
            return 4;
        }else{
            return 0;
        }
    }

}
