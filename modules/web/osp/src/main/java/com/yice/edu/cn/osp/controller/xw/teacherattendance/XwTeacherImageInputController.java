package com.yice.edu.cn.osp.controller.xw.teacherattendance;

import cn.hutool.core.util.NumberUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.teacherattendance.XwTeacherImageInput;
import com.yice.edu.cn.common.util.FileTypeUtil;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherService;
import com.yice.edu.cn.osp.service.kqsdk.DeviceManageService;
import com.yice.edu.cn.osp.service.xw.teacherattendance.XwTeacherImageInputService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/xwTeacherImageInput")
@Api(value = "/xwTeacherImageInput", description = "模块")
public class XwTeacherImageInputController {
    @Autowired
    private XwTeacherImageInputService xwTeacherImageInputService;
    @Autowired
    private DeviceManageService deviceManageService;
    @Autowired
    private FileTypeUtil fileTypeUtil;
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/look/lookXwTeacherImageInputByTeacherId/{teacherId}")
    @ApiOperation(value = "去查看页面,通过teacherId查找", notes = "返回响应对象", response = XwTeacherImageInput.class)
    public ResponseJson lookXwTeacherImageInputByTeacherId(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable("teacherId") String teacherId) {
        List<XwTeacherImageInput> list = xwTeacherImageInputService.findXwTeacherImageInputByTeacherId(teacherId, mySchoolId());
        return new ResponseJson(list);
    }

    @PostMapping("/saveXwTeacherImageInput")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = XwTeacherImageInput.class)
    public ResponseJson saveXwTeacherImageInput(
            @ApiParam(value = "对象", required = true)
            @RequestBody XwTeacherImageInput xwTeacherImageInput) {
        try {
            xwTeacherImageInput.setSchoolId(mySchoolId());
            //调用打卡机sdk验证能否打卡成功
            XwTeacherImageInput xw = deviceManageService.setOneCardAndFace(xwTeacherImageInput);
            if (!StringUtils.isEmpty(xw)) {
                return new ResponseJson(false, xw.getMachineStatus());
            } else {
                //成功
                xwTeacherImageInput.setStatus("1");
                XwTeacherImageInput s = xwTeacherImageInputService.saveXwTeacherImageInput(xwTeacherImageInput);
                return new ResponseJson(true, "上传成功");
            }
        } catch (Exception e) {
            return new ResponseJson(false, "设备异常！");
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
            XwTeacherImageInput xw = deviceManageService.setOneCardAndFace(xwTeacherImageInput);
            if (!StringUtils.isEmpty(xw)) {
                return new ResponseJson(false, xw.getMachineStatus());
            } else {
                //成功
                xwTeacherImageInput.setStatus("1");
                xwTeacherImageInputService.updateXwTeacherImageInput(xwTeacherImageInput);
                return new ResponseJson(true, "修改成功");
            }
        } catch (Exception e) {
            return new ResponseJson(false, "设备异常！");
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
    public ResponseJson findXwTeacherImageInputListAll(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwTeacherImageInput xwTeacherImageInput) {
        xwTeacherImageInput.setSchoolId(mySchoolId());
        //查询全部信息之前，对比教师表和图像录入信息表的信息是否统一，若教师辞职，就删除对应图像录入表中数据
//        List<String> xwIdList = xwTeacherImageInputService.findXwTeacherleft(mySchoolId());
//        if (xwIdList.size() != 0)
//            xwIdList.stream().forEach(id -> xwTeacherImageInputService.deleteXwTeacherImageInput(id));

        List<XwTeacherImageInput> data = xwTeacherImageInputService.findXwTeacherImageInputListAlls(xwTeacherImageInput);
        //过滤掉中移动的数据（ststus为2就是通过中移动录入的数据）
        List<XwTeacherImageInput> newList =
                data.stream().filter(obj -> Optional.ofNullable(obj.getStatus()).orElse("0").indexOf("2") == -1).collect(Collectors.toList());
        long count = xwTeacherImageInputService.findXwTeacherImageInputListAllCount(mySchoolId(), Constant.TEACHER_IMAGE_IMPUT.HK_STATUS);
        return new ResponseJson(newList, count);
    }

    @GetMapping("/findXwTeacherImageInputByTeacherName/{teacherName}")
    @ApiOperation(value = "根据教师名称查询信息", notes = "返回所有对象", response = XwTeacherImageInput.class)
    public ResponseJson findXwTeacherImageInputByTeacherName(
            @ApiParam(value = "教师名称", required = true)
            @PathVariable("teacherName") String teacherName) {
        if (teacherName == null) return new ResponseJson(false);
        List<XwTeacherImageInput> xwTeacher = xwTeacherImageInputService.
                findXwTeacherImageInputByTeacherName(teacherName, mySchoolId());

        long count = xwTeacherImageInputService.findXwTeacherImageInputByTeacherNameCount
                (teacherName, mySchoolId());
        return new ResponseJson(xwTeacher, count);
    }

    @PostMapping("/uploadImg")
    public String uploadAvatar(MultipartFile file) {
        return QiniuUtil.commonUploadFile(file, Constant.Upload.UPLOAD_NETWORK + "jpg");
    }

    @PostMapping("/uploadQiniuFile")
    @ApiOperation(value = "创建时间：2018-10-29。说明：上传文件到七牛", notes = "返回资源名称和资源的url")
    public ResponseJson uploadQiniuFile(@ApiParam(value = "上传到七牛的文件file", required = true) MultipartFile file) {
        //不包含文件后缀名
        List<Teacher> teacherList;
        String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
        System.out.println(NumberUtil.isNumber(fileName) + "======");
        if (NumberUtil.isNumber(fileName)) {
            Teacher teacher = new Teacher();
            teacher.setSchoolId(mySchoolId());
            teacher.setWorkNumber(fileName);
            teacherList = teacherService.findTeacherListByCondition(teacher);
            if (teacherList.size() == 0) {
                return new ResponseJson(false, "工号不存在");
            }
        } else {
            return new ResponseJson(false, "工号非法，文件名工号只能是数字");
        }
        //检验重复
        String teacherId = teacherList.get(0).getId();
        XwTeacherImageInput xwTeacherImageInput = new XwTeacherImageInput();
        xwTeacherImageInput.setTeacherId(teacherId);
        xwTeacherImageInput.setSchoolId(mySchoolId());
        XwTeacherImageInput xw = xwTeacherImageInputService.findOneXwTeacherImageInputByCondition(xwTeacherImageInput);
        if (xw != null) {
            return new ResponseJson(false, "图像上传重复！");
        }

        Map<String, String> map = new HashMap<>();
        String url = QiniuUtil.commonUploadFile(file, Constant.Upload.UPLOAD_NETWORK + "jpg");
        map.put("img", url);
        map.put("schoolId", mySchoolId());
        map.put("teacherName", teacherList.get(0).getName());
        map.put("workers", fileName);
        map.put("teacherId", teacherList.get(0).getId());
        return new ResponseJson(map);
    }

    @PostMapping("/saveXwTeacherImageInputBatch")
    @ApiOperation(value = "批量上传保存", notes = "返回保存好的对象", response = XwTeacherImageInput.class)
    public ResponseJson saveXwTeacherImageInputBatch(
            @ApiParam(value = "图片对象", required = true)
            @RequestBody List<XwTeacherImageInput> xwTeacherImageInputList) {
        try {
            //打卡机验证
            List<XwTeacherImageInput> listCard = deviceManageService.setCardAndFace(xwTeacherImageInputList);
            if (listCard.isEmpty()) {
                //全部成功
                List<XwTeacherImageInput> listSucceedAll = xwTeacherImageInputList.stream().map(xwTeacherImageInput -> {
                    //1为图像已经录入的标志
                    xwTeacherImageInput.setStatus("1");
                    return xwTeacherImageInputService.saveXwTeacherImageInput(xwTeacherImageInput);
                }).collect(Collectors.toList());
                return new ResponseJson(true, listSucceedAll);
            }
            return new ResponseJson(false, "图像异常！");
        } catch (Exception e) {
            return new ResponseJson(false, "设备连接超时，情稍后再试！");
        }
    }

    private boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
