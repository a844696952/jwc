package com.yice.edu.cn.bmp.service.dm.kq;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.bmp.feignClient.dm.kq.EccStudentKqRecordFeign;
import com.yice.edu.cn.bmp.feignClient.student.StudentFeign;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.kq.ProtectedStudent;
import com.yice.edu.cn.common.util.date.DateUtils;
import com.yice.edu.cn.common.util.jmessage.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class EccStudentKqService {

    @Autowired
    private EccStudentKqRecordFeign eccStudentKqRecordFeign;

    @Autowired
    private StudentFeign studentFeign;


    /**
     * 根据时间获取考勤统计
     * @param dmClassCard 班牌信息
     * @param date  当前统计时间 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public List<ProtectedStudent> getKqStatistics(DmClassCard dmClassCard, String date){
                ProtectedStudent protectedStudent=new ProtectedStudent();
                protectedStudent.setSchoolId(dmClassCard.getSchoolId());
                protectedStudent.setClassId(dmClassCard.getClassId());
                protectedStudent.setGradeId(dmClassCard.getGradeId());
                protectedStudent.setKqEndDate(date);
                protectedStudent.setKqBeginDate(DateUtils.getOriginalDateTime(date,1));
                return  eccStudentKqRecordFeign.findCurrentKqListByCondition(protectedStudent);
    }

    /**
     * 查询一段时间内的当前学生考勤数据
     * @param protectedStudent
     * @return
     */
    public List<ProtectedStudent> findKqListByStudentId(ProtectedStudent protectedStudent){
        if(StringUtils.isNotEmpty(protectedStudent.getKqBeginDate())){
            //计算出当月最后一天
            DateTime lastDay = DateUtil.endOfMonth(DateTime.now());
            DateTime firstDay=DateUtil.beginOfMonth(DateTime.now());
            if(DateUtil.parse(protectedStudent.getKqBeginDate(),Constant.DATE_FORMATTER_DAY).isAfter(lastDay)){
                return  new ArrayList<>();
            }else if(DateUtil.parse(protectedStudent.getKqBeginDate(),Constant.DATE_FORMATTER_DAY).isAfterOrEquals(firstDay) && DateUtil.parse(protectedStudent.getKqBeginDate(),Constant.DATE_FORMATTER_DAY)
                .isBeforeOrEquals(lastDay)){
                protectedStudent.setKqEndDate(DateUtil.format(DateTime.now(),"yyyy-MM-dd HH:mm:ss"));
            }else{
                DateTime dateTime = DateUtil.endOfMonth(DateUtil.parse(protectedStudent.getKqBeginDate(), Constant.DATE_FORMATTER_DAY));
                protectedStudent.setKqEndDate(DateUtil.format(dateTime,Constant.DATE_FORMATTER));
            }
            String kqBeginDate= DateUtils.getMonthOfDate(protectedStudent.getKqBeginDate());
            if(StringUtils.isNotEmpty(kqBeginDate)){
                protectedStudent.setKqBeginDate(kqBeginDate);
            }
        }
        return  eccStudentKqRecordFeign.findKqListByStudentId(protectedStudent);
    }


    /**
     * 查询一段时间内的考勤统计
     * @param protectedStudent
     * @return
     */
    public List<ProtectedStudent> findStudentKqByCondition(ProtectedStudent protectedStudent){
       return eccStudentKqRecordFeign.findStudentKqByCondition(protectedStudent);
    }


}
