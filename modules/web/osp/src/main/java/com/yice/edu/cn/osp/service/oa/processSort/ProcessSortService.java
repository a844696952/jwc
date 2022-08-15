package com.yice.edu.cn.osp.service.oa.processSort;

import com.yice.edu.cn.common.pojo.oa.processSort.ProcessSort;
import com.yice.edu.cn.osp.feignClient.oa.processSort.ProcessSortFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessSortService {
    @Autowired
    private ProcessSortFeign processSortFeign;

    public ProcessSort findProcessSortById(String id) {
        return processSortFeign.findProcessSortById(id);
    }

    public ProcessSort saveProcessSort(ProcessSort processSort) {
        return processSortFeign.saveProcessSort(processSort);
    }

    public List<ProcessSort> findProcessSortListByCondition(ProcessSort processSort) {
        return processSortFeign.findProcessSortListByCondition(processSort);
    }

    public ProcessSort findOneProcessSortByCondition(ProcessSort processSort) {
        return processSortFeign.findOneProcessSortByCondition(processSort);
    }

    public long findProcessSortCountByCondition(ProcessSort processSort) {
        return processSortFeign.findProcessSortCountByCondition(processSort);
    }

    public void updateProcessSort(ProcessSort processSort) {
        processSortFeign.updateProcessSort(processSort);
    }

    public void deleteProcessSort(String id) {
        processSortFeign.deleteProcessSort(id);
    }

    public void deleteProcessSortByCondition(ProcessSort processSort) {
        processSortFeign.deleteProcessSortByCondition(processSort);
    }

    public List<ProcessSort> getProcessSortList(String schoolId) {
       return  processSortFeign.getProcessSortList(schoolId);
    }
}
