package com.yice.edu.cn.xw.service.dormManage.houseApplican;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.HouseApplicanFiles;
import com.yice.edu.cn.xw.dao.dormManage.houseApplican.IHouseApplicanFilesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HouseApplicanFilesService {
    @Autowired
    private IHouseApplicanFilesDao houseApplicanFilesDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public HouseApplicanFiles findHouseApplicanFilesById(String id) {
        return houseApplicanFilesDao.findHouseApplicanFilesById(id);
    }
    @Transactional
    public void saveHouseApplicanFiles(HouseApplicanFiles houseApplicanFiles) {
        houseApplicanFiles.setId(sequenceId.nextId());
        houseApplicanFilesDao.saveHouseApplicanFiles(houseApplicanFiles);
    }
    @Transactional(readOnly = true)
    public List<HouseApplicanFiles> findHouseApplicanFilesListByCondition(HouseApplicanFiles houseApplicanFiles) {
        return houseApplicanFilesDao.findHouseApplicanFilesListByCondition(houseApplicanFiles);
    }
    @Transactional(readOnly = true)
    public HouseApplicanFiles findOneHouseApplicanFilesByCondition(HouseApplicanFiles houseApplicanFiles) {
        return houseApplicanFilesDao.findOneHouseApplicanFilesByCondition(houseApplicanFiles);
    }
    @Transactional(readOnly = true)
    public long findHouseApplicanFilesCountByCondition(HouseApplicanFiles houseApplicanFiles) {
        return houseApplicanFilesDao.findHouseApplicanFilesCountByCondition(houseApplicanFiles);
    }
    @Transactional
    public void updateHouseApplicanFiles(HouseApplicanFiles houseApplicanFiles) {
        houseApplicanFilesDao.updateHouseApplicanFiles(houseApplicanFiles);
    }
    @Transactional
    public void deleteHouseApplicanFiles(String id) {
        houseApplicanFilesDao.deleteHouseApplicanFiles(id);
    }
    @Transactional
    public void deleteHouseApplicanFilesByCondition(HouseApplicanFiles houseApplicanFiles) {
        houseApplicanFilesDao.deleteHouseApplicanFilesByCondition(houseApplicanFiles);
    }
    @Transactional
    public void batchSaveHouseApplicanFiles(List<HouseApplicanFiles> houseApplicanFiless){
        houseApplicanFiless.forEach(houseApplicanFiles -> houseApplicanFiles.setId(sequenceId.nextId()));
        houseApplicanFilesDao.batchSaveHouseApplicanFiles(houseApplicanFiless);
    }

}
