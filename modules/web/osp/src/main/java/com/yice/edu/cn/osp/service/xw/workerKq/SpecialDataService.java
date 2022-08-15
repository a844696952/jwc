package com.yice.edu.cn.osp.service.xw.workerKq;

import com.yice.edu.cn.common.pojo.xw.workerKq.SpecialData;
import com.yice.edu.cn.osp.feignClient.xw.workerKq.SpecialDataFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialDataService {
    @Autowired
    private SpecialDataFeign specialDataFeign;

    public SpecialData findSpecialDataById(String id) {
        return specialDataFeign.findSpecialDataById(id);
    }

    public SpecialData saveSpecialData(SpecialData specialData) {
        return specialDataFeign.saveSpecialData(specialData);
    }

    public List<SpecialData> findSpecialDataListByCondition(SpecialData specialData) {
        return specialDataFeign.findSpecialDataListByCondition(specialData);
    }

    public SpecialData findOneSpecialDataByCondition(SpecialData specialData) {
        return specialDataFeign.findOneSpecialDataByCondition(specialData);
    }

    public long findSpecialDataCountByCondition(SpecialData specialData) {
        return specialDataFeign.findSpecialDataCountByCondition(specialData);
    }

    public void updateSpecialData(SpecialData specialData) {
        specialDataFeign.updateSpecialData(specialData);
    }

    public void deleteSpecialData(String id) {
        specialDataFeign.deleteSpecialData(id);
    }

    public void deleteSpecialDataByCondition(SpecialData specialData) {
        specialDataFeign.deleteSpecialDataByCondition(specialData);
    }
}
