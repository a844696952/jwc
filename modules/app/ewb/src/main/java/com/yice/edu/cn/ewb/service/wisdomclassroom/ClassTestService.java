package com.yice.edu.cn.ewb.service.wisdomclassroom;

import com.yice.edu.cn.common.pojo.jy.wisdomclassroom.ClassTest;
import com.yice.edu.cn.ewb.feignClient.wisdomclassroom.ClassTestFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClassTestService {

    @Autowired
    private ClassTestFeign classTestFeign;

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    public ClassTest findClassTestById(String id){
        return classTestFeign.findClassTestById(id);
    }


    /**
     * 新增课堂检测
     * @param classTest
     * @return
     */
    @Transactional(rollbackFor =Exception.class)
    public Boolean saveClassTest(ClassTest classTest){
        return classTestFeign.saveClassTest(classTest);
    }

    /**
     * 查询课堂检测列表
     * @param classTest
     * @return
     */
    public List<ClassTest> findClassTestByCondition(ClassTest classTest){
        return classTestFeign.findClassTestByCondition(classTest);
    }

}
