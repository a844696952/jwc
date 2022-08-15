package com.yice.edu.cn.ecc.feignClient.classCard;

import com.yice.edu.cn.common.pojo.dm.classCard.DmTimedTask;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="dm",contextId = "timedTaskFeign",path = "/dmTimedTask")
public interface TimedTaskFeign {

    @PostMapping("/findDmTimedTaskListByCondition")
    List<DmTimedTask> findDmTimedTaskListByCondition(DmTimedTask dmTimedTask);

}
