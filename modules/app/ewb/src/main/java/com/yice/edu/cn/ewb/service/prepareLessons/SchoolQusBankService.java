package com.yice.edu.cn.ewb.service.prepareLessons;

import com.yice.edu.cn.common.pojo.jw.qusBankResource.ResourceVo;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.SchoolQusBank;
import com.yice.edu.cn.common.pojo.jy.topics.TopicParam;
import com.yice.edu.cn.ewb.feignClient.prepareLessons.SchoolQusBankFeign;
import com.yice.edu.cn.ewb.feignClient.topics.TopicsFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yice.edu.cn.ewb.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.ewb.interceptor.LoginInterceptor.mySchoolId;

@Service
public class SchoolQusBankService {

    @Autowired
    private SchoolQusBankFeign schoolQusBankFeign;

    @Autowired
    private TopicsFeign topicsFeign;

    public SchoolQusBank findSchoolQusBankById(String id) {
        return schoolQusBankFeign.findSchoolQusBankById(id);
    }

    public SchoolQusBank saveSchoolQusBank(SchoolQusBank schoolQusBank) {
        return schoolQusBankFeign.saveSchoolQusBank(schoolQusBank);
    }

    public List<SchoolQusBank> findSchoolQusBankListByCondition(SchoolQusBank schoolQusBank) {
        return schoolQusBankFeign.findSchoolQusBankListByCondition(schoolQusBank);
    }

    public SchoolQusBank findOneSchoolQusBankByCondition(SchoolQusBank schoolQusBank) {
        return schoolQusBankFeign.findOneSchoolQusBankByCondition(schoolQusBank);
    }

    public long findSchoolQusBankCountByCondition(SchoolQusBank schoolQusBank) {
        return schoolQusBankFeign.findSchoolQusBankCountByCondition(schoolQusBank);
    }

    public void updateSchoolQusBank(SchoolQusBank schoolQusBank) {
        schoolQusBankFeign.updateSchoolQusBank(schoolQusBank);
    }

    public void deleteSchoolQusBank(String id) {
        schoolQusBankFeign.deleteSchoolQusBank(id);
    }

    public void deleteSchoolQusBankByCondition(SchoolQusBank schoolQusBank) {
        schoolQusBankFeign.deleteSchoolQusBankByCondition(schoolQusBank);
    }

    public Object findTopicDetail(ResourceVo resourceVo) {
        TopicParam topicParam = new TopicParam().setId(resourceVo.getTempId())
                .setSchoolId(mySchoolId())
                .setSource(resourceVo.getPlatform())
                .setTeacherId(myId());
        return topicsFeign.findTopicDetail(topicParam);
    }
}
