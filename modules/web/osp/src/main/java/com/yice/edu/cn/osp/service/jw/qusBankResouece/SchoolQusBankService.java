package com.yice.edu.cn.osp.service.jw.qusBankResouece;

import cn.hutool.core.bean.BeanUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.PersonalTopics;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.SchoolQusBank;
import com.yice.edu.cn.common.pojo.jy.resources.JySchoolResourcesByDay;
import com.yice.edu.cn.osp.feignClient.jw.qusBankResource.PersonalTopicsFeign;
import com.yice.edu.cn.osp.feignClient.jw.qusBankResource.SchoolQusBankFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;

@Service
public class SchoolQusBankService {
    @Autowired
    private SchoolQusBankFeign schoolQusBankFeign;
    @Autowired
    private PersonalTopicsFeign personalTopicsFeign;

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
    public List<JySchoolResourcesByDay> findSchoolQusNumByCreateTimeZone(SchoolQusBank schoolQusBank){
       return schoolQusBankFeign.findSchoolQusNumByCreateTimeZone(schoolQusBank);
    }

    public ResponseJson copyTopicToPersonalTopics(String id) {
        SchoolQusBank schoolQusBank = this.findSchoolQusBankById(id);
        if(schoolQusBank==null){
            return new ResponseJson(false,201,"题目不存在");
        }else{
            //复制到我的题库
            PersonalTopics personalTopics = new PersonalTopics();
            BeanUtil.copyProperties(schoolQusBank,personalTopics);
            personalTopics.setTeacherId(myId());
            personalTopicsFeign.copyTopicToPersonalTopics(personalTopics);
            return new ResponseJson();
        }
    }
}
