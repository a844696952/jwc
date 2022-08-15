package com.yice.edu.cn.dm.service.dmStudentAttendant;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.dmMobileRedBanner.DmMobileRedBanner;
import com.yice.edu.cn.common.pojo.dm.dmMobileRedBanner.DmMobileRedBannerList;
import com.yice.edu.cn.common.pojo.dm.dmStudentAttendant.DmStudentAttendant;
import com.yice.edu.cn.common.pojo.dm.dmStudentAttendant.DmStudentAttendantList;
import com.yice.edu.cn.dm.dao.dmStudentAttendant.IDmStudentAttendantDao;
import com.yice.edu.cn.dm.dao.dmStudentAttendant.IDmStudentAttendantListDao;
import com.yice.edu.cn.dm.dao.honourRoll.DmHonourRollDao;
import com.yice.edu.cn.dm.service.dmMobileRedBanner.DmMobileRedBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DmStudentAttendantService {
    @Autowired
    private IDmStudentAttendantDao dmStudentAttendantDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private DmHonourRollDao dmHonourRollDao;
    @Autowired
    private IDmStudentAttendantListDao dmStudentAttendantListDao;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public DmStudentAttendant findDmStudentAttendantById(String id) {
        DmStudentAttendant dmStudentAttendant = dmStudentAttendantDao.findDmStudentAttendantById(id);
        if(dmStudentAttendant == null){
            return dmStudentAttendant;
        }
        dmStudentAttendant.setStudentList(dmStudentAttendant.getStudentIds().split(","));
        return dmStudentAttendant;
    }

    /**
     * 1.先保存值日生表；2.再保存值日生学生列表
     * @param dmStudentAttendant
     */
    @Transactional
    public void saveDmStudentAttendant(DmStudentAttendant dmStudentAttendant) {
        //保存值日生表
        String studentAttendantId = sequenceId.nextId();
        dmStudentAttendant.setId(studentAttendantId);
        String[] studentList = dmStudentAttendant.getStudentList();
        dmStudentAttendant.setStudentNames(dmHonourRollDao.getStudentName(studentList));
        dmStudentAttendant.setStudentIds(DmMobileRedBannerService.transArrayToString(studentList));
        dmStudentAttendantDao.saveDmStudentAttendant(dmStudentAttendant);
        //保存值日生学生表
        List<DmStudentAttendantList> list = bulidStudentAttentantListByStudentIds(studentList,studentAttendantId,dmStudentAttendant.getSchoolId());
        dmStudentAttendantListDao.batchSaveDmStudentAttendantList(list);

    }
    @Transactional(readOnly = true)
    public List<DmStudentAttendant> findDmStudentAttendantListByCondition(DmStudentAttendant dmStudentAttendant) {
        List<DmStudentAttendant> studentAttendantList = dmStudentAttendantDao.findDmStudentAttendantListByCondition(dmStudentAttendant);
        studentAttendantList.forEach(item-> item.setStudentList(item.getStudentIds().split(",")));
        return studentAttendantList;
    }
    @Transactional(readOnly = true)
    public DmStudentAttendant findOneDmStudentAttendantByCondition(DmStudentAttendant dmStudentAttendant) {
        DmStudentAttendant record = dmStudentAttendantDao.findOneDmStudentAttendantByCondition(dmStudentAttendant);
        if(record == null){
            return dmStudentAttendant;
        }
        record.setStudentList(record.getStudentIds().split(","));
        return record;
    }
    @Transactional(readOnly = true)
    public long findDmStudentAttendantCountByCondition(DmStudentAttendant dmStudentAttendant) {
        return dmStudentAttendantDao.findDmStudentAttendantCountByCondition(dmStudentAttendant);
    }

    /**
     * 编辑值日生：1.编辑值日生表；2.编辑值日生学生列表
     * @param dmStudentAttendant
     */
    @Transactional
    public void updateDmStudentAttendant(DmStudentAttendant dmStudentAttendant) {
        //1.更新值日生表
        String[] studentList = dmStudentAttendant.getStudentList();
        dmStudentAttendant.setStudentNames(dmHonourRollDao.getStudentName(studentList));
        dmStudentAttendant.setStudentIds(DmMobileRedBannerService.transArrayToString(studentList));
        dmStudentAttendantDao.updateDmStudentAttendant(dmStudentAttendant);
        //2.更新值日生学生表
        DmStudentAttendantList dmStudentAttendantList = new DmStudentAttendantList();
        String studentAttendantId = dmStudentAttendant.getId();
        dmStudentAttendantList.setStudentAttendantId(studentAttendantId);
        dmStudentAttendantListDao.deleteDmStudentAttendantListByCondition(dmStudentAttendantList);

        List<DmStudentAttendantList> list = bulidStudentAttentantListByStudentIds(studentList,studentAttendantId,dmStudentAttendant.getSchoolId());
        dmStudentAttendantListDao.batchSaveDmStudentAttendantList(list);
    }
    @Transactional
    public void updateDmStudentAttendantForNotNull(DmStudentAttendant dmStudentAttendant) {
        dmStudentAttendantDao.updateDmStudentAttendantForNotNull(dmStudentAttendant);
    }

    /**
     * 删除值日生表时，先删除值日生表，再删除值日生学生
     * @param id
     */
    @Transactional
    public void deleteDmStudentAttendant(String id) {
        dmStudentAttendantDao.deleteDmStudentAttendant(id);

        DmStudentAttendantList dmStudentAttendantList = new DmStudentAttendantList();
        dmStudentAttendantList.setStudentAttendantId(id);
        dmStudentAttendantListDao.deleteDmStudentAttendantListByCondition(dmStudentAttendantList);
    }
    @Transactional
    public void deleteDmStudentAttendantByCondition(DmStudentAttendant dmStudentAttendant) {
        dmStudentAttendantDao.deleteDmStudentAttendantByCondition(dmStudentAttendant);
    }
    @Transactional
    public void batchSaveDmStudentAttendant(List<DmStudentAttendant> dmStudentAttendants){
        dmStudentAttendants.forEach(dmStudentAttendant -> dmStudentAttendant.setId(sequenceId.nextId()));
        dmStudentAttendantDao.batchSaveDmStudentAttendant(dmStudentAttendants);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    /**
     * studentAttentantId 生成值日生学生列表
     * @param studentIds
     * @param studentAttentantId
     * @param schoolId
     * @return
     */
    private List<DmStudentAttendantList> bulidStudentAttentantListByStudentIds(String[] studentIds, String studentAttentantId,String schoolId){
        List<DmStudentAttendantList> list = new ArrayList<>();
        for (String studentId : studentIds) {
            DmStudentAttendantList dmStudentAttendantList = new DmStudentAttendantList();
            dmStudentAttendantList.setId(sequenceId.nextId());
            dmStudentAttendantList.setStudentId(studentId);
            dmStudentAttendantList.setStudentAttendantId(studentAttentantId);
            dmStudentAttendantList.setSchoolId(schoolId);
            list.add(dmStudentAttendantList);
        }
        return list;
    }

    @Transactional
    public void deleteDmStudentAttendantByClassIds(List<String> clazzIds) {
        dmStudentAttendantDao.deleteDmStudentAttendantByClassIds(clazzIds);
        dmStudentAttendantListDao.deleteDmStudentAttendantByClassIds(clazzIds);
    }
}
