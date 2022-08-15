package com.yice.edu.cn.osp.service.oa.processLib;

import com.yice.edu.cn.common.pojo.oa.process.ProcessLib;
import com.yice.edu.cn.osp.feignClient.oa.processLib.ProcessLibFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessLibService {
    @Autowired
    private ProcessLibFeign processLibFeign;

    public ProcessLib findProcessLibById(String id) {
        return processLibFeign.findProcessLibById(id);
    }

    public ProcessLib saveProcessLib(ProcessLib processLib) {
        return processLibFeign.saveProcessLib(processLib);
    }

    public List<ProcessLib> findProcessLibListByCondition(ProcessLib processLib) {
        return processLibFeign.findProcessLibListByCondition(processLib);
    }

    public ProcessLib findOneProcessLibByCondition(ProcessLib processLib) {
        return processLibFeign.findOneProcessLibByCondition(processLib);
    }

    public long findProcessLibCountByCondition(ProcessLib processLib) {
        return processLibFeign.findProcessLibCountByCondition(processLib);
    }

    public void updateProcessLib(ProcessLib processLib) {
        processLibFeign.updateProcessLib(processLib);
    }

    public void deleteProcessLib(String id) {
        processLibFeign.deleteProcessLib(id);
    }

    public void deleteProcessLibByCondition(ProcessLib processLib) {
        processLibFeign.deleteProcessLibByCondition(processLib);
    }
}
