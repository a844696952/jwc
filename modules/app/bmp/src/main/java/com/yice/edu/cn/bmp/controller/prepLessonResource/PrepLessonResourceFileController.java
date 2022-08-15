package com.yice.edu.cn.bmp.controller.prepLessonResource;

import com.yice.edu.cn.bmp.service.dd.DdService;
import com.yice.edu.cn.bmp.service.prepLessonResource.CollectorFileService;
import com.yice.edu.cn.bmp.service.prepLessonResource.PrepLessonResourceFileService;
import com.yice.edu.cn.bmp.service.subjectSource.SubjectMaterialService;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jy.prepLessonResource.CollectorFile;
import com.yice.edu.cn.common.pojo.jy.prepLessonResource.PrepLessonResourceFile;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.SubjectMaterial;
import com.yice.edu.cn.common.util.object.ObjectKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.bmp.interceptor.LoginInterceptor.myParentId;


@RestController
@RequestMapping("/prepLessonResourceFile")
@Api(value = "/prepLessonResourceFile",description = "文档图片资源模块")
public class PrepLessonResourceFileController {
    @Autowired
    private PrepLessonResourceFileService prepLessonResourceFileService;



    @Autowired
    private SubjectMaterialService subjectMaterialService;




    /* @Autowired
    private MaterialItemService materialItemService;
    @Autowired
    private MaterialService materialService;

    @Autowired
    private SchoolService schoolService;*/
/*


    @GetMapping("/update/findPrepLessonResourceFileById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findPrepLessonResourceFileById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        PrepLessonResourceFile prepLessonResourceFile = prepLessonResourceFileService.findPrepLessonResourceFileById(id);
        return new ResponseJson(prepLessonResourceFile);
    }

    @PostMapping("/update/updatePrepLessonResourceFile")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updatePrepLessonResourceFile(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody PrepLessonResourceFile prepLessonResourceFile) {
        prepLessonResourceFileService.updatePrepLessonResourceFile(prepLessonResourceFile);
        return new ResponseJson();
    }

    @GetMapping("/look/lookPrepLessonResourceFileById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookPrepLessonResourceFileById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        PrepLessonResourceFile prepLessonResourceFile = prepLessonResourceFileService.findPrepLessonResourceFileById(id);
        return new ResponseJson(prepLessonResourceFile);
    }

    @PostMapping("/find/findPrepLessonResourceFilesByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findPrepLessonResourceFilesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PrepLessonResourceFile prepLessonResourceFile) {
        List<PrepLessonResourceFile> data = prepLessonResourceFileService.findPrepLessonResourceFileListByCondition(prepLessonResourceFile);
        long count = prepLessonResourceFileService.findPrepLessonResourceFileCountByCondition(prepLessonResourceFile);
        return new ResponseJson(data, count);
    }

    @PostMapping("/find/findPrepLessonResourceFilesByCondition2")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findPrepLessonResourceFilesByCondition2(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PrepLessonResourceFile prepLessonResourceFile) {
        List<PrepLessonResourceFile> data = prepLessonResourceFileService.findPrepLessonResourceFileListByCondition2(prepLessonResourceFile);
        long count = prepLessonResourceFileService.findPrepLessonResourceFileCountByCondition2(prepLessonResourceFile);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOnePrepLessonResourceFileByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOnePrepLessonResourceFileByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody PrepLessonResourceFile prepLessonResourceFile) {
        PrepLessonResourceFile one = prepLessonResourceFileService.findOnePrepLessonResourceFileByCondition(prepLessonResourceFile);
        return new ResponseJson(one);
    }

    @GetMapping("/deletePrepLessonResourceFile/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deletePrepLessonResourceFile(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        prepLessonResourceFileService.deletePrepLessonResourceFile(id);
        return new ResponseJson();
    }


    @PostMapping("/findPrepLessonResourceFileListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findPrepLessonResourceFileListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody PrepLessonResourceFile prepLessonResourceFile) {
        List<PrepLessonResourceFile> data = prepLessonResourceFileService.findPrepLessonResourceFileListByCondition(prepLessonResourceFile);
        return new ResponseJson(data);
    }

    @PostMapping("/ignore/uploadFile")
    public FileObj uploadPrepLessonResourceFile(MultipartFile file) {
        FileObj fileObj = new FileObj();
        try {
            fileObj.setSuccess(true);
            fileObj.setPath(QiniuUtil.uploadFile(file, Constant.Upload.UPLOAD_PREPLESSON));
            System.out.println("上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            fileObj.setSuccess(false);
            fileObj.setPath("上传出错了");
        }
        return fileObj;
    }

    @GetMapping("/update/downloadCountChange/{id}")
    @ApiOperation(value = "下载数量增加", notes = "返回响应对象")
    public ResponseJson downloadCountChange(
            @ApiParam(value = "下载数量增加的id", required = true)
            @PathVariable String id) {
        prepLessonResourceFileService.downloadCountChange(id);
        return new ResponseJson();
    }



    @GetMapping("/find/findMaterialItemTreeByGradeId/{materialId}")
    @ApiOperation(value = "根据年级Id查找章节树", notes = "返回响应对象,不包含总条数")
    public ResponseJson findMaterialItemTreeByGradeId(
            @ApiParam(value = "教材id")
            @PathVariable String materialId) {

        MaterialItem materialItem = new MaterialItem();
        materialItem.setMaterialId(materialId);

        List<MaterialItem> data = materialItemService.findMaterialItemListByCondition(materialItem);
        List<MaterialItem> treeData = ObjectKit.buildTree(data, "-1");

        return new ResponseJson(treeData);
    }

    @PostMapping("/find/findMaterialListByCondition")
    @ApiOperation(value = "根据条件查找教材列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findMaterialListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询{subjectMaterialId:树id}")
            @Validated
            @RequestBody Material material) {
        List<Material> data = materialService.findMaterialListByCondition(material);
        return new ResponseJson(data);
    }
*/

    @PostMapping("/find/findMatFileListByMatItemid")
    @ApiOperation(value = "去更新页面,通过条件查找，fileType文件类型/courseId科目id/gradeId年级id/filename文件名,分页需要传pager", notes = "返回响应对象")
    public ResponseJson findMatFileByMatItemid(
            @ApiParam(value = "fileType文件类型/courseId科目id/gradeId年级id/filename文件名,分页需要传pager", required = true)
            @RequestBody PrepLessonResourceFile prepLessonResourceFile) {
        prepLessonResourceFile.setRole("1");
        if (prepLessonResourceFile.getPager()==null){
            Pager pager = new Pager();
            pager.setSortField("updateTime");
            pager.setSortOrder("desc");
            pager.setPaging(false);
            prepLessonResourceFile.setPager(pager);
        }
        List<PrepLessonResourceFile> data = prepLessonResourceFileService.findMatFileListByMatItemid(prepLessonResourceFile);
        long count = prepLessonResourceFileService.findMatFilesCountByMatItemid(prepLessonResourceFile);
       return new ResponseJson(data, count);
    }

/*

    //findMatFileListByCondition3
    @PostMapping("/find/findMatFileListByCondition3")
    @ApiOperation(value = "去更新页面,通过章节id查找", notes = "返回响应对象")
    public ResponseJson findMatFileListByCondition3(
            @ApiParam(value = "去更新页面,需要用到的教材章节id", required = true)
            @RequestBody PrepLessonResourceFile prepLessonResourceFile) {
        System.out.println(prepLessonResourceFile);
        List<PrepLessonResourceFile> data = prepLessonResourceFileService.findMatFileListByCondition3(prepLessonResourceFile);
        long count = prepLessonResourceFileService.findMatFilesCountByCondition3(prepLessonResourceFile);
        return new ResponseJson(data, count);
    }
*/

    @PostMapping("/find/findMatFileListGroupByFiletypeToAppShow")
    @ApiOperation(value = "根据文件类型指定数量展示", notes = "返回响应对象")
    public ResponseJson findMatFileListGroupByFiletypeToAppShow(
            @ApiParam(value = "需要传入展示条数 fileShowNumByType", required = true)
            @RequestBody PrepLessonResourceFile prepLessonResourceFile) {

        prepLessonResourceFile.setRole("1");
        if (prepLessonResourceFile.getPager()==null){
            Pager pager = new Pager();
            pager.setSortField("updateTime");
            pager.setSortOrder("desc");
            pager.setPaging(false);
            prepLessonResourceFile.setPager(pager);
        }
        List<PrepLessonResourceFile> data = prepLessonResourceFileService.findMatFileListByMatItemid(prepLessonResourceFile);
        List<PrepLessonResourceFile> docList = new ArrayList<>();
        List<PrepLessonResourceFile> picList = new ArrayList<>();
        List<PrepLessonResourceFile> videoList = new ArrayList<>();
        List<PrepLessonResourceFile> audioList = new ArrayList<>();

        int docNum=0;
        int picNum=0;
        int videoNum=0;
        int audioNum=0;
        Map<String,String> fileShowNumByType = prepLessonResourceFile.getFileShowNumByType();

        if (fileShowNumByType!=null){
            for (String key : fileShowNumByType.keySet()){
                    if (fileShowNumByType.get(key)==null||fileShowNumByType.get(key).equals("")){continue;}
                    if (key.equals("1")){docNum=Integer.parseInt(fileShowNumByType.get(key));}
                    if (key.equals("2")){picNum=Integer.parseInt(fileShowNumByType.get(key));}
                    if (key.equals("3")){videoNum=Integer.parseInt(fileShowNumByType.get(key));}
                    if (key.equals("4")){audioNum=Integer.parseInt(fileShowNumByType.get(key));}
            }
        }
        for (PrepLessonResourceFile file : data){
            if (file.getFileType().equals("1")&&docList.size()<docNum){
                docList.add(file);
            }
            if (file.getFileType().equals("2")&&picList.size()<picNum){
                picList.add(file);
            }
            if (file.getFileType().equals("3")&&videoList.size()<videoNum){
                videoList.add(file);
            }
            if (file.getFileType().equals("4")&&audioList.size()<audioNum){
                audioList.add(file);
            }
            if (docList.size()==docNum&&picList.size()==picNum&&videoList.size()==videoNum&&audioList.size()==audioNum){break;}
        }
        Map<String,List> map = new HashMap<>();
        map.put("doc",docList);
        map.put("picture",picList);
        map.put("video",videoList);
        map.put("audio",audioList);
        return new ResponseJson(map);
    }
   /* @GetMapping("/find/findSubjectMaterialTreeByAnnualPeriodId/{annualPeriodId}")
    @ApiOperation(value = "根据年段id查找科目教材树", notes = "返回响应对象,不包含总条数")
    public ResponseJson findSubjectMaterialTreeByAnnualPeriodId(
            @PathVariable String annualPeriodId
    ) {
        SubjectMaterial subjectMaterial  = new SubjectMaterial();
        subjectMaterial.setAnnualPeriodId(annualPeriodId);
        Pager pager = new Pager();
        pager.setSortField("ddId");
        pager.setSortOrder("asc");
        pager.setPaging(false);
        subjectMaterial.setPager(pager);
        List<SubjectMaterial> data=subjectMaterialService.findSubjectMaterialListByCondition(subjectMaterial);
        List<SubjectMaterial> treeData = ObjectKit.buildTree(data,"-1");

        return new ResponseJson(treeData);
    }*/
   @GetMapping("/update/numLookChange/{id}")
   @ApiOperation(value = "文档图片文件查看次数加1，要传入文件id", notes = "返回响应对象")
   public ResponseJson numLookChange(
           @ApiParam(value = "文件id", required = true)
           @PathVariable String id){
       prepLessonResourceFileService.numLookChange(id);
       return new ResponseJson();
   }

}