package com.yice.edu.cn.common.easypoiplus;

/**
 * @author dengfengfeng
 * 实现此接口即可获得加强版功能,目前只做了标记是否需要加锁
 */
public interface ExcelPlus {

    /**
     * 数据起始行下标(从0开始)
     * @return
     */
    default int dataStartRowIndex(){
        return 0;
    };
}
