package com.yice.edu.cn.dm.service.modeManage;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.dm.modeManage.ExamTask;
import com.yice.edu.cn.common.pojo.jw.classSchedule.ClassScheduleInit;
import com.yice.edu.cn.common.util.date.DateUtils;
import com.yice.edu.cn.common.util.jmessage.utils.StringUtils;
import com.yice.edu.cn.dm.service.classCard.DmClassCardService;
import com.yice.edu.cn.dm.service.classSchedule.ClassScheduleInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendClassModeService {

    @Autowired
    private ModeTaskService modeTaskService;

    @Autowired
    private DmClassCardService dmClassCardService;

    @Autowired
    private ClassScheduleInitService classScheduleInitService;

    @Autowired
    private SequenceId sequenceId;

    public List<String> openAttendClassMode(String schoolId){
        //1.根据schoolId查询班牌所有设备别名
        List<String> userNames = dmClassCardService.findUserNamesBySchoolId(schoolId);
        //2.根据schoolId查询课程时间
        List<ClassScheduleInit> classScheduleInitList = classScheduleInitService.findListClassScheduleInitBySchool(schoolId);
        List<ClassScheduleInit> scheduleInits = classScheduleInitList.stream().filter(c -> c.getNumber() == 1).collect(Collectors.toList());
        ExamTask examTask=new ExamTask();
        examTask.setUserName(userNames);
        examTask.setTaskType(3);
        examTask.setStatus(1);
        examTask.setTaskId(sequenceId.nextId());
        examTask.setSchoolId(schoolId);
        if(StringUtils.isNotEmpty(scheduleInits.get(0).getStartTime())){
            examTask.setBeginTime(DateUtils.getClassTime(scheduleInits.get(0).getStartTime()));
        }
        examTask.setPushDate(null);
        modeTaskService.sendModeMsg(examTask);
        List<ExamTask> classModeTask = modeTaskService.findClassModeTask(schoolId);
        if(CollUtil.isNotEmpty(classModeTask)){
            modeTaskService.updateClassModeTask(examTask);
        }else{
            modeTaskService.addExamTask(examTask);
        }
        return userNames;
    }

    public List<String> closeAttendClassMode(String schoolId){
        List<String> userNames = dmClassCardService.findUserNamesBySchoolId(schoolId);
        ExamTask examTask=new ExamTask();
        examTask.setUserName(userNames);
        examTask.setTaskType(3);
        examTask.setStatus(2);
        examTask.setSchoolId(schoolId);
        modeTaskService.sendModeMsg(examTask);
        modeTaskService.updateClassModeTask(examTask);
        return userNames;
    }



}
