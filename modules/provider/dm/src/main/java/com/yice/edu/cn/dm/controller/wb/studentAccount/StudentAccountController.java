package com.yice.edu.cn.dm.controller.wb.studentAccount;

import com.yice.edu.cn.common.pojo.dm.classCard.DmDeleteData;
import com.yice.edu.cn.common.pojo.wb.studentAccount.StudentAccount;
import com.yice.edu.cn.common.pojo.wb.studentAccount.WisdomClassStudentAccount;
import com.yice.edu.cn.dm.service.wb.studentAccount.StudentAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/studentAccount")
@Api(value = "/studentAccount",description = "模块")
public class StudentAccountController {
    @Autowired
    private StudentAccountService studentAccountService;

    @GetMapping("/findStudentAccountById/{id}")
    @ApiOperation(value = "通过id查找", notes = "返回对象")
    public StudentAccount findStudentAccountById(
            @ApiParam(value = "需要用到的id", required = true)
            @PathVariable String id){
        return studentAccountService.findStudentAccountById(id);
    }

    @PostMapping("/saveStudentAccount")
    @ApiOperation(value = "保存", notes = "返回对象")
    public StudentAccount saveStudentAccount(
            @ApiParam(value = "对象", required = true)
            @RequestBody StudentAccount studentAccount){
        return studentAccountService.saveStudentAccount(studentAccount);
    }

    @PostMapping("/findStudentAccountListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回列表")
    public List<StudentAccount> findStudentAccountListByCondition(
            @ApiParam(value = "对象")
            @RequestBody StudentAccount studentAccount){
        return studentAccountService.findStudentAccountListByCondition(studentAccount);
    }
    @PostMapping("/findStudentAccountCountByCondition")
    @ApiOperation(value = "根据条件查找列表个数", notes = "返回总个数")
    public long findStudentAccountCountByCondition(
            @ApiParam(value = "对象")
            @RequestBody StudentAccount studentAccount){
        return studentAccountService.findStudentAccountCountByCondition(studentAccount);
    }

    @PostMapping("/updateStudentAccount")
    @ApiOperation(value = "修改", notes = "对象必传")
    public Integer updateStudentAccount(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody StudentAccount studentAccount){
        return studentAccountService.updateStudentAccount(studentAccount);
    }


    @GetMapping("/deleteStudentAccount/{id}")
    @ApiOperation(value = "通过id删除")
    public void deleteStudentAccount(
            @ApiParam(value = "对象", required = true)
            @PathVariable String id){
        studentAccountService.deleteStudentAccount(id);
    }
    @PostMapping("/deleteStudentAccountByCondition")
    @ApiOperation(value = "根据条件删除")
    public void deleteStudentAccountByCondition(
            @ApiParam(value = "对象")
            @RequestBody StudentAccount studentAccount){
        studentAccountService.deleteStudentAccountByCondition(studentAccount);
    }
    @PostMapping("/findOneStudentAccountByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "返回单个,没有时为空")
    public StudentAccount findOneStudentAccountByCondition(
            @ApiParam(value = "对象")
            @RequestBody StudentAccount studentAccount){
        return studentAccountService.findOneStudentAccountByCondition(studentAccount);
    }

    @PostMapping("/batchSaveStudentAccount")
    @ApiOperation(value = "批量保存对象")
    public void batchSaveStudentAccount(
            @ApiParam(value = "对象", required = true)
            @RequestBody List<StudentAccount> studentAccounts){
        studentAccountService.batchSaveStudentAccount(studentAccounts);
    }

    @PostMapping("/batchResetStudentPwd")
    @ApiOperation(value = "批量重置密码")
    public Integer batchResetStudentPwd(
            @ApiParam(value = "对象", required = true)
            @RequestBody List<String> studentIds){
        return studentAccountService.batchResetStudentPwd(studentIds);
    }

    @PostMapping("/findStudentAccountImei")
    @ApiOperation(value = "根据条件查找学生列表（IMEI）", notes = "返回列表")
    public List<StudentAccount> findStudentAccountImei(
            @ApiParam(value = "对象")
            @RequestBody StudentAccount studentAccount) {
        return studentAccountService.findStudentAccountImei(studentAccount);
    }

    /**
     * 查找最大的学生账号
     * @return
     */
    @GetMapping("/getMaxPadAccount")
    @ApiOperation(value = "查找最大的学生账号", notes = "返回学生账号最大值")
    public Integer getMaxPadAccount() {
        return studentAccountService.getMaxPadAccount();
    }

    @PostMapping("/batchDeleteByIds")
    @ApiOperation(value = "批量删除")
    public void batchDeleteByIds(
            @ApiParam(value = "对象", required = true)
            @RequestBody List<String> ids){
        studentAccountService.batchDeleteByIds(ids);
    }

    @PostMapping("/findStudentAccountByImeiAndId")
    @ApiOperation(value = "通过imei和id查找", notes = "返回对象")
    public StudentAccount findStudentAccountByImeiAndId(
            @ApiParam(value = "通过imei和id查找", required = true)
            @RequestBody StudentAccount studentAccount){
        return studentAccountService.findStudentAccountByImeiAndId(studentAccount);
    }

    @PostMapping("/findStudentAccountByStudentIds")
    @ApiOperation(value = "根据studentId列表查询", notes = "返回列表")
    public List<StudentAccount> findStudentAccountByStudentIds(
            @ApiParam(value = "对象")
            @RequestBody List<String> studentIds){
        return studentAccountService.findStudentAccountByStudentIds(studentIds);
    }


    @PostMapping("/batchUpdateStudentType")
    @ApiOperation(value = "学生类型批量修改", notes = "必传")
    public void batchUpdateStudentType(
            @ApiParam(value = "对象,对象属性不为空则修改", required = true)
            @RequestBody StudentAccount studentAccount){
        studentAccountService.batchUpdateStudentType(studentAccount.getIds(),studentAccount.getStudentType());
    }

    @PostMapping("/deleteStudentAccountByClassIds")
    @ApiOperation(value = "根据班级id列表删除学生对象")
    public void deleteStudentAccountByClassIds(
            @ApiParam(value = "电子班牌删除对象")
            @RequestBody DmDeleteData dmDeleteData){
        studentAccountService.deleteStudentAccountByClassIds(dmDeleteData.getClassIdList());
    }

    @PostMapping("/findWisdomClassStudentAccountListByCondition")
    @ApiOperation(value = "根据条件查找笔盒绑定学生列表",notes = "返回笔盒绑定学生列表")
    public List<WisdomClassStudentAccount> findWisdomClassStudentAccountListByCondition(
            @ApiParam("学生账号对象")
            @RequestBody StudentAccount studentAccount
    ){
        return studentAccountService.findWisdomClassStudentAccountListByCondition(studentAccount);
    }

    @PostMapping("/findWisdomClassStudentAccountCountByCondition")
    @ApiOperation(value = "根据条件查找笔盒绑定列表学生总个数",notes = "返回笔盒绑定学生列表总个数")
    public Long findWisdomClassStudentAccountCountByCondition(
            @ApiParam("学生账号对象")
            @RequestBody StudentAccount studentAccount
    ){
        return studentAccountService.findWisdomClassStudentAccountCountByCondition(studentAccount);
    }

    @PostMapping("/updateWisdomClassStudentAccount")
    @ApiOperation("根据条件更新笔盒绑定的学生列表")
    public Integer updateWisdomClassStudentAccount(
            @ApiParam("学生账号对象")
            @RequestBody StudentAccount studentAccount
    ){
        return studentAccountService.updateWisdomClassStudentAccount(studentAccount);
    }


}
