package com.yice.edu.cn.yed.controller.jy.titleQuota;

import cn.hutool.crypto.digest.DigestUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jy.titleQuota.ResourcePlatform;
import com.yice.edu.cn.common.pojo.jy.titleQuota.SuperTel;
import com.yice.edu.cn.common.pojo.jy.titleQuota.TopicQuotaResources;
import com.yice.edu.cn.common.pojo.yedAdmin.Admin;
import com.yice.edu.cn.common.util.isMobileNO.IsMobileNO;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.yed.service.jw.teacher.TeacherService;
import com.yice.edu.cn.yed.service.jy.titleQuota.SuperTelService;
import com.yice.edu.cn.yed.service.jy.titleQuota.TopicQuotaResourcesService;
import com.yice.edu.cn.yed.service.system.admin.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.yice.edu.cn.yed.interceptor.LoginInterceptor.currentAdmin;
import static com.yice.edu.cn.yed.interceptor.LoginInterceptor.myId;

@RestController
@RequestMapping("/topicQuotaResources")
@Api(value = "/topicQuotaResources",description = "题目额度资源表模块")
public class TopicQuotaResourcesController {
    @Autowired
    private TopicQuotaResourcesService topicQuotaResourcesService;
    @Autowired
    private SuperTelService superTelService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private AdminService adminService;

    @CreateCache(name = Constant.Redis.YED_ADMIN_IDENTIFYING_CODE, expire = Constant.Redis.YED_ADMIN_IDENTIFYING_CODE_TIME)
    private Cache<String, String> codeCache;

    @GetMapping("/getBaiscInfo/{schoolId}")
    @ApiOperation(value = "获取该学校题库基本情况", notes = "返回发送结果")
    public ResponseJson getBaiscInfo(@PathVariable String schoolId) {
        TopicQuotaResources topicQuotaResources = topicQuotaResourcesService.getBaiscInfo(schoolId);
        if(topicQuotaResources!=null){
            SuperTel superTel = new SuperTel();
            String tel = superTelService.findOneSuperTelByCondition(superTel).getTel();
            topicQuotaResources.setTel(tel);
        }
        return new ResponseJson(topicQuotaResources);
    }

    @PostMapping("/update/saveOrupdateTopicQuotaResources")
    @ApiOperation(value = "保存或修改题目额度资源表对象", notes = "返回响应对象")
    public ResponseJson updateTopicQuotaResources(
            @ApiParam(value = "被修改的题目额度资源表对象,对象属性不为空则修改", required = true)
            @RequestBody TopicQuotaResources topicQuotaResources){
        SuperTel superTel = new SuperTel();
        String tel = superTelService.findOneSuperTelByCondition(superTel).getTel();
        String pwd = DigestUtil.sha1Hex(topicQuotaResources.getPassword());
        Admin admin = adminService.findAdminById(myId());
        if(!admin.getPassword().equals(pwd)){
            return new ResponseJson(false, "密码错误");
        }
        String code = codeCache.get(tel);
        if (code == null) {
            return new ResponseJson(false, "验证码已过期");
        }
        if(code.equals(topicQuotaResources.getCode1())){
            codeCache.remove(tel);
            topicQuotaResources.setOpertor(currentAdmin().getRealName());
            topicQuotaResources = topicQuotaResourcesService.updateTopicQuotaResources4Person(topicQuotaResources);
            return new ResponseJson(topicQuotaResources);
        }else {
            return new ResponseJson(false, "用户输入验证码错误");
        }
    }

    @GetMapping("/getIdentifyingCode/{schoolId}")
    @ApiOperation(value = "获取验证码", notes = "返回发送结果")
    public ResponseJson getIdentifyingCode(@PathVariable String schoolId) {
        SuperTel superTel = new SuperTel();
        String tel = superTelService.findOneSuperTelByCondition(superTel).getTel();
        return sendIdentifyingCode(tel);
    }

    @PostMapping("/getIdentifyingCode4superAd")
    @ApiOperation(value = "获取验证码", notes = "返回发送结果")
    public ResponseJson getIdentifyingCode4superAd(
            @RequestBody TopicQuotaResources topicQuotaResources) {
        String tel = topicQuotaResources.getPritivetel();
        return sendIdentifyingCode(tel);
    }

    public ResponseJson sendIdentifyingCode(String tel){//获取验证码
        boolean mobileNO = IsMobileNO.isMobileNO(tel);//验证手机号码的合格性
        if(!mobileNO){
            return new ResponseJson(false, "该手机号码不是合格运营商电话号码，请重新输入");
        }
        String code = teacherService.getIdentifyingCode(tel);
        if (code != "") {
            codeCache.put(tel, code);
            return new ResponseJson(true, "验证码发送成功");
        }
        return new ResponseJson(false, "验证码发送失败");
    }

    @PostMapping("/validatorIdentifyingCodeByAdmin")
    @ApiOperation(value = "验正证码", notes = "返回保存好的题目额度资源表对象", response=TopicQuotaResources.class)
    public ResponseJson validatorIdentifyingCodeByAdmin(
            @ApiParam(value = "题目额度资源表对象", required = true)
            @RequestBody TopicQuotaResources topicQuotaResources) {
        String code1 = codeCache.get(topicQuotaResources.getPritivetel());
        String code2 = codeCache.get(topicQuotaResources.getTel());
        if (code1 == null) {
            return new ResponseJson(false, "验证码已过期");
        }
        if (code2 == null) {
            return new ResponseJson(false, "验证码已过期");
        }
        if (code1.equals(topicQuotaResources.getCode1())) {
            if (code2.equals(topicQuotaResources.getCode2())) {
                String pwd = DigestUtil.sha1Hex(topicQuotaResources.getSurepassword());
                Admin admin = adminService.findAdminById(myId());
                if (!admin.getPassword().equals(pwd)) {
                    return new ResponseJson(false, "密码错误");
                }
                codeCache.remove(topicQuotaResources.getPritivetel());
                codeCache.remove(topicQuotaResources.getTel());
                SuperTel superTel = new SuperTel();
                superTel.setTel(topicQuotaResources.getPritivetel());
                superTelService.deleteSuperTelByCondition(superTel);
                superTel.setTel(topicQuotaResources.getTel());
                superTelService.saveSuperTel(superTel);
                return new ResponseJson(true, "电话号码修改成功");
            }
        }
        return new ResponseJson(false, "用户输入验证码错误");
    }

    @PostMapping("/update/uploadImg")
    @ApiOperation(value = "保修图片上传", notes = "返回图片地址")
    public String uploadImg(MultipartFile file){
        return QiniuUtil.uploadImage(file, Constant.Upload.TKPZ_ASSETS_STOCK_DETAIL);
    }

    @PostMapping("/findPaltFormByCondition")
    @ApiOperation(value = "传入schoolId", notes = "返回单个学生答题记录表,没有时为空")
    public List<ResourcePlatform> findPaltFormByCondition(
            @ApiParam(value = "学生答题记录表对象")
            @RequestBody TopicQuotaResources topicQuotaResources){
        return topicQuotaResourcesService.findPaltFormByCondition(topicQuotaResources);
    }
}
