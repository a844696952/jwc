package com.yice.edu.cn.osp.feignClient.xw.workerKq;

import com.yice.edu.cn.common.pojo.xw.workerKq.KqWorkerDaily;
import com.yice.edu.cn.common.pojo.xw.workerKq.WorkerKqRules;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "workerKqRulesFeign",path = "/workerKqRules")
public interface WorkerKqRulesFeign {
    @GetMapping("/findWorkerKqRulesById/{id}")
    WorkerKqRules findWorkerKqRulesById(@PathVariable("id") String id);
    @PostMapping("/saveWorkerKqRules")
    WorkerKqRules saveWorkerKqRules(WorkerKqRules workerKqRules);
    @PostMapping("/findWorkerKqRulesListByCondition")
    List<WorkerKqRules> findWorkerKqRulesListByCondition(WorkerKqRules workerKqRules);
    @PostMapping("/findOneWorkerKqRulesByCondition")
    WorkerKqRules findOneWorkerKqRulesByCondition(WorkerKqRules workerKqRules);
    @PostMapping("/findWorkerKqRulesCountByCondition")
    long findWorkerKqRulesCountByCondition(WorkerKqRules workerKqRules);
    @PostMapping("/updateWorkerKqRules")
    void updateWorkerKqRules(WorkerKqRules workerKqRules);
    @GetMapping("/deleteWorkerKqRules/{id}")
    void deleteWorkerKqRules(@PathVariable("id") String id);
    @PostMapping("/deleteWorkerKqRulesByCondition")
    void deleteWorkerKqRulesByCondition(WorkerKqRules workerKqRules);
    @PostMapping("/createTeacherKqSendManageTask")
    void createTeacherKqSendManageTask(WorkerKqRules workerKqRules);
}
