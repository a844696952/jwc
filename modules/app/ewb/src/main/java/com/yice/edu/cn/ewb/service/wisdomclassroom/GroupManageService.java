package com.yice.edu.cn.ewb.service.wisdomclassroom;

import com.yice.edu.cn.common.pojo.wb.groupManage.GroupManage;
import com.yice.edu.cn.ewb.feignClient.wisdomclassroom.GroupManageFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupManageService {
    @Autowired
    private GroupManageFeign groupManageFeign;

    public GroupManage findGroupManageById(String id) {
        return groupManageFeign.findGroupManageById(id);
    }

    public GroupManage saveGroupManage(GroupManage groupManage) {
        return groupManageFeign.saveGroupManage(groupManage);
    }

    public List<GroupManage> findGroupManageListByCondition(GroupManage groupManage) {
        List<GroupManage> groupManageList = groupManageFeign.findGroupManageListByCondition(groupManage);
        groupManageList.forEach(x->{
            GroupManage groupMembers = groupManageFeign.findGroupManageById(x.getId());
            x.setStudents(groupMembers.getStudents());
        });
        return groupManageList;
    }

    public GroupManage findOneGroupManageByCondition(GroupManage groupManage) {
        return groupManageFeign.findOneGroupManageByCondition(groupManage);
    }

    public long findGroupManageCountByCondition(GroupManage groupManage) {
        return groupManageFeign.findGroupManageCountByCondition(groupManage);
    }

    public void updateGroupManage(GroupManage groupManage) {
        groupManageFeign.updateGroupManage(groupManage);
    }

    public void deleteGroupManage(String id) {
        groupManageFeign.deleteGroupManage(id);
    }

    public void deleteGroupManageByCondition(GroupManage groupManage) {
        groupManageFeign.deleteGroupManageByCondition(groupManage);
    }

    public void moveGroupManage(List<GroupManage> groupManages) {
        groupManageFeign.moveGroupManage(groupManages);
    }
}
