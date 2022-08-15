package com.yice.edu.cn.osp.service.jy.homework;

import com.yice.edu.cn.common.pojo.jy.wisdomclassroom.ClassTest;
import com.yice.edu.cn.common.pojo.jy.wisdomclassroom.TopicDetail;
import com.yice.edu.cn.common.util.jmessage.utils.StringUtils;
import com.yice.edu.cn.osp.feignClient.jy.homework.ClassTestFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课堂检测
 * @author ：ly
 * @date ：Created in 2019/6/12 11:48
 * @description：
 * @modified By：
 * @version:
 */
@Service
public class ClassTestService {

    @Autowired
    private ClassTestFeign classTestFeign;


    public Boolean addClassTest(ClassTest classTest){
        return  classTestFeign.saveClassTest (classTest);
    }

    public List<TopicDetail> findTopDetailListByCondition(TopicDetail topicDetail){
        return  classTestFeign.findTopDetailListByCondition (topicDetail);
    }

    public long findTopDetailCountByCondition(TopicDetail topicDetail){
        return  classTestFeign.findTopDetailCountByCondition (topicDetail);
    }

    public ClassTest findClassTestById(String id){
        if(StringUtils.isNotEmpty (id)){
            return  classTestFeign.findClassTestById (id);
        }
        return null;
    }

    public List<ClassTest> findClassTestListByCondition(ClassTest classTest){
        return  classTestFeign.findClassTestListByCondition (classTest);
    }

    public  Long findClassTestCountByCondition(ClassTest classTest){
        return  classTestFeign.findClassTestCountByCondition (classTest);
    }

    public List<ClassTest> findListByCondition(ClassTest classTest){
        return classTestFeign.findListByCondition(classTest);
    }


    public void deleteClassTest(List<String> classId) {
        classTestFeign.deleteClassTest(classId);
    }
}
