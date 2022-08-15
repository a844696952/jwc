package com.yice.edu.cn.osp.service.xw.stuLeaveManage;

import com.yice.edu.cn.common.pojo.jw.schoolYear.CurSchoolYear;
import com.yice.edu.cn.common.pojo.xw.stuLeaveManage.StuLeave;
import com.yice.edu.cn.common.service.CurSchoolYearService;
import com.yice.edu.cn.osp.feignClient.xw.stuLeaveManage.StuLeaveFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StuLeaveService {
    @Autowired
    private StuLeaveFeign stuLeaveFeign;

    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public StuLeave findStuLeaveById(String id) {
        return stuLeaveFeign.findStuLeaveById(id);
    }

    public StuLeave saveStuLeave(StuLeave stuLeave) {
        return stuLeaveFeign.saveStuLeave(stuLeave);
    }

    public void batchSaveStuLeave(List<StuLeave> stuLeaves) {
        stuLeaveFeign.batchSaveStuLeave(stuLeaves);
    }

    public List<StuLeave> findStuLeaveListByCondition(StuLeave stuLeave) {
        return stuLeaveFeign.findStuLeaveListByCondition(stuLeave);
    }

    public StuLeave findOneStuLeaveByCondition(StuLeave stuLeave) {
        return stuLeaveFeign.findOneStuLeaveByCondition(stuLeave);
    }

    public long findStuLeaveCountByCondition(StuLeave stuLeave) {
        return stuLeaveFeign.findStuLeaveCountByCondition(stuLeave);
    }

    public void updateStuLeave(StuLeave stuLeave) {
        stuLeaveFeign.updateStuLeave(stuLeave);
    }

    public void updateStuLeaveForAll(StuLeave stuLeave) {
        stuLeaveFeign.updateStuLeaveForAll(stuLeave);
    }

    public void deleteStuLeave(String id) {
        stuLeaveFeign.deleteStuLeave(id);
    }

    public void deleteStuLeaveByCondition(StuLeave stuLeave) {
        stuLeaveFeign.deleteStuLeaveByCondition(stuLeave);
    }

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public List<StuLeave> findStuLeaveListByConditionForWH(StuLeave stuLeave) {
        return stuLeaveFeign.findStuLeaveListByConditionForWH(stuLeave);
    }

    public long findStuLeaveCountByConditionForWH(StuLeave stuLeave) {
        return stuLeaveFeign.findStuLeaveCountByConditionForWH(stuLeave);
    }
}
