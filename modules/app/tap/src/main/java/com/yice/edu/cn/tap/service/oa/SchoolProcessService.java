package com.yice.edu.cn.tap.service.oa;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.oa.process.SchoolProcess;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import com.yice.edu.cn.tap.feignClient.oa.SchoolProcessFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SchoolProcessService {
    @Autowired
    private SchoolProcessFeign schoolProcessFeign;
    public SchoolProcess findSchoolProcessById(String id) {
        return schoolProcessFeign.findSchoolProcessById(id);
    }

    public SchoolProcess saveSchoolProcess(SchoolProcess schoolProcess) {
        return schoolProcessFeign.saveSchoolProcess(schoolProcess);
    }

    public List<SchoolProcess> findSchoolProcessListByCondition(SchoolProcess schoolProcess) {
        return schoolProcessFeign.findSchoolProcessListByCondition(schoolProcess);
    }

    public SchoolProcess findOneSchoolProcessByCondition(SchoolProcess schoolProcess) {
        return schoolProcessFeign.findOneSchoolProcessByCondition(schoolProcess);
    }

    public long findSchoolProcessCountByCondition(SchoolProcess schoolProcess) {
        return schoolProcessFeign.findSchoolProcessCountByCondition(schoolProcess);
    }

    public void updateSchoolProcess(SchoolProcess schoolProcess) {
        schoolProcessFeign.updateSchoolProcess(schoolProcess);
    }

    public void deleteSchoolProcess(String id) {
        schoolProcessFeign.deleteSchoolProcess(id);
    }

    public void deleteSchoolProcessByCondition(SchoolProcess schoolProcess) {
        schoolProcessFeign.deleteSchoolProcessByCondition(schoolProcess);
    }


    public void addNewProcessLib(List<String> processLibIds, String schoolId) {
        schoolProcessFeign.addNewProcessLib(processLibIds,schoolId);
    }

    public List<SchoolProcess> listValidSchoolProcessBySchoolId(String schoolId) {
        return schoolProcessFeign.listValidSchoolProcessBySchoolId(schoolId);
    }

    public SchoolProcess findSchoolProcessByIdForStart(String schoolProcessId, String id) {
        return schoolProcessFeign.findSchoolProcessByIdForStart(schoolProcessId,id);
    }

    public void start(ProcessBusinessData processBusinessData) {
        schoolProcessFeign.start(processBusinessData);
    }

    public ResponseJson startProcess(Map<String,Object> formData, String schoolProcessId, String id) {
        return schoolProcessFeign.startProcess(formData,schoolProcessId,id);
    }

    public ResponseJson startProcess(ProcessBusinessData processBusinessData) {
        return schoolProcessFeign.startProcess(processBusinessData);
    }

    public ResponseJson findFlowConditionByFormData(ProcessBusinessData processBusinessData) {
        return schoolProcessFeign.findFlowConditionByFormData(processBusinessData);
    }

    public List<SchoolProcess> listSchoolProcessGroup(String mySchoolId) {
        return schoolProcessFeign.listSchoolProcessGroup(mySchoolId);
    }

    public List<SchoolProcess> findSchoolProcessForSelect(String schoolId) {
        return schoolProcessFeign.findSchoolProcessForSelect(schoolId);
    }
}
