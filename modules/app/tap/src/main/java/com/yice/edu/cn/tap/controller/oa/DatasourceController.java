package com.yice.edu.cn.tap.controller.oa;

import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockDetail.AssetsStockDetail;
import com.yice.edu.cn.tap.service.department.DepartmentService;
import com.yice.edu.cn.tap.service.jw.building.BuildingService;
import com.yice.edu.cn.tap.service.oa.DatasourceService;
import com.yice.edu.cn.tap.service.zc.AssetsStockDetailService.AssetsStockDetailService;
import com.yice.edu.cn.tap.service.zc.assetsFile.AssetsFileService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;


@RestController
@RequestMapping("/datasource")
public class DatasourceController {
    @Autowired
    private DatasourceService datasourceService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private AssetsStockDetailService assetsStockDetailService;
    @Autowired
    private AssetsFileService assetsFileService;
    @Autowired
    private DepartmentService departmentService;
    /**
     * 获取我所在的部门列表
     *
     * @return
     */
    @GetMapping("/getMyDepartments/{initiatorId}")
    public ResponseJson getMyDepartments(@PathVariable("initiatorId") String initiatorId){
        List<Department> departmentNames=datasourceService.getMyDepartmentNames(initiatorId);
        return new ResponseJson(departmentNames);
    }


    /**
     * 获取学校的会议室列表
     * @return
     */
    @GetMapping("/getSchoolMeetingRooms")
    public ResponseJson getSchoolMeetingRooms(){
        Building building = new Building();
        building.setSchoolId(mySchoolId());
        building.setTypeId("109");
        Pager pager = new Pager();
        pager.setPaging(false).setIncludes("id","name");
        building.setPager(pager);
        List<Building> buildings = buildingService.findBuildingListByCondition(building);
        return new ResponseJson(buildings);
    }

    /**
     * 获取学校的数字教室列表，数字教室指多媒体教室或者实验室
     * @return
     */
    @GetMapping("/getSchoolNumberRooms")
    public ResponseJson getSchoolNumberRooms(){
        List<Building> numberRooms=buildingService.findSchoolNumberRooms(mySchoolId());
        return new ResponseJson(numberRooms);
    }

    /**
     * 获取上课的时段
     *
     * @return
     */
    @GetMapping("/getTimeInterval")
    public ResponseJson getTimeInterval() {
        return new ResponseJson(datasourceService.getTimeInterval());
    }

    /**
     * 获取课程节数
     *
     * @return
     */
    @GetMapping("/getclassHour")
    public ResponseJson getclassHour() {
        return new ResponseJson(datasourceService.getclassHour());
    }

    @PostMapping("/findAssetsStockDetailsByCondition")
    @ApiOperation(value = "资产明细查询", notes = "资产名称模糊查询", response= AssetsStockDetail.class)
    public ResponseJson findAssetsStockDetailsByCondition(
            @ApiParam(value = "assetsName模糊查询,assetsNo精确查询,assetsProperty精确查询,可以组合")
            @Validated
            @RequestBody AssetsStockDetail assetsStockDetail){
        assetsStockDetail.setSchoolId(mySchoolId());
        assetsStockDetail.getPager().setIncludes("id","assetsNo","assetsName","assetsProperty","assetsPrice","assetsBuyTime","scrapTime").setLike("assetsName");
        List<AssetsStockDetail> data=assetsStockDetailService.findAssetsStockDetailListByCondition(assetsStockDetail);
        return new ResponseJson(data);
    }
    @PostMapping("/findAssetsFilesByCondition")
    @ApiOperation(value = "资产档案查询", notes = "资产名称模糊查询", response= AssetsFile.class)
    public ResponseJson findAssetsFilesByCondition(
            @ApiParam(value = "inventoryCode,assetsName,assetsProperty三个字段可做查询,assetsName是模糊查询")
            @Validated
            @RequestBody AssetsFile assetsFile){
        assetsFile.setSchoolId(mySchoolId());
        assetsFile.getPager().setIncludes("id","inventoryCode","assetsName","assetsProperty","assetsPrice").setLike("assetsName");
        List<AssetsFile> data=assetsFileService.findAssetsFileListByCondition(assetsFile);
        return new ResponseJson(data);
    }
    /**
     * 根据部门id获取部门信息
     * @param departmentId
     * @return
     */
    @GetMapping("/getDepartmentById/{departmentId}")
    public ResponseJson getDepartmentById(@PathVariable("departmentId") String departmentId){
        Department dep = departmentService.findDepartmentById(departmentId);
        return new ResponseJson(Arrays.asList(dep));
    }

}
