package com.yice.edu.cn.osp.service.xw.dormManage.dorm;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPersonOut;
import com.yice.edu.cn.osp.feignClient.xw.dormManage.dorm.DormPersonOutFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DormPersonOutService {
    @Autowired
    private DormPersonOutFeign dormPersonOutFeign;

    public DormPersonOut findDormPersonOutById(String id) {
        return dormPersonOutFeign.findDormPersonOutById(id);
    }

    public DormPersonOut saveDormPersonOut(DormPersonOut dormPersonOut) {
        return dormPersonOutFeign.saveDormPersonOut(dormPersonOut);
    }

    public List<DormPersonOut> findDormPersonOutListByCondition(DormPersonOut dormPersonOut) {
        return dormPersonOutFeign.findDormPersonOutListByCondition(dormPersonOut);
    }

    public DormPersonOut findOneDormPersonOutByCondition(DormPersonOut dormPersonOut) {
        return dormPersonOutFeign.findOneDormPersonOutByCondition(dormPersonOut);
    }

    public long findDormPersonOutCountByCondition(DormPersonOut dormPersonOut) {
        return dormPersonOutFeign.findDormPersonOutCountByCondition(dormPersonOut);
    }

    public void updateDormPersonOut(DormPersonOut dormPersonOut) {
        dormPersonOutFeign.updateDormPersonOut(dormPersonOut);
    }

    public void deleteDormPersonOut(String id) {
        dormPersonOutFeign.deleteDormPersonOut(id);
    }

    public void deleteDormPersonOutByCondition(DormPersonOut dormPersonOut) {
        dormPersonOutFeign.deleteDormPersonOutByCondition(dormPersonOut);
    }
    /*----------------------------------------------------------------------------------------------*/

    public List<DormPersonOut> findDormPersonOutListByConditionAndPersonType(DormPersonOut dormPersonOut){
        if (dormPersonOut!=null&&dormPersonOut.getSearchTimeZone()!=null&&dormPersonOut.getSearchTimeZone().size()>0){
            List<String> searchTimeZone = dormPersonOut.getSearchTimeZone();
            dormPersonOut.setStartTime(searchTimeZone.get(0));
            dormPersonOut.setEndTime(searchTimeZone.get(1));
        }
        return dormPersonOutFeign.findDormPersonOutListByConditionAndPersonType(dormPersonOut);
    }

    public long findDormPersonOutCountByConditionAndPersonType(DormPersonOut dormPersonOut){
        if (dormPersonOut!=null&&dormPersonOut.getSearchTimeZone()!=null&&dormPersonOut.getSearchTimeZone().size()>0){
            List<String> searchTimeZone = dormPersonOut.getSearchTimeZone();
            dormPersonOut.setStartTime(searchTimeZone.get(0));
            dormPersonOut.setEndTime(searchTimeZone.get(1));
        }
        return dormPersonOutFeign.findDormPersonOutCountByConditionAndPersonType(dormPersonOut);
    }
}
