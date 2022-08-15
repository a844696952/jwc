package com.yice.edu.cn.bmp.service.xwDormManage;

import com.yice.edu.cn.bmp.feignClient.xwDormManage.DormPersonLogFeign;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPersonLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DormPersonLogService {
    @Autowired
    private DormPersonLogFeign dormPersonLogFeign;


    public List<DormPersonLog> findDormPersonLogListByCondition(DormPersonLog dormPersonLog) {
        return dormPersonLogFeign.findDormPersonLogListByCondition(dormPersonLog);
    }



}
