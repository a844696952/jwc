package com.yice.edu.cn.osp.feignClient.wb.groupManage;

import com.yice.edu.cn.common.pojo.wb.groupManage.GroupManage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "groupManageFeign",path = "/groupManage")
public interface GroupManageFeign {
    @GetMapping("/findGroupManageById/{id}")
    GroupManage findGroupManageById(@PathVariable("id") String id);
    @PostMapping("/saveGroupManage")
    int saveGroupManage(GroupManage groupManage);
    @PostMapping("/findGroupManageListByCondition")
    List<GroupManage> findGroupManageListByCondition(GroupManage groupManage);
    @PostMapping("/findOneGroupManageByCondition")
    GroupManage findOneGroupManageByCondition(GroupManage groupManage);
    @PostMapping("/findGroupManageCountByCondition")
    long findGroupManageCountByCondition(GroupManage groupManage);
    @PostMapping("/updateGroupManage")
    int updateGroupManage(GroupManage groupManage);
    @GetMapping("/deleteGroupManage/{id}")
    void deleteGroupManage(@PathVariable("id") String id);
    @PostMapping("/deleteGroupManageByCondition")
    void deleteGroupManageByCondition(GroupManage groupManage);
    @PostMapping("/moveGroupManage")
    void moveGroupManage(List<GroupManage> groupManages);
}
