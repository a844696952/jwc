package com.yice.edu.cn.jy.controller.resources;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.resources.JyCollectionResource;
import com.yice.edu.cn.common.pojo.jy.resources.JyResouces;
import com.yice.edu.cn.common.pojo.jy.resources.JyResoucesType;
import com.yice.edu.cn.jy.service.resources.JyCollectionResourceService;
import com.yice.edu.cn.jy.service.resources.JyResoucesTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jyCollectionResource")
@Api(value = "/jyCollectionResource",description = "创建时间：2018-11-11。说明：用于存放校本资源模块")
public class JyCollectionResourceController {
    @Autowired
    private JyCollectionResourceService jyCollectionResourceService;
    @Autowired
    private JyResoucesTypeService jyResoucesTypeService;
    @GetMapping("/findJyCollectionResourceById/{id}")
    @ApiOperation(value = "通过id查找创建时间：2018-11-11。说明：用于存放校本资源", notes = "创建时间：2018-11-11。说明：用于存放校本资源对象")
    public JyCollectionResource findJyCollectionResourceById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return jyCollectionResourceService.findJyCollectionResourceById(id);
    }

    @PostMapping("/saveJyCollectionResource")
    @ApiOperation(value = "保存创建时间：2018-11-11。说明：用于存放校本资源", notes = "创建时间：2018-11-11。说明：用于存放校本资源对象")
    public JyCollectionResource saveJyCollectionResource(
            @ApiParam(value = "创建时间：2018-11-11。说明：用于存放校本资源对象", required = true)
            @RequestBody JyCollectionResource jyCollectionResource){
        jyCollectionResourceService.saveJyCollectionResource(jyCollectionResource);
        return jyCollectionResource;
    }
    @PostMapping("/batchSaveJyCollectionResource")
    @ApiOperation(value = "新增校本教案，在校本教案中，不存在新增，校本教案由讲师分享加入的", notes = "返回响应对象")
    public void batchSaveJyCollectionResource(
            @ApiParam(value = "创建时间：2018-11-11。说明：用于存放校本资源对象", required = true)
            @RequestBody List<JyCollectionResource> jyCollectionResources){
        jyCollectionResourceService.batchSaveJyCollectionResource(jyCollectionResources);
    }
    @PostMapping("/findJyCollectionResourceListByCondition")
    @ApiOperation(value = "根据条件查找创建时间：2018-11-11。说明：用于存放校本资源列表", notes = "创建时间：2018-11-11。说明：用于存放校本资源列表")
    public List<JyCollectionResource> findJyCollectionResourceListByCondition(
            @ApiParam(value = "创建时间：2018-11-11。说明：用于存放校本资源对象")
            @RequestBody JyCollectionResource jyCollectionResource){
        return jyCollectionResourceService.findJyCollectionResourceListByCondition(jyCollectionResource);
    }
    @PostMapping("/findJyCollectionResourceCountByCondition")
    @ApiOperation(value = "根据条件查找创建时间：2018-11-11。说明：用于存放校本资源列表个数", notes = "创建时间：2018-11-11。说明：用于存放校本资源总个数")
    public long findJyCollectionResourceCountByCondition(
            @ApiParam(value = "创建时间：2018-11-11。说明：用于存放校本资源对象")
            @RequestBody JyCollectionResource jyCollectionResource){
        return jyCollectionResourceService.findJyCollectionResourceCountByCondition(jyCollectionResource);
    }

    @PostMapping("/updateJyCollectionResource")
    @ApiOperation(value = "修改创建时间：2018-11-11。说明：用于存放校本资源", notes = "创建时间：2018-11-11。说明：用于存放校本资源对象必传")
    public void updateJyCollectionResource(
            @ApiParam(value = "创建时间：2018-11-11。说明：用于存放校本资源对象,对象属性不为空则修改", required = true)
            @RequestBody JyCollectionResource jyCollectionResource){
        jyCollectionResourceService.updateJyCollectionResource(jyCollectionResource);
    }

    @GetMapping("/deleteJyCollectionResource/{id}")
    @ApiOperation(value = "通过id删除创建时间：2018-11-11。说明：用于存放校本资源")
    public void deleteJyCollectionResource(
            @ApiParam(value = "创建时间：2018-11-11。说明：用于存放校本资源对象", required = true)
            @PathVariable String id){
        jyCollectionResourceService.deleteJyCollectionResource(id);
    }
    @PostMapping("/deleteJyCollectionResourceByCondition")
    @ApiOperation(value = "根据条件删除创建时间：2018-11-11。说明：用于存放校本资源")
    public void deleteJyCollectionResourceByCondition(
            @ApiParam(value = "创建时间：2018-11-11。说明：用于存放校本资源对象")
            @RequestBody JyCollectionResource jyCollectionResource){
        jyCollectionResourceService.deleteJyCollectionResourceByCondition(jyCollectionResource);
    }
    @PostMapping("/findOneJyCollectionResourceByCondition")
    @ApiOperation(value = "根据条件查找单个创建时间：2018-11-11。说明：用于存放校本资源,结果必须为单条数据", notes = "返回单个创建时间：2018-11-11。说明：用于存放校本资源,没有时为空")
    public JyCollectionResource findOneJyCollectionResourceByCondition(
            @ApiParam(value = "创建时间：2018-11-11。说明：用于存放校本资源对象")
            @RequestBody JyCollectionResource jyCollectionResource){
        return jyCollectionResourceService.findOneJyCollectionResourceByCondition(jyCollectionResource);
    }

    @PostMapping("/findJyCollectionResourceList")
    @ApiOperation(value = "返回文件夹和资源列表")
    public List<JyCollectionResource> findJyCollectionResourceList(
            @ApiParam(value = "参数为整个JyCollectionResource对象")
            @RequestBody JyCollectionResource jyCollectionResource){
        return jyCollectionResourceService.findJyCollectionResourceList(jyCollectionResource);
    }
    @PostMapping("/findJyCollectionResourceCount")
    @ApiOperation(value = "返回文件夹和资源个数")
    public long findJyCollectionResourceCount(
            @ApiParam(value = "参数为整个JyCollectionResource对象")
            @RequestBody JyCollectionResource jyCollectionResource){
        return jyCollectionResourceService.findJyCollectionResourceCount(jyCollectionResource);
    }

    /**
     * 批量移动文件到相应的文件夹
     * @param jyResouces
     */
    @PostMapping("/updateManyCollectionResouces")
    @ApiOperation(value = "批量修改文件")
    public void updateManyCollectionResouces(
            @ApiParam(value = "参数为整个JyCollectionResource对象")
            @RequestBody JyResouces jyResouces){
        jyCollectionResourceService.updateManyCollectionResouces(jyResouces);
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
    @ApiOperation(value = "删除文件夹", notes = "返回响应对象")
    public ResponseJson deleteJyResoucesType(
            @ApiParam(value = "整个jyResoucesType对象", required = true)
            @PathVariable String id) {
        jyCollectionResourceService.deleteJyResoucesType(id);
        return new ResponseJson();
    }
}
