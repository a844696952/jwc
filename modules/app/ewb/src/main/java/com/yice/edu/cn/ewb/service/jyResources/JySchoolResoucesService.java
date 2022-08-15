package com.yice.edu.cn.ewb.service.jyResources;

import com.yice.edu.cn.common.pojo.jy.resources.JySchoolResouces;
import com.yice.edu.cn.ewb.feignClient.jyResources.JySchoolResoucesFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JySchoolResoucesService {
    @Autowired
    private JySchoolResoucesFeign jySchoolResoucesFeign;


    public List<JySchoolResouces> findJySchoolResoucesListByCondition(JySchoolResouces jySchoolResouces) {
        return jySchoolResoucesFeign.findJySchoolResoucesListByCondition(jySchoolResouces);
    }



    public long findJySchoolResoucesCountByCondition(JySchoolResouces jySchoolResouces) {
        return jySchoolResoucesFeign.findJySchoolResoucesCountByCondition(jySchoolResouces);
    }


    /**
     * 返回资源列表和文件夹列表
     * @param jySchoolResouces
     * @return
     */
    public List<JySchoolResouces> findJySchoolResoucesList(JySchoolResouces jySchoolResouces) {
        return jySchoolResoucesFeign.findJySchoolResoucesList(jySchoolResouces);
    }
    /**
     * 返回资源和文件夹记录数
     * @param jySchoolResouces
     * @return
     */
    public long findJySchoolResoucesCount(JySchoolResouces jySchoolResouces) {
        return jySchoolResoucesFeign.findJySchoolResoucesCount(jySchoolResouces);
    }



}
