package com.yice.edu.cn.osp.service.jw.schoolDateCenter;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.SchoolDateCenter.SchoolDateCenter;
import com.yice.edu.cn.common.pojo.jw.SchoolDateCenter.schoolDateAssist.*;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.DatePaper;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.Paper;
import com.yice.edu.cn.common.pojo.jw.exam.buildPaper.paper.PaperDayMonth;
import com.yice.edu.cn.common.pojo.jw.qusBankResource.SchoolQusBank;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.schoolCompusCenter.SchoolCompusCenter;
import com.yice.edu.cn.common.pojo.jw.student.JwStudentGraduation;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jy.resources.JyResouces;
import com.yice.edu.cn.common.pojo.jy.resources.JySchoolResouces;
import com.yice.edu.cn.common.pojo.jy.resources.JySchoolResourceCensus;
import com.yice.edu.cn.common.pojo.jy.resources.JySchoolResourcesByDay;
import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockDetail.AssetsStockDetail;
import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairNew;
import com.yice.edu.cn.common.util.LocalDateTimeUtils;
import com.yice.edu.cn.common.util.math.MathKit;
import com.yice.edu.cn.osp.feignClient.jw.schoolDateCenter.SchoolDateCenterFeign;
import com.yice.edu.cn.osp.feignClient.jw.student.JwStudentGraduationFeign;
import com.yice.edu.cn.osp.feignClient.jw.student.StudentFeign;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherFeign;
import com.yice.edu.cn.osp.feignClient.xw.workerKq.KqWorkerDailyFeign;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.dm.school.SchoolService;
import com.yice.edu.cn.osp.service.jw.classes.JwClassesService;
import com.yice.edu.cn.osp.service.jw.exam.buildPaper.paper.PaperService;
import com.yice.edu.cn.osp.service.jw.qusBankResouece.SchoolQusBankService;
import com.yice.edu.cn.osp.service.jy.resources.JySchoolResoucesService;
import com.yice.edu.cn.osp.service.zc.assetsStockDetail.AssetsStockDetailService;
import com.yice.edu.cn.osp.service.zc.repairNew.RepairNewService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class SchoolDateCenterService {
    @Autowired
    private SchoolDateCenterFeign schoolDateCenterFeign;
    @Autowired
    private TeacherFeign teacherFeign;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private StudentFeign studentFeign;
    @Autowired
    private JwStudentGraduationFeign jwStudentGraduationFeign;
    @Autowired
    private JySchoolResoucesService jySchoolResoucesService;
    @Autowired
    private PaperService paperService;
    @Autowired
    private SchoolQusBankService schoolQusBankService;

    @Autowired
    private JwClassesService jwClassesService;

    @Autowired
    private KqWorkerDailyFeign kqWorkerDailyFeign;
    @Autowired
    private AssetsStockDetailService assetsStockDetailService;
    @Autowired
    private RepairNewService repairNewService;


/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public SchoolDateCenter findSchoolDateCenterById(String id) {
        return schoolDateCenterFeign.findSchoolDateCenterById(id);
    }

    public SchoolDateCenter saveSchoolDateCenter(SchoolDateCenter schoolDateCenter) {
        return schoolDateCenterFeign.saveSchoolDateCenter(schoolDateCenter);
    }

    public void batchSaveSchoolDateCenter(List<SchoolDateCenter> schoolDateCenters){
        schoolDateCenterFeign.batchSaveSchoolDateCenter(schoolDateCenters);
    }

    public List<SchoolDateCenter> findSchoolDateCenterListByCondition(SchoolDateCenter schoolDateCenter) {
        return schoolDateCenterFeign.findSchoolDateCenterListByCondition(schoolDateCenter);
    }

    public SchoolDateCenter findOneSchoolDateCenterByCondition(SchoolDateCenter schoolDateCenter) {
        return schoolDateCenterFeign.findOneSchoolDateCenterByCondition(schoolDateCenter);
    }

    public long findSchoolDateCenterCountByCondition(SchoolDateCenter schoolDateCenter) {
        return schoolDateCenterFeign.findSchoolDateCenterCountByCondition(schoolDateCenter);
    }

    public void updateSchoolDateCenter(SchoolDateCenter schoolDateCenter) {
        schoolDateCenterFeign.updateSchoolDateCenter(schoolDateCenter);
    }

    public void updateSchoolDateCenterForAll(SchoolDateCenter schoolDateCenter) {
        schoolDateCenterFeign.updateSchoolDateCenterForAll(schoolDateCenter);
    }

    public void deleteSchoolDateCenter(String id) {
        schoolDateCenterFeign.deleteSchoolDateCenter(id);
    }

    public void deleteSchoolDateCenterByCondition(SchoolDateCenter schoolDateCenter) {
        schoolDateCenterFeign.deleteSchoolDateCenterByCondition(schoolDateCenter);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    public SchoolDateCenter findSchoolCompusCenterBySchoolId(String schoolId){
        return schoolDateCenterFeign.findSchoolCompusCenterBySchoolId(schoolId);
    }


    public SchoolDateCenter getTruesSchoolDateCenter(String schoolId){
        return schoolDateCenterFeign.getTruesSchoolDateCenter(schoolId);
    }

    public void updateSchoolDateCenterByType(SchoolDateCenter schoolDateCenter,Integer type){
        schoolDateCenterFeign.updateSchoolDateCenterByType(schoolDateCenter,type);
    }
}
