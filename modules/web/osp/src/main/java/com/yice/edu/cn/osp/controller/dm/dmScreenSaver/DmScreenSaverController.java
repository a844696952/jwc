package com.yice.edu.cn.osp.controller.dm.dmScreenSaver;

import cn.hutool.core.date.DateUtil;
import com.qiniu.http.Response;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.dmScreenSaver.AreaByDmClassVo;
import com.yice.edu.cn.common.pojo.dm.dmScreenSaver.DmScreenSaver;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.util.FileTypeUtil;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.osp.service.dd.DdService;
import com.yice.edu.cn.osp.service.dm.dmScreenSaver.DmScreenSaverService;
import com.yice.edu.cn.osp.service.jw.classes.JwClassesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.*;

@RestController
@RequestMapping("/dmScreenSaver")
@Api(value = "/dmScreenSaver", description = "模块")
public class DmScreenSaverController {
    @Autowired
    private DmScreenSaverService dmScreenSaverService;
//    @Autowired
//    private JwAcademicBuildingService jwAcademicBuildingService;
    @Autowired
    private JwClassesService jwClassesService;
    @Autowired
    private DdService ddService;
    @Autowired
    private FileTypeUtil fileTypeUtil;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/saveDmScreenSaver")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = DmScreenSaver.class)
    public ResponseJson saveDmScreenSaver(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmScreenSaver dmScreenSaver) {
        dmScreenSaver.setSchoolId(mySchoolId());
        dmScreenSaver.setTeacherId(myId());
        Date date = DateUtil.parse(dmScreenSaver.getEndTime());
        //如果date小于当前时间，则返回-1,如果大于则返回-2,如果相等则返回0
        int compareTo = date.compareTo(DateUtil.date());
        if(compareTo==-1 || compareTo==0){
            //结束时间小于当前时间，则添加屏保，需要更改为已结束
            dmScreenSaver.setStatus(Constant.dmScreenSaver.STOP);
        }else{
            //如果状态是4，则直接保存为草稿
            if(dmScreenSaver.getStatus()==4){
                dmScreenSaver.setStatus(Constant.dmScreenSaver.DRAFT);
            }else{
                dmScreenSaver.setStatus(Constant.dmScreenSaver.RUNNING);
            }
        }
        DmScreenSaver s = dmScreenSaverService.saveDmScreenSaver(dmScreenSaver);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findDmScreenSaverById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response = DmScreenSaver.class)
    public ResponseJson findDmScreenSaverById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        DmScreenSaver dmScreenSaver = dmScreenSaverService.findDmScreenSaverById(id);
        return new ResponseJson(dmScreenSaver);
    }

    @PostMapping("/update/updateDmScreenSaver")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateDmScreenSaver(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody DmScreenSaver dmScreenSaver) {
        Date date = DateUtil.parse(dmScreenSaver.getEndTime());
        //如果date小于当前时间，则返回-1,如果大于则返回-2,如果相等则返回0
        int compareTo = date.compareTo(DateUtil.date());
        if(compareTo==-1 || compareTo==0){
            //结束时间小于当前时间，则添加屏保，需要更改为已结束
            dmScreenSaver.setStatus(Constant.dmScreenSaver.STOP);
        }else{
            //如果状态是4，则直接保存为草稿
            if(dmScreenSaver.getStatus()==4){
                dmScreenSaver.setStatus(Constant.dmScreenSaver.DRAFT);
            }else{
                dmScreenSaver.setStatus(Constant.dmScreenSaver.RUNNING);
            }
        }
        dmScreenSaverService.updateDmScreenSaver(dmScreenSaver);
        return new ResponseJson();
    }

    @GetMapping("/look/lookDmScreenSaverById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response = DmScreenSaver.class)
    public ResponseJson lookDmScreenSaverById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        DmScreenSaver dmScreenSaver = dmScreenSaverService.findDmScreenSaverById(id);
        return new ResponseJson(dmScreenSaver);
    }

    @PostMapping("/findDmScreenSaversByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response = DmScreenSaver.class)
    public ResponseJson findDmScreenSaversByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmScreenSaver dmScreenSaver) {
        dmScreenSaver.setSchoolId(mySchoolId());
        List<DmScreenSaver> data = dmScreenSaverService.findDmScreenSaverListByCondition(dmScreenSaver);
        long count = dmScreenSaverService.findDmScreenSaverCountByCondition(dmScreenSaver);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneDmScreenSaverByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response = DmScreenSaver.class)
    public ResponseJson findOneDmScreenSaverByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmScreenSaver dmScreenSaver) {
        DmScreenSaver one = dmScreenSaverService.findOneDmScreenSaverByCondition(dmScreenSaver);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteDmScreenSaver/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmScreenSaver(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        dmScreenSaverService.deleteDmScreenSaver(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmScreenSaverListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response = DmScreenSaver.class)
    public ResponseJson findDmScreenSaverListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmScreenSaver dmScreenSaver) {
        dmScreenSaver.setSchoolId(mySchoolId());
        List<DmScreenSaver> data = dmScreenSaverService.findDmScreenSaverListByCondition(dmScreenSaver);
        return new ResponseJson(data);
    }

    @PostMapping("/uploadQiniuFile")
    @ApiOperation(value = "创建时间：2018-10-29。说明：上传文件到七牛", notes = "返回资源名称和资源的url")
    public ResponseJson uploadQiniuFile(@ApiParam(value = "上传到七牛的文件file", required = true) MultipartFile file) {
        //文件名后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        int suffixInt = fileTypeUtil.setResouceType(suffix);
        if (suffixInt == 0) {
            return new ResponseJson(false, "不支持的文件格式");
        }
        //不包含文件后缀名
        String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
        Map<String, String> map = new HashMap<>();
        map.put("name", file.getOriginalFilename());
        //获取保存文件路径
        String url = QiniuUtil.commonUploadFile(file, Constant.Upload.UPLOAD_NETWORK + suffix);
        map.put("url", url);
        return new ResponseJson(map);
    }

    @PostMapping("/batchUpdateDmScreenSaver")
    @ApiOperation(value = "批量修改屏保密码")
    public void batchUpdateDmScreenSaver(
            @ApiParam(value = "对象")
            @RequestBody DmScreenSaver dmScreenSaver) {
        dmScreenSaver.setSchoolId(mySchoolId());
        dmScreenSaver.setTeacherId(myId());
        dmScreenSaverService.batchUpdateDmScreenSaver(dmScreenSaver);
    }

    @PostMapping("/batchDeleteDmScreenSaver")
    @ApiOperation(value = "批量删除屏保")
    public void batchDeleteDmScreenSaver(
            @ApiParam(value = "对象")
            @RequestBody DmScreenSaver dmScreenSaver) {
        dmScreenSaverService.batchDeleteDmScreenSaver(dmScreenSaver);
    }

    @PostMapping("/stopDmScreenSaver")
    @ApiOperation(value = "停止显示屏保", notes = "返回响应对象")
    public ResponseJson stopDmScreenSaver(
            @ApiParam(value = "被停止的对象,对象属性不为空则修改", required = true)
            @RequestBody DmScreenSaver dmScreenSaver) {
        //修改屏保的最后显示时间
        dmScreenSaver.setEndTime(DateUtil.now());
        //设置屏保停止
        dmScreenSaver.setStatus(Constant.dmScreenSaver.STOP);
        dmScreenSaverService.updateDmScreenSaver(dmScreenSaver);
        return new ResponseJson();
    }


    @PostMapping("/getAreaByDmClass")
    @ApiOperation(value = "获取班级名称和班牌的用户名")
    public List<AreaByDmClassVo> getAreaByDmClass(
            @ApiParam(value = "被停止的对象,对象属性不为空则修改", required = true)
            @RequestBody DmScreenSaver dmScreenSaver) {
        dmScreenSaver.setSchoolId(mySchoolId());
        return dmScreenSaverService.getAreaByDmClass(dmScreenSaver);
    }

//    @GetMapping("/jwAcademicBuildingService")
//    @ApiOperation(value = "获取班级名称和班牌的用户名")
//    public List<BuildingSpaceClass> jwAcademicBuildingService() {
//        return jwAcademicBuildingService.getBuildingSpaceClassInfoBySchoolId(mySchoolId());
//    }

    @PostMapping("/getAreaByDmClassName")
    @ApiOperation(value = "获取班级名称和班级名称")
    public List<AreaByDmClassVo> getAreaByDmClassName(
            @ApiParam(value = "被停止的对象,对象属性不为空则修改", required = true)
            @RequestBody DmScreenSaver dmScreenSaver) {
        List<String> stringList = new ArrayList<>();
        if (null != dmScreenSaver.getId()) {
            dmScreenSaver = dmScreenSaverService.findDmScreenSaverById(dmScreenSaver.getId());
            String string[] = dmScreenSaver.getSendArea().split(",");
            stringList = Arrays.asList(string);
        }
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.GRADE);
        dd.setLevelType(currentTeacher().getSchool().getTypeId());
        List<Dd> data = ddService.findDdListByCondition(dd);
        JwClasses jwClasses = new JwClasses();
        jwClasses.setSchoolId(mySchoolId());
        List<JwClasses> jwClassesList = jwClassesService.findJwClassesListByCondition(jwClasses);
        List<AreaByDmClassVo> areaByDmClassVoList = new ArrayList<>();
        List<String> finalStringList = stringList;
        data.stream().forEach(e -> {
            AreaByDmClassVo areaByDmClassVo = new AreaByDmClassVo();
            areaByDmClassVo.setLabel(e.getName());
            areaByDmClassVo.setId(e.getGradeId());
            List<AreaByDmClassVo> areaByDmClassVos = new ArrayList<>();
            jwClassesList.stream().forEach(j -> {
                //同一个年级下的
                if (e.getId().equals(j.getGradeId())) {
                    AreaByDmClassVo areaByDmClassVo1 = new AreaByDmClassVo();
                    areaByDmClassVo1.setId(j.getId());
                    areaByDmClassVo1.setLabel(j.getGradeName() + "(" + j.getNumber() + ")班");
                    if (finalStringList != null && finalStringList.contains(j.getId())) {
                        areaByDmClassVo1.setSelected(true);
                    }
                    areaByDmClassVos.add(areaByDmClassVo1);
                }
            });
            areaByDmClassVo.setChildren(areaByDmClassVos);
            if (areaByDmClassVo.getChildren().size() > 0) {
                areaByDmClassVoList.add(areaByDmClassVo);
            }
        });
        return areaByDmClassVoList;
    }

    @PostMapping("deleteQiniuFile")
    @ApiOperation(value = "创建时间：2019-01-21。说明：用于删除七牛的文件", notes = "返回响应对象")
    public Response deleteQiniuFile(@ApiParam(value = "去删除七牛的文件,需要用到qinliuUrl", required = true) String qinliuUrl) {
        return QiniuUtil.deleteFile(qinliuUrl);
    }
    @PostMapping("/batchUpdateDmScreenSaverStatus")
    @ApiOperation(value = "根据时间修改当前学校所有的屏保状态")
    public void batchUpdateDmScreenSaverStatus(
            @ApiParam(value = "对象是整个对象", required = true)
            @RequestBody DmScreenSaver dmScreenSaver){
        dmScreenSaverService.batchUpdateDmScreenSaverStatus(dmScreenSaver);
    }

}
