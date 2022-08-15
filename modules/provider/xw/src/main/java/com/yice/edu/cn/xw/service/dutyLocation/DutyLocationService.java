package com.yice.edu.cn.xw.service.dutyLocation;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.checkedDetail.CheckedDetail;
import com.yice.edu.cn.common.pojo.xw.dutyLocation.DutyLocation;
import com.yice.edu.cn.xw.dao.checkedDetail.ICheckedDetailDao;
import com.yice.edu.cn.xw.dao.dutyLocation.IDutyLocationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DutyLocationService {
    @Autowired
    private IDutyLocationDao dutyLocationDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private ICheckedDetailDao checkedDetailDao;

    @Transactional(readOnly = true)
    public DutyLocation findDutyLocationById(String id) {
        return dutyLocationDao.findDutyLocationById(id);
    }
    @Transactional
    public void saveDutyLocation(DutyLocation dutyLocation) {
        //新增地址判重
        DutyLocation dutyLocation1 = new DutyLocation();
        dutyLocation1.setPlace(dutyLocation.getPlace());
        dutyLocation1.setSchoolId(dutyLocation.getSchoolId());
        long count = dutyLocationDao.findDutyLocationCountByCondition(dutyLocation1);
        if(count>0){
            dutyLocation.setCode("204");
            dutyLocation.setMsg("值班地点已存在");
            return;
        }
        dutyLocation.setId(sequenceId.nextId());
        dutyLocationDao.saveDutyLocation(dutyLocation);
    }
    @Transactional(readOnly = true)
    public List<DutyLocation> findDutyLocationListByCondition(DutyLocation dutyLocation) {
        return dutyLocationDao.findDutyLocationListByCondition(dutyLocation);
    }
    @Transactional(readOnly = true)
    public DutyLocation findOneDutyLocationByCondition(DutyLocation dutyLocation) {
        return dutyLocationDao.findOneDutyLocationByCondition(dutyLocation);
    }
    @Transactional(readOnly = true)
    public long findDutyLocationCountByCondition(DutyLocation dutyLocation) {
        return dutyLocationDao.findDutyLocationCountByCondition(dutyLocation);
    }
    @Transactional
    public void updateDutyLocation(DutyLocation dutyLocation) {
        dutyLocationDao.updateDutyLocation(dutyLocation);
    }
    @Transactional
    public void deleteDutyLocation(String id) {
        dutyLocationDao.deleteDutyLocation(id);
    }
    @Transactional
    public void deleteDutyLocationByCondition(DutyLocation dutyLocation) {
        CheckedDetail checkedDetail1 = new CheckedDetail();
        checkedDetail1.setSchoolId(dutyLocation.getSchoolId());
        checkedDetail1.setSpaceId(dutyLocation.getId());
        long count = checkedDetailDao.findCheckedDetailCountByCondition(checkedDetail1);
        if(count>0){
            dutyLocation.setCode("204");
            dutyLocation.setMsg("地点有值班安排，不允许删除");
            return;
        }
        dutyLocationDao.deleteDutyLocationByCondition(dutyLocation);
    }
    @Transactional
    public void batchSaveDutyLocation(List<DutyLocation> dutyLocations){
        dutyLocations.forEach(dutyLocation -> dutyLocation.setId(sequenceId.nextId()));
        dutyLocationDao.batchSaveDutyLocation(dutyLocations);
    }

}
