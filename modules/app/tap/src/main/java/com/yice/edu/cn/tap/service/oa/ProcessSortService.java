package com.yice.edu.cn.tap.service.oa;

import com.yice.edu.cn.common.pojo.oa.processSort.ProcessSort;
import com.yice.edu.cn.tap.feignClient.oa.ProcessSortFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessSortService {
    @Autowired
    private ProcessSortFeign processSortFeign;
    public List<ProcessSort> getProcessSortList(String schoolId) {
       return  processSortFeign.getProcessSortList(schoolId);
    }
}
