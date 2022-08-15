package com.yice.edu.cn.osp.service.jw.locConfig;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.osp.service.dm.school.SchoolService;

@Service
public class LocConfigService {
    @Autowired
    private SchoolService schoolService;
    
    public School findSchoolById(String id) {
        return schoolService.findSchoolById(id);
    }

    public void updateSchool(School school) {
    	schoolService.updateSchool(school,school.getId());
    }
    
    public List<Integer> queryClockInRange(){
   	 return schoolService.queryClockInRange();
   }
}
