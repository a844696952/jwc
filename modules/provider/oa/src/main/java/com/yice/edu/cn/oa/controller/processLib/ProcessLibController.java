package com.yice.edu.cn.oa.controller.processLib;

import com.yice.edu.cn.common.pojo.oa.process.ProcessLib;
import com.yice.edu.cn.oa.service.processLib.ProcessLibService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/processLib")
@Api(value = "/processLib",description = "流程库模块")
public class ProcessLibController {
    @Autowired
    private ProcessLibService processLibService;

    @GetMapping("/findProcessLibById/{id}")
    @ApiOperation(value = "通过id查找流程库", notes = "返回流程库对象")
    public ProcessLib findProcessLibById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return processLibService.findProcessLibById(id);
    }

    @PostMapping("/saveProcessLib")
    @ApiOperation(value = "保存流程库", notes = "返回流程库对象")
    public ProcessLib saveProcessLib(
            @ApiParam(value = "流程库对象", required = true)
            @RequestBody ProcessLib processLib){
        processLibService.saveProcessLib(processLib);
        return processLib;
    }

    @PostMapping("/findProcessLibListByCondition")
    @ApiOperation(value = "根据条件查找流程库列表", notes = "返回流程库列表")
    public List<ProcessLib> findProcessLibListByCondition(
            @ApiParam(value = "流程库对象")
            @RequestBody ProcessLib processLib){
        return processLibService.findProcessLibListByCondition(processLib);
    }
    @PostMapping("/findProcessLibCountByCondition")
    @ApiOperation(value = "根据条件查找流程库列表个数", notes = "返回流程库总个数")
    public long findProcessLibCountByCondition(
            @ApiParam(value = "流程库对象")
            @RequestBody ProcessLib processLib){
        return processLibService.findProcessLibCountByCondition(processLib);
    }

    @PostMapping("/updateProcessLib")
    @ApiOperation(value = "修改流程库", notes = "流程库对象必传")
    public void updateProcessLib(
            @ApiParam(value = "流程库对象,对象属性不为空则修改", required = true)
            @RequestBody ProcessLib processLib){
        processLibService.updateProcessLib(processLib);
    }

    @GetMapping("/deleteProcessLib/{id}")
    @ApiOperation(value = "通过id删除流程库")
    public void deleteProcessLib(
            @ApiParam(value = "流程库对象", required = true)
            @PathVariable String id){
        processLibService.deleteProcessLib(id);
    }
    @PostMapping("/deleteProcessLibByCondition")
    @ApiOperation(value = "根据条件删除流程库")
    public void deleteProcessLibByCondition(
            @ApiParam(value = "流程库对象")
            @RequestBody ProcessLib processLib){
        processLibService.deleteProcessLibByCondition(processLib);
    }
    @PostMapping("/findOneProcessLibByCondition")
    @ApiOperation(value = "根据条件查找单个流程库,结果必须为单条数据", notes = "返回单个流程库,没有时为空")
    public ProcessLib findOneProcessLibByCondition(
            @ApiParam(value = "流程库对象")
            @RequestBody ProcessLib processLib){
        return processLibService.findOneProcessLibByCondition(processLib);
    }
}
