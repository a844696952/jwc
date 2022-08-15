package com.yice.edu.cn.ecc.controller.honourRoll;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRoll;
import com.yice.edu.cn.common.pojo.dm.honourRoll.DmHonourRollStudent;
import com.yice.edu.cn.common.pojo.dm.honourRoll.EccHonourRoll;
import com.yice.edu.cn.ecc.service.honourRoll.DmHonourRollService;
import com.yice.edu.cn.ecc.service.honourRoll.DmHonourRollStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dmHonourRoll")
@Api(value = "/dmHonourRoll",description = "光荣榜，管理模块")
public class DmHonourRollController {
    @Autowired
    private DmHonourRollService dmHonourRollService;
    @Autowired
    private DmHonourRollStudentService dmHonourRollStudentService;

    @PostMapping("/findDmHonourRollByCondition")
    @ApiOperation(value = "根据条件查找光荣榜，管理,(classId", notes = "没有时返回空", response=DmHonourRoll.class)
    public ResponseJson findDmHonourRollByConditionFirst(@ApiParam("classId，pager 必填")@RequestBody DmHonourRoll dmHonourRoll){
        List<EccHonourRoll> eccHonourRollList = new ArrayList<>();
        //传入班级编号
        if(StringUtils.isEmpty(dmHonourRoll.getClassId())){
            return new ResponseJson(false,"班级编号不能为空");
        }
        dmHonourRoll.setStatus(1);
        DmHonourRoll one=dmHonourRollService.findOneDmHonourRollByCondition(dmHonourRoll);
        if(one==null){
            return new ResponseJson(eccHonourRollList);
        }
        DmHonourRollStudent dmHonourRollStudent = new DmHonourRollStudent();
        dmHonourRollStudent.setHonourId(one.getId());
        dmHonourRollStudent.setPager(dmHonourRoll.getPager());
        dmHonourRollStudent.setClassId(dmHonourRoll.getClassId());
        eccHonourRollList = dmHonourRollStudentService.getHonourRollList(dmHonourRollStudent);
        if(StringUtils.isEmpty(one.getHonourRemark())){
            one.setHonourRemark("");
        }
        return new ResponseJson(eccHonourRollList,one);
    }
}
