package com.yice.edu.cn.osp.controller.jy.titleQuota;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.titleQuota.HistoryTeacherAes;
import com.yice.edu.cn.common.pojo.jy.titleQuota.HistoryTeacherAesExtend;
import com.yice.edu.cn.osp.service.jy.titleQuota.HistoryTeacherAesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/historyTeacherAes")
@Api(value = "/historyTeacherAes",description = "题目额度教师使用记录表模块")
public class HistoryTeacherAesController {
    @Autowired
    private HistoryTeacherAesService historyTeacherAesService;


    @PostMapping("/saveHistoryTeacherAes")
    @ApiOperation(value = "保存题目额度教师使用记录表对象", notes = "返回保存好的题目额度教师使用记录表对象", response=HistoryTeacherAes.class)
    public ResponseJson saveHistoryTeacherAes(
            @ApiParam(value = "题目额度教师使用记录表对象", required = true)
            @RequestBody HistoryTeacherAes historyTeacherAes){
        historyTeacherAes.setSchoolId(mySchoolId());
        HistoryTeacherAes s=historyTeacherAesService.saveHistoryTeacherAes(historyTeacherAes);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findHistoryTeacherAesById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找题目额度教师使用记录表", notes = "返回响应对象", response=HistoryTeacherAes.class)
    public ResponseJson findHistoryTeacherAesById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        HistoryTeacherAes historyTeacherAes=historyTeacherAesService.findHistoryTeacherAesById(id);
        return new ResponseJson(historyTeacherAes);
    }

    @PostMapping("/update/updateHistoryTeacherAes")
    @ApiOperation(value = "修改题目额度教师使用记录表对象非空字段", notes = "返回响应对象")
    public ResponseJson updateHistoryTeacherAes(
            @ApiParam(value = "被修改的题目额度教师使用记录表对象,对象属性不为空则修改", required = true)
            @RequestBody HistoryTeacherAes historyTeacherAes){
        historyTeacherAesService.updateHistoryTeacherAes(historyTeacherAes);
        return new ResponseJson();
    }

    @PostMapping("/update/updateHistoryTeacherAesForAll")
    @ApiOperation(value = "修改题目额度教师使用记录表对象所有字段", notes = "返回响应对象")
    public ResponseJson updateHistoryTeacherAesForAll(
            @ApiParam(value = "被修改的题目额度教师使用记录表对象", required = true)
            @RequestBody HistoryTeacherAes historyTeacherAes){
        historyTeacherAesService.updateHistoryTeacherAesForAll(historyTeacherAes);
        return new ResponseJson();
    }

    @GetMapping("/look/lookHistoryTeacherAesById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找题目额度教师使用记录表", notes = "返回响应对象", response=HistoryTeacherAes.class)
    public ResponseJson lookHistoryTeacherAesById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        HistoryTeacherAes historyTeacherAes=historyTeacherAesService.findHistoryTeacherAesById(id);
        return new ResponseJson(historyTeacherAes);
    }

    @PostMapping("/findHistoryTeacherAessByCondition")
    @ApiOperation(value = "根据条件查找题目额度教师使用记录表", notes = "返回响应对象", response=HistoryTeacherAes.class)
    public ResponseJson findHistoryTeacherAessByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody HistoryTeacherAesExtend historyTeacherAesExtend){
        historyTeacherAesExtend.setSchoolId(mySchoolId());
        HistoryTeacherAes historyTeacherAes = new HistoryTeacherAes();
        BeanUtils.copyProperties(historyTeacherAesExtend, historyTeacherAes);
        if(historyTeacherAesExtend.getTimeArr()!=null && historyTeacherAesExtend.getTimeArr().length==2){
            historyTeacherAes.getPager().setRangeField("createTime");
            Date date1 = DateUtil.parse(historyTeacherAesExtend.getTimeArr()[0]);
            Date date2 = DateUtil.parse(historyTeacherAesExtend.getTimeArr()[1]);
            String beginOfDay = DateUtil.beginOfDay(date1).toString();
            String endOfDay = DateUtil.endOfDay(date2).toString();
            historyTeacherAes.getPager().setRangeArray(new String[]{beginOfDay,endOfDay});
        }
        List<Map<String, Object>> data = historyTeacherAesService.findHistoryTeacherAesListByCondition4Like(historyTeacherAes);
        List<HistoryTeacherAes> nums = historyTeacherAesService.findHistoryTeacherAesCountByCondition4Like(historyTeacherAes);
        int count = 0;
        long total = 0;
        if(nums!=null){
            for(int i=0;i<nums.size();i++){
                count+=nums.get(i).getNumTeacherVisits();
            }
            total = nums.size();
        }
        return new ResponseJson(data,count,total);
    }


    @PostMapping("/findOneHistoryTeacherAesByCondition")
    @ApiOperation(value = "根据条件查找单个题目额度教师使用记录表,结果必须为单条数据", notes = "没有时返回空", response=HistoryTeacherAes.class)
    public ResponseJson findOneHistoryTeacherAesByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody HistoryTeacherAes historyTeacherAes){
        HistoryTeacherAes one=historyTeacherAesService.findOneHistoryTeacherAesByCondition(historyTeacherAes);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteHistoryTeacherAes/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteHistoryTeacherAes(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        historyTeacherAesService.deleteHistoryTeacherAes(id);
        return new ResponseJson();
    }


    @PostMapping("/findHistoryTeacherAesListByCondition")
    @ApiOperation(value = "根据条件查找题目额度教师使用记录表列表", notes = "返回响应对象,不包含总条数", response=HistoryTeacherAes.class)
    public ResponseJson findHistoryTeacherAesListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody HistoryTeacherAes historyTeacherAes){
       historyTeacherAes.setSchoolId(mySchoolId());
        List<HistoryTeacherAes> data=historyTeacherAesService.findHistoryTeacherAesListByCondition(historyTeacherAes);
        return new ResponseJson(data);
    }

    @PostMapping("/findIsExist")
    @ApiOperation(value = "查询题目额度教师使用记录表是否存在记录topicId,schoolId", notes = "返回题目额度教师使用记录表对象")
    public HistoryTeacherAes findIsExist(
            @ApiParam(value = "题目额度教师使用记录表对象", required = true)
            @RequestBody HistoryTeacherAes historyTeacherAes){
        historyTeacherAes =historyTeacherAesService.findIsExist(historyTeacherAes);
        return historyTeacherAes;
    }


    @PostMapping("/findIsDownload")
    @ApiOperation(value = "能否继续下载题", notes = "返回题目额度教师使用记录表对象")
    public HistoryTeacherAes findIsDownload(
            @ApiParam(value = "题目额度教师使用记录表对象topicId,schoolId,teacherId", required = true)
            @RequestBody HistoryTeacherAes historyTeacherAes){
        historyTeacherAes =historyTeacherAesService.findIsDownload(historyTeacherAes);
        return historyTeacherAes;
    }


    @PostMapping("/findByTeacherIdVist")
    @ApiOperation(value = "老师每次访问+1处理", notes = "返回题目额度教师使用记录表对象")
    public HistoryTeacherAes findByTeacherIdVist(
            @ApiParam(value = "题目额度教师使用记录表对象topicId,schoolId,teacherId", required = true)
            @RequestBody HistoryTeacherAes historyTeacherAes){
        historyTeacherAes =historyTeacherAesService.findByTeacherIdVist(historyTeacherAes);
        return historyTeacherAes;
    }


    @PostMapping("/findHistoryTeacherAesCountByCondition4Like")
    public List<HistoryTeacherAes> findHistoryTeacherAesCountByCondition4Like(@RequestBody HistoryTeacherAes historyTeacherAes) {
        return  historyTeacherAesService.findHistoryTeacherAesCountByCondition4Like(historyTeacherAes);
    }


    @PostMapping("/findHistoryTeacherAesListByCondition4Like")
    public List<Map<String, Object>> findHistoryTeacherAesListByCondition4Like(@RequestBody HistoryTeacherAes historyTeacherAes) {
        return historyTeacherAesService.findHistoryTeacherAesListByCondition4Like(historyTeacherAes);
    }
}
