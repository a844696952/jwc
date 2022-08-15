package com.yice.edu.cn.osp.service.wb.classRegister;

import com.yice.edu.cn.common.pojo.wb.classRegister.ClassRegister;
import com.yice.edu.cn.osp.feignClient.wb.classRegister.ClassRegisterFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassRegisterService {
    @Autowired
    private ClassRegisterFeign classRegisterFeign;

    public ClassRegister findClassRegisterById(String id) {
        return classRegisterFeign.findClassRegisterById(id);
    }

    public ClassRegister saveClassRegister(ClassRegister classRegister) {
        return classRegisterFeign.saveClassRegister(classRegister);
    }

    public List<ClassRegister> findClassRegisterListByCondition(ClassRegister classRegister) {
        return classRegisterFeign.findClassRegisterListByCondition(classRegister);
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
    public List<ClassRegister> findClassRegisterListByCondition2(ClassRegister classRegister) {
        return classRegisterFeign.findClassRegisterListByCondition2(classRegister);
    }
    public long findClassRegisterCountByCondition2(ClassRegister classRegister) {
        return classRegisterFeign.findClassRegisterCountByCondition2(classRegister);
    }
}
