package com.yice.edu.cn.ecc.service.parent;

import com.yice.edu.cn.common.pojo.jw.parent.ParentStudent;
import com.yice.edu.cn.ecc.feignClient.parent.ParentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ParentService {
    @Autowired
    private ParentFeign parentFeign;
    public List<ParentStudent> findParentStudentListByCondition(ParentStudent parentStudent){
        return parentFeign.findParentStudentListByCondition(parentStudent);
    }


}
