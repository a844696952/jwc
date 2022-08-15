package com.yice.edu.cn.tap.controller.repairNew;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairFile;
import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairNew;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.tap.service.jw.building.BuildingService;
import com.yice.edu.cn.tap.service.repairNew.RepairFileService;
import com.yice.edu.cn.tap.service.repairNew.RepairNewService;
import com.yice.edu.cn.tap.service.teacher.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.stream.Collectors;


import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/repairNew")
@Api(value = "/repairNew",description = "新报修表模块")
public class RepairNewController {
    @Autowired
    private RepairNewService repairNewService;
    @Autowired
    private RepairFileService repairFileService;

    @Autowired
    private BuildingService buildingService;
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/saveRepairNew")
    @ApiOperation(value = "保存新报修表对象", notes = "返回保存好的新报修表对象", response=RepairNew.class)
    public ResponseJson saveRepairNew(
            @ApiParam(value = "新报修表对象", required = true)
            @RequestBody RepairNew repairNew){
        repairNew.setSchoolId(mySchoolId());
        //添加判断 防止 多处一起扫码  然后可以重复提交
        long i = repairNewService.findRepairNewCountByCondition(repairNew);
        if (i == 0 ){
            Teacher teacherById = teacherService.findTeacherById(myId());
            repairNew.setSubmitterId(myId());
            repairNew.setSubmitterName(teacherById.getName());
            repairNew.setSubmitterTel(teacherById.getTel());
            RepairNew s=repairNewService.saveRepairNew(repairNew);
            return new ResponseJson(s);
        }
        return new ResponseJson(false,"该资产已报修");
    }

    @GetMapping("/look/lookRepairNewById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找新报修表", notes = "返回响应对象", response=RepairNew.class)
    public ResponseJson lookRepairNewById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        RepairNew repairNew=repairNewService.findRepairNewById(id);
        return new ResponseJson(repairNew);
    }


    @PostMapping("/findRepairNewsByCondition")
    @ApiOperation(value = "查询我的报修表", notes = "返回响应对象", response=RepairNew.class)
    public ResponseJson findRepairNewsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody RepairNew repairNew){
        repairNew.setSchoolId(mySchoolId());
        repairNew.setSubmitterId(myId());
        repairNew.getPager().setSortField("repairPriority");
        repairNew.getPager().setSortOrder(Pager.DESC);
        List<RepairNew> data=repairNewService.findRepairNewListByCondition(repairNew);
        long count=repairNewService.findRepairNewCountByCondition(repairNew);
        return new ResponseJson(data,count);
    }

    @PostMapping("/uploadImg")
    @ApiOperation(value = "保修图片上传", notes = "返回图片地址")
    public String uploadImg(MultipartFile file){
        return QiniuUtil.uploadImage(file, Constant.Upload.ZC_BAOXIU);
    }


    @GetMapping("/update/selectRepairFilesById/{id}")
    @ApiOperation(value = "根据id查找新报修附件表", notes = "返回响应对象,不包含总条数", response=RepairNew.class)
    public ResponseJson selectRepairFilesById(
            @PathVariable String id){
        RepairFile repairFile = new RepairFile();
        repairFile.setSchoolId(mySchoolId());
        repairFile.setRepairId(id);
        List<RepairFile> data=repairFileService.findRepairFileListByCondition(repairFile);
        return new ResponseJson(data);
    }

    //查询本校所有楼栋
    @PostMapping("/update/selectBuildingNameAllList")
    @ApiOperation(value = "根据条件查找查询本校所有楼栋", notes = "返回响应对象")
    public ResponseJson selectBuildingNameAllList() {
        Building building = new Building();
        building.setSchoolId(mySchoolId());
        Pager pager = new Pager();
        pager.setSortField("createTime");
        pager.setPaging(false);
        pager.setSortOrder(Pager.DESC);
        building.setPager(pager);
        List<Building> data = buildingService.findBuildingListByCondition(building).stream().filter(ele -> ele.getParentId().equals("-1")).collect(Collectors.toList());
        return new ResponseJson(data);
    }


    //查询指定楼栋所有场地
    @GetMapping("/update/selectBuildingRoomNameAllList/{buildingId}")
    @ApiOperation(value = "根据条件查找查询本校所有场地 传schoolId buildingId", notes = "返回响应对象")
    public ResponseJson selectBuildingRoomNameAllList(
            @ApiParam(value = "楼栋id", required = true)
            @PathVariable String buildingId) {
        Building building = new Building();
        building.setSchoolId(mySchoolId());
        building.setParentId(buildingId);
        Pager pager = new Pager();
        pager.setSortField("createTime");
        pager.setPaging(false);
        pager.setSortOrder(Pager.DESC);
        building.setPager(pager);
        List<String> floorIdStr = buildingService.findBuildingListByCondition(building).stream().map(Building::getId).collect(Collectors.toList());
        if (floorIdStr.size() == 0 ){
            return new ResponseJson(false,"该楼栋未创建楼层");
        }
        building.setFloorIdStr(floorIdStr);
        List<Building> data = buildingService.findBuildingRoomNameAll(building);
        if (data.size() == 0 ){
            return new ResponseJson(false,"该楼层未创建场地");
        }
        return new ResponseJson(data);
    }


}
