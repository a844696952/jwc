package com.yice.edu.cn.jy.controller.resources;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.resources.*;
import com.yice.edu.cn.jy.service.resources.JySchoolResoucesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jySchoolResouces")
@Api(value = "/jySchoolResouces",description = "校本资源模块接口")
public class JySchoolResoucesController {
    @Autowired
    private JySchoolResoucesService jySchoolResoucesService;

    @GetMapping("/findJySchoolResoucesById/{id}")
    @ApiOperation(value = "通过id查找校本资源", notes = "返回校本资源对象")
    public JySchoolResouces findJySchoolResoucesById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return jySchoolResoucesService.findJySchoolResoucesById(id);
    }

    @PostMapping("/saveJySchoolResouces")
    @ApiOperation(value = "新增校本教案，在校本教案中，不存在新增，校本教案由讲师分享加入的", notes = "返回响应对象")
    public JySchoolResouces saveJySchoolResouces(
            @ApiParam(value = "创建者：陈飞龙。创建时间：2018-11-11。说明：用于存放校本资源对象", required = true)
            @RequestBody JySchoolResouces jySchoolResouces){
        jySchoolResoucesService.saveJySchoolResouces(jySchoolResouces);
        return jySchoolResouces;
    }
    @PostMapping("/batchSaveJySchoolResouces")
    @ApiOperation(value = "新增校本教案，在校本教案中，不存在新增，校本教案由讲师分享加入的", notes = "返回响应对象")
    public void batchSaveJySchoolResouces(
            @ApiParam(value = "创建者：陈飞龙。创建时间：2018-11-11。说明：用于存放校本资源对象", required = true)
            @RequestBody List<JySchoolResouces> jySchoolResoucess){
        jySchoolResoucesService.batchSaveJySchoolResouces(jySchoolResoucess);
    }

    @PostMapping("/findJySchoolResoucesListByCondition")
    @ApiOperation(value = "根据条件查找校本资源列表", notes = "用于存放校本资源列表")
    public List<JySchoolResouces> findJySchoolResoucesListByCondition(
            @ApiParam(value = "校本资源对象")
            @RequestBody JySchoolResouces jySchoolResouces){
        return jySchoolResoucesService.findJySchoolResoucesListByCondition(jySchoolResouces);
    }
    @PostMapping("/findJySchoolResoucesCountByCondition")
    @ApiOperation(value = "根据条件查找校本资源列表个数", notes = "用于存放校本资源总个数")
    public long findJySchoolResoucesCountByCondition(
            @ApiParam(value = "校本资源对象")
            @RequestBody JySchoolResouces jySchoolResouces){
        return jySchoolResoucesService.findJySchoolResoucesCountByCondition(jySchoolResouces);
    }

    @PostMapping("/updateJySchoolResouces")
    @ApiOperation(value = "修改校本资源", notes = "校本资源对象必传")
    public void updateJySchoolResouces(
            @ApiParam(value = "校本资源对象,对象属性不为空则修改", required = true)
            @RequestBody JySchoolResouces jySchoolResouces){
        jySchoolResoucesService.updateJySchoolResouces(jySchoolResouces);
    }

    @GetMapping("/deleteJySchoolResouces/{id}")
    @ApiOperation(value = "通过id删除校本资源")
    public void deleteJySchoolResouces(
            @ApiParam(value = "校本资源对象", required = true)
            @PathVariable String id){
        jySchoolResoucesService.deleteJySchoolResouces(id);
    }
    @PostMapping("/deleteJySchoolResoucesByCondition")
    @ApiOperation(value = "根据条件删除校本资源")
    public void deleteJySchoolResoucesByCondition(
            @ApiParam(value = "校本资源对象")
            @RequestBody JySchoolResouces jySchoolResouces){
        jySchoolResoucesService.deleteJySchoolResoucesByCondition(jySchoolResouces);
    }
    @PostMapping("/findOneJySchoolResoucesByCondition")
    @ApiOperation(value = "根据条件查找单个校本资源,结果必须为单条数据", notes = "返回单个校本资源,没有时为空")
    public JySchoolResouces findOneJySchoolResoucesByCondition(
            @ApiParam(value = "校本资源对象")
            @RequestBody JySchoolResouces jySchoolResouces){
        return jySchoolResoucesService.findOneJySchoolResoucesByCondition(jySchoolResouces);
    }

    @PostMapping("/findJySchoolResoucesList")
    @ApiOperation(value = "返回文件夹和资源列表")
    public List<JySchoolResouces> findJySchoolResoucesList(
            @ApiParam(value = "参数为整个JySchoolResouces对象")
            @RequestBody JySchoolResouces jySchoolResouces){
        return jySchoolResoucesService.findJySchoolResoucesList(jySchoolResouces);
    }
    @PostMapping("/findJySchoolResoucesCount")
    @ApiOperation(value = "返回文件夹和资源个数")
    public long findJySchoolResoucesCount(
            @ApiParam(value = "参数为整个JySchoolResouces对象")
            @RequestBody JySchoolResouces jySchoolResouces){
        return jySchoolResoucesService.findJySchoolResoucesCount(jySchoolResouces);
    }
    /**
     * 批量移动文件到相应的文件夹
     * @param
     */
    @PostMapping("/updateManySchoolResouces")
    @ApiOperation(value = "返回文件夹和资源个数")
    public void updateManySchoolResouces(
            @ApiParam(value = "参数为整个JySchoolResouces对象")
            @RequestBody JyResouces jyResouces){
        jySchoolResoucesService.updateManySchoolResouces(jyResouces);
    }

    /**
     * 批量收藏
     *      * @param rowData
     */
    @PostMapping("/insertManySchoolResouces")
    @ApiOperation(value = "返回文件夹和资源个数")
    public List<JyCollectionResource> insertManySchoolResouces(
            @ApiParam(value = "参数为整个JySchoolResouces对象")
            @RequestBody JySchoolResouces jySchoolResouces){
        return jySchoolResoucesService.insertManySchoolResouces(jySchoolResouces);
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
        return jySchoolResoucesService.getFileList(jyCollectionResource);
    }
    /**
     * 去重复
     * @param jySchoolResouces
     * @return
     */
    @PostMapping("/repeatSchoolResouces")
    @ApiOperation(value = "去重复")
    public int repeatCollectionResouces(
            @ApiParam(value = "参数为整个JyCollectionResource对象")
            @RequestBody JySchoolResouces jySchoolResouces){
        return jySchoolResoucesService.repeatSchoolResouces(jySchoolResouces);
    }

    /**
     * 通过文件夹编号删除校本资源
     * @param jyResouces
     * @return
     */
    @PostMapping("/deleteByResoucesType")
    @ApiOperation(value = "通过文件夹编号删除校本资源")
    public void deleteByResoucesType(
            @ApiParam(value = "参数为整个jyResouces对象")
            @RequestBody JyResouces jyResouces){
        jySchoolResoucesService.deleteByResoucesType(jyResouces);
    }

    /**
     * 通过文件编号删除校本资源
     * @param jyResouces
     * @return
     */
    @PostMapping("/deleteByResoucesId")
    @ApiOperation(value = "通过文件夹编号删除校本资源")
    public void deleteByResoucesId(
            @ApiParam(value = "参数为整个jyResouces对象")
            @RequestBody JyResouces jyResouces){
        jySchoolResoucesService.deleteByResoucesId(jyResouces);
    }

    //删除文件夹
    @GetMapping("/deleteJyResoucesType/{id}")
    @ApiOperation(value = "删除文件夹", notes = "返回响应对象")
    public ResponseJson deleteJyResoucesType(
            @ApiParam(value = "整个jyResoucesType对象", required = true)
            @PathVariable String id) {
        jySchoolResoucesService.deleteJyResoucesType(id);
        return new ResponseJson();
    }

    //统计查询
    @PostMapping("/censusSchoolResouces")
    @ApiOperation(value = "统计校本资源总数量，传入schoolId,teacherId,startTime(开始时间),endTime（结束时间）,type（类型），schollId必传", notes = "返回响应对象")
    public List<JySchoolResourceCensus> censusSchoolResouces(@RequestBody JySchoolResouces jySchoolResouces){
        return jySchoolResoucesService.censusSchoolResouces(jySchoolResouces);
    }

    //统计查询
    @PostMapping("/censusSumSchoolResouces")
    @ApiOperation(value = "统计校本资源总数量，传入schoolId,teacherId,startTime(开始时间),endTime（结束时间）,type（类型），schollId必传", notes = "返回响应对象")
    public long censusSumSchoolResouces(@RequestBody JySchoolResouces jySchoolResouces){
        return jySchoolResoucesService.censusSumSchoolResouces(jySchoolResouces);
    }

    //统计查询
    @PostMapping("/censusSumResoucesByDay")
    @ApiOperation(value = "统计校本资源每天的上传数量，传入schoolId,teacherId,startTime(开始时间),endTime（结束时间）,type（类型），schollId必传", notes = "返回响应对象")
    public List<JySchoolResourcesByDay> censusSumResoucesByDay(@RequestBody JySchoolResouces jySchoolResouces){
        return jySchoolResoucesService.censusSumResoucesByDay(jySchoolResouces);
    }
}
