package com.yice.edu.cn.dm.service.wb.groupManage;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.wb.groupManage.GroupManage;
import com.yice.edu.cn.common.pojo.wb.groupManage.StudentGroup;
import com.yice.edu.cn.dm.dao.wb.groupManage.IGroupManageDao;
import com.yice.edu.cn.dm.dao.wb.groupManage.IStudentGroupDao;
import com.yice.edu.cn.dm.feignClient.jw.student.StudentFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GroupManageService {
    @Autowired
    private IGroupManageDao groupManageDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IStudentGroupDao studentGroupDao;
    @Autowired
    private StudentFeign studentFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public GroupManage findGroupManageById(String id) {
        GroupManage groupManage = groupManageDao.findGroupManageById(id);
        List<Student> students =  studentFeign.findStudentsByGroupId(id);
        groupManage.setStudents(students);
        return groupManage;
    }

    /**
     * 保存小组
     * @param groupManage
     */
    @Transactional
    public int saveGroupManage(GroupManage groupManage) {
        groupManage.setId(sequenceId.nextId());
        groupManage.setGroupName(groupManage.getGroupName().trim());
        int count = groupManageDao.selectRepeatNameByName(groupManage);
        if(count==0){
            Integer maxGroupNumber = groupManageDao.selectMaxGroupNumber(groupManage.getClassesId());
            if(Objects.nonNull(maxGroupNumber)){
                groupManage.setGroupNumber(maxGroupNumber+1);
            }else{
                groupManage.setGroupNumber(0);
            }
            List<StudentGroup> studentGroups = insertStudentGroups(groupManage);
            studentGroupDao.batchSaveStudentGroup(studentGroups);
            groupManageDao.saveGroupManage(groupManage);
            return 1;
        }else{
            return 0;
        }

    }
    @Transactional(readOnly = true)
    public List<GroupManage> findGroupManageListByCondition(GroupManage groupManage) {
        return groupManageDao.findGroupManageListByCondition(groupManage);
    }
    @Transactional(readOnly = true)
    public GroupManage findOneGroupManageByCondition(GroupManage groupManage) {
        return groupManageDao.findOneGroupManageByCondition(groupManage);
    }
    @Transactional(readOnly = true)
    public long findGroupManageCountByCondition(GroupManage groupManage) {
        return groupManageDao.findGroupManageCountByCondition(groupManage);
    }
    @Transactional
    public int updateGroupManage(GroupManage groupManage) {
        groupManage.setGroupName(groupManage.getGroupName().trim());
        int count = groupManageDao.selectRepeatNameByName(groupManage);
        if(count==0){
            studentGroupDao.deleteStudentGroupByGroupId(groupManage.getId());
            List<StudentGroup> studentGroups = insertStudentGroups(groupManage);
            studentGroupDao.batchSaveStudentGroup(studentGroups);
            groupManageDao.updateGroupManage(groupManage);
            return 1;
        }else{
            return 0;
        }
    }
    @Transactional
    public void deleteGroupManage(String id) {
        groupManageDao.deleteGroupManage(id);
        studentGroupDao.deleteStudentGroupByGroupId(id);
    }
    @Transactional
    public void deleteGroupManageByCondition(GroupManage groupManage) {
        groupManageDao.deleteGroupManageByCondition(groupManage);
    }
    @Transactional
    public void batchSaveGroupManage(List<GroupManage> groupManages){
        groupManages.forEach(groupManage -> groupManage.setId(sequenceId.nextId()));
        groupManageDao.batchSaveGroupManage(groupManages);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public List<StudentGroup> insertStudentGroups(GroupManage groupManage){
        String[] studentIds = groupManage.getStudentIds();
        ArrayList<StudentGroup> studentGroups = new ArrayList<>(studentIds.length);
        String time = DateUtil.now();
        for (String studentId : studentIds) {
            StudentGroup studentGroup = new StudentGroup();
            studentGroup.setClassesId(groupManage.getClassesId());
            studentGroup.setGroupId(groupManage.getId());
            studentGroup.setStudentId(studentId);
            studentGroup.setId(sequenceId.nextId());
            studentGroup.setCreateTime(time);
            studentGroup.setSchoolId(groupManage.getSchoolId());
            studentGroups.add(studentGroup);
        }
        return studentGroups;
    }

    public void moveGroupManage(List<GroupManage> groupManages) {
        for (int i = 0; i < groupManages.size(); i++) {
            groupManages.get(i).setGroupNumber(i);
        }
        groupManages.forEach(groupManage -> groupManageDao.moveGroupManage(groupManage));

    }
}
