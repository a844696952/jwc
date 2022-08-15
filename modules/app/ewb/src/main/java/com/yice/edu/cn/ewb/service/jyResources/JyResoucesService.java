package com.yice.edu.cn.ewb.service.jyResources;

import com.yice.edu.cn.common.pojo.jy.resources.JyResouces;
import com.yice.edu.cn.ewb.feignClient.jyResources.JyResoucesFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JyResoucesService {
    @Autowired
    private JyResoucesFeign jyResoucesFeign;


    public List<JyResouces> findJyResoucesListByCondition(JyResouces jyResouces) {
        return jyResoucesFeign.findJyResoucesListByCondition(jyResouces);
    }



    public long findJyResoucesCountByCondition(JyResouces jyResouces) {
        return jyResoucesFeign.findJyResoucesCountByCondition(jyResouces);
    }



}
