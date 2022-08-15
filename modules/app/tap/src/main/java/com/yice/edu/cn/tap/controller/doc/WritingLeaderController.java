package com.yice.edu.cn.tap.controller.doc;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.document.Writing;
import com.yice.edu.cn.common.pojo.xw.document.WritingLeader;
import com.yice.edu.cn.tap.interceptor.LoginInterceptor;
import com.yice.edu.cn.tap.service.doc.WritingLeaderService;
import com.yice.edu.cn.tap.service.doc.WritingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/writingLeader")
@Api(value = "/writingLeader",description = "（发文管理）我批阅的")
public class WritingLeaderController {
    @Autowired
    private WritingLeaderService writingLeaderService;

    @Autowired
    private WritingService writingService;




    @GetMapping("/look/lookWritingLeaderById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookWritingLeaderById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        Writing writing=writingService.findWritingById(id);
        return new ResponseJson(writing);
    }



    //我批阅的
    @PostMapping("/look/findWritingLeadersByConditionGai")
    @ApiOperation(value = "根据条件查找,返回我批阅的发文列表 （条件:当前状态（writingType字段 0为全部 1为待我审批 2为已完成 3为已驳回  时间段（SearchTimeZone[]字段） 公文类型（writingNumberType字段 1为公文  2为教育督导）））", notes = "返回响应对象")
    public ResponseJson findWritingLeadersByConditionGai(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Writing writing){
        if(writing.getWritingNumberType()!=null&&writing.getWritingNumberType()==3){
            writing.setWritingNumberType(null);
        }
        if(writing.getWritingType()!=null&&writing.getWritingType()==0){
            writing.setWritingType(null);
        }
        writing.setSchoolId(LoginInterceptor.mySchoolId());
        writing.setLeaderId(LoginInterceptor.myId());
        String[]  zero = writing.getSearchTimeZone();
        if(zero!=null&&zero.length>0){
            writing.setStartTime(zero[0]);
            writing.setEndTime(zero[1]);
        }
        //清除前台的排序
        if("id".equals(writing.getPager().getSortField())||writing.getPager().getSortField()==null){
            writing.getPager().setSortField("");
            writing.setOrder("true");
        }

        List<Writing> data = writingService.findWritingListByCondition(writing);
        long count = writingService.findWritingCountByCondition(writing);
        return new ResponseJson(data,count);
    }


    //驳回
    @PostMapping("/update/updateWriting")
    @ApiOperation(value = "驳回发文,将writingType改为3后，传递Writing对象", notes = "返回响应对象")
    public ResponseJson updateWriting(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Writing writing){
        writingLeaderService.updateWriting(writing);
        return new ResponseJson();
    }

    //审核通过
    @PostMapping("/update/updateWritingAndLeader")
    @ApiOperation(value = "审核通过,将writingType变为2后，传递Writing对象",notes = "返回响应对象")
    public ResponseJson updateWritingAndLeader(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Writing writing
    ){
        writingLeaderService.updateWritingAndLeader(writing);
        return  new ResponseJson();
    }

}
