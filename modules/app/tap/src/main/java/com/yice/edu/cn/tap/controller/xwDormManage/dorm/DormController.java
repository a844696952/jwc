package com.yice.edu.cn.tap.controller.xwDormManage.dorm;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.Dorm;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildAdmin;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildVo;
import com.yice.edu.cn.tap.service.xwDormManage.dorm.DormBuildAdminService;
import com.yice.edu.cn.tap.service.xwDormManage.dorm.DormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dorm")
@Api(value = "/dorm",description = "住宿管理模块")
public class DormController {
    @Autowired
    private DormService dormService;
    @Autowired
    private DormBuildAdminService dormBuildAdminService;


    /*----------------------------------------------------------------------------------------------------------*/

    @GetMapping("/findDormBuildingList")
    @ApiOperation(value = "根据登录人查询宿舍楼列表", notes = "返回宿舍楼列表", response=Dorm.class)
    public ResponseJson findDormBuildingList(){
        List<Building> dormBuilding = dormBuildAdminService.findDormBuildingByLogin();
        return new ResponseJson(dormBuilding);
    }

    @PostMapping("/findDormBuildingTreeByCondition")
    @ApiOperation(value = "根据条件查找宿舍楼列表树", notes = "返回宿舍楼列表树", response=Dorm.class)
    public ResponseJson findDormBuildingTreeByCondition(
            @ApiParam(value = "dormBuildId和dormType必填,其他属性不为空则作为条件查询")
            @RequestBody DormBuildVo dormBuildVo){
            dormBuildVo.setSchoolId(mySchoolId());
            List<Dorm> list = dormService.findDormBuildingTreeByCondition(dormBuildVo);
            return new ResponseJson(list);
    }


}
