package com.yice.edu.cn.dy.service.schoolManage.institution;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesTimeStatus;
import com.yice.edu.cn.dy.dao.schoolManage.institution.IMesTimeStatusDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MesTimeStatusService {
    @Autowired
    private IMesTimeStatusDao mesTimeStatusDao;
    @Autowired
    private SequenceId sequenceId;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public MesTimeStatus findMesTimeStatusById(String id) {
        return mesTimeStatusDao.findMesTimeStatusById(id);
    }

    @Transactional
    public void saveMesTimeStatus(MesTimeStatus mesTimeStatus) {
        mesTimeStatus.setId(sequenceId.nextId());
        mesTimeStatusDao.saveMesTimeStatus(mesTimeStatus);
    }

    @Transactional(readOnly = true)
    public List<MesTimeStatus> findMesTimeStatusListByCondition(MesTimeStatus mesTimeStatus) {
        return mesTimeStatusDao.findMesTimeStatusListByCondition(mesTimeStatus);
    }

    @Transactional(readOnly = true)
    public MesTimeStatus findOneMesTimeStatusByCondition(MesTimeStatus mesTimeStatus) {
        return mesTimeStatusDao.findOneMesTimeStatusByCondition(mesTimeStatus);
    }

    @Transactional(readOnly = true)
    public long findMesTimeStatusCountByCondition(MesTimeStatus mesTimeStatus) {
        return mesTimeStatusDao.findMesTimeStatusCountByCondition(mesTimeStatus);
    }

    @Transactional
    public void updateMesTimeStatus(MesTimeStatus mesTimeStatus) {
        mesTimeStatusDao.updateMesTimeStatus(mesTimeStatus);
    }

    @Transactional
    public void deleteMesTimeStatus(String id) {
        mesTimeStatusDao.deleteMesTimeStatus(id);
    }

    @Transactional
    public void deleteMesTimeStatusByCondition(MesTimeStatus mesTimeStatus) {
        mesTimeStatusDao.deleteMesTimeStatusByCondition(mesTimeStatus);
    }

    @Transactional
    public void batchSaveMesTimeStatus(List<MesTimeStatus> mesTimeStatuss) {
        mesTimeStatuss.forEach(mesTimeStatus -> mesTimeStatus.setId(sequenceId.nextId()));
        mesTimeStatusDao.batchSaveMesTimeStatus(mesTimeStatuss);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
