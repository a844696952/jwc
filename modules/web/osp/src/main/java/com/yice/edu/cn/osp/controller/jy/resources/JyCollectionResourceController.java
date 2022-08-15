package com.yice.edu.cn.osp.controller.jy.resources;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.resources.JyCollectionResource;
import com.yice.edu.cn.common.pojo.jy.resources.JyResouces;
import com.yice.edu.cn.common.pojo.jy.resources.JyResoucesType;
import com.yice.edu.cn.common.util.http.HttpKit;
import com.yice.edu.cn.common.util.zip.ZipUtils;
import com.yice.edu.cn.osp.service.jy.resources.JyCollectionResourceService;
import com.yice.edu.cn.osp.service.jy.resources.JyResoucesTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/jyCollectionResource")
@Api(value = "/jyCollectionResource",description = "实现我的收藏资源的新增，删除，修改，重命名，新建文件夹，重命名文件，取消收藏，批量取消收藏，移动文件到文件夹，批量删除文件")
public class JyCollectionResourceController {
    @Autowired
    private JyCollectionResourceService jyCollectionResourceService;
    @Autowired
    private JyResoucesTypeService jyResoucesTypeService;
    @Autowired
    private ZipUtils zipUtils;

    @PostMapping("/saveJyCollectionResource")
    @ApiOperation(value = "保存我的收藏，如果收藏里面包含了，则无法新增成功，不允许重复", notes = "返回响应对象")
    public ResponseJson saveJyCollectionResource(
            @ApiParam(value = "参数是整个对象", required = true)
            @RequestBody JyCollectionResource jyCollectionResource){
        int cnt = jyCollectionResourceService.repeatCollectionResouces(jyCollectionResource);
        if(cnt==0) {
            jyCollectionResource.setFileId("0");
            jyCollectionResource.setTeacherId(myId());
            JyCollectionResource s = jyCollectionResourceService.saveJyCollectionResource(jyCollectionResource);
            return new ResponseJson(s);
        }else{
            return new ResponseJson(false,"与其他文件名称相同，请修改");
        }

    }
    @PostMapping("/batchSaveJyCollectionResource")
    @ApiOperation(value = "批量新增我的收藏", notes = "返回响应对象")
    public void batchSaveJyCollectionResource(
            @ApiParam(value = "对象的集合", required = true)
            @RequestBody List<JyCollectionResource> jyCollectionResources){
        jyCollectionResourceService.batchSaveJyCollectionResource(jyCollectionResources);
    }
    @GetMapping("/update/findJyCollectionResourceById/{id}")
    @ApiOperation(value = "通过id号，查询记录", notes = "返回响应对象")
    public ResponseJson findJyCollectionResourceById(
            @ApiParam(value = "参数是id必填", required = true)
            @PathVariable String id){
        JyCollectionResource jyCollectionResource=jyCollectionResourceService.findJyCollectionResourceById(id);
        return new ResponseJson(jyCollectionResource);
    }

    @PostMapping("/update/updateJyCollectionResource")
    @ApiOperation(value = "修改我的收藏，修改之前进行去重复，在系统主要用于重命名文件操作", notes = "返回响应对象")
    public ResponseJson updateJyCollectionResource(
            @ApiParam(value = "传入文件名", required = true)
            @RequestBody JyCollectionResource jyCollectionResource){
        jyCollectionResource.setTeacherId(myId());
        int cnt = jyCollectionResourceService.repeatCollectionResouces(jyCollectionResource);
        if(cnt==0){
            jyCollectionResourceService.updateJyCollectionResource(jyCollectionResource);
            return new ResponseJson();
        }else{
            return new ResponseJson(false,"与其他文件名称相同，请修改");
        }
    }

    @GetMapping("/look/lookJyCollectionResourceById/{id}")
    @ApiOperation(value = "通过id查询到某条记录", notes = "返回响应对象")
    public ResponseJson lookJyCollectionResourceById(
            @ApiParam(value = "参数是id", required = true)
            @PathVariable String id){
        JyCollectionResource jyCollectionResource=jyCollectionResourceService.findJyCollectionResourceById(id);
        return new ResponseJson(jyCollectionResource);
    }

    @PostMapping("/findJyCollectionResourcesByCondition")
    @ApiOperation(value = "根据需要的条件传入，搜索，结果是一个集合", notes = "返回响应对象")
    public ResponseJson findJyCollectionResourcesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody JyCollectionResource jyCollectionResource){
        jyCollectionResource.setTeacherId(myId());
        List<JyCollectionResource> data=jyCollectionResourceService.findJyCollectionResourceListByCondition(jyCollectionResource);
        long count=jyCollectionResourceService.findJyCollectionResourceCountByCondition(jyCollectionResource);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneJyCollectionResourceByCondition")
    @ApiOperation(value = "根据需要的条件传入，搜索，结果是一个对象", notes = "没有时返回空")
    public ResponseJson findOneJyCollectionResourceByCondition(
            @ApiParam(value = "参数是整个对象")
            @RequestBody JyCollectionResource jyCollectionResource){
        JyCollectionResource one=jyCollectionResourceService.findOneJyCollectionResourceByCondition(jyCollectionResource);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteJyCollectionResource/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteJyCollectionResource(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        jyCollectionResourceService.deleteJyCollectionResource(id);
        return new ResponseJson();
    }


    @PostMapping("/findJyCollectionResourceListByCondition")
    @ApiOperation(value = "根据需要的条件传入，搜索，结果是一个集合", notes = "返回响应对象,不包含总条数")
    public ResponseJson findJyCollectionResourceListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody JyCollectionResource jyCollectionResource){
        List<JyCollectionResource> data=jyCollectionResourceService.findJyCollectionResourceListByCondition(jyCollectionResource);
        return new ResponseJson(data);
    }

    @PostMapping("/findJyCollectionResources")
    @ApiOperation(value = "返回当前讲师的资源列表和文件夹")
    public ResponseJson findJyCollectionResources(
            @ApiParam(value = "参数为整个JyCollectionResource对象")
            @Validated
            @RequestBody JyCollectionResource jyCollectionResource) {
        jyCollectionResource.setTeacherId(myId());
        List<JyCollectionResource> data = jyCollectionResourceService.findJyCollectionResourceList(jyCollectionResource);
        long count = jyCollectionResourceService.findJyCollectionResourceCount(jyCollectionResource);
        return new ResponseJson(data, count);
    }
    /**
     * 批量移动文件到相应的文件夹
     * @param jyResouces
     */
    @PostMapping("/updateManyCollectionResouces")
    @ApiOperation(value = "批量修改文件")
    public ResponseJson updateManyCollectionResouces(
            @ApiParam(value = "参数为整个JyCollectionResource对象")
            @RequestBody JyResouces jyResouces){
        if(jyResouces.getRowDatas().length>0){
            List<JyResoucesType> jyResoucesTypes = jyResoucesTypeService.removeRepartResoucesType(jyResouces);
            jyResouces.setCnt(0);
            jyResoucesTypes.stream().forEach((JyResoucesType e) ->{
                //如果移动的文件夹包含在了目标文件夹
                if(jyResouces.getFileId().equals(e.getId())){
                    jyResouces.setCnt(jyResouces.getCnt()+1);
                }
            });
            if(jyResouces.getCnt()>0){
                return new ResponseJson(false,"移动文件中包含了目标文件夹或其目录，操作失败");
            }else{
                jyCollectionResourceService.updateManyCollectionResouces(jyResouces);
                return new ResponseJson();
            }
        }else{
            jyCollectionResourceService.updateManyCollectionResouces(jyResouces);
            return new ResponseJson();
        }
    }
    /**
     * 批量删除文件
     * @param jyCollectionResource
     */
    @PostMapping("/deleteManyCollectionResouces")
    @ApiOperation(value = "批量删除文件")
    public void deleteManyCollectionResouces(
            @ApiParam(value = "参数为整个JyCollectionResource对象")
            @RequestBody JyCollectionResource jyCollectionResource){
        jyCollectionResourceService.deleteManyCollectionResouces(jyCollectionResource);
    }
    /**
     * 批量获取文件
     * @param jyCollectionResource
     * @return
     */
    @PostMapping("/getFileList")
    @ApiOperation(value = "批量获取文件")
    public List<JyCollectionResource> getFileList(
            @ApiParam(value = "参数为整个JyCollectionResource对象")
            @RequestBody JyCollectionResource jyCollectionResource){
        return jyCollectionResourceService.getFileList(jyCollectionResource);
    }
    @PostMapping(value = "/downLoadZipFile")
    @ApiOperation(value = "下载压缩文件")
    public void downLoadZipFile(HttpServletResponse response,@RequestBody JyCollectionResource jyCollectionResource){
        List<JyCollectionResource> fileList = jyCollectionResourceService.getFileList(jyCollectionResource);//查询数据库中记录
        try {
            zipUtils.down(fileList,response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //新建文件夹
    @PostMapping("/saveJyResoucesType")
    @ApiOperation(value = "用于新建我的上传里的文件夹", notes = "返回响应对象")
    public ResponseJson saveJyResoucesType(
            @ApiParam(value = "整个jyResoucesType对象", required = true)
            @RequestBody JyResoucesType jyResoucesType) {
        if(null==jyResoucesType.getName() || "".equals(jyResoucesType.getName())){
            return new ResponseJson(false, "请输入文件名");
        }
        jyResoucesType.setTeacherId(myId());
        jyResoucesType.setSchoolId(mySchoolId());
        jyResoucesType.setType(Constant.RESOUCES_TYPE.COLLECTION_TYPE);
        long cnt = jyResoucesTypeService.findJyResoucesTypeCountByCondition(jyResoucesType);
        if(cnt==0){
            //说明没有重复的文件夹
            JyResoucesType s = jyResoucesTypeService.saveJyResoucesType(jyResoucesType);
            return new ResponseJson(s);
        }else{
            return new ResponseJson(false, "该文件夹已存在");
        }
    }

    //重命名文件夹
    @PostMapping("/renameJyResoucesType")
    @ApiOperation(value = "重命名我的上传文件夹", notes = "返回响应对象")
    public ResponseJson renameJyResoucesType(
            @ApiParam(value = "整个jyResoucesType对象", required = true)
            @RequestBody JyResoucesType jyResoucesType) {
        if(null==jyResoucesType.getName() || "".equals(jyResoucesType.getName())){
            return new ResponseJson(false, "请输入文件名");
        }
        jyResoucesType.setTeacherId(myId());
        jyResoucesType.setSchoolId(mySchoolId());
        jyResoucesType.setType(Constant.RESOUCES_TYPE.COLLECTION_TYPE);
        int cnt = jyResoucesTypeService.repeatType(jyResoucesType);
        if(cnt==0){
            //说明没有重复的文件夹
            jyResoucesTypeService.updateJyResoucesType(jyResoucesType);
            return new ResponseJson();
        }else{
            return new ResponseJson(false, "与其他文件名称相同，请修改");
        }
    }


    //获取文件名
    @GetMapping("/getJyResoucesType/{id}")
    @ApiOperation(value = "重命名我的上传文件夹", notes = "返回响应对象")
    public ResponseJson getJyResoucesType(
            @ApiParam(value = "整个jyResoucesType对象", required = true)
            @PathVariable String id) {
        JyResoucesType jyResoucesType = jyResoucesTypeService.findJyResoucesTypeById(id);
        return new ResponseJson(jyResoucesType);
    }
    //删除文件夹
    @GetMapping("/deleteJyResoucesType/{id}")
    @ApiOperation(value = "重命名我的上传文件夹", notes = "返回响应对象")
    public ResponseJson deleteJyResoucesType(
            @ApiParam(value = "整个jyResoucesType对象", required = true)
            @PathVariable String id) {
        jyCollectionResourceService.deleteJyResoucesType(id);
        return new ResponseJson();
    }

    //单个下载文件
    @PostMapping("/downloadFile")
    public void downloadFile(@RequestBody JyCollectionResource jyCollectionResource, HttpServletResponse response) {
        HttpKit.downloadFile(jyCollectionResource.getUrl(), response);
    }

    /**
     * 去重复
     * @param jyCollectionResource
     * @return
     */
    @PostMapping("/repeatCollectionResouces")
    @ApiOperation(value = "去重复")
    public int repeatCollectionResouces(
            @ApiParam(value = "参数为整个JyCollectionResource对象")
            @RequestBody JyCollectionResource jyCollectionResource){
        return jyCollectionResourceService.repeatCollectionResouces(jyCollectionResource);
    }
}
