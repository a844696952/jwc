package com.yice.edu.cn.ewb.controller.homework;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.homework.Homework;
import com.yice.edu.cn.common.pojo.jy.homework.app.HomeworkEwbVo;
import com.yice.edu.cn.ewb.service.homework.HomeworkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.yice.edu.cn.ewb.interceptor.LoginInterceptor.myId;

@RestController
@RequestMapping("/homework")
@Api(value = "/homework",description = "教师作业模块")
public class HomeworkController {
   @Autowired
   private HomeworkService homeworkService;

    @GetMapping("/findHomeworkById/{id}")
    @ApiOperation(value = "通过作业id查找布置作业", notes = "返回响应对象-Homework")
    public ResponseJson findHomeworkById(
            @ApiParam(value = "需要用到的作业id", required = true)
            @PathVariable String id){
        Homework homework=homeworkService.findHomeworkById(id);
        return new ResponseJson(homework);
    }

    @PostMapping("/findHomeworkListByConditionEwb")
    @ApiOperation(value = "根据条件查找布置作业列表", notes = "返回查询作业列表-Homework")
    public ResponseJson findHomeworkListByConditionEwb(
            @ApiParam(value = "{\n" +
                    "    \"page\": 0\n" +
                    "    \"pageSize\": 0\n" +
                    "    \"sortField\": \"  \"\n" +
                    "    \"sortOrder\": \"desc\"\n" +
                    "    \"startTime\": \"yyyy-MM-dd hh:mm:ss\"\n" +
                    "    \"endTime\": \"yyyy-MM-dd hh:mm:ss\"\n" +
                    "}")
            @Validated
            @RequestBody HomeworkEwbVo homeworkEwbVo){
        Homework homework = new Homework();
        homework.setPager(homeworkEwbVo.getPager());
        homework.setPublishStatus(Constant.HOMEWORK.PUBLISH_ON);
        homework.setGradeId(homeworkEwbVo.getGradeId());
        homework.setClassesId(homeworkEwbVo.getClassesId());
        homework.setSubjectId(homeworkEwbVo.getSubjectId());
        homework.setTeacherId(myId());
        Date date;
        Date startTime;
        Date endTime;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String staTime;
        String eTime;
        switch (homeworkEwbVo.getType()){
            case 0:
                break;
            case 1:
                date=DateUtil.date();
                startTime = getStartTime(date);
                endTime = getEndTime(date);
                staTime=df.format(startTime);
                eTime=df.format(endTime);
                homework.setEwbStartTime(staTime);
                homework.setEwbEndTime(eTime);
                break;
            case 2:
                Date yesterday = DateUtil.yesterday();
                startTime = getStartTime(yesterday);
                endTime = getEndTime(yesterday);
                staTime=df.format(startTime);
                eTime=df.format(endTime);
                homework.setEwbStartTime(staTime);
                homework.setEwbEndTime(eTime);
                break;
            case 3:
                date=DateUtil.date();
                Date twoDay = getBeforeOrAfterDate(date, -2);
                startTime = getStartTime(twoDay);
                endTime = getEndTime(date);
                staTime=df.format(startTime);
                eTime=df.format(endTime);
                homework.setEwbStartTime(staTime);
                homework.setEwbEndTime(eTime);
                break;
            case 4:
                homework.setEwbStartTime(homeworkEwbVo.getStartTime());
                homework.setEwbEndTime(homeworkEwbVo.getEndTime());
                break;
        }
        List<Homework> data=homeworkService.findHomeworkListByConditionEwb(homework);
        return new ResponseJson(data);
    }
    /**
     * 获取每天的开始时间 00:00:00:00
     *
     * @param date
     * @return
     */
    public static Date getStartTime(Date date) {
        Calendar dateStart = Calendar.getInstance();
        dateStart.setTime(date);
        dateStart.set(Calendar.HOUR_OF_DAY, 0);
        dateStart.set(Calendar.MINUTE, 0);
        dateStart.set(Calendar.SECOND, 0);
        return dateStart.getTime();
    }

    /**
     * 获取每天的开始时间 23:59:59:999
     *
     * @param date
     * @return
     */
    public static Date getEndTime(Date date) {
        Calendar dateEnd = Calendar.getInstance();
        dateEnd.setTime(date);
        dateEnd.set(Calendar.HOUR_OF_DAY, 23);
        dateEnd.set(Calendar.MINUTE, 59);
        dateEnd.set(Calendar.SECOND, 59);
        return dateEnd.getTime();
    }
    public  Date getBeforeOrAfterDate(Date date, int num) {
        Calendar calendar = Calendar.getInstance();//获取日历
        calendar.setTime(date);//当date的值是当前时间，则可以不用写这段代码。
        calendar.add(Calendar.DATE, num);
        Date d = calendar.getTime();//把日历转换为Date
        return d;
    }
}
