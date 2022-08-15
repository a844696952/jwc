package com.yice.edu.cn.yed.controller.baseData.zySchoolInit;

import com.alibaba.fastjson.JSON;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.xw.kq.DataReceiveResBean;
import com.yice.edu.cn.common.pojo.xw.kq.KqSchoolInit;
import com.yice.edu.cn.common.util.zyDetector.AESUtil;
import com.yice.edu.cn.common.util.zyDetector.ZyDetector;
import com.yice.edu.cn.yed.service.baseData.school.SchoolService;
import com.yice.edu.cn.yed.service.baseData.zySchoolInit.KqSchoolInitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kqSchoolInit")
@Api(value = "/kqSchoolInit",description = "学校初始化中移账号表模块")
public class KqSchoolInitController {
    @Autowired
    private KqSchoolInitService kqSchoolInitService;
    @Autowired
    private SchoolService schoolService;
    @Value("#{'${spring.profiles.active}'=='prod'}")
    private Boolean isProd;

    //
    @PostMapping("/saveKqSchoolInit")
    @ApiOperation(value = "保存学校初始化中移账号表对象", notes = "返回保存好的学校初始化中移账号表对象", response = KqSchoolInit.class)
    public ResponseJson saveKqSchoolInit(
            @ApiParam(value = "学校初始化中移账号表对象", required = true)
            @RequestBody KqSchoolInit kqSchoolInit) {
        KqSchoolInit s = kqSchoolInitService.saveKqSchoolInit(kqSchoolInit);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findKqSchoolInitById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找学校初始化中移账号表", notes = "返回响应对象", response = KqSchoolInit.class)
    public ResponseJson findKqSchoolInitById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        KqSchoolInit kqSchoolInit = kqSchoolInitService.findKqSchoolInitById(id);
        return new ResponseJson(kqSchoolInit);
    }

    @PostMapping("/update/updateKqSchoolInit")
    @ApiOperation(value = "修改学校初始化中移账号表对象", notes = "返回响应对象")
    public ResponseJson updateKqSchoolInit(
            @ApiParam(value = "被修改的学校初始化中移账号表对象,对象属性不为空则修改", required = true)
            @RequestBody KqSchoolInit kqSchoolInit) {
        kqSchoolInitService.updateKqSchoolInit(kqSchoolInit);
        return new ResponseJson();
    }

    @GetMapping("/look/lookKqSchoolInitById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找学校初始化中移账号表", notes = "返回响应对象", response = KqSchoolInit.class)
    public ResponseJson lookKqSchoolInitById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        KqSchoolInit kqSchoolInit = kqSchoolInitService.findKqSchoolInitById(id);
        return new ResponseJson(kqSchoolInit);
    }

    @PostMapping("/find/findKqSchoolInitsByCondition")
    @ApiOperation(value = "根据条件查找学校初始化中移账号表", notes = "返回响应对象", response = KqSchoolInit.class)
    public ResponseJson findKqSchoolInitsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqSchoolInit kqSchoolInit) {
        List<KqSchoolInit> data = kqSchoolInitService.findKqSchoolInitListByCondition(kqSchoolInit);
        long count = kqSchoolInitService.findKqSchoolInitCountByCondition(kqSchoolInit);
        return new ResponseJson(data, count);
    }

    @PostMapping("/find/findOneKqSchoolInitByCondition")
    @ApiOperation(value = "根据条件查找单个学校初始化中移账号表,结果必须为单条数据", notes = "没有时返回空", response = KqSchoolInit.class)
    public ResponseJson findOneKqSchoolInitByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody KqSchoolInit kqSchoolInit) {
        KqSchoolInit one = kqSchoolInitService.findOneKqSchoolInitByCondition(kqSchoolInit);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteKqSchoolInit/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteKqSchoolInit(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        kqSchoolInitService.deleteKqSchoolInit(id);
        return new ResponseJson();
    }


    @PostMapping("/find/findKqSchoolInitListByCondition")
    @ApiOperation(value = "根据条件查找学校初始化中移账号表列表", notes = "返回响应对象,不包含总条数", response = KqSchoolInit.class)
    public ResponseJson findKqSchoolInitListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqSchoolInit kqSchoolInit) {
        List<KqSchoolInit> data = kqSchoolInitService.findKqSchoolInitListByCondition(kqSchoolInit);
        return new ResponseJson(data);
    }

    //获取未初始化的学校
    @GetMapping("/find/findUnInitSchoolListByCondition")
    @ApiOperation(value = "获取未初始化的学校", notes = "返回响应对象,包含总条数", response = KqSchoolInit.class)
    public ResponseJson findUnInitSchoolListByCondition() {
        //查找所有学校
        School school = new School();
        List<School> schoolList = schoolService.findSchoolListByCondition(school);
        System.out.println("所有学校" + schoolList.size());
        //查找已经初始化的学校
        KqSchoolInit kqSchoolInit = new KqSchoolInit();
        List<KqSchoolInit> kqSchoolInitList = kqSchoolInitService.findKqSchoolInitListByCondition(kqSchoolInit);
        System.out.println("所有已经初始化的学校" + kqSchoolInitList.size());
        //剔除已经初始化的学校，得到未初始化学校列表
        List<School> unInitSchools = kqSchoolInitService.getUnInitSchool(kqSchoolInitList, schoolList);
        long count = unInitSchools.size();
        System.out.println(unInitSchools.size());
        System.out.println(count);
        return new ResponseJson(unInitSchools, count);
    }

    //请求中移初始化接口
    @PostMapping("/find/requestZyInit")
    @ApiOperation(value = "根据条件查找学校初始化中移账号表列表", notes = "返回响应对象,不包含总条数", response = KqSchoolInit.class)
    public ResponseJson requestZyInit(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqSchoolInit kqSchoolInit) {
        System.out.println("这是请求方法参数" + kqSchoolInit);
        //添加初始化参数
        KqSchoolInit kqInit = new KqSchoolInit();
        kqInit.setCustId(kqSchoolInit.getCustId());
        kqInit.setUserNm(kqSchoolInit.getUserNm());
        kqInit.setUserPhone(kqSchoolInit.getUserPhone());
        //请求中移初始化接口并返回初始化结果
        String response = ZyDetector.postRequest(isProd, ZyDetector.GET_USER_ACCT, null, kqSchoolInit.getRequstSource(), kqSchoolInit.getVersion(), JSON.toJSONString(kqInit));
        DataReceiveResBean dataReceiveResBean = JSON.parseObject(response, DataReceiveResBean.class);
        System.out.println("这是返回响应对象(解密前)" + dataReceiveResBean);
        if (dataReceiveResBean == null || !dataReceiveResBean.getReturnCode().equals(ZyDetector.SUCCESS)) {
            return new ResponseJson(false, "初始化失败");
        }
        //初始化参数解密
        Map<String, String> map = dataReceiveResBean.getBean();
        map.put("coCode", AESUtil.decrypt(map.get("coCode").toString(), ZyDetector.INIT_KEY));
        map.put("userAcct", AESUtil.decrypt(map.get("userAcct").toString(), ZyDetector.INIT_KEY));
        map.put("userPw", AESUtil.decrypt(map.get("userPw").toString(), ZyDetector.INIT_KEY));
        map.put("key", AESUtil.decrypt(map.get("key").toString(), ZyDetector.INIT_KEY));
        System.out.println("这是返回的Bean(解密后)" + map);
        //写入库
        kqSchoolInit.setCoCode(map.get("coCode"));
        kqSchoolInit.setUserAcct(map.get("userAcct"));
        kqSchoolInit.setUserPw(map.get("userPw"));
        kqSchoolInit.setKey(map.get("key"));
        KqSchoolInit kqSchool = kqSchoolInitService.saveKqSchoolInit(kqSchoolInit);
        return new ResponseJson(kqSchool);
    }

    //请求中移更新密钥接口
    @PostMapping("/find/requestZyUpdateKey")
    @ApiOperation(value = "更新中移学校密钥", notes = "返回响应对象,不包含总条数", response = KqSchoolInit.class)
    public ResponseJson requestZyUpdateKey(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqSchoolInit kqSchoolInit) {
        System.out.println("这是请求方法参数" + kqSchoolInit);
        //添加初始化参数
        KqSchoolInit kqInit = new KqSchoolInit();
        kqInit.setCustId(kqSchoolInit.getCustId());
        kqInit.setUserPw(kqSchoolInit.getUserPw());
        kqInit.setUserAcct(kqSchoolInit.getUserAcct());
        kqInit.setOperation(ZyDetector.RENEW_KEY);
        String reqParam = AESUtil.encrypt(JSON.toJSONString(kqInit), kqSchoolInit.getKey());
        //请求中移初始化接口并返回初始化结果
        String response = ZyDetector.postRequest(isProd, ZyDetector.SEND_KEY, kqSchoolInit.getCoCode(), kqSchoolInit.getRequstSource(), kqSchoolInit.getVersion(), reqParam);
        DataReceiveResBean dataReceiveResBean = JSON.parseObject(response, DataReceiveResBean.class);
        System.out.println("这是返回响应对象(解密前)" + dataReceiveResBean);
        if (dataReceiveResBean == null || !dataReceiveResBean.getReturnCode().equals(ZyDetector.SUCCESS)) {
            return new ResponseJson(false, "更新密钥失败,请联系中移同步密钥");
        }
        //初始化参数解密
        Map<String, String> map = dataReceiveResBean.getBean();
        /*map.put("coCode", AESUtil.decrypt(map.get("coCode").toString(), ZyDetector.INIT_KEY));
        map.put("userAcct", AESUtil.decrypt(map.get("userAcct").toString(), ZyDetector.INIT_KEY));
        map.put("userPw", AESUtil.decrypt(map.get("userPw").toString(), ZyDetector.INIT_KEY));*/
        KqSchoolInit kqSchool1 = new KqSchoolInit();
        kqSchool1.setCustId(kqSchoolInit.getCustId());
        KqSchoolInit kqSchool = kqSchoolInitService.findOneKqSchoolInitByCondition(kqSchool1);
        String myKey = kqSchool.getKey();
        System.out.println("我的密钥" + myKey);
        map.put("key", AESUtil.decrypt(map.get("key").toString(), myKey));
        System.out.println("这是返回的Bean(解密后)" + map);
        //写入库
        /*kqSchoolInit.setCoCode(map.get("coCode"));
        kqSchoolInit.setUserAcct(map.get("userAcct"));
        kqSchoolInit.setUserPw(map.get("userPw"));*/

        kqSchoolInit.setKey(map.get("key"));
        kqSchoolInitService.updateKqSchoolInit(kqSchoolInit);
        return new ResponseJson(kqSchoolInit);
    }

    //请求中移更新密钥接口
    @PostMapping("/find/requestZyFindKey")
    @ApiOperation(value = "更新中移学校密钥", notes = "返回响应对象,不包含总条数", response = KqSchoolInit.class)
    public ResponseJson requestZyFindKey(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody KqSchoolInit kqSchoolInit) {
        System.out.println("这是请求方法参数" + kqSchoolInit);
        //添加初始化参数
        KqSchoolInit kqInit = new KqSchoolInit();
        kqInit.setCustId(kqSchoolInit.getCustId());
        kqInit.setUserPw(kqSchoolInit.getUserPw());
        kqInit.setUserAcct(kqSchoolInit.getUserAcct());
        kqInit.setOperation(ZyDetector.GENERATE_KEY);
        String reqParam = AESUtil.encrypt(JSON.toJSONString(kqInit), kqSchoolInit.getKey());
        //请求中移初始化接口并返回初始化结果
        String response = ZyDetector.postRequest(isProd, ZyDetector.SEND_KEY, kqSchoolInit.getCoCode(), kqSchoolInit.getRequstSource(), kqSchoolInit.getVersion(), reqParam);
        DataReceiveResBean dataReceiveResBean = JSON.parseObject(response, DataReceiveResBean.class);
        System.out.println("这是返回响应对象(解密前)" + dataReceiveResBean);
        if (dataReceiveResBean == null || !dataReceiveResBean.getReturnCode().equals(ZyDetector.SUCCESS)) {
            return new ResponseJson(false, "获取密钥失败,请联系中移同步密钥");
        }
        //初始化参数解密
        Map<String, String> map = dataReceiveResBean.getBean();
        /*map.put("coCode", AESUtil.decrypt(map.get("coCode").toString(), ZyDetector.INIT_KEY));
        map.put("userAcct", AESUtil.decrypt(map.get("userAcct").toString(), ZyDetector.INIT_KEY));
        map.put("userPw", AESUtil.decrypt(map.get("userPw").toString(), ZyDetector.INIT_KEY));*/

        KqSchoolInit kqSchool1 = new KqSchoolInit();
        kqSchool1.setCustId(kqSchoolInit.getCustId());
        KqSchoolInit kqSchool = kqSchoolInitService.findOneKqSchoolInitByCondition(kqSchool1);
        String myKey = kqSchool.getKey();
        System.out.println("我的密钥" + myKey);
        map.put("key", AESUtil.decrypt(map.get("key").toString(), myKey));
        System.out.println("这是返回的Bean(解密后)" + map);
        //写入库
        /*kqSchoolInit.setCoCode(map.get("coCode"));
        kqSchoolInit.setUserAcct(map.get("userAcct"));
        kqSchoolInit.setUserPw(map.get("userPw"));*/

        kqSchoolInit.setKey(map.get("key"));
        kqSchoolInitService.updateKqSchoolInit(kqSchoolInit);

        return new ResponseJson(kqSchoolInit);


    }
}