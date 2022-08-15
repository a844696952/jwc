package com.yice.edu.cn.tap.service.xwStudentKq;

import com.yice.edu.cn.common.pojo.xw.kq.KqDailyStatistical;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import com.yice.edu.cn.tap.feignClient.xwStudentKq.XwStudentKqFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author:xushu
 * @date:2019/3/8.
 * 徐庶
 */
@Service
public class XwStudentKqService {
    @Autowired
    private XwStudentKqFeign xwStudentKqFeign;

    public  KqDailyStatistical inTimeCountByRules(KqOriginalData kqOriginalData) {
        return xwStudentKqFeign.inTimeCountByRules(kqOriginalData);
    }
    public  List<KqDailyStatistical> findKqDailyStatisticalListByCondition(KqDailyStatistical kqDailyStatistical) {
        return  xwStudentKqFeign.findKqDailyStatisticalListByCondition(kqDailyStatistical);
    }
    public long  findKqDailyStatisticalCountByCondition(KqDailyStatistical kqDailyStatistical) {
        return xwStudentKqFeign.findKqDailyStatisticalCountByCondition(kqDailyStatistical);
    }

}
