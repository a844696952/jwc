package com.yice.edu.cn.dm.service.wb.classRegister;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.wb.classRegister.ClassRegister;
import com.yice.edu.cn.dm.dao.wb.classRegister.IClassRegisterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClassRegisterService {
    @Autowired
    private IClassRegisterDao classRegisterDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public ClassRegister findClassRegisterById(String id) {
        return classRegisterDao.findClassRegisterById(id);
    }
    @Transactional
    public void saveClassRegister(ClassRegister classRegister) {
        classRegister.setId(sequenceId.nextId());
        classRegisterDao.saveClassRegister(classRegister);
    }
    @Transactional(readOnly = true)
    public List<ClassRegister> findClassRegisterListByCondition(ClassRegister classRegister) {
        return classRegisterDao.findClassRegisterListByCondition(classRegister);
    }
    @Transactional(readOnly = true)
    public ClassRegister findOneClassRegisterByCondition(ClassRegister classRegister) {
        return classRegisterDao.findOneClassRegisterByCondition(classRegister);
    }
    @Transactional(readOnly = true)
    public long findClassRegisterCountByCondition(ClassRegister classRegister) {
        return classRegisterDao.findClassRegisterCountByCondition(classRegister);
    }
    @Transactional
    public void updateClassRegister(ClassRegister classRegister) {
        classRegisterDao.updateClassRegister(classRegister);
    }

    @Transactional
    public void updateClassRegisterStatus(ClassRegister classRegister) {
        classRegisterDao.updateClassRegisterStatus(classRegister);
    }
    @Transactional
    public void deleteClassRegister(String id) {
        classRegisterDao.deleteClassRegister(id);
    }
    @Transactional
    public void deleteClassRegisterByCondition(ClassRegister classRegister) {
        classRegisterDao.deleteClassRegisterByCondition(classRegister);
    }
    @Transactional
    public void batchSaveClassRegister(List<ClassRegister> classRegisters){
        classRegisters.forEach(classRegister -> classRegister.setId(sequenceId.nextId()));
        classRegisterDao.batchSaveClassRegister(classRegisters);
    }

    @Transactional(readOnly = true)
    public List<ClassRegister> findClassRegisterListByCondition2(ClassRegister classRegister) {
        return classRegisterDao.findClassRegisterListByCondition2(classRegister);
    }
    @Transactional(readOnly = true)
    public long findClassRegisterCountByCondition2(ClassRegister classRegister) {
        return classRegisterDao.findClassRegisterCountByCondition2(classRegister).size();
    }

}
