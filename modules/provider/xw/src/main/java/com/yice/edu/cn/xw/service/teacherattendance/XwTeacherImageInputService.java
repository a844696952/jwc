package com.yice.edu.cn.xw.service.teacherattendance;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.teacherattendance.XwTeacherImageInput;
import com.yice.edu.cn.xw.dao.teacherattendance.IXwTeacherImageInputDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class XwTeacherImageInputService {
    @Autowired
    private IXwTeacherImageInputDao xwTeacherImageInputDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public XwTeacherImageInput findXwTeacherImageInputById(String id) {
        return xwTeacherImageInputDao.findXwTeacherImageInputById(id);
    }

    @Transactional(readOnly = true)
    public List<XwTeacherImageInput> findXwTeacherImageInputByTeacherId(String teacherId, String schoolId) {
        return xwTeacherImageInputDao.findXwTeacherImageInputByTeacherId(teacherId,schoolId);
    }

    @Transactional
    public void saveXwTeacherImageInput(XwTeacherImageInput xwTeacherImageInput) {
        xwTeacherImageInput.setId(sequenceId.nextId());
        xwTeacherImageInputDao.saveXwTeacherImageInput(xwTeacherImageInput);
    }

    @Transactional(readOnly = true)
    public List<XwTeacherImageInput> findXwTeacherImageInputListByCondition(XwTeacherImageInput xwTeacherImageInput) {
        return xwTeacherImageInputDao.findXwTeacherImageInputListByCondition(xwTeacherImageInput);
    }

    @Transactional(readOnly = true)
    public XwTeacherImageInput findOneXwTeacherImageInputByCondition(XwTeacherImageInput xwTeacherImageInput) {
        return xwTeacherImageInputDao.findOneXwTeacherImageInputByCondition(xwTeacherImageInput);
    }

    @Transactional(readOnly = true)
    public long findXwTeacherImageInputCountByCondition(XwTeacherImageInput xwTeacherImageInput) {
        return xwTeacherImageInputDao.findXwTeacherImageInputCountByCondition(xwTeacherImageInput);
    }

    @Transactional
    public void updateXwTeacherImageInput(XwTeacherImageInput xwTeacherImageInput) {
        xwTeacherImageInputDao.updateXwTeacherImageInputTeahcer(xwTeacherImageInput);
    }

    @Transactional
    public void deleteXwTeacherImageInput(String id) {
        xwTeacherImageInputDao.deleteXwTeacherImageInput(id);
    }

    @Transactional
    public void deleteXwTeacherImageInputByCondition(XwTeacherImageInput xwTeacherImageInput) {
        xwTeacherImageInputDao.deleteXwTeacherImageInputByCondition(xwTeacherImageInput);
    }

    @Transactional
    public void batchSaveXwTeacherImageInput(List<XwTeacherImageInput> xwTeacherImageInputs) {
        xwTeacherImageInputs.forEach(xwTeacherImageInput -> xwTeacherImageInput.setId(sequenceId.nextId()));
        xwTeacherImageInputDao.batchSaveXwTeacherImageInput(xwTeacherImageInputs);
    }

    @Transactional
    public List<XwTeacherImageInput> findXwTeacherImageInputListAlls(XwTeacherImageInput xwTeacherImageInput) {
        return xwTeacherImageInputDao.findXwTeacherImageInputListAlls(xwTeacherImageInput);
    }

    @Transactional
    public long findXwTeacherImageInputListAllCount(String schoolId,int status) {
        return xwTeacherImageInputDao.findXwTeacherImageInputListAllCount(schoolId,status);
    }


    @Transactional
    public List<XwTeacherImageInput> findXwTeacherImageInputByTeacherName(String teacherName, String schoolId) {
        return xwTeacherImageInputDao.findXwTeacherImageInputByTeacherName(teacherName, schoolId);
    }

    @Transactional
    public long findXwTeacherImageInputByTeacherNameCount(String teacherName, String schoolId) {
        return xwTeacherImageInputDao.findXwTeacherImageInputByTeacherNameCount(teacherName, schoolId);
    }

    @Transactional
    public List<String> findXwTeacherleft(String schoolId) {
        return xwTeacherImageInputDao.findXwTeacherleft(schoolId);
    }
}
