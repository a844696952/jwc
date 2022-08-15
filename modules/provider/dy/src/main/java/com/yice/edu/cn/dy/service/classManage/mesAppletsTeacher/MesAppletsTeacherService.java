package com.yice.edu.cn.dy.service.classManage.mesAppletsTeacher;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.mes.classManage.mesAppletsTeacher.MesAppletsTeacher;
import com.yice.edu.cn.dy.dao.classManage.mesAppletsTeacher.IMesAppletsTeacherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MesAppletsTeacherService {
    @Autowired
    private IMesAppletsTeacherDao mesAppletsTeacherDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
@Transactional(readOnly = true, rollbackFor = Exception.class)
    public MesAppletsTeacher findMesAppletsTeacherById(String id) {
        return mesAppletsTeacherDao.findMesAppletsTeacherById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveMesAppletsTeacher(MesAppletsTeacher mesAppletsTeacher) {
        mesAppletsTeacher.setId(sequenceId.nextId());
        mesAppletsTeacher.setCreateTime(DateUtil.now());
        mesAppletsTeacherDao.saveMesAppletsTeacher(mesAppletsTeacher);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public List<MesAppletsTeacher> findMesAppletsTeacherListByCondition(MesAppletsTeacher mesAppletsTeacher) {
        return mesAppletsTeacherDao.findMesAppletsTeacherListByCondition(mesAppletsTeacher);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public MesAppletsTeacher findOneMesAppletsTeacherByCondition(MesAppletsTeacher mesAppletsTeacher) {
        return mesAppletsTeacherDao.findOneMesAppletsTeacherByCondition(mesAppletsTeacher);
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public long findMesAppletsTeacherCountByCondition(MesAppletsTeacher mesAppletsTeacher) {
        return mesAppletsTeacherDao.findMesAppletsTeacherCountByCondition(mesAppletsTeacher);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateMesAppletsTeacher(MesAppletsTeacher mesAppletsTeacher) {
        mesAppletsTeacherDao.updateMesAppletsTeacher(mesAppletsTeacher);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteMesAppletsTeacher(String id) {
        mesAppletsTeacherDao.deleteMesAppletsTeacher(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteMesAppletsTeacherByCondition(MesAppletsTeacher mesAppletsTeacher) {
        mesAppletsTeacherDao.deleteMesAppletsTeacherByCondition(mesAppletsTeacher);
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchSaveMesAppletsTeacher(List<MesAppletsTeacher> mesAppletsTeachers){
        mesAppletsTeacherDao.batchSaveMesAppletsTeacher(mesAppletsTeachers);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
