package com.yice.edu.cn.ewb.service.classRegister;

import com.yice.edu.cn.common.pojo.wb.classRegister.ClassRegister;
import com.yice.edu.cn.common.util.studyTime.GetStudyTimeUtils;
import com.yice.edu.cn.ewb.feignClient.classRegister.ClassRegisterFeign;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClassRegisterService {
    @Autowired
    private ClassRegisterFeign classRegisterFeign;

    public ClassRegister findClassRegisterById(String id) {
        return classRegisterFeign.findClassRegisterById(id);
    }

    public ClassRegister saveClassRegister(ClassRegister classRegister) {
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        if(9<=month ||month>=12 || 1<=month ||month>=2 )
            classRegister.setTerm("上学期");
        classRegister.setTerm("下学期");
        classRegister.setStatus(1);
        classRegister.setSchoolYear(GetStudyTimeUtils.getStudyTime());
        return classRegisterFeign.saveClassRegister(classRegister);
    }

    public List<ClassRegister> findClassRegisterListByCondition(ClassRegister classRegister) {
        List<ClassRegister> li =classRegisterFeign.findClassRegisterListByCondition(classRegister);
        li.forEach(s->{
            if(StringUtils.isBlank(s.getEndTime())){
                s.setEndTime("  ");
            }
        });
        return li;
    }

    public ClassRegister findOneClassRegisterByCondition(ClassRegister classRegister) {
        return classRegisterFeign.findOneClassRegisterByCondition(classRegister);
    }

    public long findClassRegisterCountByCondition(ClassRegister classRegister) {
        return classRegisterFeign.findClassRegisterCountByCondition(classRegister);
    }

    public void updateClassRegister(ClassRegister classRegister) {
        classRegisterFeign.updateClassRegister(classRegister);
    }

    public void deleteClassRegister(String id) {
        classRegisterFeign.deleteClassRegister(id);
    }

    public void deleteClassRegisterByCondition(ClassRegister classRegister) {
        classRegisterFeign.deleteClassRegisterByCondition(classRegister);
    }
}
