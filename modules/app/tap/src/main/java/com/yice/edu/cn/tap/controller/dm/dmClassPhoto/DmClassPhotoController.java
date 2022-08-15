package com.yice.edu.cn.tap.controller.dm.dmClassPhoto;

import cn.hutool.json.JSONUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.classes.DmClassPhoto;
import com.yice.edu.cn.common.pojo.dm.classes.GradeAndClasses;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.ts.jpush.Extras;
import com.yice.edu.cn.common.pojo.ts.jpush.JpushApp;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.tap.interceptor.LoginInterceptor;
import com.yice.edu.cn.tap.service.classes.JwClassesService;
import com.yice.edu.cn.tap.service.dd.DdService;
import com.yice.edu.cn.tap.service.dmClassPhoto.DmClassPhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmClassPhoto")
@Api(value = "/dmClassPhoto",description = "班级相册表模块")
public class DmClassPhotoController {
    @Autowired
    private DmClassPhotoService dmClassPhotoService;
    @Autowired
    private JwClassesService jwClassesService;
    @Autowired
    private DdService ddService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    @PostMapping("/saveDmClassPhoto")
    @ApiOperation(value = "保存班级相册表对象", notes = "返回响应对象")
    public synchronized ResponseJson saveDmClassPhoto(
            @ApiParam(value = "班级相册表对象", required = true)
            @RequestBody DmClassPhoto dmClassPhoto){
        if(StringUtils.isBlank(dmClassPhoto.getClassId())){
            return new ResponseJson(false,"班级编号不能为空");
        }else if(StringUtils.isBlank(dmClassPhoto.getImgUrl())){
            return new ResponseJson(false,"图片地址不能为空");
        }else{
            if(StringUtils.isBlank(dmClassPhoto.getImgName())){
                dmClassPhoto.setImgName("照片_"+ UUID.randomUUID().toString());
            }
            dmClassPhoto.setSchoolId(mySchoolId());
            DmClassPhoto s=dmClassPhotoService.saveDmClassPhoto(dmClassPhoto);
            sendMsg();
            return new ResponseJson(s);
        }
    }

    /**
     * 推送相册更新信息
     */
    private void  sendMsg(){
        Push push = Push.newBuilder(JpushApp.ECC)
                .setTag(mySchoolId())
                .setMessage(Push.Message.newBuilder().setMsgContent("更新相册").setTitle("更新相册")
                        .setExtras(Extras.newBuilder().setType(Extras.DM_CLASSPHOTO_MSG).build())
                        .build()
                ).build();
        stringRedisTemplate.convertAndSend(Constant.MCS_CHANEL.JPUSH_SEND_PUSH, JSONUtil.toJsonStr(push));
    }




    @GetMapping("/update/findDmClassPhotoById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找班级相册表", notes = "返回响应对象")
    public ResponseJson findDmClassPhotoById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        DmClassPhoto dmClassPhoto=dmClassPhotoService.findDmClassPhotoById(id);
        return new ResponseJson(dmClassPhoto);
    }

    @PostMapping("/update/updateDmClassPhoto")
    @ApiOperation(value = "修改班级相册表对象", notes = "返回响应对象")
    public ResponseJson updateDmClassPhoto(
            @ApiParam(value = "被修改的班级相册表对象,对象属性不为空则修改", required = true)
            @RequestBody DmClassPhoto dmClassPhoto){
        dmClassPhotoService.updateDmClassPhoto(dmClassPhoto);
        sendMsg();
        return new ResponseJson();
    }

    @GetMapping("/look/lookDmClassPhotoById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找班级相册表", notes = "返回响应对象")
    public ResponseJson lookDmClassPhotoById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmClassPhoto dmClassPhoto=dmClassPhotoService.findDmClassPhotoById(id);
        return new ResponseJson(dmClassPhoto);
    }

    @PostMapping("/findDmClassPhotosByCondition")
    @ApiOperation(value = "根据条件查找班级相册表", notes = "返回响应对象")
    public ResponseJson findDmClassPhotosByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmClassPhoto dmClassPhoto){
       dmClassPhoto.setSchoolId(mySchoolId());
        List<DmClassPhoto> data=dmClassPhotoService.findDmClassPhotoListByCondition(dmClassPhoto);
        long count=dmClassPhotoService.findDmClassPhotoCountByCondition(dmClassPhoto);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneDmClassPhotoByCondition")
    @ApiOperation(value = "根据条件查找单个班级相册表,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneDmClassPhotoByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmClassPhoto dmClassPhoto){
        DmClassPhoto one=dmClassPhotoService.findOneDmClassPhotoByCondition(dmClassPhoto);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteDmClassPhoto/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmClassPhoto(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmClassPhotoService.deleteDmClassPhoto(id);
        sendMsg();
        return new ResponseJson();
    }


    @PostMapping("/findDmClassPhotoListByCondition")
    @ApiOperation(value = "根据条件查找班级相册表列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findDmClassPhotoListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmClassPhoto dmClassPhoto){
       dmClassPhoto.setSchoolId(mySchoolId());
        List<DmClassPhoto> data=dmClassPhotoService.findDmClassPhotoListByCondition(dmClassPhoto);
        return new ResponseJson(data);
    }

    @PostMapping("/batchDeleteDmClassPhoto")
    @ApiOperation(value = "根据id批量删除", notes = "返回响应对象")
    public ResponseJson batchDeleteDmClassPhoto(
            @RequestBody List<String> idlist){
        dmClassPhotoService.batchDeleteDmClassPhoto(idlist);
        sendMsg();
        return new ResponseJson();
    }
    @PostMapping("/findJwClassessByCondition")
    @ApiOperation(value = "根据条件查找班级信息", notes = "返回响应对象")
    public ResponseJson findJwClassessByCondition(@ApiParam(value = "属性不为空则作为条件查询") @RequestBody JwClasses jwClasses) {
        jwClasses.setSchoolId(mySchoolId());
        List<JwClasses> data = jwClassesService.findJwClassesListByCondition(jwClasses);

        jwClasses.setSchoolId(LoginInterceptor.mySchoolId());
        long count = jwClassesService.findJwClassesCountByCondition(jwClasses);
        return new ResponseJson(data,count);
    }

    @PostMapping("/uploadAvatar")
    @ApiOperation(value = "单个图片上传",notes = "返回图片路径字符串")
    public ResponseJson uploadAvatar(@RequestParam("file") MultipartFile file){
        final String path = QiniuUtil.uploadImage(file, Constant.Upload.UPLOAD_CLASS_IMG);
        return new ResponseJson(path);
    }

    @PostMapping("/batchUpload")
    @ApiOperation(value = "多个图片上传",notes = "返回图片路径集合",response = String.class,responseContainer = "List")
    public ResponseJson batchUpload(@ApiParam(value = "指定files")@RequestParam("files") List<MultipartFile> files){
        List<String> paths = new ArrayList<>();
        files.forEach(file->paths.add(QiniuUtil.uploadImage(file, Constant.Upload.UPLOAD_CLASS_IMG)));
        return new ResponseJson(paths);
    }
    @PostMapping("/getGradeAndClasses")
    @ApiOperation(value = "获取当前登录账户讲师学校的年纪和班级，树形展示",notes = "返回学校的年纪和班级，树形展示")
    public ResponseJson getGradeAndClasses() {
        try{
            Dd dd = new Dd();
            dd.setTypeId(Constant.DD_TYPE.GRADE);
            dd.setLevelType(currentTeacher().getSchool().getTypeId());
            //通过字典表查询当前学校有多少年级
            List<Dd> data = ddService.findDdListByCondition(dd);
            //按照班号排序
            List<JwClasses> jwClassesList = jwClassesService.getClassTree(mySchoolId(),myId());
            List<GradeAndClasses> areaByDmClassVoList = new ArrayList<>();
            data.stream().forEach(e -> {
                GradeAndClasses gradeAndClasses = new GradeAndClasses();
                gradeAndClasses.setName(e.getName());
                gradeAndClasses.setId(e.getId());
                //定义临时的list对象，用来存储
                List<GradeAndClasses> gradeAndClassesList = new ArrayList<>();
                jwClassesList.stream().forEach(j -> {
                    //同一个年级下的
                    if (e.getId().equals(j.getGradeId())) {
                        GradeAndClasses gradeAndClasses1 = new GradeAndClasses();
                        gradeAndClasses1.setId(j.getId());
                        gradeAndClasses1.setName(j.getNumber());
                        gradeAndClassesList.add(gradeAndClasses1);
                    }
                });
                gradeAndClasses.setChildren(gradeAndClassesList);
                if (gradeAndClasses.getChildren().size() > 0) {
                    areaByDmClassVoList.add(gradeAndClasses);
                }
            });
            return new ResponseJson(areaByDmClassVoList);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseJson(false,"服务器异常");
        }

    }
}
