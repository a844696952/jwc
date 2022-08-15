package com.yice.edu.cn.bmp.service.stuInAndOut;

import com.yice.edu.cn.bmp.feignClient.stuInAndOut.KqOriginalDataFeign;
import com.yice.edu.cn.common.pojo.xw.kq.KqDailyStatistical;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KqOriginalDataService {
    @Autowired
    private KqOriginalDataFeign kqOriginalDataFeign;

    public KqOriginalData findKqOriginalDataById(String id) {
        return kqOriginalDataFeign.findKqOriginalDataById(id);
    }

    public KqOriginalData saveKqOriginalData(KqOriginalData kqOriginalData) {
        return kqOriginalDataFeign.saveKqOriginalData(kqOriginalData);
    }

    public List<KqOriginalData> findKqOriginalDataListByCondition(KqOriginalData kqOriginalData) {
        return kqOriginalDataFeign.findKqOriginalDataListByCondition(kqOriginalData);
    }

    public KqOriginalData findOneKqOriginalDataByCondition(KqOriginalData kqOriginalData) {
        return kqOriginalDataFeign.findOneKqOriginalDataByCondition(kqOriginalData);
    }

    public long findKqOriginalDataCountByCondition(KqOriginalData kqOriginalData) {
        return kqOriginalDataFeign.findKqOriginalDataCountByCondition(kqOriginalData);
    }

    public void updateKqOriginalData(KqOriginalData kqOriginalData) {
        kqOriginalDataFeign.updateKqOriginalData(kqOriginalData);
    }

    public void deleteKqOriginalData(String id) {
        kqOriginalDataFeign.deleteKqOriginalData(id);
    }

    public void deleteKqOriginalDataByCondition(KqOriginalData kqOriginalData) {
        kqOriginalDataFeign.deleteKqOriginalDataByCondition(kqOriginalData);
    }

    public KqDailyStatistical judgePunchStatusByrules(KqOriginalData kqOriginalData){
        return kqOriginalDataFeign.judgePunchStatusByrules(kqOriginalData);
    }
    public KqDailyStatistical dayBasicRecords(KqOriginalData kqOriginalData){
        return kqOriginalDataFeign.dayBasicRecords(kqOriginalData);
    }
    public KqDailyStatistical inTimeCountByRules(KqOriginalData kqOriginalData){
        return kqOriginalDataFeign.inTimeCountByRules(kqOriginalData);
    }
    public boolean statusOfKqOriginalData(KqOriginalData kqOriginalData){
        return kqOriginalDataFeign.statusOfKqOriginalData(kqOriginalData);
    }


}

