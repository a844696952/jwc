package com.yice.edu.cn.bmp.service.dm.classcard;

import com.yice.edu.cn.bmp.feignClient.dm.classcard.DmClassCardFeign;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DmClassCardService {
    @Autowired
    private DmClassCardFeign dmClassCardFeign;


    /**
     * 通过学生id 查询 班牌
     * @param studentId
     * @return
     */
    public DmClassCard findDmClassCardByStudentId(String studentId) {
        return dmClassCardFeign.findDmClassCardByStudentId(studentId);
    }


}
