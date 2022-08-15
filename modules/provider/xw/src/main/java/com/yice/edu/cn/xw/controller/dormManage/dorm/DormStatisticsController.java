package com.yice.edu.cn.xw.controller.dormManage.dorm;

import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildVo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildingPersonInfo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormStatistics;
import com.yice.edu.cn.xw.service.dormManage.dorm.DormStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dormStatistics")
@Api(value = "/dormStatistics",description = "模块")
public class DormStatisticsController {

    @Autowired
    private DormStatisticsService dormStatisticsService;

    @PostMapping("/findDormInfoStatistics")
    @ApiOperation(value = "根据条件查找统计楼层信息", notes = "返回楼层信息列表,没有时为空")
    public List<DormStatistics> findDormInfoStatistics(
            @ApiParam(value = "对象")
            @RequestBody DormBuildVo dormBuildVo){
        return dormStatisticsService.findDormInfoStatistics(dormBuildVo);
    }
    @PostMapping("/findDormInfoCountStatistics")
    @ApiOperation(value = "根据条件查找统计楼层信息条数", notes = "返回条数")
    public long findDormInfoCountStatistics(
            @ApiParam(value = "对象")
            @RequestBody DormBuildVo dormBuildVo){
        return dormStatisticsService.findDormInfoCountStatistics(dormBuildVo);

    }

    @PostMapping("/findDormBuildStatistics")
    @ApiOperation(value = "根据宿舍id查询宿舍楼入住人数信息", notes = "返回对象")
    public DormStatistics findDormBuildStatistics(
            @ApiParam(value = "对象")
            @RequestBody DormBuildVo dormBuildVo){
        return dormStatisticsService.findDormBuildStatistics(dormBuildVo);
    }


    @PostMapping("/findDormBuildFloorsByCondition")
    @ApiOperation(value = "根据条件查询宿舍楼的楼层数", notes = "返回对象")
    public List<DormBuildingPersonInfo> findDormBuildFloorsByCondition(
            @ApiParam(value = "对象")
            @RequestBody Building building){
        return dormStatisticsService.findDormBuildFloorsByCondition(building);
    }
}
