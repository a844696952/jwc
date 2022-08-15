package com.yice.edu.cn.xw.controller.dj.partyMember;

import com.yice.edu.cn.common.pojo.xw.dj.partyMember.IdAndName;
import com.yice.edu.cn.common.pojo.xw.dj.partyMember.XwPartyMember;
import com.yice.edu.cn.common.pojo.xw.dj.partyMember.XwPartyMemberExcel;
import com.yice.edu.cn.common.pojo.xw.dj.partyMember.XwPartyMemberVaild;
import com.yice.edu.cn.xw.service.dj.partyMember.XwPartyMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xwPartyMember")
@Api(value = "/xwPartyMember",description = "校务管理中的党建管理的党员管理模块")
public class XwPartyMemberController {
    @Autowired
    private XwPartyMemberService xwPartyMemberService;

    @GetMapping("/findXwPartyMemberById/{id}")
    @ApiOperation(value = "通过id查找校务管理中的党建管理的党员管理", notes = "返回校务管理中的党建管理的党员管理对象")
    public XwPartyMember findXwPartyMemberById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return xwPartyMemberService.findXwPartyMemberById(id);
    }

    @PostMapping("/saveXwPartyMember")
    @ApiOperation(value = "保存校务管理中的党建管理的党员管理", notes = "返回校务管理中的党建管理的党员管理对象")
    public XwPartyMember saveXwPartyMember(
            @ApiParam(value = "校务管理中的党建管理的党员管理对象", required = true)
            @RequestBody XwPartyMember xwPartyMember){
        xwPartyMemberService.saveXwPartyMember(xwPartyMember);
        return xwPartyMember;
    }


    @PostMapping("/findXwPartyMemberListByCondition")
    @ApiOperation(value = "根据条件查找校务管理中的党建管理的党员管理列表", notes = "返回校务管理中的党建管理的党员管理列表")
    public List<XwPartyMember> findXwPartyMemberListByCondition(
            @ApiParam(value = "校务管理中的党建管理的党员管理对象")
            @RequestBody XwPartyMember xwPartyMember){
        return xwPartyMemberService.findXwPartyMemberListByCondition(xwPartyMember);
    }
    @PostMapping("/findXwPartyMemberCountByCondition")
    @ApiOperation(value = "根据条件查找校务管理中的党建管理的党员管理列表个数", notes = "返回校务管理中的党建管理的党员管理总个数")
    public long findXwPartyMemberCountByCondition(
            @ApiParam(value = "校务管理中的党建管理的党员管理对象")
            @RequestBody XwPartyMember xwPartyMember){
        return xwPartyMemberService.findXwPartyMemberCountByCondition(xwPartyMember);
    }

    @PostMapping("/updateXwPartyMember")
    @ApiOperation(value = "修改校务管理中的党建管理的党员管理", notes = "校务管理中的党建管理的党员管理对象必传")
    public void updateXwPartyMember(
            @ApiParam(value = "校务管理中的党建管理的党员管理对象,对象属性不为空则修改", required = true)
            @RequestBody XwPartyMember xwPartyMember){
        xwPartyMemberService.updateXwPartyMember(xwPartyMember);
    }

    @GetMapping("/deleteXwPartyMember/{id}")
    @ApiOperation(value = "通过id删除校务管理中的党建管理的党员管理")
    public void deleteXwPartyMember(
            @ApiParam(value = "校务管理中的党建管理的党员管理对象", required = true)
            @PathVariable String id){
        xwPartyMemberService.deleteXwPartyMember(id);
    }
    @PostMapping("/deleteXwPartyMemberByCondition")
    @ApiOperation(value = "根据条件删除校务管理中的党建管理的党员管理")
    public void deleteXwPartyMemberByCondition(
            @ApiParam(value = "校务管理中的党建管理的党员管理对象")
            @RequestBody XwPartyMember xwPartyMember){
        xwPartyMemberService.deleteXwPartyMemberByCondition(xwPartyMember);
    }
    @PostMapping("/findOneXwPartyMemberByCondition")
    @ApiOperation(value = "根据条件查找单个校务管理中的党建管理的党员管理,结果必须为单条数据", notes = "返回单个校务管理中的党建管理的党员管理,没有时为空")
    public XwPartyMember findOneXwPartyMemberByCondition(
            @ApiParam(value = "校务管理中的党建管理的党员管理对象")
            @RequestBody XwPartyMember xwPartyMember){
        return xwPartyMemberService.findOneXwPartyMemberByCondition(xwPartyMember);
    }

    @PostMapping("/findXwPartyMemberListByConditions")
    @ApiOperation(value = "根据条件查找校务管理中的党建管理的党员管理列表,列表接口", notes = "返回校务管理中的党建管理的党员管理列表")
    public List<XwPartyMember> findXwPartyMemberListByConditions(
            @ApiParam(value = "校务管理中的党建管理的党员管理对象")
            @RequestBody XwPartyMember xwPartyMember){
        return xwPartyMemberService.findXwPartyMemberListByConditions(xwPartyMember);
    }
    @PostMapping("/findXwPartyMemberCountByConditions")
    @ApiOperation(value = "根据条件查找校务管理中的党建管理的党员管理列表个数,列表接口", notes = "返回校务管理中的党建管理的党员管理总个数")
    public long findXwPartyMemberCountByConditions(
            @ApiParam(value = "校务管理中的党建管理的党员管理对象")
            @RequestBody XwPartyMember xwPartyMember){
        return xwPartyMemberService.findXwPartyMemberCountByConditions(xwPartyMember);
    }


    @PostMapping("/uploadExcel")
    @ApiOperation(value = "上传excel", notes = "返回校务管理中的党建管理的党员管理对象")
    public void uploadExcel(
            @ApiParam(value = "校务管理中的党建管理的党员管理对象", required = true)
            @RequestBody List<XwPartyMemberExcel> list){
        xwPartyMemberService.uploadExcel(list);
    }

    @PostMapping("/batchUpdateParty")
    @ApiOperation(value = "批量修改党员信息", notes = "")
    public void batchUpdateParty(
            @ApiParam(value = "校务管理中的党建管理的党员管理对象", required = true)
            @RequestBody List<XwPartyMember> list){
        xwPartyMemberService.batchUpdateParty(list);
    }


    @GetMapping("/getTeacherList/{schoolId}")
    @ApiOperation(value = "通过id查找校务管理中的党建管理的党员管理", notes = "返回校务管理中的党建管理的党员管理对象")
    public List<IdAndName> getTeacherList(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String schoolId){
        return xwPartyMemberService.getTeacherList(schoolId);
    }

    @PostMapping("/batchSaveXwPartyMember")
    @ApiOperation(value = "保存校务管理中的党建管理的党员管理", notes = "返回校务管理中的党建管理的党员管理对象")
    public void batchSaveXwPartyMember(
            @ApiParam(value = "校务管理中的党建管理的党员管理对象", required = true)
            @RequestBody List<XwPartyMember> xwPartyMemberList){
        xwPartyMemberService.batchSaveXwPartyMember(xwPartyMemberList);
    }

    @PostMapping("/getPartyMemberTree/{schoolId}")
    @ApiOperation(value = "根据类别获取党组织结构树", notes = "根据类别获取党组织结构树")
    public List<XwPartyMember> getPartyMemberTree(@PathVariable("schoolId")String schoolId){
        List<XwPartyMember> partyMemberTree = xwPartyMemberService.getPartyMemberTree(schoolId);
        return partyMemberTree;
    }

    @PostMapping("/findXwPartyMemberListByArray")
    @ApiOperation(value = "根据条件查找校务管理中的党建管理的党员管理列表,根据指定的id（传入数组）获取", notes = "返回校务管理中的党建管理的党员管理列表")
    public List<XwPartyMember> findXwPartyMemberListByArray(
            @ApiParam(value = "校务管理中的党建管理的党员管理对象")
            @RequestBody XwPartyMember xwPartyMember){
        return xwPartyMemberService.findXwPartyMemberListByArray(xwPartyMember);
    }
    @PostMapping("/getTeacherNameListByString")
    @ApiOperation(value = "根据条件查找校务管理中的党建管理的党员管理列表,根据指定的id（传入数组）获取名称", notes = "返回校务管理中的党建管理的党员管理列表获取名称")
    public String getTeacherNameListByString(
            @ApiParam(value = "校务管理中的党建管理的党员管理对象")
            @RequestBody XwPartyMember xwPartyMember){
        return xwPartyMemberService.getTeacherNameListByString(xwPartyMember);
    }

    @PostMapping("/bachUpdateByArray")
    @ApiOperation(value = "修改校务管理中的党建管理的党员管理", notes = "校务管理中的党建管理的党员管理对象必传")
    public void bachUpdateByArray(
            @ApiParam(value = "校务管理中的党建管理的党员管理对象,对象属性不为空则修改", required = true)
            @RequestBody XwPartyMember xwPartyMember){
        xwPartyMemberService.bachUpdateByArray(xwPartyMember);
    }

    @PostMapping("/batchSaveXwPartyMemberByRowData")
    @ApiOperation(value = "批量修改党员信息", notes = "")
    public void batchSaveXwPartyMemberByRowData(
            @ApiParam(value = "校务管理中的党建管理的党员管理对象", required = true)
            @RequestBody List<XwPartyMember> list){
        xwPartyMemberService.batchSaveXwPartyMemberByRowData(list);
    }
    @GetMapping("/lookXwPartyMemberById/{id}")
    @ApiOperation(value = "通过id查找校务管理中的党建管理的党员管理", notes = "返回校务管理中的党建管理的党员管理对象")
    public XwPartyMemberVaild lookXwPartyMemberById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return xwPartyMemberService.lookXwPartyMemberById(id);
    }

}
