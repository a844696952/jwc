package com.yice.edu.cn.osp.service.xw.stuInAndOut;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Page;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.xw.kq.KqOriginalData;
import com.yice.edu.cn.common.pojo.xw.stuInAndOut.ClassTime;
import com.yice.edu.cn.common.pojo.xw.stuInAndOut.StuInAndOutBigData;
import com.yice.edu.cn.common.pojo.xw.stuInAndOut.StuInAndOutBigDataClassDetail;
import com.yice.edu.cn.common.util.StringUtils;
import com.yice.edu.cn.osp.feignClient.jw.student.StudentFeign;
import com.yice.edu.cn.osp.feignClient.jw.teacher.TeacherClassesFeign;
import com.yice.edu.cn.osp.feignClient.xw.kq.KqOriginalDataFeign;
import com.yice.edu.cn.osp.feignClient.xw.stuInAndOut.ClassTimeFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class StuInAndOutBigDataService {
    @Autowired
    private ClassTimeFeign classTimeFeign;
    @Autowired
    private StudentFeign studentFeign;
    @Autowired
    private KqOriginalDataFeign kqOriginalDataFeign;
    @Autowired
    private TeacherClassesFeign teacherClassesFeign;


    public List<ClassTime> findClassTimeListByCondition(ClassTime classTime) {
        classTime.setSchoolId(mySchoolId());
        classTime.getPager().setSortField("startTime");
        classTime.getPager().setSortOrder("ASC");
        return classTimeFeign.findClassTimeListByCondition(classTime);
    }


    public StuInAndOutBigData findStuInAndOutBigData() {
        StuInAndOutBigData stuInAndOutBigData = new StuInAndOutBigData();
        int schoolAllStuNum = 0;
        int schoolAllLeaveStuNum = 0;
        int schoolAllNoInStuNum = 0;
        int schoolAllInStuNum = 0;
        List<StuInAndOutBigDataClassDetail> classDetail = new ArrayList<>();
        List<Map<String, Object>> schoolStudentNowStatusNum = studentFeign.findSchoolStudentNowStatusNum(mySchoolId());
        List<Map<String, Object>> schoolStudentNowStatusNumGroupByClassesId = studentFeign.findSchoolStudentNowStatusNumGroupByClassesId(mySchoolId());
        List<Student> notInStudents = new ArrayList<>();
        List<Student> inStudents = new ArrayList<>();
        List<Student> leaveStudents = new ArrayList<>();
        schoolAllStuNum = (int) schoolStudentNowStatusNum.get(0).get("total");
        schoolAllNoInStuNum = (int) schoolStudentNowStatusNum.get(0).get("not_in_school_num");
        schoolAllInStuNum = (int) schoolStudentNowStatusNum.get(0).get("in_school_num");
        schoolAllLeaveStuNum = (int) schoolStudentNowStatusNum.get(0).get("leave_num");
        //班级缺勤详情
        //是否在上课时间，若不是上课时间缺勤统计不进行。
        //获得当前时间
        String now = DateUtil.now();
        KqOriginalData kqOriginalData = new KqOriginalData();
        kqOriginalData.setSchoolId(mySchoolId());
        kqOriginalData.setCapturedTime(now);
        boolean notClassTime = statusOfKqOriginalData(kqOriginalData);//状态 true非上课时间  false上课时间
        //循环所有班级
        for (Map<String, Object> map : schoolStudentNowStatusNumGroupByClassesId) {
            StuInAndOutBigDataClassDetail data = new StuInAndOutBigDataClassDetail();
            TeacherClasses teacherClasses = new TeacherClasses();
            String classesId = (String) map.get("classes_id");
            teacherClasses.setClassesId(classesId);
            Teacher headmaster = teacherClassesFeign.findHeadmasterByClasses(teacherClasses);
            String grade_name = map.get("grade_name") == null ? "" : (String) map.get("grade_name");
            String number = map.get("number") == null ? null : "（"+(Integer) map.get("number")+"）班";
            String className ="";
            if (number!=null&& grade_name !=null){
                className = grade_name + number ;
           }
            data.setHeadmasterName("");
            data.setHeadmasterTel("");
            if (headmaster != null) {
                data.setHeadmasterName(headmaster.getName());
                data.setHeadmasterTel(headmaster.getTel());
            }
            data.setClassName(className);
            data.setClassAbsentStuNum(0);
            if (!notClassTime) {//上课时间进行统计
                int not_in_school_num = (int) map.get("not_in_school_num");
                data.setClassAbsentStuNum(not_in_school_num);

            }
            int leave_num = (int) map.get("leave_num");
            data.setClassLeaveStuNum(leave_num);
            classDetail.add(data);
        }
//出入校记录
        KqOriginalData kqOriginalData1 = new KqOriginalData();
        kqOriginalData1.setSchoolId(mySchoolId());
        kqOriginalData1.setDerectionFlag(Constant.KQ_ORIGINAL_DATA.DERECTION_FLAG_IS_IN);
        Pager pager = new Pager();
        pager.setPaging(true);
        pager.setPageSize(10);
        pager.setPage(1);
        String[] zone = new String[2];
        zone[0] = DateUtil.today()+" 00:00:00";
        zone[1] = DateUtil.today()+" 23:59:59";
        pager.setRangeArray(zone);
        pager.setRangeField("capturedTime");
        pager.setIncludes("id","capturedImage","derectionFlag","passStatus","gradeName","classesNumber","capturedTime","name");
        pager.setSortOrder("desc");
        pager.setSortField("capturedTime");
        kqOriginalData1.setPager(pager);
        //入校
        kqOriginalData1.setPassStatus("-1");
        List<KqOriginalData> allKqStuInOriDatas = findKqStudentOriginalDatasByCondition(kqOriginalData1);
        kqOriginalData1.setPassStatus(Constant.KQ_ORIGINAL_DATA.PASS_STATUS_WRONG);
        List<KqOriginalData> inSchoolWrongOriDatas = findKqStudentOriginalDatasByCondition(kqOriginalData1);
        kqOriginalData1.getPager().setPaging(false);
        Long inSchoolAllWrongOriDataNum = findKqOriginalDataCountByCondition(kqOriginalData1);
        //出校
        kqOriginalData1.getPager().setPaging(true);
        kqOriginalData1.setDerectionFlag(Constant.KQ_ORIGINAL_DATA.DERECTION_FLAG_IS_OUT);
        kqOriginalData1.setPassStatus("-1");
        List<KqOriginalData> allKqStuOutOriDatas = findKqStudentOriginalDatasByCondition(kqOriginalData1);
        kqOriginalData1.setPassStatus(Constant.KQ_ORIGINAL_DATA.PASS_STATUS_WRONG);
        List<KqOriginalData> outSchoolWrongOriDatas =findKqStudentOriginalDatasByCondition(kqOriginalData1);
        kqOriginalData1.getPager().setPaging(false);
        Long outSchoolAllWrongOriDataNum = findKqOriginalDataCountByCondition(kqOriginalData1);
        //List<KqOriginalData> inSchoolWrongOriDatas = allKqStuInOriDatas.stream().filter(kqOriginalData2 -> kqOriginalData2.getPassStatus()!=null&&kqOriginalData2.getPassStatus().equals(Constant.KQ_ORIGINAL_DATA.PASS_STATUS_WRONG)).collect(Collectors.toList());
        //List<KqOriginalData> outSchoolWrongOriDatas = allKqStuOutOriDatas.stream().filter(kqOriginalData2 -> kqOriginalData2.getPassStatus()!=null&&kqOriginalData2.getPassStatus().equals(Constant.KQ_ORIGINAL_DATA.PASS_STATUS_WRONG)).collect(Collectors.toList());
        //班级请假详情
        stuInAndOutBigData.setSchoolAllLeaveStuNum(schoolAllLeaveStuNum);
        stuInAndOutBigData.setSchoolAllNoInStuNum(schoolAllNoInStuNum);
        stuInAndOutBigData.setSchoolAllInStuNum(schoolAllInStuNum);
        stuInAndOutBigData.setSchoolAllStuNum(schoolAllStuNum);
        stuInAndOutBigData.setClassDetail(classDetail);
        stuInAndOutBigData.setAllInSchoolCaptureRecord(allKqStuInOriDatas);
        stuInAndOutBigData.setAllInSchoolAbnormalCaptureRecord(inSchoolWrongOriDatas);
        stuInAndOutBigData.setAllInSchoolAbnormalCaptureRecordNum(inSchoolAllWrongOriDataNum);
        stuInAndOutBigData.setAllOutSchoolCaptureRecord(allKqStuOutOriDatas);
        stuInAndOutBigData.setAllOutSchoolAbnormalCaptureRecord(outSchoolWrongOriDatas);
        stuInAndOutBigData.setAllOutSchoolAbnormalCaptureRecordNum(outSchoolAllWrongOriDataNum);
        return stuInAndOutBigData;
    }

    public boolean statusOfKqOriginalData(KqOriginalData kqOriginalData) {
        return kqOriginalDataFeign.statusOfKqOriginalData(kqOriginalData);
    }
    public List<KqOriginalData> findKqStudentOriginalDatasByCondition(KqOriginalData kqOriginalData) {
        if (kqOriginalData.getUserType()==null||kqOriginalData.getUserType().equals("")){
            kqOriginalData.setUserType("2,4");
        }
        kqOriginalData.setSchoolId(mySchoolId());
        //kqOriginalData.setDeviceFactory(Constant.KQ_ORIGINAL_DATA.DEVICE_FACTORY_ZY);
        List<KqOriginalData> data = kqOriginalDataFeign.findKqOriginalDataListByCondition(kqOriginalData);
        return data;
    }
    public Long  findKqOriginalDataCountByCondition(KqOriginalData kqOriginalData) {
        if (kqOriginalData.getUserType()==null||kqOriginalData.getUserType().equals("")){
            kqOriginalData.setUserType("2,4");
        }
        kqOriginalData.setSchoolId(mySchoolId());
        //kqOriginalData.setDeviceFactory(Constant.KQ_ORIGINAL_DATA.DEVICE_FACTORY_ZY);
        long count = kqOriginalDataFeign.findKqOriginalDataCountByCondition(kqOriginalData);
        return count;
    }

    public List<KqOriginalData> findSomeTimeAgoKqOriginalDataListByCondition(KqOriginalData kqOriginalData) {
        if (kqOriginalData.getUserType()==null||kqOriginalData.getUserType().equals("")){
            kqOriginalData.setUserType("2,4");
        }
        if (StrUtil.isEmpty(kqOriginalData.getPassStatus())){
            kqOriginalData.setPassStatus("-1");
        }
        kqOriginalData.setSchoolId(mySchoolId());
        //kqOriginalData.setDeviceFactory(Constant.KQ_ORIGINAL_DATA.DEVICE_FACTORY_ZY);
        List<KqOriginalData> data = kqOriginalDataFeign.findSomeTimeAgoKqOriginalDataListByCondition(kqOriginalData);
        return data;
    }
}
