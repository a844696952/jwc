package com.yice.edu.cn.dy.service.schoolManage.institution;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesCustomTitle;
import com.yice.edu.cn.dy.dao.schoolManage.institution.IMesCustomTitleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MesCustomTitleService {
    @Autowired
    private IMesCustomTitleDao mesCustomTitleDao;
    @Autowired
    private SequenceId sequenceId;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public MesCustomTitle findMesCustomTitleById(String id) {
        return mesCustomTitleDao.findMesCustomTitleById(id);
    }

    @Transactional
    public void saveMesCustomTitle(MesCustomTitle mesCustomTitle) {
        mesCustomTitle.setId(sequenceId.nextId());
        mesCustomTitleDao.saveMesCustomTitle(mesCustomTitle);
    }

    @Transactional(readOnly = true)
    public List<MesCustomTitle> findMesCustomTitleListByCondition(MesCustomTitle mesCustomTitle) {
        return mesCustomTitleDao.findMesCustomTitleListByCondition(mesCustomTitle);
    }

    @Transactional(readOnly = true)
    public MesCustomTitle findOneMesCustomTitleByCondition(MesCustomTitle mesCustomTitle) {
        return mesCustomTitleDao.findOneMesCustomTitleByCondition(mesCustomTitle);
    }

    @Transactional(readOnly = true)
    public long findMesCustomTitleCountByCondition(MesCustomTitle mesCustomTitle) {
        return mesCustomTitleDao.findMesCustomTitleCountByCondition(mesCustomTitle);
    }

    @Transactional
    public void updateMesCustomTitle(MesCustomTitle mesCustomTitle) {
        mesCustomTitleDao.updateMesCustomTitle(mesCustomTitle);
    }

    @Transactional
    public void deleteMesCustomTitle(String id) {
        mesCustomTitleDao.deleteMesCustomTitle(id);
    }

    @Transactional
    public void deleteMesCustomTitleByCondition(MesCustomTitle mesCustomTitle) {
        mesCustomTitleDao.deleteMesCustomTitleByCondition(mesCustomTitle);
    }

    @Transactional
    public void batchSaveMesCustomTitle(List<MesCustomTitle> mesCustomTitles) {
        mesCustomTitles.forEach(mesCustomTitle -> mesCustomTitle.setId(sequenceId.nextId()));
        mesCustomTitleDao.batchSaveMesCustomTitle(mesCustomTitles);
    }
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
