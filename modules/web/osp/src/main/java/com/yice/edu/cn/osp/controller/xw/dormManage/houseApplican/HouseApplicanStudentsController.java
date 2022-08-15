package com.yice.edu.cn.osp.controller.xw.dormManage.houseApplican;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildAdmin;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.HouseApplicanStudents;
import com.yice.edu.cn.osp.service.xw.dormManage.dorm.DormBuildAdminService;
import com.yice.edu.cn.osp.service.xw.dormManage.houseApplican.HouseApplicanStudentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/houseApplicanStudents")
@Api(value = "/houseApplicanStudents",description = "模块")
public class HouseApplicanStudentsController {
    @Autowired
    private HouseApplicanStudentsService houseApplicanStudentsService;

    @Autowired
    private DormBuildAdminService dormBuildAdminService;

    @PostMapping("/saveHouseApplicanStudents")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response= HouseApplicanStudents.class)
    public ResponseJson saveHouseApplicanStudents(
            @ApiParam(value = "对象", required = true)
            @RequestBody HouseApplicanStudents houseApplicanStudents){
       houseApplicanStudents.setSchoolId(mySchoolId());
        HouseApplicanStudents s=houseApplicanStudentsService.saveHouseApplicanStudents(houseApplicanStudents);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findHouseApplicanStudentsById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=HouseApplicanStudents.class)
    public ResponseJson findHouseApplicanStudentsById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        HouseApplicanStudents houseApplicanStudents=houseApplicanStudentsService.findHouseApplicanStudentsById(id);
        return new ResponseJson(houseApplicanStudents);
    }

    @PostMapping("/update/updateHouseApplicanStudents")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateHouseApplicanStudents(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody HouseApplicanStudents houseApplicanStudents){
        houseApplicanStudentsService.updateHouseApplicanStudents(houseApplicanStudents);
        return new ResponseJson();
    }

    @GetMapping("/look/lookHouseApplicanStudentsById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=HouseApplicanStudents.class)
    public ResponseJson lookHouseApplicanStudentsById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        HouseApplicanStudents houseApplicanStudents=houseApplicanStudentsService.findHouseApplicanStudentsById(id);
        return new ResponseJson(houseApplicanStudents);
    }

    @PostMapping("/findHouseApplicanStudentssByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=HouseApplicanStudents.class)
    public ResponseJson findHouseApplicanStudentssByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody HouseApplicanStudents houseApplicanStudents){
       houseApplicanStudents.setSchoolId(mySchoolId());
        List<HouseApplicanStudents> data=houseApplicanStudentsService.findHouseApplicanStudentsListByCondition(houseApplicanStudents);
        long count=houseApplicanStudentsService.findHouseApplicanStudentsCountByCondition(houseApplicanStudents);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneHouseApplicanStudentsByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=HouseApplicanStudents.class)
    public ResponseJson findOneHouseApplicanStudentsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody HouseApplicanStudents houseApplicanStudents){
        HouseApplicanStudents one=houseApplicanStudentsService.findOneHouseApplicanStudentsByCondition(houseApplicanStudents);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteHouseApplicanStudents/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteHouseApplicanStudents(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        houseApplicanStudentsService.deleteHouseApplicanStudents(id);
        return new ResponseJson();
    }


    @PostMapping("/findHouseApplicanStudentsListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=HouseApplicanStudents.class)
    public ResponseJson findHouseApplicanStudentsListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody HouseApplicanStudents houseApplicanStudents){
       houseApplicanStudents.setSchoolId(mySchoolId());
        List<HouseApplicanStudents> data=houseApplicanStudentsService.findHouseApplicanStudentsListByCondition(houseApplicanStudents);
        return new ResponseJson(data);
    }


    @PostMapping("/findHouseApplicanStudents")
    @ApiOperation(value = "住宿申请名单", notes = "返回响应对象", response=HouseApplicanStudents.class)
    public ResponseJson findHouseApplicanStudents(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody HouseApplicanStudents houseApplicanStudents){
        DormBuildAdmin dormBuildAdmin = new DormBuildAdmin();
        dormBuildAdmin.setSchoolId(mySchoolId());
        dormBuildAdmin.setStaffId(myId());
        List<DormBuildAdmin> dormBuildAdminListByCondition = dormBuildAdminService.findDormBuildAdminListByCondition(dormBuildAdmin);
        //宿舍老师 0.宿管人员,  1.宿管老师
        List<DormBuildAdmin> collect2 = dormBuildAdminListByCondition.stream().filter(ele -> ele.getStaffType().equals("1")).collect(Collectors.toList());
        if (collect2.size()> 0 ){
            houseApplicanStudents.setSchoolId(mySchoolId());
            List<HouseApplicanStudents> data=houseApplicanStudentsService.findHouseApplicanStudents(houseApplicanStudents);
            long count=houseApplicanStudentsService.findHouseApplicanStudentsCount(houseApplicanStudents);
            return new ResponseJson(data,count);
        }else
            return new ResponseJson();

    }

    @GetMapping("/look/lookHouseApplicanStudentsByhouseApplicanId/{houseApplicanId}")
    @ApiOperation(value = "住宿申请名单,未分配 houseApplicanId", notes = "返回响应对象", response=HouseApplicanStudents.class)
    public ResponseJson lookHouseApplicanStudentsByhouseApplicanId(
            @ApiParam(value = "去查看页面 houseApplicanId", required = true)
            @PathVariable String houseApplicanId){
        HouseApplicanStudents houseApplicanStudents=houseApplicanStudentsService.lookHouseApplicanStudentsByhouseApplicanId(houseApplicanId);
        return new ResponseJson(houseApplicanStudents);
    }


    @PostMapping("/saveHouseApplicanStudents1")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response= HouseApplicanStudents.class)
    public ResponseJson saveHouseApplicanStudents1(
            @ApiParam(value = "对象", required = true)
            @RequestBody HouseApplicanStudents houseApplicanStudents){
        houseApplicanStudents.setSchoolId(mySchoolId());
        HouseApplicanStudents s=houseApplicanStudentsService.saveHouseApplicanStudents1(houseApplicanStudents);
        return new ResponseJson(s);
    }


    @PostMapping("/look/lookHouseApplicanStudents")
    @ApiOperation(value = "住宿申请名单,已分配 houseApplicanId", notes = "返回响应对象", response=HouseApplicanStudents.class)
    public ResponseJson lookHouseApplicanStudents(
            @ApiParam(value = "去查看页面 houseApplicanId", required = true)
            @RequestBody HouseApplicanStudents houseApplicanStudents){
        List<HouseApplicanStudents> data=houseApplicanStudentsService.lookHouseApplicanStudents(houseApplicanStudents);
        long count=houseApplicanStudentsService.lookHouseApplicanStudentsCount(houseApplicanStudents.getHouseApplicanId());
        return new ResponseJson(data,count);
    }

    @GetMapping("exportHouseApplicanStudent/{houseApplicanId}")
    @ApiOperation(value = "导出 传houseApplicanId", notes = "返回响应对象", response=HouseApplicanStudents.class)
    public void exportHouseApplicanStudent(HttpServletResponse response, @PathVariable String houseApplicanId) {
        //Workbook w = teacherService.exportTeacher();
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=assetsSort.xls");

        try (ServletOutputStream s = response.getOutputStream()) {
            Workbook workbook = houseApplicanStudentsService.export(houseApplicanId);
            workbook.write(s);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
