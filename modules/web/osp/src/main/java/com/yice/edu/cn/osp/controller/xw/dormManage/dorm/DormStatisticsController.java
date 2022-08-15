package com.yice.edu.cn.osp.controller.xw.dormManage.dorm;


import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.*;
import com.yice.edu.cn.osp.service.xw.dormManage.dorm.DormBuildAdminService;
import com.yice.edu.cn.osp.service.xw.dormManage.dorm.DormStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dormStatistics")
@Api(value = "/dormStatistics",description = "住宿统计模块")
public class DormStatisticsController {

    @Autowired
    private DormStatisticsService statisticsService;


    @GetMapping("/findDormBuildingList")
    @ApiOperation(value = "根据登录人查询宿舍楼列表", notes = "返回宿舍楼列表", response=Dorm.class)
    public ResponseJson findDormBuildingList(){
        List<DormBuildingPersonInfo> DormBuildingAndFloor = statisticsService.findDormBuildingAndFloor();
        return new ResponseJson(DormBuildingAndFloor);
    }


    @PostMapping("/findDormInfoStatistics")
    @ApiOperation(value = "统计宿舍入住信息", notes = "返回宿舍入住信息", response=Dorm.class)
    public ResponseJson findDormBuildingInfoStatistics(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DormBuildVo dormBuildVo){
        dormBuildVo.setSchoolId(mySchoolId());
        List<DormStatistics> list = statisticsService.findDormInfoStatistics(dormBuildVo);
        long count = statisticsService.findDormInfoCountStatistics(dormBuildVo);
        DormStatistics dormBuildStatistics = statisticsService.findDormBuildStatistics(dormBuildVo);
        return new ResponseJson(dormBuildStatistics,list,count);
    }
}
