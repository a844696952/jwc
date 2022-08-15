package com.yice.edu.cn.osp.controller.xw.workerKq;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.workerKq.KqWorkerMonth;
import com.yice.edu.cn.osp.service.xw.workerKq.KqWorkerMonthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/kqWorkerMonth")
@Api(value = "/kqWorkerMonth",description = "教职工考勤月统计模块")
public class KqWorkerMonthController {
    @Autowired
    private KqWorkerMonthService kqWorkerMonthService;

  /*  @PostMapping("/save/saveKqWorkerMonth")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response= KqWorkerMonth.class)
    public ResponseJson saveKqWorkerMonth(
            @ApiParam(value = "对象", required = true)
            @RequestBody KqWorkerMonth kqWorkerMonth){
       KqWorkerMonth s=kqWorkerMonthService.saveKqWorkerMonth(kqWorkerMonth);
        return new ResponseJson();
    }

    @GetMapping("/update/findKqWorkerMonthById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=KqWorkerMonth.class)
    public ResponseJson findKqWorkerMonthById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
       // KqWorkerMonth kqWorkerMonth=kqWorkerMonthService.findKqWorkerMonthById(id);
        return new ResponseJson();
    }

    @PostMapping("/update/updateKqWorkerMonth")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateKqWorkerMonth(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody KqWorkerMonth kqWorkerMonth){
        //kqWorkerMonthService.updateKqWorkerMonth(kqWorkerMonth);
        return new ResponseJson();
    }

    @GetMapping("/look/lookKqWorkerMonthById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response=KqWorkerMonth.class)
    public ResponseJson lookKqWorkerMonthById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        //KqWorkerMonth kqWorkerMonth=kqWorkerMonthService.findKqWorkerMonthById(id);
        return new ResponseJson();
    }

    @PostMapping("/findKqWorkerMonthsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=KqWorkerMonth.class)
    public ResponseJson findKqWorkerMonthsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqWorkerMonth kqWorkerMonth){
       // List<KqWorkerMonth> data=kqWorkerMonthService.findKqWorkerMonthListByCondition(kqWorkerMonth);
        //long count=kqWorkerMonthService.findKqWorkerMonthCountByCondition(kqWorkerMonth);
        return new ResponseJson();
    }
    @PostMapping("/findOneKqWorkerMonthByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=KqWorkerMonth.class)
    public ResponseJson findOneKqWorkerMonthByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody KqWorkerMonth kqWorkerMonth){
       // KqWorkerMonth one=kqWorkerMonthService.findOneKqWorkerMonthByCondition(kqWorkerMonth);
        return new ResponseJson();
    }
    @GetMapping("/deleteKqWorkerMonth/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteKqWorkerMonth(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
      //  kqWorkerMonthService.deleteKqWorkerMonth(id);
        return new ResponseJson();
    }


    @PostMapping("/findKqWorkerMonthListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=KqWorkerMonth.class)
    public ResponseJson findKqWorkerMonthListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqWorkerMonth kqWorkerMonth){
       // List<KqWorkerMonth> data=kqWorkerMonthService.findKqWorkerMonthListByCondition(kqWorkerMonth);
        return new ResponseJson();
    }*/

    @PostMapping("/find/findWorkerMonthRecordList")
    @ApiOperation(value = "考勤月统计查询", notes = "返回响应对象,不包含总条数", response=KqWorkerMonth.class)
    public ResponseJson findWorkerMonthRecordList(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody KqWorkerMonth kqWorkerMonth){
        kqWorkerMonth.setSchoolId(mySchoolId());
        List<KqWorkerMonth> data=kqWorkerMonthService.findWorkerMonthRecordList(kqWorkerMonth);
        long count =0;
        if (data!=null&&data.size()>0){
            count = data.size();
            //分页
            Pager pager = kqWorkerMonth.getPager()==null?null:kqWorkerMonth.getPager();
            if (pager!=null){
                data=  data.stream().skip(pager.getBeginRow()).limit(pager.getPageSize()).collect(Collectors.toList());
            }
        }else {
            data =new ArrayList<>();
        }

        return new ResponseJson(data,count);
    }

    @PostMapping("/update/exportKqDaily")
    public void exportKqDaily(@ApiParam(value = "日考勤信息对象") @RequestBody  KqWorkerMonth kqWorkerMonth, HttpServletResponse response) {

        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=student.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            kqWorkerMonth.getPager().setLike("userName");
            kqWorkerMonth.getPager().setSortOrder("asc");
            kqWorkerMonth.getPager().setSortField("kqDate");
            kqWorkerMonth.getPager().setPaging(false);
            Workbook workbook = kqWorkerMonthService.exportWorkerKq(kqWorkerMonth);
            workbook.write(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
