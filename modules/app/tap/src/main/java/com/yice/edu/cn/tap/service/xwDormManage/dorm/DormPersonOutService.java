package com.yice.edu.cn.tap.service.xwDormManage.dorm;

import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPersonOut;
import com.yice.edu.cn.tap.feignClient.xwDormManage.dorm.DormPersonOutFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DormPersonOutService {
    @Autowired
    private DormPersonOutFeign dormPersonOutFeign;

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
