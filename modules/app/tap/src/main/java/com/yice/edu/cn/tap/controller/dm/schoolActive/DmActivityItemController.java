package com.yice.edu.cn.tap.controller.dm.schoolActive;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivityItem;
import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivitySiginUp;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.tap.service.dm.schoolActive.DmActivityItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;


@RestController
@RequestMapping("/dmActivityItem")
@Api(value = "/dmActivityItem",description = "活动节目表模块")
public class DmActivityItemController {
    @Autowired
    private DmActivityItemService dmActivityItemService;

    @PostMapping("/saveDmActivityItem")
    @ApiOperation(value = "保存活动节目表对象", notes = "返回保存好的活动节目表对象", response= DmActivityItem.class)
    public ResponseJson saveDmActivityItem(
            @ApiParam(value = "活动节目表对象", required = true)
            @RequestBody DmActivityItem dmActivityItem){
       dmActivityItem.setSchoolId(mySchoolId());
        DmActivityItem s=dmActivityItemService.saveDmActivityItem(dmActivityItem);
        return new ResponseJson(s);
    }


    @PostMapping("/updateDmActivityItem")
    @ApiOperation(value = "修改活动节目表对象", notes = "返回响应对象")
    public ResponseJson updateDmActivityItem(
            @ApiParam(value = "被修改的活动节目表对象,对象属性不为空则修改", required = true)
            @RequestBody DmActivityItem dmActivityItem){
        dmActivityItemService.updateDmActivityItem(dmActivityItem);
        return new ResponseJson();
    }

    @GetMapping("/findDmActivityItemById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找活动节目表", notes = "返回响应对象", response=DmActivityItem.class)
    public ResponseJson findDmActivityItemById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id){
        DmActivityItem dmActivityItem=dmActivityItemService.findDmActivityItemById(id);
        return new ResponseJson(dmActivityItem);
    }

    @GetMapping("/deleteDmActivityItem/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteDmActivityItem(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        dmActivityItemService.deleteDmActivityItem(id);
        return new ResponseJson();
    }


    @PostMapping("/findDmActivityItemListByCondition")
    @ApiOperation(value = "根据条件查找活动节目表列表", notes = "返回响应对象,不包含总条数", response=DmActivityItem.class)
    public ResponseJson findDmActivityItemListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmActivityItem dmActivityItem){
       dmActivityItem.setSchoolId(mySchoolId());
        List<DmActivityItem> data=dmActivityItemService.findDmActivityItemListByCondition(dmActivityItem);
        return new ResponseJson(data);
    }




    @PostMapping("/findClassStudentByClassesId")
    @ApiOperation(value = "查询班级里的所有学生", notes = "返回响应对象", response= Student.class)
    public ResponseJson findClassStudentByClassesId(
            @ApiParam(value = "去查看页面,需要用到的classesId", required = true)
            @RequestBody DmActivitySiginUp dmActivitySiginUp){
        List<Student> students = dmActivityItemService.findClassStudentByClassesId(dmActivitySiginUp);
        return new ResponseJson(students);
    }


    @PostMapping("/saveDmActivityItemAndPeople")
    @ApiOperation(value = "保存活动节目表对象和活动人员", notes = "返回保存好的活动节目表对象")
    public ResponseJson saveDmActivityItemAndPeople(
            @ApiParam(value = "活动节目表对象和活动人员对象", required = true)
            @RequestBody DmActivityItem dmActivityItem){
        dmActivityItem.setSchoolId(mySchoolId());
        dmActivityItemService.saveDmActivityItemAndPeople(dmActivityItem);
        return new ResponseJson();
    }

    @PostMapping("/updateDmActivityItemAndPeople")
    @ApiOperation(value = "修改活动节目表对象和报名人员", notes = "返回响应对象")
    public ResponseJson updateDmActivityItemAndPeople(
            @ApiParam(value = "被修改的活动节目表对象,对象属性不为空则修改", required = true)
            @RequestBody DmActivityItem dmActivityItem){
        dmActivityItemService.updateDmActivityItemAndPeople(dmActivityItem);
        return new ResponseJson();
    }


    @PostMapping("/findDmActivityItemsByCondition")
    @ApiOperation(value = "去查看页面,通过活动id和班级id查找活动节目表", notes = "返回响应对象", response=DmActivityItem.class)
    public ResponseJson findDmActivityItemsByCondition(
            @ApiParam(value = "根据条件查找活动节目表列表", required = true)
            @RequestBody DmActivityItem dmActivityItem){
        List<DmActivityItem> dmActivityItems=dmActivityItemService.findDmActivityItemsByCondition(dmActivityItem);
        return new ResponseJson(dmActivityItems);
    }



}
