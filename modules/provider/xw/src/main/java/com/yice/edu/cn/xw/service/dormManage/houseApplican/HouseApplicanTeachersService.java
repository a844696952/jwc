package com.yice.edu.cn.xw.service.dormManage.houseApplican;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.HouseApplicanTeachers;
import com.yice.edu.cn.xw.dao.dormManage.houseApplican.IHouseApplicanTeachersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HouseApplicanTeachersService {
    @Autowired
    private IHouseApplicanTeachersDao houseApplicanTeachersDao;
    @Autowired
    private SequenceId sequenceId;

    @Transactional(readOnly = true)
    public HouseApplicanTeachers findHouseApplicanTeachersById(String id) {
        return houseApplicanTeachersDao.findHouseApplicanTeachersById(id);
    }
    @Transactional
    public void saveHouseApplicanTeachers(HouseApplicanTeachers houseApplicanTeachers) {
        houseApplicanTeachers.setId(sequenceId.nextId());
        houseApplicanTeachersDao.saveHouseApplicanTeachers(houseApplicanTeachers);
    }
    @Transactional(readOnly = true)
    public List<HouseApplicanTeachers> findHouseApplicanTeachersListByCondition(HouseApplicanTeachers houseApplicanTeachers) {
        return houseApplicanTeachersDao.findHouseApplicanTeachersListByCondition(houseApplicanTeachers);
    }
    @Transactional(readOnly = true)
    public HouseApplicanTeachers findOneHouseApplicanTeachersByCondition(HouseApplicanTeachers houseApplicanTeachers) {
        return houseApplicanTeachersDao.findOneHouseApplicanTeachersByCondition(houseApplicanTeachers);
    }
    @Transactional(readOnly = true)
    public long findHouseApplicanTeachersCountByCondition(HouseApplicanTeachers houseApplicanTeachers) {
        return houseApplicanTeachersDao.findHouseApplicanTeachersCountByCondition(houseApplicanTeachers);
    }
    @Transactional
    public void updateHouseApplicanTeachers(HouseApplicanTeachers houseApplicanTeachers) {
        houseApplicanTeachersDao.updateHouseApplicanTeachers(houseApplicanTeachers);
    }
    @Transactional
    public void deleteHouseApplicanTeachers(String id) {
        houseApplicanTeachersDao.deleteHouseApplicanTeachers(id);
    }
    @Transactional
    public void deleteHouseApplicanTeachersByCondition(HouseApplicanTeachers houseApplicanTeachers) {
        houseApplicanTeachersDao.deleteHouseApplicanTeachersByCondition(houseApplicanTeachers);
    }
    @Transactional
    public void batchSaveHouseApplicanTeachers(List<HouseApplicanTeachers> houseApplicanTeacherss){
        houseApplicanTeacherss.forEach(houseApplicanTeachers -> houseApplicanTeachers.setId(sequenceId.nextId()));
        houseApplicanTeachersDao.batchSaveHouseApplicanTeachers(houseApplicanTeacherss);
    }

}
