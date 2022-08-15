package com.yice.edu.cn.osp.service.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.osp.feignClient.jw.school.SchoolFeign;
import com.yice.edu.cn.osp.feignClient.login.LoginFeign;

@Service
public class LoginService {
    @Autowired
    private LoginFeign loginFeign;
    @Autowired
    private SchoolFeign schoolFeign;

    public Teacher login(Teacher teacher) {
        return loginFeign.login(teacher);
    }
    
    public Integer riseGradeStatus(String schoolId) {
    	return schoolFeign.prepareRise(schoolId);
    }

}
