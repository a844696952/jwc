package com.yice.edu.cn.pcd.controller.repairNew;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.eehManagement.EehSchool;
import com.yice.edu.cn.common.pojo.jw.eehManagement.EehTree;
import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairNew;
import com.yice.edu.cn.pcd.service.eehManagement.EehSchoolService;
import com.yice.edu.cn.pcd.service.eehManagement.EehTreeService;
import com.yice.edu.cn.pcd.service.repairNew.RepairNewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.yice.edu.cn.pcd.interceptor.LoginInterceptor.currentAccount;
import static com.yice.edu.cn.pcd.interceptor.LoginInterceptor.currentEehId;

@RestController
@RequestMapping("/repairNew")
@Api(value = "/repairNew",description = "新报修表模块")
public class RepairNewController {
    @Autowired
    private RepairNewService repairNewService;
    @Autowired
    private EehSchoolService eehSchoolService;
    @Autowired
    private EehTreeService eehTreeService;

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
        List<RepairNew> data=repairNewService.findRepairNewListByCondition(repairNew);
        long count=repairNewService.findRepairNewCountByCondition(repairNew);
        return new ResponseJson(data,count);
    }


    @PostMapping("/findRepairNewsBySchoolIds")
    @ApiOperation(value = "根据条件查找新报修表", notes = "返回响应对象", response=RepairNew.class)
    public ResponseJson findRepairNewsBySchoolIds(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody RepairNew repairNew){
        /*EehTree eehTreeById = eehTreeService.findEehTreeById(currentEehId());
        List<String> eehids=new ArrayList<>();
        String[] split = eehTreeById.getRelation().split(";");
        for (int i = 0; i <split.length ; i++) {
            eehids.add(split[i]);
        }*/
        List<String> eehids = eehTreeService.findChildEehId(currentEehId());
        List<EehSchool> eehSchoolList = eehSchoolService.findEehSchoolListByEehIds(eehids);
        List<RepairNew> data=new ArrayList<>();
        long count=0;
        if(eehSchoolList.size()>0){
            List<String> schoolIds=new ArrayList<>();
            eehSchoolList.forEach(s->{
                schoolIds.add(s.getSchoolId());
            });
            repairNew.setSchoolIds(schoolIds);
             data=repairNewService.findRepairNewsBySchoolIds(repairNew);
             count=repairNewService.findRepairNewCountBySchoolIds(repairNew);
            return new ResponseJson(data,count);
        }else {
            return new ResponseJson(data,count);
        }


    }
}
