package com.yice.edu.cn.tap.service.oa;

import com.yice.edu.cn.common.pojo.oa.processCopy.ProcessCopy;
import com.yice.edu.cn.common.pojo.oa.processCopy.ProcessCopyVo;
import com.yice.edu.cn.tap.feignClient.oa.ProcessCopyFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessCopyService {
    @Autowired
    private ProcessCopyFeign processCopyFeign;

    public ProcessCopy findProcessCopyById(String id) {
        return processCopyFeign.findProcessCopyById(id);
    }

    public ProcessCopy saveProcessCopy(ProcessCopy processCopy) {
        return processCopyFeign.saveProcessCopy(processCopy);
    }

    public List<ProcessCopyVo> findProcessCopyListByCondition(ProcessCopy processCopy) {
        return processCopyFeign.findProcessCopyListByCondition(processCopy);
    }

    public ProcessCopy findOneProcessCopyByCondition(ProcessCopy processCopy) {
        return processCopyFeign.findOneProcessCopyByCondition(processCopy);
    }

    public long findProcessCopyCountByCondition(ProcessCopy processCopy) {
        return processCopyFeign.findProcessCopyCountByCondition(processCopy);
    }

    public void updateProcessCopy(ProcessCopy processCopy) {
        processCopyFeign.updateProcessCopy(processCopy);
    }

    public void deleteProcessCopy(String id) {
        processCopyFeign.deleteProcessCopy(id);
    }

    public void deleteProcessCopyByCondition(ProcessCopy processCopy) {
        processCopyFeign.deleteProcessCopyByCondition(processCopy);
    }
}
