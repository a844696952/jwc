package com.yice.edu.cn.osp.controller.oa.datasource;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockDetail.AssetsStockDetail;
import com.yice.edu.cn.osp.service.jw.building.BuildingService;
import com.yice.edu.cn.osp.service.jw.department.DepartmentService;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherService;
import com.yice.edu.cn.osp.service.oa.datasource.DatasourceService;
import com.yice.edu.cn.osp.service.zc.assetsFile.AssetsFileService;
import com.yice.edu.cn.osp.service.zc.assetsStockDetail.AssetsStockDetailService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/datasource")
public class DatasourceController {
    @Autowired
    private DatasourceService datasourceService;
    @Autowired
    private AssetsStockDetailService assetsStockDetailService;
    @Autowired
    private AssetsFileService assetsFileService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private DepartmentService departmentService;
    /**
     * 获取我所在的部门列表
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
              *  获取上课的时段
     * @return
     */
    @GetMapping("/getTimeInterval")
    public ResponseJson getTimeInterval(){
        return new ResponseJson(datasourceService.getTimeInterval());
    }
    
    /**
              *  获取课程节数
     * @return
     */
    @GetMapping("/getclassHour")
    public ResponseJson getclassHour(){
        return new ResponseJson(datasourceService.getclassHour());
    }

    @PostMapping("/findAssetsStockDetailsByCondition")
    @ApiOperation(value = "资产明细查询", notes = "资产名称模糊查询", response= AssetsStockDetail.class)
    public ResponseJson findAssetsStockDetailsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody AssetsStockDetail assetsStockDetail){
        assetsStockDetail.setSchoolId(mySchoolId());
        assetsStockDetail.getPager().setIncludes("id","assetsNo","assetsName","assetsProperty","assetsPrice","assetsBuyTime","scrapTime","useTimeLimitDate").setLike("assetsName,assetsNo");
        List<AssetsStockDetail> data=assetsStockDetailService.findAssetsStockDetailListByCondition(assetsStockDetail);
        long count=assetsStockDetailService.findAssetsStockDetailCountByCondition(assetsStockDetail);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findAssetsFilesByCondition")
    @ApiOperation(value = "资产档案查询", notes = "资产名称模糊查询", response= AssetsFile.class)
    public ResponseJson findAssetsFilesByCondition(
            @ApiParam(value = "inventoryCode,assetsName,assetsProperty三个字段可做查询,assetsName是模糊查询")
            @Validated
            @RequestBody AssetsFile assetsFile){
        assetsFile.setSchoolId(mySchoolId());
        assetsFile.getPager().setIncludes("id","inventoryCode","assetsName","assetsProperty","assetsPrice").setLike("assetsName,inventoryCode");
        List<AssetsFile> data=assetsFileService.findAssetsFileListByCondition(assetsFile);
        long count=assetsFileService.findAssetsFileCountByCondition(assetsFile);
        return new ResponseJson(data,count);
    }
    /**
     * 获取老师当前头像
     * @param id
     * @param response
     * @throws IOException
     */
    @GetMapping("/teacherHeadImg/{id}")
    public void teacherHeadImg(@PathVariable String id, HttpServletResponse response) throws IOException {
        Teacher t = teacherService.findTeacherById(id);
        if(Objects.nonNull(t)){
            String url= Constant.RES_PRE + (StrUtil.isNotEmpty(t.getImgUrl())? t.getImgUrl(): Constant.AVATAR.MAN );
            try(InputStream is = URLUtil.getStream(new URL(url));
                ServletOutputStream os = response.getOutputStream()){
                IoUtil.copy(is,os);
            }
        }
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
