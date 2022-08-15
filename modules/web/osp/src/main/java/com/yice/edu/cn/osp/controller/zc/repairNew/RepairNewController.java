package com.yice.edu.cn.osp.controller.zc.repairNew;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.jw.department.Department;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildAdmin;
import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairStaff;
import com.yice.edu.cn.common.pojo.xw.zc.assetsContract.AssetsContract;
import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairFile;
import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairNew;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.osp.service.jw.building.BuildingService;
import com.yice.edu.cn.osp.service.jw.department.DepartmentService;
import com.yice.edu.cn.osp.service.xw.dormManage.dorm.DormBuildAdminService;
import com.yice.edu.cn.osp.service.zc.repairNew.RepairStaffService;
import com.yice.edu.cn.osp.service.zc.repairNew.RepairFileService;
import com.yice.edu.cn.osp.service.zc.repairNew.RepairNewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/repairNew")
@Api(value = "/repairNew",description = "新报修表模块")
public class RepairNewController {
    @Autowired
    private RepairNewService repairNewService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private RepairStaffService repairStaffService;
    @Autowired
    private RepairFileService repairFileService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private DormBuildAdminService dormBuildAdminService;


    @PostMapping("/saveRepairNew")
    @ApiOperation(value = "保存新报修表对象", notes = "返回保存好的新报修表对象", response=RepairNew.class)
    public ResponseJson saveRepairNew(
            @ApiParam(value = "新报修表对象", required = true)
            @RequestBody RepairNew repairNew){
        repairNew.setSchoolId(mySchoolId());
        RepairNew s=repairNewService.saveRepairNew(repairNew);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findRepairNewById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找新报修表", notes = "返回响应对象", response=RepairNew.class)
    public ResponseJson findRepairNewById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        RepairNew repairNew=repairNewService.findRepairNewById(id);
        return new ResponseJson(repairNew);
    }

    //处理
    @PostMapping("/update/updateRepairNew")
    @ApiOperation(value = "修改新报修表对象", notes = "返回响应对象")
    public ResponseJson updateRepairNew(
            @ApiParam(value = "被修改的新报修表对象,对象属性不为空则修改", required = true)
            @RequestBody RepairNew repairNew){
        repairNew.setSchoolId(mySchoolId());
        repairNew.setDisposeStaffId(currentTeacher().getId());
        repairNew.setDisposeStaffName(currentTeacher().getName());
        repairNewService.updateRepairNew(repairNew);

        return new ResponseJson();
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
    @ApiOperation(value = "根据条件查找新报修表", notes = "返回响应对象", response=RepairNew.class)
    public ResponseJson findRepairNewsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody RepairNew repairNew){
        repairNew.setSchoolId(mySchoolId());
        repairNew.getPager().setLike("repairNo");
        repairNew.getPager().setLike("assetName");
        repairNew.getPager().setLike("submitterName");
        repairNew.getPager().setLike("submitterDepartementId");
        repairNew.getPager().setLike("repairType");
        repairNew.getPager().setLike("repairPriority");
        repairNew.getPager().setLike("status");
        //排序已写在XML中
        List<RepairNew> data=repairNewService.findRepairNewListByCondition(repairNew);
        long count=repairNewService.findRepairNewCountByCondition(repairNew);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneRepairNewByCondition")
    @ApiOperation(value = "根据条件查找单个新报修表,结果必须为单条数据", notes = "没有时返回空", response=RepairNew.class)
    public ResponseJson findOneRepairNewByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody RepairNew repairNew){
        RepairNew one=repairNewService.findOneRepairNewByCondition(repairNew);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteRepairNew/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteRepairNew(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        repairNewService.deleteRepairNew(id);
        return new ResponseJson();
    }


    @PostMapping("/findRepairNewListByCondition")
    @ApiOperation(value = "根据条件查找新报修表列表", notes = "返回响应对象,不包含总条数", response=RepairNew.class)
    public ResponseJson findRepairNewListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody RepairNew repairNew){
       repairNew.setSchoolId(mySchoolId());
        List<RepairNew> data=repairNewService.findRepairNewListByCondition(repairNew);
        return new ResponseJson(data);
    }


    //查询所有部门
    @GetMapping("/update/selectDept/{forDepartment}")
    public ResponseJson selectDept(@PathVariable boolean forDepartment) {
        List<Department> data=departmentService.findDepartmentHaveOrNotTeacherForTree(mySchoolId(),forDepartment, 0);
        return new ResponseJson(data);
    }

    //查询所有维修人员
    @GetMapping("/update/findRepairStaffsByCondition")
    @ApiOperation(value = "根据条件查找维修人员信息表", notes = "返回响应对象")
    public ResponseJson findRepairStaffsByCondition() {
        RepairStaff repairStaff = new RepairStaff();
        repairStaff.setSchoolId(mySchoolId());
        List<RepairStaff> data = repairStaffService.findRepairStaffListByCondition(repairStaff);
        return new ResponseJson(data);
    }

    //查询宿舍维修人员
    @GetMapping("/update/findRepairStaffsByConditionDorm")
    @ApiOperation(value = "根据条件查找维修人员信息表", notes = "返回响应对象")
    public ResponseJson findRepairStaffsByConditionDorm() {
        RepairStaff repairStaff = new RepairStaff();
        repairStaff.setSchoolId(mySchoolId());
        repairStaff.setStaffType("2");
        List<RepairStaff> data = repairStaffService.findRepairStaffListByCondition(repairStaff);
        return new ResponseJson(data);
    }

    //指派维修人
    @PostMapping("/update/updateRepairNewPerson")
    @ApiOperation(value = "修改报修人信息表对象", notes = "返回响应对象")
    public ResponseJson updateRepairNewPerson(
            @ApiParam(value = "被修改的报修信息表对象,对象属性不为空则修改", required = true)
            @RequestBody RepairNew repairNew) {
        repairNew.setSchoolId(mySchoolId());
        repairNew.setRecordStaffId(currentTeacher().getId());
        repairNew.setRecordStaffName(currentTeacher().getName());
        repairNewService.updateRepairNewPerson(repairNew);
        return new ResponseJson();
    }


    @PostMapping("/ignore/uploadFile")
    @ApiOperation(value = "说明：上传文件到七牛", notes = "返回资源名称和资源的url", response = AssetsContract.class)
    public ResponseJson uploadQiniuFile(@ApiParam(value = "上传到七牛的文件", required = true) MultipartFile file) {
        //文件名后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        Map<String, String> map = new HashMap<>();
        //获取保存文件路径
        String url = QiniuUtil.commonUploadFile(file, Constant.Upload.ZC_REPAIRNEW + suffix);
        map.put("url", url);
        map.put("contractName", file.getOriginalFilename());
        return new ResponseJson(map);
    }


    //报废
    @PostMapping("/update/scrapRepairNew")
    @ApiOperation(value = "报废新报修表对象", notes = "返回响应对象")
    public ResponseJson scrapRepairNew(
            @ApiParam(value = "被修改的新报修表对象,对象属性不为空则修改", required = true)
            @RequestBody RepairNew repairNew){
        repairNew.setSchoolId(mySchoolId());
        repairNew.setScrapRemark(repairNew.getScrapRemark());
        repairNew.setOperatorId(currentTeacher().getId());
        repairNew.setOperatorName(currentTeacher().getName());
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s8 = formatter.format(currentTime);
        repairNew.setScrapTime(s8);
        //已处理
        repairNew.setStatus("3");
        //改为报废状态
        repairNew.setProcessType("3");
        repairNewService.scrapRepairNew(repairNew);
        return new ResponseJson();
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

    @GetMapping("/look/lookRepairNewByAssetNo/{assetNo}")
    @ApiOperation(value = "去查看页面,通过id查找新报修表", notes = "返回响应对象", response=RepairNew.class)
    public ResponseJson lookRepairNewByAssetNo(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String assetNo){
        RepairNew repairNew=repairNewService.lookRepairNewByAssetNo(assetNo);
        return new ResponseJson(repairNew);
    }


    @PostMapping("/update/findRepairNewsCountByWeek")
    @ApiOperation(value = "根据条件查找当前周报修次数", notes = "返回报修次数", response=RepairNew.class)
    public ResponseJson findRepairNewsCountByWeek(
            @ApiParam(value = "被修改的报修信息表对象,对象属性不为空则修改", required = true)
            @RequestBody RepairNew repairNew) {
        repairNew.setSchoolId(mySchoolId());
        long week =repairNewService.findRepairNewsCountByWeek(repairNew);
        long weekLose1 =repairNewService.findRepairNewsCountByWeekLose1(repairNew);
        Map<String,Long> map = new HashMap<>();
        map.put("week",week);
        map.put("weekLose1",weekLose1);
        System.out.println(map);
        //return new ResponseJson(week,weekLose1);
        return new ResponseJson(map);
    }

    //查询本校所有楼栋
    @PostMapping("/update/selectBuildingNameAllList")
    @ApiOperation(value = "根据条件查找查询本校所有楼栋", notes = "返回响应对象")
    public ResponseJson selectBuildingNameAllList(
            @ApiParam(value = "楼栋id,楼栋name", required = true)
            @RequestBody RepairNew repairNew) {
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


    //查询本校报修表所有楼栋
    @PostMapping("/update/selectBuildingNameList")
    @ApiOperation(value = "根据条件查找查询本校所有楼栋", notes = "返回响应对象")
    public ResponseJson selectBuildingNameList(
            @ApiParam(value = "楼栋id,楼栋name", required = true)
            @RequestBody RepairNew repairNew) {
        repairNew.setSchoolId(mySchoolId());
        Pager pager = new Pager();
        pager.setSortField("createTime");
        pager.setPaging(false);
        pager.setSortOrder(Pager.DESC);
        repairNew.setPager(pager);
        List<RepairNew> data = repairNewService.selectBuildingNameList(repairNew);
        return new ResponseJson(data);
    }

    //查询本校 宿管人员 管理 楼栋
    @PostMapping("/update/selectBuildingNameListByTeacher")
    @ApiOperation(value = "查询本校 宿管人员 管理 楼栋", notes = "返回响应对象")
    public ResponseJson selectBuildingNameListByTeacher(
            @ApiParam(value = "楼栋id,楼栋name", required = true)
            @RequestBody RepairNew repairNew) {
        //查询当前教师的  宿管职务
        DormBuildAdmin dormBuildAdmin = new DormBuildAdmin();
        dormBuildAdmin.setSchoolId(repairNew.getSchoolId());
        dormBuildAdmin.setStaffId(currentTeacher().getId());
        Pager pager = new Pager();
        pager.setSortField("createTime");
        pager.setPaging(false);
        pager.setSortOrder(Pager.DESC);dormBuildAdmin.setPager(pager);
        List<DormBuildAdmin> dormBuildAdminListByCondition = dormBuildAdminService.findDormBuildAdminListByCondition(dormBuildAdmin);
        //宿舍老师 0.宿管人员,  1.宿管老师
        List<DormBuildAdmin> collect1 = dormBuildAdminListByCondition.stream().filter(ele -> ele.getStaffType().equals("0")).collect(Collectors.toList());
        List<DormBuildAdmin> collect2 = dormBuildAdminListByCondition.stream().filter(ele -> ele.getStaffType().equals("1")).collect(Collectors.toList());
        if (collect2.size()>0){
            //查询所有楼栋
            Building building = new Building();
            building.setSchoolId(mySchoolId());
            Pager pager1 = new Pager();
            pager1.setSortField("createTime");
            pager1.setPaging(false);
            pager1.setSortOrder(Pager.DESC);
            building.setPager(pager1);
            List<Building> data = buildingService.findBuildingListByCondition(building).stream().filter(ele -> ele.getParentId().equals("-1")).collect(Collectors.toList());
            return new ResponseJson(data);
        }else if (collect1.size()>0){
            //宿管人员所管理的楼栋
            List<String> collect = dormBuildAdminService.findDormBuildAdminListByCondition(dormBuildAdmin).stream().map(DormBuildAdmin::getDormBuildId).distinct().collect(Collectors.toList());
            List<Building> listAll = new ArrayList();
            for (String list : collect){
                Building findBuildingById = buildingService.findBuildingById(list);
                listAll.add(findBuildingById);
            }
            return new ResponseJson(listAll);
        }
        return new ResponseJson();
    }

    //查询本校报修表 宿管人员 管理de  存放场地
    @PostMapping("/update/selectRoomNameByTeacher")
    @ApiOperation(value = "根据条件查找查询本校所有楼栋", notes = "返回响应对象")
    public ResponseJson selectRoomNameByTeacher(
            @ApiParam(value = "场地名称id", required = true)
            @RequestBody RepairNew repairNew) {
        repairNew.setSchoolId(mySchoolId());
        repairNew.setTypeId("107");
        Pager pager1 = new Pager();
        pager1.setSortField("createTime");
        pager1.setPaging(false);
        pager1.setSortOrder(Pager.DESC);
        repairNew.setPager(pager1);

        DormBuildAdmin dormBuildAdmin = new DormBuildAdmin();
        dormBuildAdmin.setSchoolId(repairNew.getSchoolId());
        dormBuildAdmin.setStaffId(currentTeacher().getId());
        Pager pager = new Pager();
        pager.setSortField("createTime");
        pager.setPaging(false);
        pager.setSortOrder(Pager.DESC);dormBuildAdmin.setPager(pager);
        List<DormBuildAdmin> dormBuildAdminListByCondition = dormBuildAdminService.findDormBuildAdminListByCondition(dormBuildAdmin);
        //宿舍老师 0.宿管人员,  1.宿管老师
        List<DormBuildAdmin> collect1 = dormBuildAdminListByCondition.stream().filter(ele -> ele.getStaffType().equals("0")).collect(Collectors.toList());
        List<DormBuildAdmin> collect2 = dormBuildAdminListByCondition.stream().filter(ele -> ele.getStaffType().equals("1")).collect(Collectors.toList());
        if (collect2.size()>0){
            List<RepairNew> data = repairNewService.selectRoomName(repairNew);
            return new ResponseJson(data);
        }else if (collect1.size()>0){
            //获取多个 管理的楼栋id
            List<String> buildId = collect1.stream().map(DormBuildAdmin :: getDormBuildId).distinct().collect(Collectors.toList());
            repairNew.setBuildingIdStr(buildId);
            List<RepairNew> data = repairNewService.selectRoomName(repairNew);
            return new ResponseJson(data);
        }

        return null;
    }


    //查询本校报修表所有存放场地
    @PostMapping("/update/selectRoomName")
    @ApiOperation(value = "根据条件查找查询本校所有楼栋", notes = "返回响应对象")
    public ResponseJson selectRoomName(
            @ApiParam(value = "场地名称id", required = true)
            @RequestBody RepairNew repairNew) {
        repairNew.setSchoolId(mySchoolId());
        Pager pager = new Pager();
        pager.setSortField("createTime");
        pager.setPaging(false);
        pager.setSortOrder(Pager.DESC);
        repairNew.setPager(pager);
        List<RepairNew> data = repairNewService.selectRoomName(repairNew);
        return new ResponseJson(data);
    }

    @PostMapping("/findRepairNewsDormByCondition")
    @ApiOperation(value = "根据条件查找新报修表", notes = "返回响应对象", response=RepairNew.class)
    public ResponseJson findRepairNewsDormByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody RepairNew repairNew){
        repairNew.setSchoolId(mySchoolId());
        repairNew.setTypeId("107");
        repairNew.getPager().setLike("repairNo");
        repairNew.getPager().setLike("assetName");
        repairNew.getPager().setLike("submitterName");
        repairNew.getPager().setLike("submitterDepartementId");
        repairNew.getPager().setLike("repairType");
        repairNew.getPager().setLike("repairPriority");
        repairNew.getPager().setLike("status");
        //排序已写在XML中
        //查询当前人员的 宿舍职务
        DormBuildAdmin dormBuildAdmin = new DormBuildAdmin();
        dormBuildAdmin.setSchoolId(repairNew.getSchoolId());
        dormBuildAdmin.setStaffId(currentTeacher().getId());
        Pager pager = new Pager();
        pager.setSortField("createTime");
        pager.setPaging(false);
        pager.setSortOrder(Pager.DESC);dormBuildAdmin.setPager(pager);

        List<DormBuildAdmin> dormBuildAdminListByCondition = dormBuildAdminService.findDormBuildAdminListByCondition(dormBuildAdmin);
        //宿舍老师 0.宿管人员,  1.宿管老师
        List<DormBuildAdmin> collect1 = dormBuildAdminListByCondition.stream().filter(ele -> ele.getStaffType().equals("0")).collect(Collectors.toList());
        List<DormBuildAdmin> collect2 = dormBuildAdminListByCondition.stream().filter(ele -> ele.getStaffType().equals("1")).collect(Collectors.toList());
        if (collect2.size()>0){
            List<RepairNew> data=repairNewService.findRepairNewListByCondition(repairNew);
            long count=repairNewService.findRepairNewCountByCondition(repairNew);
            return new ResponseJson(data,count);
        }else if (collect1.size()>0){
            //获取多个 管理的楼栋id
            List<String> buildId = collect1.stream().map(DormBuildAdmin :: getDormBuildId).distinct().collect(Collectors.toList());
            //实体类新建数组类型 装楼栋数组 去后台查询
            repairNew.setBuildingIdStr(buildId);
            List<RepairNew> data=repairNewService.findRepairNewListByCondition(repairNew);
            long count=repairNewService.findRepairNewCountByCondition(repairNew);
            return new ResponseJson(data,count);
        }
        return new ResponseJson();
    }

    // 判断 是否要加 repairNew.setTypeId("107");

}
