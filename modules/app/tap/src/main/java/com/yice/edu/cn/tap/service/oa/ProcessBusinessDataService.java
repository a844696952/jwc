package com.yice.edu.cn.tap.service.oa;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import com.yice.edu.cn.tap.feignClient.oa.ProcessBusinessDataFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessBusinessDataService {
    @Autowired
    private ProcessBusinessDataFeign processBusinessDataFeign;

    public ProcessBusinessData findProcessBusinessDataById(String id) {
        return processBusinessDataFeign.findProcessBusinessDataById(id);
    }

    public ProcessBusinessData saveProcessBusinessData(ProcessBusinessData processBusinessData) {
        return processBusinessDataFeign.saveProcessBusinessData(processBusinessData);
    }

    public List<ProcessBusinessData> findProcessBusinessDataListByCondition(ProcessBusinessData processBusinessData) {
        return processBusinessDataFeign.findProcessBusinessDataListByCondition(processBusinessData);
    }

    public ProcessBusinessData findOneProcessBusinessDataByCondition(ProcessBusinessData processBusinessData) {
        return processBusinessDataFeign.findOneProcessBusinessDataByCondition(processBusinessData);
    }

    public long findProcessBusinessDataCountByCondition(ProcessBusinessData processBusinessData) {
        return processBusinessDataFeign.findProcessBusinessDataCountByCondition(processBusinessData);
    }

    public void updateProcessBusinessData(ProcessBusinessData processBusinessData) {
        processBusinessDataFeign.updateProcessBusinessData(processBusinessData);
    }

    public void deleteProcessBusinessData(String id) {
        processBusinessDataFeign.deleteProcessBusinessData(id);
    }

    public void deleteProcessBusinessDataByCondition(ProcessBusinessData processBusinessData) {
        processBusinessDataFeign.deleteProcessBusinessDataByCondition(processBusinessData);
    }

    public ResponseJson clearLeave(ProcessBusinessData processBusinessData) {
        return processBusinessDataFeign.clearLeave(processBusinessData);
    }


    public ResponseJson calculateLeaveStatis(ProcessBusinessData processBusinessData) {
        return processBusinessDataFeign.calculateLeaveStatis(processBusinessData);
    }

    public ResponseJson sendMessageByProcessDataById(String id) {
        return processBusinessDataFeign.sendMessageByProcessDataById(id);
    }

    public ResponseJson cancelFlow(String id, ProcessBusinessData processBusinessData) {
        return processBusinessDataFeign.cancelFlow(id,processBusinessData);
    }

    public ResponseJson pushUrge(String id) {
        return processBusinessDataFeign.pushUrge(id);
    }
}
