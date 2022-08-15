package com.yice.edu.cn.tap.service.xwDormManage.dorm;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.Dorm;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildVo;
import com.yice.edu.cn.tap.feignClient.xwDormManage.dorm.DormFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DormService {
    @Autowired
    private DormFeign dormFeign;

    public List<Dorm> findDormBuildingTreeByCondition(DormBuildVo dormBuildVo) {
       return dormFeign.findDormBuildingTreeByConditionTap(dormBuildVo);

    }

}
