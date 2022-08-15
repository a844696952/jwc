package com.yice.edu.cn.tap.controller.teacher;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.teacherattendance.XwTeacherImageInput;
import com.yice.edu.cn.common.util.FileTypeUtil;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.tap.service.kqsdk.DeviceManageService;
import com.yice.edu.cn.tap.service.teacher.XwTeacherImageInputService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/xwTeacherImageInput")
@Api(value = "/xwTeacherImageInput", description = "教师图像录入app端接口模块")
public class XwTeacherImageInputController {
    @Autowired
    private XwTeacherImageInputService xwTeacherImageInputService;
    @Autowired
    private DeviceManageService deviceManageService;
    @Autowired
    private FileTypeUtil fileTypeUtil;

    @PostMapping("/saveXwTeacherImageInput")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = XwTeacherImageInput.class)
    public ResponseJson saveXwTeacherImageInput(
            @ApiParam(value = "对象", required = true)
            @RequestBody XwTeacherImageInput xwTeacherImageInput) {
        try {
            xwTeacherImageInput.setSchoolId(mySchoolId());
            //调用打卡机sdk验证能否打卡成功
            XwTeacherImageInput xwTeacher = deviceManageService.setOneCardAndFace(xwTeacherImageInput);
            if (!StringUtils.isEmpty(xwTeacher)) {
                return new ResponseJson(false, xwTeacher.getMachineStatus());
            } else {
                XwTeacherImageInput xp = new XwTeacherImageInput();
                xp.setSchoolId(mySchoolId());
                xp.setTeacherId(xwTeacherImageInput.getTeacherId());
                XwTeacherImageInput img = xwTeacherImageInputService.findOneXwTeacherImageInputByCondition(xp);
                //已经上传
                if (!StringUtils.isEmpty(img)) {
                    return new ResponseJson(false, "请勿重复上传！");
                }
                //成功，1为已经上传的标志
                xwTeacherImageInput.setStatus("1");
                XwTeacherImageInput xw = xwTeacherImageInputService.saveXwTeacherImageInput(xwTeacherImageInput);
                return new ResponseJson(true, "上传成功");
            }
        } catch (Exception e) {
            return new ResponseJson(false, "设备异常，请重试！");
        }
    }

    @GetMapping("/update/findXwTeacherImageInputById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response = XwTeacherImageInput.class)
    public ResponseJson findXwTeacherImageInputById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        XwTeacherImageInput xwTeacherImageInput = xwTeacherImageInputService.findXwTeacherImageInputById(id);
        return new ResponseJson(xwTeacherImageInput);
    }

    @PostMapping("/update/updateXwTeacherImageInput")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateXwTeacherImageInput(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody XwTeacherImageInput xwTeacherImageInput) {
        try {
            xwTeacherImageInput.setSchoolId(mySchoolId());
            //调用打卡机sdk验证能否打卡成功
            XwTeacherImageInput xwTeacher = deviceManageService.setOneCardAndFace(xwTeacherImageInput);
            if (!StringUtils.isEmpty(xwTeacher)) {
                return new ResponseJson(false, xwTeacher.getMachineStatus());
            } else {
                //成功
                xwTeacherImageInput.setStatus("1");
                xwTeacherImageInputService.updateXwTeacherImageInput(xwTeacherImageInput);
                return new ResponseJson(true, "修改成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseJson(false, "设备异常");
        }

    }

    @GetMapping("/look/lookXwTeacherImageInputById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response = XwTeacherImageInput.class)
    public ResponseJson lookXwTeacherImageInputById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        XwTeacherImageInput xwTeacherImageInput = xwTeacherImageInputService.findXwTeacherImageInputById(id);
        return new ResponseJson(xwTeacherImageInput);
    }

    @GetMapping("/look/lookXwTeacherImageInputByTeacherId/{teacherId}")
    @ApiOperation(value = "去查看页面,通过teacherId查找", notes = "返回响应对象", response = XwTeacherImageInput.class)
    public ResponseJson lookXwTeacherImageInputByTeacherId(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable("teacherId") String teacherId) {
        List<XwTeacherImageInput> list = xwTeacherImageInputService.findXwTeacherImageInputByTeacherId(teacherId, mySchoolId());
        ////过滤掉中移动的数据（ststus为2就是通过中移动录入的数据）
        List<XwTeacherImageInput> collect =
                list.stream().filter(obj -> Optional.ofNullable(obj.getStatus()).orElse("0").indexOf("2") == -1).collect(Collectors.toList());
        return new ResponseJson(collect);
    }

    @PostMapping("/findXwTeacherImageInputsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response = XwTeacherImageInput.class)
    public ResponseJson findXwTeacherImageInputsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwTeacherImageInput xwTeacherImageInput) {
        xwTeacherImageInput.setSchoolId(mySchoolId());
        List<XwTeacherImageInput> data = xwTeacherImageInputService.findXwTeacherImageInputListByCondition(xwTeacherImageInput);
        long count = xwTeacherImageInputService.findXwTeacherImageInputCountByCondition(xwTeacherImageInput);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneXwTeacherImageInputByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response = XwTeacherImageInput.class)
    public ResponseJson findOneXwTeacherImageInputByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody XwTeacherImageInput xwTeacherImageInput) {
        XwTeacherImageInput one = xwTeacherImageInputService.findOneXwTeacherImageInputByCondition(xwTeacherImageInput);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteXwTeacherImageInput/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteXwTeacherImageInput(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        xwTeacherImageInputService.deleteXwTeacherImageInput(id);
        return new ResponseJson();
    }


    @PostMapping("/findXwTeacherImageInputListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response = XwTeacherImageInput.class)
    public ResponseJson findXwTeacherImageInputListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwTeacherImageInput xwTeacherImageInput) {
        xwTeacherImageInput.setSchoolId(mySchoolId());
        List<XwTeacherImageInput> data = xwTeacherImageInputService.findXwTeacherImageInputListByCondition(xwTeacherImageInput);
        return new ResponseJson(data);
    }

    @PostMapping("/findXwTeacherImageInputListAll")
    @ApiOperation(value = "查询全表信息", notes = "返回所有对象", response = XwTeacherImageInput.class)
    public ResponseJson findXwTeacherImageInputListAll() {
        List<XwTeacherImageInput> data = xwTeacherImageInputService.findXwTeacherImageInputListAll(mySchoolId());
        long count = xwTeacherImageInputService.findXwTeacherImageInputListAllCount(mySchoolId());
        return new ResponseJson(data, count);
    }

    @PostMapping("/findXwTeacherImageInputByTeacherName")
    @ApiOperation(value = "根据教师查询信息", notes = "返回所有对象", response = XwTeacherImageInput.class)
    public ResponseJson findXwTeacherImageInputByTeacherName(
            @ApiParam(value = "教师名称")
            @Validated
            @RequestBody XwTeacherImageInput xwTeacherImageInput
    ) {
        if (xwTeacherImageInput.getTeacherName() == null) return new ResponseJson();
        List<XwTeacherImageInput> xwTeacher = xwTeacherImageInputService.
                findXwTeacherImageInputByTeacherName(xwTeacherImageInput.getTeacherName(), mySchoolId());

        long count = xwTeacherImageInputService.findXwTeacherImageInputByTeacherNameCount
                (xwTeacherImageInput.getTeacherName(), mySchoolId());
        return new ResponseJson(xwTeacher, count);
    }

    @PostMapping("/uploadNetWorkpng")
    @ApiOperation(value = "单个图片上传", notes = "返回图片路径字符串")
    public ResponseJson uploadNetWorkpng(@RequestParam("file") MultipartFile file) {
        String path = QiniuUtil.commonUploadFile(file, Constant.Upload.UPLOAD_NETWORK + "jpg");
        return new ResponseJson(path);
    }

}
