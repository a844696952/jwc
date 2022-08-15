package com.yice.edu.cn.osp.controller.jy.resourceCenter;

import com.qiniu.http.Response;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenter;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceCenterTypeCondition;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.ResourceFileTypeUtil;
import com.yice.edu.cn.common.pojo.jy.resourceCenter.TeacherCourse;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.osp.service.jy.resourceCenter.ResourceCenterService;
import com.yice.edu.cn.osp.service.jy.resourceCenter.ResourceCenterTypeConditionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/resourceCenter")
@Api(value = "/resourceCenter",description = "资源中心信息表模块")
public class ResourceCenterController {
    @Autowired
    private ResourceCenterService resourceCenterService;
    @Autowired
    private ResourceCenterTypeConditionService resourceCenterTypeConditionService;
    @Autowired
    private ResourceFileTypeUtil fileTypeUtil;



    @PostMapping("/saveResourceCenter")
    @ApiOperation(value = "批量保存资源中心信息表对象", notes = "返回保存好的资源中心信息表对象", response=ResourceCenter.class)
    public void saveResourceCenterList(
            @ApiParam(value = "资源中心信息表对象", required = true)
            @RequestBody ResourceCenter resourceCenter){
        resourceCenter.getFileList().forEach(resourceCenter1 -> {
           resourceCenterService.saveResourceCenter(resourceCenter1);
        });
    }
    @GetMapping("/update/findResourceCenterById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找资源中心信息表", notes = "返回响应对象", response=ResourceCenter.class)
    public ResponseJson findResourceCenterById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        ResourceCenter resourceCenter=resourceCenterService.findResourceCenterById(id);
        return new ResponseJson(resourceCenter);
    }

    @PostMapping("/update/updateResourceCenter")
    @ApiOperation(value = "修改资源中心信息表对象", notes = "返回响应对象")
    public ResponseJson updateResourceCenter(
            @ApiParam(value = "被修改的资源中心信息表对象,对象属性不为空则修改", required = true)
            @RequestBody ResourceCenter resourceCenter){
        resourceCenter.setSchoolId(mySchoolId());
        resourceCenterService.updateResourceCenter(resourceCenter);
        return new ResponseJson();
    }

    @GetMapping("/look/lookResourceCenterById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找资源中心信息表", notes = "返回响应对象", response=ResourceCenter.class)
    public ResponseJson lookResourceCenterById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        ResourceCenter resourceCenter=resourceCenterService.findResourceCenterById(id);
        return new ResponseJson(resourceCenter);
    }

    @PostMapping("/find/findResourceCentersByCondition")
    @ApiOperation(value = "根据条件查找资源中心信息表", notes = "返回响应对象", response=ResourceCenter.class)
    public ResponseJson findResourceCentersByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ResourceCenter resourceCenter){
        resourceCenter.setSchoolId(mySchoolId());
        List<ResourceCenter> data=resourceCenterService.findResourceCenterListByCondition(resourceCenter);
        long count=resourceCenterService.findResourceCenterCountByCondition(resourceCenter);
        return new ResponseJson(data,count);
    }
    @PostMapping("/find/findOneResourceCenterByCondition")
    @ApiOperation(value = "根据条件查找单个资源中心信息表,结果必须为单条数据", notes = "没有时返回空", response=ResourceCenter.class)
    public ResponseJson findOneResourceCenterByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody ResourceCenter resourceCenter){
        ResourceCenter one=resourceCenterService.findOneResourceCenterByCondition(resourceCenter);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteResourceCenter/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteResourceCenter(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        resourceCenterService.deleteResourceCenter(id);
        return new ResponseJson();
    }


    @PostMapping("/find/findResourceCenterListByCondition")
    @ApiOperation(value = "根据条件查找资源中心信息表列表", notes = "返回响应对象,不包含总条数", response=ResourceCenter.class)
    public ResponseJson findResourceCenterListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ResourceCenter resourceCenter){
        List<ResourceCenter> data=resourceCenterService.findResourceCenterListByCondition(resourceCenter);
        return new ResponseJson(data);
    }

    @PostMapping("/upload/uploadQiniuFile")
    @ApiOperation(value = "上传文件到七牛", notes = "返回资源名称和资源的url")
    public ResponseJson uploadQiniuFile(@ApiParam(value = "上传到七牛的文件file", required = true) MultipartFile file) {
        //文件名后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        String mp4="mp4";
        if(!mp4.equals(suffix)){
            return new ResponseJson(false,"不支持的文件格式");
        }
        Map<String, String> map = new HashMap<>();
        map.put("name", file.getOriginalFilename());
        //获取保存文件路径
        String url = QiniuUtil.commonUploadFile(file, Constant.Upload.UPLOAD_RESOURCE_CENTER + suffix);
        map.put("url", url);
        return new ResponseJson(map);
    }

    @PostMapping("/upload/uploadCourseware")
    @ApiOperation(value = "上传课件到七牛", notes = "返回资源名称和资源的url")
    public ResponseJson uploadCourseware(@ApiParam(value = "上传到七牛的文件file", required = true) MultipartFile file) {
        //文件名后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        int suffixInt = fileTypeUtil.setResouceType(suffix);
        if(suffixInt==0){
            return new ResponseJson(false,"不支持的文件格式");
        }
        //不包含文件后缀名
        String fileName = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));
        Map<String, String> map = new HashMap<>();
        map.put("name", file.getOriginalFilename());
        //获取保存文件路径
        String url = QiniuUtil.commonUploadFile(file, Constant.Upload.UPLOAD_RESOURCE_CENTER + suffix);
        map.put("url", url);
        map.put("fileId", "0");
        map.put("type",String.valueOf(suffixInt));
        return new ResponseJson(map);
    }




//    @PostMapping("/upload/uploadFile")
//    @ApiOperation(value = "上传课件到七牛", notes = "返回资源名称和资源的url")
//    public ResponseJson uploadFile(@ApiParam(value = "上传到七牛的文件file", required = true) MultipartFile file, HttpServletRequest request) throws Exception {
//        File path = new File(ResourceUtils.getURL("classpath:").getPath());
//        if(!path.exists()) {
//            path = new File("");
//        }
//        File upload = new File(path.getAbsolutePath(),"public/upload/");
//        if(!upload.exists()) {
//            upload.mkdirs();
//        }
//        //文件名后缀
//        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
//        int suffixInt = fileTypeUtil.setResouceType(suffix);
//        if(suffixInt==0){
//            return new ResponseJson(false,"不支持的文件格式");
//        }
//        Map<String, String> map = new HashMap<>();
//        map.put("name", file.getOriginalFilename());
//
//        String uuid = IdUtil.randomUUID();
//        file.transferTo(new File(upload.getAbsolutePath()+File.separator+uuid+"."+suffix));
//        map.put("url", "/upload/"+uuid+"."+suffix);
//        map.put("fileId", "0");
//        map.put("type",String.valueOf(suffixInt));
//        return new ResponseJson(map);
//
//    }

    @PostMapping("/find/findTeacherCourseListBySchoolId")
    @ApiOperation(value = "根据学校id获取全校老师与所教课程信息", notes = "返回单个资源中心信息表,没有时为空")
    public ResponseJson findTeacherCourseListBySchoolId(@ApiParam(value = "资源中心信息表对象")
                                                               @RequestBody TeacherCourse teacherCourse){
        teacherCourse.setSchoolId(mySchoolId());
        List<TeacherCourse> list= resourceCenterService.findTeacherCourseListBySchoolId(teacherCourse);
        long count=resourceCenterService.findTeacherCourseCountBySchoolId(teacherCourse);
        list.forEach(teacherCourse1 -> {
            teacherCourse1.setCourseName(Stream.of(teacherCourse1.getCourseName().split(",")).distinct().collect(Collectors.joining(",")));
        });


        return  new ResponseJson(list,count);
    }

    @PostMapping("/find/findResourceCenterTypeConditionListByCondition")
    @ApiOperation(value = "根据条件查找资源分类条件关联表列表", notes = "返回响应对象,不包含总条数", response=ResourceCenterTypeCondition.class)
    public ResponseJson findResourceCenterTypeConditionListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody ResourceCenterTypeCondition resourceCenterTypeCondition){
        List<ResourceCenterTypeCondition> data=resourceCenterTypeConditionService.findResourceCenterTypeConditionListByCondition(resourceCenterTypeCondition);
        data.forEach(res-> {
            res.setId(null);
            res.setResourceCenterId(null);
        });
        return new ResponseJson(data);
    }

    @PostMapping("/upload/deleteQiniuFile")
    @ApiOperation(value = "创建时间：2018-10-29。说明：用于删除七牛的文件", notes = "返回响应对象")
    public Response deleteQiniuFile(@ApiParam(value = "去删除七牛的文件,需要用到qinliuUrl", required = true) String qinliuUrl) {
        return QiniuUtil.deleteFile(qinliuUrl);
    }


}
