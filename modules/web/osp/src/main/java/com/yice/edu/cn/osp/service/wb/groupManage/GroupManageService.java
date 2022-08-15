package com.yice.edu.cn.osp.service.wb.groupManage;

import com.yice.edu.cn.common.pojo.wb.groupManage.GroupManage;
import com.yice.edu.cn.osp.feignClient.wb.groupManage.GroupManageFeign;
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

    public int saveGroupManage(GroupManage groupManage) {
        return groupManageFeign.saveGroupManage(groupManage);
    }

    public List<GroupManage> findGroupManageListByCondition(GroupManage groupManage) {
        return groupManageFeign.findGroupManageListByCondition(groupManage);
    }

    public GroupManage findOneGroupManageByCondition(GroupManage groupManage) {
        return groupManageFeign.findOneGroupManageByCondition(groupManage);
    }

    public long findGroupManageCountByCondition(GroupManage groupManage) {
        return groupManageFeign.findGroupManageCountByCondition(groupManage);
    }

    public int updateGroupManage(GroupManage groupManage) {
        return groupManageFeign.updateGroupManage(groupManage);
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
