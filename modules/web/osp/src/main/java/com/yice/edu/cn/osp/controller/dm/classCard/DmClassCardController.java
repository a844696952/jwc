package com.yice.edu.cn.osp.controller.dm.classCard;

import com.yice.edu.cn.common.annotation.SysLog;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.classCard.ClassCardLock;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.classCard.DmTimedTask;
import com.yice.edu.cn.common.pojo.dm.classCard.Equipment;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.ts.jpush.Push;
import com.yice.edu.cn.osp.service.dm.classCard.DmClassCardService;
import com.yice.edu.cn.osp.service.dm.classCard.DmTimedTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.*;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/dmClassCard")
@Api(value = "/dmClassCard",description = "云班牌设备管理模块")
public class DmClassCardController {
    @Autowired
    private DmClassCardService dmClassCardService;
    @Autowired
    private DmTimedTaskService dmTimedTaskService;

    @PostMapping("/saveDmClassCard")
    @ApiOperation(value = "保存对象", notes = "返回响应对象")
    //@SysLog("新增班牌并绑定班级")
    public ResponseJson saveDmClassCard(
            @ApiParam(value = "对象", required = true)
            @RequestBody DmClassCard dmClassCard) {
        ResponseJson.Result r = new ResponseJson.Result();
        dmClassCard.setSchoolId(mySchoolId());
        //判断是否已经绑定
        List<DmClassCard> data = dmClassCardService.findDmClassCardListByclassId(dmClassCard);
        if (data.size() > 0) {
            return new ResponseJson(false, "该班级已经绑定");
        }
        //判断用户名是否被占用
        DmClassCard card = new DmClassCard();
        card.setUserName(dmClassCard.getUserName());
        List<DmClassCard> data2 = dmClassCardService.findDmClassCardUser(card);
        if (data2.size() > 0) {
            return new ResponseJson(false, "用户名已被使用");
        }
        dmClassCard.setLockStatus(0);//默认未锁屏
        dmClassCardService.saveDmClassCard(dmClassCard);
        dmClassCard.setTeacherId(myId());
        return new ResponseJson(dmClassCard);

    }


    @GetMapping("/update/findDmClassCardById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象")
    public ResponseJson findDmClassCardById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        DmClassCard dmClassCard = dmClassCardService.findDmClassCardById(id);
        return new ResponseJson(dmClassCard);
    }

    @PostMapping("/update/updateDmClassCard")
    @ApiOperation(value = "修改云班牌设备信息", notes = "返回响应对象")
    //@SysLog("修改云班牌绑定信息")
    public ResponseJson updateDmClassCard(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @Validated
            @RequestBody DmClassCard dmClassCard) {
        dmClassCard.setSchoolId(mySchoolId());
        //判断是否已经绑定
        List<DmClassCard> data = dmClassCardService.findDmClassCardListByclassId(dmClassCard);
        if (data.size() > 0) {
            return new ResponseJson(false, "该班级已经绑定");
        }
        //判断用户名是否被占用
        DmClassCard card = new DmClassCard();
        card.setUserName(dmClassCard.getUserName());
        card.setId(dmClassCard.getId());
        List<DmClassCard> data2 = dmClassCardService.findDmClassCardUser(card);
        if (data2.size() > 0) {
            return new ResponseJson(false, "用户名已被使用");
        }
        dmClassCardService.updateDmClassCard(dmClassCard);
        dmClassCard.setTeacherId(myId());
        return new ResponseJson(dmClassCard);
    }

    @GetMapping("/look/lookDmClassCardById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象")
    public ResponseJson lookDmClassCardById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        DmClassCard dmClassCard = dmClassCardService.findDmClassCardById(id);
        return new ResponseJson(dmClassCard);
    }

    @PostMapping("/findDmClassCardsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findDmClassCardsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmClassCard dmClassCard) {
        dmClassCard.setSchoolId(mySchoolId());
        List<DmClassCard> data = dmClassCardService.findDmClassCardListByCondition(dmClassCard);
        long count = dmClassCardService.findDmClassCardCountByCondition(dmClassCard);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneDmClassCardByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空")
    public ResponseJson findOneDmClassCardByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmClassCard dmClassCard) {
        DmClassCard one = dmClassCardService.findOneDmClassCardByCondition(dmClassCard);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteDmClassCard/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
   // @SysLog("删除云班牌")
    public ResponseJson deleteDmClassCard(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        DmClassCard dmClassCard = dmClassCardService.findDmClassCardById(id);
        dmClassCardService.deleteDmClassCard(id);
        DmTimedTask dt = new DmTimedTask();
        dt.setEquipmentId(dmClassCard.getUserName());
        dmTimedTaskService.deleteDmTimedTaskByCondition(dt);
        dmClassCard.setTeacherId(myId());
        return new ResponseJson(dmClassCard);
    }


    @PostMapping("/findDmClassCardListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数")
    public ResponseJson findDmClassCardListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody DmClassCard dmClassCard) {
        dmClassCard.setSchoolId(mySchoolId());
        List<DmClassCard> data = dmClassCardService.findDmClassCardListByCondition(dmClassCard);
        return new ResponseJson(data);
    }

    @PostMapping("/download")
    public void exportTeacher(@ApiParam(value = "云班牌设备信息对象")
                              @RequestBody DmClassCard dmClassCard, HttpServletResponse response) {
        //Workbook w = teacherService.exportTeacher();
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=student.xls");
        try (ServletOutputStream s = response.getOutputStream()) {
            dmClassCard.setSchoolId(mySchoolId());
            Workbook workbook = dmClassCardService.exportDmClassCard(dmClassCard);
            workbook.write(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/getUserName")
    @ApiOperation(value = "获取学校id", notes = "返回学校id")
    public ResponseJson getUserName(@RequestParam("id") String id,@RequestParam("name") String name) {
        DmClassCard card = new DmClassCard();
        card.setUserName(name);
        card.setId(id);
        List<DmClassCard> data2 = dmClassCardService.findDmClassCardUser(card);
        if (data2.size() > 0) {
            return new ResponseJson( false);
        }
        return new ResponseJson(true);
    }


    @PostMapping("/shutdown")
    @ApiOperation(value = "根据勾选的设备唯一标识进行批量关机", notes = "返回设备状态")
    @SysLog("关机")
    public ResponseJson shutdown(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody DmClassCard dmClassCard) {
        if(dmClassCard.getRowData().length<=0){
            return new ResponseJson(false,"请勾选设备");
        }
        boolean flg = dmClassCardService.shutdown(dmClassCard);
        //修改设备状态
        if (flg) {
            String[] str = dmClassCard.getRowData();
            for (String id : str) {
                DmClassCard dm = new DmClassCard();
                //dm.setEquipmentStatus(1);
                dm.setUserName(id);
                dmClassCardService.dmClassCardStatus(dm);
            }

        }
        dmClassCard.setSchoolId(mySchoolId());
        dmClassCard.setTeacherId(myId());
        return new ResponseJson(dmClassCard);
    }

    @PostMapping("/reboot")
    @ApiOperation(value = "根据勾选的设备唯一标识进行批量重启", notes = "返回设备状态")
    @SysLog("重启")
    public ResponseJson reboot(@RequestBody DmClassCard dmClassCard) {
        if(dmClassCard.getRowData().length<=0){
            return new ResponseJson(false,"请勾选设备");
        }
        boolean flg = dmClassCardService.reboot(dmClassCard);
        //修改设备状态
        if (flg) {
            String[] str = dmClassCard.getRowData();
            for (String id : str) {
                DmClassCard dm = new DmClassCard();
                //dm.setEquipmentStatus(1);
                dm.setUserName(id);
                dmClassCardService.dmClassCardStatus(dm);
            }

        }
        dmClassCard.setSchoolId(mySchoolId());
        dmClassCard.setTeacherId(myId());
        return new ResponseJson(dmClassCard);
    }

    @PostMapping("/batchChangeLockStatusByIds")
    @ApiOperation(value = "根据锁屏状态锁屏、解锁", notes = "根据锁屏状态锁屏、解锁")
    //@SysLog("一键批量锁屏/解锁")
    public ResponseJson batchChangeLockStatusByIds(
            @ApiParam(value = "根据锁屏状态锁屏、解锁")
            @RequestBody ClassCardLock classCardLock) {
        dmClassCardService.batchChangeLockStatusByIds(classCardLock);
        DmClassCard card = new DmClassCard();
        card.setTeacherId(myId());
        card.setSchoolId(mySchoolId());
        card.setCardIds(classCardLock.getCardIds());
        return  new ResponseJson();
    }

    @GetMapping("/lockDmScreen/{id}")
    @ApiOperation(value = "锁屏", notes = "返回响应对象")
    //@SysLog("锁屏")
    public ResponseJson lockDmScreen(
            @ApiParam(value = "锁屏的id", required = true)
            @PathVariable String id) {
        dmClassCardService.lockDmScreen(id);
        DmClassCard card = new DmClassCard();
        card.setTeacherId(myId());
        card.setSchoolId(mySchoolId());
        card.setId(id);
        return new ResponseJson(card);

    }

    @GetMapping("/unLockDmScreen/{id}")
    @ApiOperation(value = "解锁", notes = "返回响应对象")
    //@SysLog("解锁")
    public ResponseJson unLockDmScreen(
            @ApiParam(value = "解锁的id", required = true)
            @PathVariable String id) {
        dmClassCardService.unLockDmScreen(id);
        DmClassCard card = new DmClassCard();
        card.setTeacherId(myId());
        card.setSchoolId(mySchoolId());
        card.setId(id);
        return new ResponseJson(card);
    }

    @GetMapping("/getDmClassCardByTeacherId/{lockStatus}")
    @ApiOperation(value = "通过讲师编号获取当前所管理的班牌", notes = "返回响应对象")
    public ResponseJson getDmClassCardByTeacherId(
            @ApiParam(value = "通过讲师编号获取当前所管理的班牌", required = true)
            @PathVariable(value = "lockStatus") String lockStatus) {
        List<DmClassCard> dmClassCardList = dmClassCardService.getDmClassCardByTeacherId(myId(),lockStatus);
        if(dmClassCardList.size()>0){
            return new ResponseJson(dmClassCardList);
        }else{
            return new ResponseJson(false,"无数据");
        }


    }
    @PostMapping("/updateDmClassManage")
    @ApiOperation(value = "批量配置权限", notes = "返回响应对象")
    public ResponseJson updateDmClassManage(
            @ApiParam(value = "", required = true)
            @RequestBody DmClassCard dmClassCard) {
        dmClassCard.setSchoolId(mySchoolId());
        //先清空教师原有权限，在进行下面的重新分配
        dmClassCardService.cleraDmClassManage(dmClassCard);
        if(dmClassCard.getRowData().length>0){
            //重新分配
            dmClassCardService.updateDmClassManage(dmClassCard);
        }
        dmClassCard.setTeacherId(myId());
        return new ResponseJson();
    }

    /**
     * 查询教师已经拥有的班牌权限
     * @param teacherId
     * @return
     * @auth zzx
     */
    @GetMapping("/findTeacherManageList/{teacherId}")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象")
    public ResponseJson findTeacherManageList(
            @ApiParam(value = "属性不为空则作为条件查询")
            @PathVariable String teacherId) {
        DmClassCard dmClassCard = new DmClassCard();
        dmClassCard.setSchoolId(mySchoolId());
        dmClassCard.setTeacherId(teacherId);
        dmClassCard.setRemark("yes");
        //查询云班牌树形结构
        List<DmClassCard> data = dmClassCardService.getDmClassCardTree(dmClassCard);
        //查询该教师已选择的班牌
        Object[] data2= dmClassCardService.findDmClassCardIdByTeacherId(dmClassCard);
        return new ResponseJson(data,data2);
    }

    @GetMapping("/getTeacherManageList")
    @ApiOperation(value = "根据条件查找树形结构班牌", notes = "返回响应对象")
    public ResponseJson getDmClassCardByTeacherId() {
        DmClassCard dmClassCard = new DmClassCard();
        dmClassCard.setSchoolId(mySchoolId());
        return new ResponseJson(dmClassCardService.getDmClassCardTree(dmClassCard));
    }

    @GetMapping("/getTeacherManageList/{remark}")
    @ApiOperation(value = "根据条件查找树形结构班牌", notes = "返回响应对象")
    public ResponseJson getDmClassCardByRemark(@PathVariable("remark") String remark) {

        DmClassCard dmClassCard = new DmClassCard();
        dmClassCard.setSchoolId(mySchoolId());
        dmClassCard.setTeacherId(myId());

        Teacher teacher = currentTeacher();
        if(Constant.STATUS.TEACHER_ADMIN.equals(teacher.getStatus())) {//说明是管理员,直接过去
            dmClassCard.setKeyWord(null);
            return new ResponseJson(dmClassCardService.getDmClassCardTree(dmClassCard));
        }else{
            dmClassCard.setKeyWord(remark);
            return new ResponseJson(dmClassCardService.getDmClassCardTree(dmClassCard));
        }
    }
}
