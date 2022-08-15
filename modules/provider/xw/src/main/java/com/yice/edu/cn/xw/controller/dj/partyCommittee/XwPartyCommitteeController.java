package com.yice.edu.cn.xw.controller.dj.partyCommittee;

import com.yice.edu.cn.common.pojo.xw.dj.partyCommittee.XwPartyCommittee;
import com.yice.edu.cn.xw.service.dj.partyCommittee.XwPartyCommitteeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xwPartyCommittee")
@Api(value = "/xwPartyCommittee",description = "党支部委员会管理，树状结构模块")
public class XwPartyCommitteeController {
    @Autowired
    private XwPartyCommitteeService xwPartyCommitteeService;

    @GetMapping("/findXwPartyCommitteeById/{id}")
    @ApiOperation(value = "通过id查找党支部委员会管理，树状结构", notes = "返回党支部委员会管理，树状结构对象")
    public XwPartyCommittee findXwPartyCommitteeById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return xwPartyCommitteeService.findXwPartyCommitteeById(id);
    }

    @PostMapping("/saveXwPartyCommittee")
    @ApiOperation(value = "保存党支部委员会管理，树状结构", notes = "返回党支部委员会管理，树状结构对象")
    public XwPartyCommittee saveXwPartyCommittee(
            @ApiParam(value = "党支部委员会管理，树状结构对象", required = true)
            @RequestBody XwPartyCommittee xwPartyCommittee){
        xwPartyCommitteeService.saveXwPartyCommittee(xwPartyCommittee);
        return xwPartyCommittee;
    }

    @PostMapping("/findXwPartyCommitteeListByCondition")
    @ApiOperation(value = "根据条件查找党支部委员会管理，树状结构列表", notes = "返回党支部委员会管理，树状结构列表")
    public List<XwPartyCommittee> findXwPartyCommitteeListByCondition(
            @ApiParam(value = "党支部委员会管理，树状结构对象")
            @RequestBody XwPartyCommittee xwPartyCommittee){
        return xwPartyCommitteeService.findXwPartyCommitteeListByCondition(xwPartyCommittee);
    }
    @PostMapping("/findXwPartyCommitteeCountByCondition")
    @ApiOperation(value = "根据条件查找党支部委员会管理，树状结构列表个数", notes = "返回党支部委员会管理，树状结构总个数")
    public long findXwPartyCommitteeCountByCondition(
            @ApiParam(value = "党支部委员会管理，树状结构对象")
            @RequestBody XwPartyCommittee xwPartyCommittee){
        return xwPartyCommitteeService.findXwPartyCommitteeCountByCondition(xwPartyCommittee);
    }

    @PostMapping("/updateXwPartyCommittee")
    @ApiOperation(value = "修改党支部委员会管理，树状结构", notes = "党支部委员会管理，树状结构对象必传")
    public void updateXwPartyCommittee(
            @ApiParam(value = "党支部委员会管理，树状结构对象,对象属性不为空则修改", required = true)
            @RequestBody XwPartyCommittee xwPartyCommittee){
        xwPartyCommitteeService.updateXwPartyCommittee(xwPartyCommittee);
    }

    @GetMapping("/deleteXwPartyCommittee/{id}")
    @ApiOperation(value = "通过id删除党支部委员会管理，树状结构")
    public void deleteXwPartyCommittee(
            @ApiParam(value = "党支部委员会管理，树状结构对象", required = true)
            @PathVariable String id){
        xwPartyCommitteeService.deleteXwPartyCommittee(id);
    }
    @PostMapping("/deleteXwPartyCommitteeByCondition")
    @ApiOperation(value = "根据条件删除党支部委员会管理，树状结构")
    public void deleteXwPartyCommitteeByCondition(
            @ApiParam(value = "党支部委员会管理，树状结构对象")
            @RequestBody XwPartyCommittee xwPartyCommittee){
        xwPartyCommitteeService.deleteXwPartyCommitteeByCondition(xwPartyCommittee);
    }
    @PostMapping("/findOneXwPartyCommitteeByCondition")
    @ApiOperation(value = "根据条件查找单个党支部委员会管理，树状结构,结果必须为单条数据", notes = "返回单个党支部委员会管理，树状结构,没有时为空")
    public XwPartyCommittee findOneXwPartyCommitteeByCondition(
            @ApiParam(value = "党支部委员会管理，树状结构对象")
            @RequestBody XwPartyCommittee xwPartyCommittee){
        return xwPartyCommitteeService.findOneXwPartyCommitteeByCondition(xwPartyCommittee);
    }

    @PostMapping("findPartyCommitteeTree/{schoolId}")
    @ApiOperation(value = "根据学校ID获取党组织结构树",notes = "根据学校ID获取党组织结构树")
    public List<XwPartyCommittee> findPartyCommitteeTree(@PathVariable("schoolId") String schoolId){
        return xwPartyCommitteeService.getPartyCommitteeTree(schoolId);
    }


    @PostMapping("findCommitteeWithParentName")
    @ApiOperation(value = "带有parentName的党组织",notes = "带有parentName的党组织")
    public List<XwPartyCommittee> findCommitteeWithParentName( @ApiParam(value = "党组织对象")
                                                                   @RequestBody XwPartyCommittee xwPartyCommittee){
        return xwPartyCommitteeService.findCommitteeWithParentName(xwPartyCommittee);
    }

}
