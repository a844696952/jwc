package com.yice.edu.cn.bmp.controller.xwDormManage;

import com.yice.edu.cn.bmp.service.xwDormManage.DormPersonLogService;
import com.yice.edu.cn.bmp.service.xwDormManage.DormPersonService;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildingPersonInfo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPersonLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dormPerson")
@Api(value = "/dormPerson",description = "住宿人员模块")
public class DormPersonController {
    @Autowired
    private DormPersonService dormPersonService;
    @Autowired
    private DormPersonLogService dormPersonLogService;

    @GetMapping("/findDormPersonInfoWithStudent/{studentId}")
    @ApiOperation(value = "根据学生id查询学生住宿信息")
    public ResponseJson findDormPersonInfoWithStudent(
            @ApiParam(value = "参数:学生id studentId ", required = true)
            @PathVariable String studentId){
        DormBuildingPersonInfo dormBuildingPersonInfo = new DormBuildingPersonInfo();
        dormBuildingPersonInfo.setPersonId(studentId);
        dormBuildingPersonInfo.setSchoolId(mySchoolId());
        List<DormBuildingPersonInfo> list = dormPersonService.findDormPersonInfoWithStudent(dormBuildingPersonInfo);
        //详细信息
        DormPersonLog dormPersonLog = new DormPersonLog();
        dormPersonLog.setSchoolId(mySchoolId());
        dormPersonLog.setPersonId(studentId);
        List<DormPersonLog> data=dormPersonLogService.findDormPersonLogListByCondition(dormPersonLog);
        if (list.size()>0){
            return new ResponseJson(list.get(0),data);
        }else {
            return new ResponseJson();
        }

    }



}
