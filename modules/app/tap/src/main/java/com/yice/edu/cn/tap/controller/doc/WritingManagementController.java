package com.yice.edu.cn.tap.controller.doc;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.xw.document.Writing;
import com.yice.edu.cn.common.pojo.xw.document.WritingManagement;
import com.yice.edu.cn.tap.interceptor.LoginInterceptor;
import com.yice.edu.cn.tap.service.doc.WritingManagementService;
import com.yice.edu.cn.tap.service.doc.WritingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/writingManagement")
@Api(value = "/writingManagement",description = "（发文管理）我接收的")
public class WritingManagementController {
    @Autowired
    private WritingManagementService writingManagementService;

    @Autowired
    private WritingService writingService;


    @PostMapping("/findWritingManagementsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findWritingManagementsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody WritingManagement writingManagement){
        List<WritingManagement> data=writingManagementService.findWritingManagementListByCondition(writingManagement);
        long count=writingManagementService.findWritingManagementCountByCondition(writingManagement);
        return new ResponseJson(data,count);
    }


    //查看详情修改查看状态
    @GetMapping("/update/lookAndupdateWritingAndWritingManagement/{id}")
    @ApiOperation(value = "查看详情，修改查看状态",notes = "返回响应对象，修改查看状态")
    public  ResponseJson lookAndupdateWritingAndWritingManagement(
            @ApiParam(value = "要查看的id", required = true)
            @PathVariable String id){
        Writing writings = new Writing();
        writings.setId(id);
        writings.setSendObjectId(LoginInterceptor.myId());
        Writing writing =  writingManagementService.lookAndupdateWritingAndWritingManagement(writings);
        return  new ResponseJson(writing);
    }

    //查询我已接收的2.0
    @PostMapping("/look/findWritingAndWritingManagement")
    @ApiOperation(value = "根据条件查找,返回我接收的发文列表 （条件:当前状态（type字段 0为全部 1为待查看 2已收文  时间段（SearchTimeZone[]字段） 公文类型（writingNumberType字段 1为公文  2为教育督导）））", notes = "返回响应对象")
    public ResponseJson findWritingAndWritingManagement(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Writing writing
    ){
        if(writing.getWritingNumberType()!=null&&writing.getWritingNumberType()==3){
            writing.setWritingNumberType(null);
        }

        if(writing.getType()!=null&&writing.getType()==0){
            writing.setType(null);
        }
        writing.setSendObjectId(LoginInterceptor.myId());
        String[] zero = writing.getSearchTimeZone();
        if(zero!=null&&zero.length>0){
            writing.setStartTime(zero[0]);
            writing.setEndTime(zero[1]);
        }
        List<Writing> list = writingManagementService.findWritingAndWritingManagement(writing);
        long count = writingManagementService.findWritingAndWritingManagementLong(writing);
        return new ResponseJson(list,count);
    }

    //根据发文Id查找到对应的已读未读名单
    @PostMapping("/ignore/findWritingManagementListByCondition")
    @ApiOperation(value = "根据条件查找列表,传递发文Id（writingId）和type查找已读未读名单(新)", notes = "返回响应对象,不包含总条数")
    public ResponseJson findWritingManagementListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody WritingManagement writingManagement){
        writingManagement.setDepartmentType(1);//排除掉组织架构
        List<WritingManagement> data=writingManagementService.findWritingManagementListByCondition(writingManagement);
        return new ResponseJson(data);
    }


    //根据发文Id查找到对应的已读未读名单
    @PostMapping("/ignore/getDocManagementReadOrUnRead")
    @ApiOperation(value = "查找名单,根据条件查找列表,传递type类型和发文Id（writingId）查找已读未读名单")
    public ResponseJson getDocManagementReadOrUnRead(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody WritingManagement writingManagement){
        List<Department> departmentList = writingManagementService.getDocManagementReadOrUnRead(writingManagement);
        return new ResponseJson(departmentList);
    }


    @GetMapping("/ingore/getWritingRedDot")
    @ApiOperation(value = "用来提示发文管理是否有新的红点",notes = "返回我批阅的和我接收的红点状态")
    public ResponseJson getWritingRedDot(){
        //发文管理-我发送的——被驳回并且未被查看的
        Writing writing = new Writing();
        writing.setUserId(LoginInterceptor.myId());//当前用户
        writing.setWritingType(3);//驳回状态
        writing.setReject(1);//未被查看
        long count2 = writingService.findWritingCountByCondition(writing);
        boolean flag = false;
        if(count2!=0){
            flag = true;
        }

        //发文管理-我批阅的-待我批阅的
        writing.setUserId(null);
        writing.setWritingType(null);
        writing.setReject(null);
        writing.setLeaderId(LoginInterceptor.myId());
        writing.setSchoolId(LoginInterceptor.mySchoolId());
        writing.setWritingType(1);
        long count3 = writingService.findWritingCountByCondition(writing);

        Boolean flag1 =false;
        if(count3!=0){
            flag1 = true;
        }

        //发文管理-我接收的-还未查看的
        writing.setLeaderId(null);
        writing.setWritingType(null);
        writing.setSchoolId(null);
        writing.setType(1);
        writing.setSendObjectId(LoginInterceptor.myId());
        long count4 = writingManagementService.findWritingAndWritingManagementLong(writing);

        Boolean flag2 = false;
        if(count4!=0){
            flag2 = true;
        }
        Boolean[] flags = new Boolean[]{flag,flag1,flag2};
        return new ResponseJson(flags);
    }

    @GetMapping("/ingore/getWritingRedDotNew")
    @ApiOperation(value = "用来提示发文管理是否有新的红点(最新版)",notes = "返回我批阅的和我接收的红点状态")
    public ResponseJson getWritingRedDotNew(){
        //发文管理-我发送的——被驳回并且未被查看的
        Writing writing = new Writing();
        writing.setUserId(LoginInterceptor.myId());//当前用户
        writing.setWritingType(3);//驳回状态
        writing.setReject(1);//未被查看
        long count2 = writingService.findWritingCountByCondition(writing);
        boolean flag = false;
        if(count2!=0){
            flag = true;
        }

        //发文管理-我批阅的-待我批阅的
        writing.setUserId(null);
        writing.setWritingType(null);
        writing.setReject(null);
        writing.setLeaderId(LoginInterceptor.myId());
        writing.setSchoolId(LoginInterceptor.mySchoolId());
        writing.setWritingType(1);
        long count3 = writingService.findWritingCountByCondition(writing);

        Boolean flag1 =false;
        if(count3!=0){
            flag1 = true;
        }

        //发文管理-我接收的-还未查看的
        writing.setLeaderId(null);
        writing.setWritingType(null);
        writing.setSchoolId(null);
        writing.setType(1);
        writing.setSendObjectId(LoginInterceptor.myId());
        long count4 = writingManagementService.findWritingAndWritingManagementLong(writing);

        Boolean flag2 = false;
        if(count4!=0){
            flag2 = true;
        }
        Map<String,Boolean> map = new HashMap<>();
        map.put("writing",flag);
        map.put("writingLeader",flag1);
        map.put("writingManagement",flag2);
        return new ResponseJson(map);
    }
}
