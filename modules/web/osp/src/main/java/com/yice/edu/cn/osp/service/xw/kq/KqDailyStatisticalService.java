package com.yice.edu.cn.osp.service.xw.kq;

import com.yice.edu.cn.common.pojo.xw.kq.KqDailyStatistical;
import com.yice.edu.cn.common.pojo.xw.kq.KqMonthStatistical;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import com.yice.edu.cn.osp.feignClient.xw.kq.KqDailyStatisticalFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KqDailyStatisticalService {
    @Autowired
    private KqDailyStatisticalFeign kqDailyStatisticalFeign;

    public KqDailyStatistical findKqDailyStatisticalById(String id) {
        return kqDailyStatisticalFeign.findKqDailyStatisticalById(id);
    }

    public KqDailyStatistical saveKqDailyStatistical(KqDailyStatistical kqDailyStatistical) {
        return kqDailyStatisticalFeign.saveKqDailyStatistical(kqDailyStatistical);
    }

    public List<KqDailyStatistical> findKqDailyStatisticalListByCondition(KqDailyStatistical kqDailyStatistical) {
        return kqDailyStatisticalFeign.findKqDailyStatisticalListByCondition(kqDailyStatistical);
    }

    public List<KqDailyStatistical> findKqDailyStatisticalParentListByCondition(KqDailyStatistical kqDailyStatistical) {

        //需要将返回的数据按照考勤日期和考勤类型分成多个表格
        List<List<KqDailyStatistical>> parentList = new ArrayList<List<KqDailyStatistical>>();
        List<KqDailyStatistical> list0 = kqDailyStatisticalFeign.findKqDailyStatisticalListByCondition(kqDailyStatistical);
        List<KqDailyStatistical> list1 = list0.stream().sorted(Comparator.comparing(KqDailyStatistical::getKqDate)).collect(Collectors.toList());//根据考勤日期自然顺序
       // System.out.println("原始数据这么多条" + list1.size());
        return list1;
    }

    public KqDailyStatistical findOneKqDailyStatisticalByCondition(KqDailyStatistical kqDailyStatistical) {
        return kqDailyStatisticalFeign.findOneKqDailyStatisticalByCondition(kqDailyStatistical);
    }

    public long findKqDailyStatisticalCountByCondition(KqDailyStatistical kqDailyStatistical) {
        return kqDailyStatisticalFeign.findKqDailyStatisticalCountByCondition(kqDailyStatistical);
    }

    public void updateKqDailyStatistical(KqDailyStatistical kqDailyStatistical) {
        kqDailyStatisticalFeign.updateKqDailyStatistical(kqDailyStatistical);
    }

    public void deleteKqDailyStatistical(String id) {
        kqDailyStatisticalFeign.deleteKqDailyStatistical(id);
    }

    public void deleteKqDailyStatisticalByCondition(KqDailyStatistical kqDailyStatistical) {
        kqDailyStatisticalFeign.deleteKqDailyStatisticalByCondition(kqDailyStatistical);
    }

    public List<KqMonthStatistical> findKqMonthStatisticalListByCondition(KqDailyStatistical kqDailyStatistical) {
        return kqDailyStatisticalFeign.findKqMonthStatisticalListByCondition(kqDailyStatistical);
    }

    public List<KqMonthStatistical> findKqMonthStatisticalListAll(KqDailyStatistical kqDailyStatistical) {
        return kqDailyStatisticalFeign.findKqMonthStatisticalListAll(kqDailyStatistical);
    }

    public KqDailyStatistical inTimeCountByRules(KqOriginalData kqOriginalData) {
        return kqDailyStatisticalFeign.inTimeCountByRules(kqOriginalData);

    }
   /* public List<KqDailyStatistical> inTimeCountByRulesForClass(KqOriginalData kqOriginalData) {
        return kqDailyStatisticalFeign.inTimeCountByRulesForClass(kqOriginalData);

    }*/

}
