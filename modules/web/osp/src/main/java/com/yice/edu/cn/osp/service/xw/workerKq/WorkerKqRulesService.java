package com.yice.edu.cn.osp.service.xw.workerKq;

import com.yice.edu.cn.common.pojo.xw.workerKq.WorkerKqRules;
import com.yice.edu.cn.osp.feignClient.xw.workerKq.WorkerKqRulesFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerKqRulesService {
    @Autowired
    private WorkerKqRulesFeign workerKqRulesFeign;

    public WorkerKqRules findWorkerKqRulesById(String id) {
        return workerKqRulesFeign.findWorkerKqRulesById(id);
    }

    public WorkerKqRules saveWorkerKqRules(WorkerKqRules workerKqRules) {
        return workerKqRulesFeign.saveWorkerKqRules(workerKqRules);
    }

    public List<WorkerKqRules> findWorkerKqRulesListByCondition(WorkerKqRules workerKqRules) {
        return workerKqRulesFeign.findWorkerKqRulesListByCondition(workerKqRules);
    }

    public WorkerKqRules findOneWorkerKqRulesByCondition(WorkerKqRules workerKqRules) {
        return workerKqRulesFeign.findOneWorkerKqRulesByCondition(workerKqRules);
    }

    public long findWorkerKqRulesCountByCondition(WorkerKqRules workerKqRules) {
        return workerKqRulesFeign.findWorkerKqRulesCountByCondition(workerKqRules);
    }

    public void updateWorkerKqRules(WorkerKqRules workerKqRules) {
        workerKqRulesFeign.updateWorkerKqRules(workerKqRules);
    }

    public void deleteWorkerKqRules(String id) {
        workerKqRulesFeign.deleteWorkerKqRules(id);
    }

    public void deleteWorkerKqRulesByCondition(WorkerKqRules workerKqRules) {
        workerKqRulesFeign.deleteWorkerKqRulesByCondition(workerKqRules);
    }

    public void createTeacherKqSendManageTask(WorkerKqRules workerKqRules) {
        workerKqRulesFeign.createTeacherKqSendManageTask(workerKqRules);
    }
}
