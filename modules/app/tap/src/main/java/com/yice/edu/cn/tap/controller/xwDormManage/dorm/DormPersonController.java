package com.yice.edu.cn.tap.controller.xwDormManage.dorm;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildingPersonInfo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPerson;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPersonLog;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPersonOut;
import com.yice.edu.cn.tap.service.xwDormManage.dorm.DormPersonLogService;
import com.yice.edu.cn.tap.service.xwDormManage.dorm.DormPersonOutService;
import com.yice.edu.cn.tap.service.xwDormManage.dorm.DormPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dormPerson")
@Api(value = "/dormPerson",description = "住宿人员模块")
public class DormPersonController {
    @Autowired
    private DormPersonService dormPersonService;
    @Autowired
    private DormPersonLogService dormPersonLogService;
    @Autowired
    private DormPersonOutService dormPersonOutService;


    @PostMapping("/findDormPersonListByConditionConnect")
    @ApiOperation(value = "根据条件连表查询人员入住信息", notes = "返回人员入住信息列表",response = DormPerson.class)
    public ResponseJson findDormPersonListByConditionConnect(
            @ApiParam(value = "参数:宿舍id dormId,宿舍类别 dormCategory")
            @Validated
            @RequestBody
            DormBuildingPersonInfo dormBuildingPersonInfo) {
        if(dormBuildingPersonInfo.getDormCategory()!=null){
            dormBuildingPersonInfo.setSchoolId(mySchoolId());
            List<DormBuildingPersonInfo> dormPersonListByConditionConnect = dormPersonService.findDormPersonListByConditionConnect(dormBuildingPersonInfo);
            if (dormBuildingPersonInfo.getDormCategory().equals(Constant.DORM_CATEGORY.DORM_STUDENT)) {
                long count = dormPersonService.findDormPersonInfoCountWithStudent(dormBuildingPersonInfo);
                return new ResponseJson(dormPersonListByConditionConnect,count);
            }else {
                long count = dormPersonService.findDormPersonInfoCountWithTeacher(dormBuildingPersonInfo);
                return new ResponseJson(dormPersonListByConditionConnect,count);
            }
        }else {
            return new ResponseJson();
        }

    }


    @PostMapping("/findDormPersonLogListByCondition")
    @ApiOperation(value = "根据条件查找宿舍人员入住信息详情", notes = "返回响应对象,不包含总条数", response=DormPersonLog.class)
    public ResponseJson findDormPersonLogListByCondition(
            @ApiParam(value = "查询人员入住记录,personId必传,属性不为空则作为条件查询")
            @RequestBody DormPersonLog dormPersonLog){
        dormPersonLog.setSchoolId(mySchoolId());
        List<DormPersonLog> data=dormPersonLogService.findDormPersonLogListByCondition(dormPersonLog);
        return new ResponseJson(data);
    }

    @PostMapping("/out/findDormPersonOutListByConditionWithStudent")
    @ApiOperation(value = "根据条件查找退宿学生人员列表", notes = "返回响应对象", response=DormPersonLog.class)
    public ResponseJson findDormPersonOutListByConditionWithStudent(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DormPersonOut dormPersonOut){
        dormPersonOut.setSchoolId(mySchoolId());
        dormPersonOut.setPersonType("1");
        Pager pager = dormPersonOut.getPager();
        pager.setSortField("outTime");
        pager.setSortOrder(Pager.DESC);
        dormPersonOut.setPager(pager);
        List<DormPersonOut> data=dormPersonOutService.findDormPersonOutListByConditionAndPersonType(dormPersonOut);
        long count = dormPersonOutService.findDormPersonOutCountByConditionAndPersonType(dormPersonOut);
        return new ResponseJson(data,count);
    }

    @PostMapping("/out/findDormPersonOutListByConditionWithTeacher")
    @ApiOperation(value = "根据条件查找退宿教师及非教职工人员列表", notes = "返回响应对象", response=DormPersonLog.class)
    public ResponseJson findDormPersonOutListByConditionWithTeacher(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DormPersonOut dormPersonOut){
        dormPersonOut.setSchoolId(mySchoolId());
        dormPersonOut.setPersonType("2");
        Pager pager = dormPersonOut.getPager();
        pager.setSortField("outTime");
        pager.setSortOrder(Pager.DESC);
        dormPersonOut.setPager(pager);
        List<DormPersonOut> data=dormPersonOutService.findDormPersonOutListByConditionAndPersonType(dormPersonOut);
        long count = dormPersonOutService.findDormPersonOutCountByConditionAndPersonType(dormPersonOut);
        return new ResponseJson(data,count);
    }

    @PostMapping("/info/findDormPersonInfoWithStudent")
    @ApiOperation(value = "班主任查询学生住宿信息")
    public ResponseJson findDormPersonInfoWithStudent(
            @ApiParam(value = "参数:班级id classesId ", required = true)
            @RequestBody DormBuildingPersonInfo dormBuildingPersonInfo){
        if (dormBuildingPersonInfo!=null&&dormBuildingPersonInfo.getClassesId()!=null){
            dormBuildingPersonInfo.setSchoolId(mySchoolId());
            List<DormBuildingPersonInfo> list = dormPersonService.findDormPersonInfoWithStudent(dormBuildingPersonInfo);
            return new ResponseJson(list);
        }else {
            return new ResponseJson();
        }

    }



}
