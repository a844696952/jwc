package com.yice.edu.cn.jy.controller.resources;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.resources.JyCollectionResource;
import com.yice.edu.cn.common.pojo.jy.resources.JyResouces;
import com.yice.edu.cn.common.pojo.jy.resources.JySchoolResouces;
import com.yice.edu.cn.common.pojo.jy.subjectSourse.SubjectMaterial;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.jy.service.resources.JyResoucesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jyResouces")
@Api(value = "/jyResouces",description = "创建时间：2018-10-29。说明：用于存放我的资源模块")
public class JyResoucesController {
    @Autowired
    private JyResoucesService jyResoucesService;

    @GetMapping("/findJyResoucesById/{id}")
    @ApiOperation(value = "通过id查找创建时间：2018-10-29。说明：用于存放我的资源", notes = "返回创建时间：2018-10-29。说明：用于存放我的资源对象")
    public JyResouces findJyResoucesById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return jyResoucesService.findJyResoucesById(id);
    }

    @PostMapping("/saveJyResouces")
    @ApiOperation(value = "创建时间：2018-10-29。说明：用于存放我的资源", notes = "返回创建时间：2018-10-29。说明：用于存放我的资源对象")
    public JyResouces saveJyResouces(
            @ApiParam(value = "创建时间：2018-10-29。说明：用于存放我的资源对象", required = true)
            @RequestBody JyResouces jyResouces){
        jyResoucesService.saveJyResouces(jyResouces);
        return jyResouces;
    }

    @PostMapping("/findJyResoucesListByCondition")
    @ApiOperation(value = "根据条件查找创建时间：2018-10-29。说明：用于存放我的资源列表", notes = "返回创建时间：2018-10-29。说明：用于存放我的资源列表")
    public List<JyResouces> findJyResoucesListByCondition(
            @ApiParam(value = "创建时间：2018-10-29。说明：用于存放我的资源对象")
            @RequestBody JyResouces jyResouces){
        return jyResoucesService.findJyResoucesListByCondition(jyResouces);
    }
    @PostMapping("/findJyResoucesCountByCondition")
    @ApiOperation(value = "根据条件查找创建时间：2018-10-29。说明：用于存放我的资源列表个数", notes = "返回创建时间：2018-10-29。说明：用于存放我的资源总个数")
    public long findJyResoucesCountByCondition(
            @ApiParam(value = "创建时间：2018-10-29。说明：用于存放我的资源对象")
            @RequestBody JyResouces jyResouces){
        return jyResoucesService.findJyResoucesCountByCondition(jyResouces);
    }

    @PostMapping("/updateJyResouces")
    @ApiOperation(value = "修改创建时间：2018-10-29。说明：用于存放我的资源", notes = "创建时间：2018-10-29。说明：用于存放我的资源对象必传")
    public void updateJyResouces(
            @ApiParam(value = "创建时间：2018-10-29。说明：用于存放我的资源对象,对象属性不为空则修改", required = true)
            @RequestBody JyResouces jyResouces){
        jyResoucesService.updateJyResouces(jyResouces);
    }

    @GetMapping("/deleteJyResouces/{id}")
    @ApiOperation(value = "通过id删除创建时间：2018-10-29。说明：用于存放我的资源")
    public void deleteJyResouces(
            @ApiParam(value = "创建时间：2018-10-29。说明：用于存放我的资源对象", required = true)
            @PathVariable String id){
        jyResoucesService.deleteJyResouces(id);
    }
    @PostMapping("/deleteJyResoucesByCondition")
    @ApiOperation(value = "根据条件删除创建时间：2018-10-29。说明：用于存放我的资源")
    public void deleteJyResoucesByCondition(
            @ApiParam(value = "创建时间：2018-10-29。说明：用于存放我的资源对象")
            @RequestBody JyResouces jyResouces){
        jyResoucesService.deleteJyResoucesByCondition(jyResouces);
    }
    @PostMapping("/findOneJyResoucesByCondition")
    @ApiOperation(value = "根据条件查找单个创建时间：2018-10-29。说明：用于存放我的资源,结果必须为单条数据", notes = "返回单个创建时间：2018-10-29。说明：用于存放我的资源,没有时为空")
    public JyResouces findOneJyResoucesByCondition(
            @ApiParam(value = "创建时间：2018-10-29。说明：用于存放我的资源对象")
            @RequestBody JyResouces jyResouces){
        return jyResoucesService.findOneJyResoucesByCondition(jyResouces);
    }

    @PostMapping("/findJyResoucesList")
    @ApiOperation(value = "返回文件夹和资源列表")
    public List<JyResouces> findJyResoucesList(
            @ApiParam(value = "参数为整个JyResouces对象")
            @RequestBody JyResouces jyResouces){
        return jyResoucesService.findJyResoucesList(jyResouces);
    }
    @PostMapping("/findJyResoucesCount")
    @ApiOperation(value = "返回文件夹和资源个数")
    public long findJyResoucesCount(
            @ApiParam(value = "参数为整个JyResouces对象")
            @RequestBody JyResouces jyResouces){
        return jyResoucesService.findJyResoucesCount(jyResouces);
    }

    @PostMapping("/batchSaveJyResouces")
    @ApiOperation(value = "创建时间：2018-10-29。说明：用于存放我的资源", notes = "返回创建时间：2018-10-29。说明：用于存放我的资源对象")
    public void batchSaveJyResouces(
            @ApiParam(value = "创建时间：2018-10-29。说明：用于存放我的资源对象", required = true)
            @RequestBody List<JyResouces> jyResoucess){
        jyResoucesService.batchSaveJyResouces(jyResoucess);
    }
    /**
     * 批量移动文件到相应的文件夹
     * @param jyResouces
     */
    @PostMapping("/updateManyResouces")
    @ApiOperation(value = "返回文件夹和资源个数")
    public void updateManyResouces(
            @ApiParam(value = "参数为整个JyResouces对象")
            @RequestBody JyResouces jyResouces){
        jyResoucesService.updateManyResouces(jyResouces);
    }
    /**
     * 批量删除文件
     * @param jyResouces
     */
    @PostMapping("/deleteManyResouces")
    @ApiOperation(value = "返回文件夹和资源个数")
    public void deleteManyResouces(
            @ApiParam(value = "参数为整个JyResouces对象")
            @RequestBody JyResouces jyResouces){
        jyResoucesService.deleteManyResouces(jyResouces);
    }
    /**
     * 批量删除文件
     * @param jyResouces
     */
    @PostMapping("/deleteManyResoucesByFileId")
    @ApiOperation(value = "返回文件夹和资源个数")
    public void deleteManyResoucesByFileId(
            @ApiParam(value = "参数为整个JyResouces对象")
            @RequestBody JyResouces jyResouces){
        jyResoucesService.deleteManyResoucesByFileId(jyResouces);
    }
    /**
     * 批量分享文件 到校本资源中
     * @param jyResouces
     */
    @PostMapping("/insertManyResouces")
    @ApiOperation(value = "批量分享")
    public void  insertManyResouces(
            @ApiParam(value = "参数为整个JyResouces对象")
            @RequestBody JyResouces jyResouces){
        jyResoucesService.insertManyResouces(jyResouces);
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
        return jyResoucesService.getFileList(jyCollectionResource);
    }
    /**
     * 去重复
     * @param jyResouces
     * @return
     */
    @PostMapping("/repeatResouces")
    @ApiOperation(value = "去重复")
    public int repeatResouces(
            @ApiParam(value = "参数为整个jyResouces对象")
            @RequestBody JyResouces jyResouces){
        return jyResoucesService.repeatResouces(jyResouces);
    }
    /**
     * 移除分享
     * @param jyResouces
     * @return
     */
    @PostMapping("/removeShare")
    @ApiOperation(value = "移除分享")
    public void removeShare(
            @ApiParam(value = "参数为整个jyResouces对象")
            @RequestBody JyResouces jyResouces){
        jyResoucesService.removeShare(jyResouces);
    }
    /**
     * 移除分享
     * @param jySchoolResouces
     * @return
     */
    @PostMapping("/saveJySchoolResouces")
    @ApiOperation(value = "移除分享")
    public void saveJySchoolResouces(
            @ApiParam(value = "参数为整个jyResouces对象")
            @RequestBody JySchoolResouces jySchoolResouces){
        jyResoucesService.saveJySchoolResouces(jySchoolResouces);
    }
    /**
     * 移除分享
     * @param jyResouces
     * @return
     */
    @PostMapping("/deleteFile")
    @ApiOperation(value = "删除文件")
    public void deleteFile(
            @ApiParam(value = "参数为整个jyResouces对象")
            @RequestBody JyResouces jyResouces){
        jyResoucesService.deleteFile(jyResouces);
    }
    @GetMapping("/deleteJyResoucesType/{id}")
    @ApiOperation(value = "删除我的上传文件夹")
    public void deleteJyResoucesType(@PathVariable("id") String id){
        jyResoucesService.deleteJyResoucesType(id);
    }

    //分享文件
    @PostMapping("/noShare")
    @ApiOperation(value = "取消分享文件", notes = "返回响应对象")
    public void noShare(
            @ApiParam(value = "校本资源对象", required = true)
            @RequestBody JyResouces jyResouces) {
        jyResoucesService.noShare(jyResouces);
    }


    @PostMapping("/changeResouces")
    @ApiOperation(value = "去重复")
    public int changeResouces(
            @ApiParam(value = "参数为整个jyResouces对象")
            @RequestBody JyResouces jyResouces){
        return jyResoucesService.changeResouces(jyResouces);
    }
}
