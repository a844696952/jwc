package com.yice.edu.cn.yed.controller.jw.yedHomePage;


import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.yed.*;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkCountQueryVo;
import com.yice.edu.cn.common.pojo.jy.homework.HomeworkCountViewVo;
import com.yice.edu.cn.yed.controller.frame.login.LoginController;
import com.yice.edu.cn.yed.service.jw.yedHomePage.YedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 教育局领导查看的大屏展示数据
 */
@RestController
@RequestMapping("/yedHomePage")
@Api(value = "/yedHomePage",description = "yed大屏展示")
public class YedController {
    @Autowired
    private YedService yedService;

    @GetMapping("/ignore/findNewbornList")
    @ApiOperation("返回新生人数占比")
    public ResponseJson findNewbornList(){
        return  new ResponseJson(yedService.findNewbornList());
    }

    @GetMapping("/ignore/findJournalStatic")
    @ApiOperation("返回日志统计数据(大屏右上角3个圈)")
    public ResponseJson findJournalStatic(){
        List<JournalCircle> journalCircles=yedService.findJournalStatic();
        return new ResponseJson(journalCircles);
    }

    @GetMapping("/ignore/findStudentCheckWork")
    @ApiOperation("查询学生考勤情况")
    public ResponseJson findStudentCheckWork(){
        StudentCheckWork checkWork=yedService.findStudentCheckWork();
        return new ResponseJson(checkWork);
    }

    @GetMapping("/ignore/findSpaceByRoleList")
    @ApiOperation("返回教学装备部署情况")
    public  ResponseJson findSpaceByRoleList(){
        List<Yed> list = yedService.findSpaceByRoleList();
        return  new ResponseJson(list);
    }

    @GetMapping("/ignore/findSchoolNotifiedListByCondition")
    @ApiOperation("返回学校重大通知事报")
    public ResponseJson findSchoolNotifiedListByCondition() {
        SchoolNotified schoolNotified = new SchoolNotified();
        Pager pager = new Pager();
        pager.setPageSize(50);
        pager.setSortField("createTime");
        pager.setSortOrder("desc");
        schoolNotified.setPager(pager);
        List<SchoolNotified> schoolNotifieds = yedService.findSchoolNotifiedListByCondition(schoolNotified);
        return new ResponseJson(schoolNotifieds);
    }
    @GetMapping("/ignore/findTaskSituation") //每月作业情况
    @ApiOperation("返回每月作业情况数据")
    public ResponseJson findTaskSituation(){
      List<AreaOperationVolume>AreaOperationVolumeList=yedService.findTaskSituation();
      return new ResponseJson(AreaOperationVolumeList);
    }



    @GetMapping("/ignore/findEnrolmentList")
    @ApiOperation("返回教育局管辖下近三年的升学率")
    public ResponseJson findEnrolmentList(){
        return new ResponseJson(yedService.findEnrolmentList());
    }
}

