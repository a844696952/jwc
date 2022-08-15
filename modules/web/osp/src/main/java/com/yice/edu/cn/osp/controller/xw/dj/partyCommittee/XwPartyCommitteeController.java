package com.yice.edu.cn.osp.controller.xw.dj.partyCommittee;

import cn.hutool.core.collection.CollUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.validateClass.GroupOne;
import com.yice.edu.cn.common.pojo.xw.dj.partyCommittee.XwPartyCommittee;
import com.yice.edu.cn.common.pojo.xw.dj.partyMember.XwPartyMember;
import com.yice.edu.cn.common.util.object.ObjectKit;
import com.yice.edu.cn.osp.service.xw.dj.partyCommittee.XwPartyCommitteeService;
import com.yice.edu.cn.osp.service.xw.dj.partyMember.XwPartyMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/xwPartyCommittee")
@Api(value = "/xwPartyCommittee",description = "党支部委员会管理，树状结构模块")
public class XwPartyCommitteeController {
    private static final Object SYNCOBJ = new Object();
    @Autowired
    private XwPartyCommitteeService xwPartyCommitteeService;
    @Autowired
    private XwPartyMemberService xwPartyMemberService;

    @PostMapping("/saveXwPartyCommittee")
    @ApiOperation(value = "保存党支部委员会管理，树状结构对象", notes = "返回保存好的党支部委员会管理，树状结构对象", response=XwPartyCommittee.class)
    public ResponseJson saveXwPartyCommittee(
            @ApiParam(value = "党支部委员会管理，树状结构对象", required = true)
            @Validated(GroupOne.class)
            @RequestBody XwPartyCommittee xwPartyCommittee){
        synchronized (SYNCOBJ){
            ResponseJson responseJson = new ResponseJson();
            xwPartyCommittee.setSchoolId(mySchoolId());
            List<XwPartyCommittee> list = xwPartyCommitteeService.findXwPartyCommitteeListByCondition(xwPartyCommittee);
            if(list != null && list.size() >0){
                responseJson.getResult().setSuccess(false);
                responseJson.getResult().setMsg("名称不能重复！");
            }else{
                XwPartyCommittee s=xwPartyCommitteeService.saveXwPartyCommittee(xwPartyCommittee);
                responseJson.setData(s);
            }
            return responseJson;
        }

    }

    @GetMapping("/update/findXwPartyCommitteeById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找党支部委员会管理，树状结构", notes = "返回响应对象", response=XwPartyCommittee.class)
    public ResponseJson findXwPartyCommitteeById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        XwPartyCommittee xwPartyCommittee=xwPartyCommitteeService.findXwPartyCommitteeById(id);
        return new ResponseJson(xwPartyCommittee);
    }

    @PostMapping("/update/updateXwPartyCommittee")
    @ApiOperation(value = "修改党支部委员会管理，树状结构对象", notes = "返回响应对象")
    public ResponseJson updateXwPartyCommittee(
            @ApiParam(value = "被修改的党支部委员会管理，树状结构对象,对象属性不为空则修改", required = true)
            @Validated(GroupOne.class)
            @RequestBody XwPartyCommittee xwPartyCommittee){
        synchronized (SYNCOBJ){
            ResponseJson responseJson = new ResponseJson();
            xwPartyCommittee.setUpdateTime(null);
            xwPartyCommittee.setCreateTime(null);
            List<XwPartyCommittee> list = xwPartyCommitteeService.findXwPartyCommitteeListByCondition(xwPartyCommittee);
            if(list != null && list.size() >0){
                responseJson.getResult().setSuccess(false);
                responseJson.getResult().setMsg("名称不能重复！");
            }else{
                xwPartyCommitteeService.updateXwPartyCommittee(xwPartyCommittee);
            }
            return responseJson;
        }
    }

    @GetMapping("/look/lookXwPartyCommitteeById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找党支部委员会管理，树状结构", notes = "返回响应对象", response=XwPartyCommittee.class)
    public ResponseJson lookXwPartyCommitteeById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        XwPartyCommittee xwPartyCommittee=xwPartyCommitteeService.findXwPartyCommitteeById(id);
        return new ResponseJson(xwPartyCommittee);
    }

    @PostMapping("/findXwPartyCommitteesByCondition")
    @ApiOperation(value = "根据条件查找党支部委员会管理，树状结构", notes = "返回响应对象", response=XwPartyCommittee.class)
    public ResponseJson findXwPartyCommitteesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwPartyCommittee xwPartyCommittee){
       xwPartyCommittee.setSchoolId(mySchoolId());
        List<XwPartyCommittee> data=xwPartyCommitteeService.findXwPartyCommitteeListByCondition(xwPartyCommittee);
        long count=xwPartyCommitteeService.findXwPartyCommitteeCountByCondition(xwPartyCommittee);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneXwPartyCommitteeByCondition")
    @ApiOperation(value = "根据条件查找单个党支部委员会管理，树状结构,结果必须为单条数据", notes = "没有时返回空", response=XwPartyCommittee.class)
    public ResponseJson findOneXwPartyCommitteeByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody XwPartyCommittee xwPartyCommittee){
        XwPartyCommittee one=xwPartyCommitteeService.findOneXwPartyCommitteeByCondition(xwPartyCommittee);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteXwPartyCommittee/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteXwPartyCommittee(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        return xwPartyCommitteeService.deleteXwPartyCommittee(id);
    }


    @PostMapping("/findXwPartyCommitteeListByCondition")
    @ApiOperation(value = "根据条件查找党支部委员会管理，树状结构列表", notes = "返回响应对象,不包含总条数", response=XwPartyCommittee.class)
    public ResponseJson findXwPartyCommitteeListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwPartyCommittee xwPartyCommittee){
       xwPartyCommittee.setSchoolId(mySchoolId());
        List<XwPartyCommittee> data=xwPartyCommitteeService.findXwPartyCommitteeListByCondition(xwPartyCommittee);
        return new ResponseJson(data);
    }

    @GetMapping("/findAllPartyCommittee")
    public ResponseJson findAllPartyCommittee(){
        XwPartyCommittee xwPartyCommittee = new XwPartyCommittee();
        xwPartyCommittee.setSchoolId(mySchoolId());
        List<XwPartyCommittee> data=xwPartyCommitteeService.findXwPartyCommitteeListByCondition(xwPartyCommittee);
        if(CollUtil.isNotEmpty(data)){
            data= data.stream().filter (x-> Objects.nonNull(x.getParentId())).collect(Collectors.toList());
        }
        return new ResponseJson(ObjectKit.buildTree(data,"-1"));
    }

    @GetMapping("/findPartyCommitteeTree")
    @ApiOperation(value = "根据学校Id查询党组织结构树", notes = "根据学校Id查询党组织结构树")
    public ResponseJson findPartyCommitteeTree(){
        List<XwPartyCommittee> partyCommitteeTree = xwPartyCommitteeService.findPartyCommitteeTree(mySchoolId());
        return new ResponseJson(partyCommitteeTree);
    }
}
