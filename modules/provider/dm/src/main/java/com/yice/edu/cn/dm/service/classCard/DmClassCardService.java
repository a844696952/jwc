package com.yice.edu.cn.dm.service.classCard;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.classCard.ClassCardLock;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.classCard.DmDeleteData;
import com.yice.edu.cn.common.pojo.dm.classCard.DmTimedTask;
import com.yice.edu.cn.dm.dao.classCard.IDmClassCardDao;
import com.yice.edu.cn.dm.dao.dmMobileRedBanner.IDmMobileRedBannerListDao;
import com.yice.edu.cn.dm.dao.dmStudentAttendant.IDmStudentAttendantDao;
import com.yice.edu.cn.dm.dao.dmStudentAttendant.IDmStudentAttendantListDao;
import com.yice.edu.cn.dm.dao.dmTimedTask.IDmTimedTaskDao;
import com.yice.edu.cn.dm.dao.honourRoll.DmHonourRollDao;
import com.yice.edu.cn.dm.dao.honourRoll.DmHonourRollStudentDao;
import com.yice.edu.cn.dm.dao.parentMsg.IParentmsgDao;
import com.yice.edu.cn.dm.dao.wb.groupManage.IGroupManageDao;
import com.yice.edu.cn.dm.dao.wb.groupManage.IStudentGroupDao;
import com.yice.edu.cn.dm.dao.wb.studentAccount.IStudentAccountDao;
import com.yice.edu.cn.dm.service.classes.DmClassDescService;
import com.yice.edu.cn.dm.service.dmMobileRedBanner.DmMobileRedBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DmClassCardService {
    @Autowired
    private IDmClassCardDao dmClassCardDao;
    @Autowired
    private IDmTimedTaskDao dmTimedTaskDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private DmHonourRollDao dmHonourRollDao;
    @Autowired
    private DmHonourRollStudentDao dmHonourRollStudentDao;
    @Autowired
    private IDmStudentAttendantDao dmStudentAttendantDao;
    @Autowired
    private IDmStudentAttendantListDao dmStudentAttendantListDao;
    @Autowired
    private DmClassDescService dmClassDescService;
    @Autowired
    private IParentmsgDao parentmsgDao;

    @Autowired
    private IDmMobileRedBannerListDao dmMobileRedBannerListDao;
    @Autowired
    private DmMobileRedBannerService dmMobileRedBannerService;
    @Autowired
    private IStudentGroupDao studentGroupDao;
    @Autowired
    private IGroupManageDao groupManageDao;
    @Autowired
    private IStudentAccountDao studentAccountDao;

    @Transactional(readOnly = true)
    public DmClassCard findDmClassCardById(String id) {
        DmClassCard dmClassCard = dmClassCardDao.findDmClassCardById(id);
        //通过修改设备时的 设备id 查询出设备的定时任务数据
            DmTimedTask dmTimedTask = dmTimedTaskDao.findDmTimedTaskByEquipmentId(dmClassCard.getUserName());
            dmClassCard.setDmTimedTaskForm(dmTimedTask!=null?dmTimedTask:new DmTimedTask());
        return dmClassCard;
    }

    @Transactional
    public void saveDmClassCard(DmClassCard dmClassCard) {
        dmClassCard.setId(sequenceId.nextId());
        dmClassCardDao.saveDmClassCard(dmClassCard);
    }

    @Transactional(readOnly = true)
    public List<DmClassCard> findDmClassCardListByCondition(DmClassCard dmClassCard) {
        return dmClassCardDao.findDmClassCardListByCondition(dmClassCard);
    }

    @Transactional(readOnly = true)
    public DmClassCard findOneDmClassCardByCondition(DmClassCard dmClassCard) {
        return dmClassCardDao.findOneDmClassCardByCondition(dmClassCard);
    }

    @Transactional(readOnly = true)
    public long findDmClassCardCountByCondition(DmClassCard dmClassCard) {
        return dmClassCardDao.findDmClassCardCountByCondition(dmClassCard);
    }

    @Transactional
    public void updateDmClassCard(DmClassCard dmClassCard) {
        dmClassCardDao.updateDmClassCard(dmClassCard);
    }

    @Transactional
    public void deleteDmClassCard(String id) {
        dmClassCardDao.deleteDmClassCard(id);
    }

    @Transactional
    public void deleteDmClassCardByCondition(DmClassCard dmClassCard) {
        dmClassCardDao.deleteDmClassCardByCondition(dmClassCard);
    }

    @Transactional
    public void batchSaveDmClassCard(List<DmClassCard> dmClassCards) {
        dmClassCardDao.batchSaveDmClassCard(dmClassCards);
    }

    //查询班级是否已经绑定设备（云班牌)
    @Transactional(readOnly = true)
    public List<DmClassCard> findDmClassCardListByclassId(DmClassCard dmClassCard) {
        return dmClassCardDao.findDmClassCardListByclassId(dmClassCard);
    }

    //批量解绑
    @Transactional
    public void relieveDmClassCardAll(String[] rowData) {
        dmClassCardDao.relieveDmClassCardAll(rowData);
    }

    //根据条件查找列表进行导出xls
    @Transactional(readOnly = true)
    public List<DmClassCard> findDmClassCardToXls(DmClassCard dmClassCard) {
        return dmClassCardDao.findDmClassCardToXls(dmClassCard);
    }

    //查询账号密码
    @Transactional(readOnly = true)
    public List<DmClassCard> findDmClassCardUser(DmClassCard dmClassCard) {
        return dmClassCardDao.findDmClassCardUser(dmClassCard);
    }

    //设置设备状态和 版本号
    @Transactional
    public void dmClassCardStatus(DmClassCard dmClassCard) {
        dmClassCardDao.dmClassCardStatus(dmClassCard);
    }

    @Transactional
    public void updateEquipmentName(DmClassCard dmClassCard) {
        dmClassCardDao.updateEquipmentName(dmClassCard);
    }

    @Transactional
    public void setVersionAll(DmTimedTask dmTimedTask) {
        dmClassCardDao.setVersionAll(dmTimedTask);
    }

    @Transactional
    public void batchChangeLockStatusByIds(ClassCardLock classCardLock) {
        dmClassCardDao.batchChangeLockStatusByIds(classCardLock);
    }

    @Transactional
    public void lockDmScreen(String id) {
        dmClassCardDao.lockDmScreen(id);
    }

    @Transactional
    public void unLockDmScreen(String id) {
        dmClassCardDao.unLockDmScreen(id);
    }
    @Transactional
    public List<DmClassCard> getDmClassCardByTeacherId(String id,String lockStatus) {
        return dmClassCardDao.getDmClassCardByTeacherId(id,lockStatus);
    }

    @Transactional
    public void updateDmClassManage(DmClassCard mClassCard) {
        dmClassCardDao.updateDmClassManage(mClassCard);
    }

    @Transactional
    public List<DmClassCard> getDmClassCardTree(DmClassCard dmClassCard) {
        return dmClassCardDao.getDmClassCardTree(dmClassCard);
    }
    @Transactional
    public   List<DmClassCard> findDmClassCardIdByTeacherId(DmClassCard dmClassCard) {
        return dmClassCardDao.findDmClassCardIdByTeacherId(dmClassCard);
    }

    @Transactional
    public void cleraDmClassManage(DmClassCard mClassCard) {
        dmClassCardDao.cleraDmClassManage(mClassCard);
    }

    @Transactional
    public   DmClassCard findDmClassCardByStudentId(String studentId) {
        return dmClassCardDao.findDmClassCardByStudentId(studentId);
    }

    @Transactional
    public List<String> findUserNamesBySchoolId(String schoolId){
        return dmClassCardDao.findUserNamesBySchoolId(schoolId);
    }

    @Transactional(readOnly = true)
    public DmClassCard selectSchoolByUserName(String userName){
        return dmClassCardDao.selectSchoolByUserName(userName);
    }

    @Transactional(readOnly = true)
    public DmClassCard findDmClassMsgCardById(String id) {
        return dmClassCardDao.findDmClassCardById(id);
    }
    
    @Transactional
    public void clearDmAndClazzRelateBySchoolId(DmClassCard dmClassCard) {
    	dmClassCardDao.clearDmAndClazzRelateBySchoolId(dmClassCard);
    };
    
    /**
     * 删升班所要删除的数据
     * @param dmDeleteData
     * 2、删除值日生数据
     * 3、删除班级信息，相册，视频
     * 4.删除流动红旗
     * 5.删除班级小组信息
     * 6.删除学生账号信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteDmData(DmDeleteData dmDeleteData) {
        //删除值日生
        dmStudentAttendantDao.deleteDmStudentAttendantByClassIds(dmDeleteData.getClassIdList());
        dmStudentAttendantListDao.deleteDmStudentAttendantByClassIds(dmDeleteData.getClassIdList());

        //删除流动红旗
        dmMobileRedBannerService.changeDmMobileRedBannerByClassIds(dmDeleteData.getClassIdList());
        dmMobileRedBannerListDao.deleteDmMobileRedBannerListByClassIds(dmDeleteData.getClassIdList());

        //删除学生账号
        studentAccountDao.deleteByClassIds(dmDeleteData.getClassIdList());

        //删除班级相册、视频、班级信息
        dmClassDescService.batchClearClassDescAndPhoto(listToString(dmDeleteData.getClassIdList(),','));
        parentmsgDao.deleteParentmsgByClassIds(dmDeleteData.getClassIdList());
        studentGroupDao.deleteStudentGroupByClassIds(dmDeleteData.getClassIdList());
        groupManageDao.deleteGroupManageByClassIds(dmDeleteData.getClassIdList());

    }

    /**
     *  List转换为字符串并加入分隔符
     * @param list,separator
     * @return
     */
    public String listToString(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

}
