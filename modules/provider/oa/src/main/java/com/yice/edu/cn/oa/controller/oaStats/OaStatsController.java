package com.yice.edu.cn.oa.controller.oaStats;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.oa.oaStats.OaStats;
import com.yice.edu.cn.common.pojo.oa.processBusinessData.ProcessBusinessData;
import com.yice.edu.cn.oa.service.oaStats.OaStatsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/oaStats")
@Api(value = "/oaStats", description = "oa统计相关接口")
@Validated
public class OaStatsController {
    @Autowired
    private OaStatsService oaStatsService;
    @PostMapping("/findTotalStats/{schoolId}")
    public ResponseJson findTotalStats(@PathVariable String schoolId, @RequestBody  OaStats oaStats){
        return oaStatsService.findTotalStats(oaStats, schoolId);
    }

    /**
     *
     * @param processBusinessData
     * @param needCount 是否需要统计count
     * @return
     */
    @PostMapping("/findStatsDetail/{needCount}")
    public ResponseJson findStatsDetail(@RequestBody ProcessBusinessData processBusinessData,@PathVariable boolean needCount){
        return oaStatsService.findStatsDetail(processBusinessData,needCount);
    }

    /**
     * 根据教师id和时间范围以及分页参数进行查询
     * @param id
     * @param pager
     * @return
     */
    @PostMapping("/findProcessesByRangeTime/{id}")
    @ApiOperation(value = "根据教师id和时间范围以及分页参数进行查询",notes = "教师id和分页参数必传,时间范围可选",response = ProcessBusinessData.class)
    public ResponseJson findProcessesByRangeTime(@PathVariable String id,@Validated @RequestBody Pager pager){
        List<ProcessBusinessData> processBusinessDatas = oaStatsService.findProcessesByRangeTime(id, pager);
        return new ResponseJson(processBusinessDatas);
    }

    /**
     * 根据学校id和时间范围以及分页参数进行查询
     * @param id
     * @param pager
     * @return
     */
    @PostMapping("/findProcessesBySchoolIdAndRangeTime/{id}")
    @ApiOperation(value = "根据学校id和时间范围以及分页参数进行查询",notes = "学校id必传,分页和时间范围可选,pager.setPaging(false)来控制是否分页",response = ProcessBusinessData.class)
    public List<ProcessBusinessData> findProcessesBySchoolIdAndRangeTime(@NotNull @PathVariable String id, @RequestBody Pager pager){
        return oaStatsService.findProcessesBySchoolIdAndRangeTime(id, pager);
    }
    @PostMapping("/findTotalMoney")
    @ApiOperation(value = "根据学校id及流程id获取审批成功统计的金额",notes = "返回响应对象ResponseJson",response = ResponseJson.class)
    public ResponseJson findTotalMoney(@RequestBody ProcessBusinessData processBusinessData){
        return oaStatsService.findTotalMoney(processBusinessData);
    }
}
