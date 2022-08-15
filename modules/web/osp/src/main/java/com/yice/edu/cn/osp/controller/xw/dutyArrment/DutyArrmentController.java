package com.yice.edu.cn.osp.controller.xw.dutyArrment;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.dutyArrment.DutyArrment;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherService;
import com.yice.edu.cn.osp.service.xw.dutyArrment.DutyArrmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dutyArrment")
@Api(value = "/dutyArrment",description = "值班管理")
public class DutyArrmentController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private DutyArrmentService dutyArrmentService;

    @PostMapping("/saveDutyArrment")
    @ApiOperation(value = "保存常规值班安排表对象,", notes = "返回保存好的常规值班安排表对象", response=DutyArrment.class)
    public ResponseJson saveDutyArrment(
            @ApiParam(value = "新增值班对象", required = true)
            @RequestBody DutyArrment dutyArrment){
        dutyArrment.setSchoolId(mySchoolId());
        DutyArrment s=dutyArrmentService.saveDutyArrment(dutyArrment);
        return new ResponseJson(s);
    }

    @PostMapping("/look/lookDutyArrmentById/findTeachersByCondition")
    @ApiOperation(value = "根据条件查找教师信息", notes = "返回响应对象")
    public ResponseJson findTeachersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Teacher teacher) {
        teacher.setSchoolId(mySchoolId());
        teacher.setPersonType(Constant.PERSON_TYPE.TEACHER);
        teacher.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        List<Teacher> data = teacherService.findTeacherListByCondition4Like(teacher);
        return new ResponseJson(data);
    }
}
