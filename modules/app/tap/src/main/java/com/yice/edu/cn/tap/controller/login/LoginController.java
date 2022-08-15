package com.yice.edu.cn.tap.controller.login;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.auth.Perm;
import com.yice.edu.cn.common.pojo.jw.teacher.LoginErrorInfo;
import com.yice.edu.cn.common.pojo.jw.teacher.LoginObj;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.weixin.Jscode2session;
import com.yice.edu.cn.common.pojo.mes.classManage.mesAppletsTeacher.MesAppletsTeacher;
import com.yice.edu.cn.common.util.jwt.JwtUtil;
import com.yice.edu.cn.tap.service.appPerm.AppPermService;
import com.yice.edu.cn.tap.service.dy.classManage.MesAppletsPostPermService;
import com.yice.edu.cn.tap.service.dy.classManage.MesAppletsTeacherService;
import com.yice.edu.cn.tap.service.qrcode.QrCodeService;
import com.yice.edu.cn.tap.service.school.SchoolService;
import com.yice.edu.cn.tap.service.teacher.TeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/login")
@Validated
public class LoginController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private AppPermService appPermService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private QrCodeService qrCodeService;
    @Autowired
    private MesAppletsTeacherService mesAppletsTeacherService;
    @Autowired
    private MesAppletsPostPermService mesAppletsPostPermService;
    @CreateCache(name = Constant.Redis.TAP_TEACHER_IDENTIFYING_CODE, expire = Constant.Redis.TAP_TEACHER_IDENTIFYING_CODE_TIME)
    private Cache<String, String> codeCache;
    @CreateCache(name = Constant.Redis.TAP_TEACHER_LOGIN_ERROR, expire = 1, timeUnit = TimeUnit.DAYS)
    private Cache<String, String> LoginErrorInfoCache;
    @CreateCache(name = Constant.Redis.DMSS_TEACHER_PERMS, expire = Constant.Redis.DMSS_TEACHER_TIMEOUT)
    private Cache<String, List<Perm>> permCache;


    @PostMapping("/login")
    @ApiOperation(value = "教师登录：传入手机号(tel)，密码(password)", notes = "返回教师信息")
    public ResponseJson login(@RequestBody Teacher teacher) throws Exception {

        String key = teacher.getTel();
        String value = LoginErrorInfoCache.get(key);
        LoginErrorInfo errorInfo = null;
        if (value != null) {
            errorInfo = JSONUtil.toBean(value, LoginErrorInfo.class);//转化为bean对象
        }
        if (errorInfo == null) {
            errorInfo = new LoginErrorInfo();
            errorInfo.setNum(0);
            errorInfo.setOk(true);
            String errorInfoStr = JSONUtil.toJsonStr(errorInfo);
            teacherService.saveLoginErroInfoToRedis(errorInfoStr, key);
        }
        if (errorInfo.isOk()) {
            Teacher t = teacherService.login(teacher);

            if (t != null) {
                /**
                 判断是否升学失败，学校是否禁用等情况
                 */
                ResponseJson responseJson = appPermService.findSchoolExpireOrSchoolYear(t.getSchoolId());
                if (!responseJson.getResult().isSuccess()){
                    return responseJson;
                }

                t.setPostList(teacherService.findTeacherPost(t.getId()));
                teacherService.clearTeacherInRedis(t.getId());
                teacherService.saveTeacherToRedis(t);
                t.setPassword(null);
                t.setSchool(null);
                String token = JwtUtil.createJWT(t.getId(), "{}", null, -1);
                if (value != null) {
                    teacherService.clearLoginErroInfoCache(key);
                }

                return new ResponseJson(token, t);
            }
        }
        int num = errorInfo.getNum();
        if (num < 5) {
            errorInfo.setNum(num + 1);
            if (num + 1 == 5) {
                errorInfo.setOk(false);
                errorInfo.setWrongTime(DateUtil.date());
            }
            String errorInfoStr = JSONUtil.toJsonStr(errorInfo);
            teacherService.updateLoginErroInfoToRedis(errorInfoStr, key);
        } else {
            Date wrongDate = errorInfo.getWrongTime();
            Date nowDate = DateUtil.date();

            long diff = nowDate.getTime() - wrongDate.getTime();

            if (diff > Constant.Redis.TAP_TEACHER_LOGIN_ERROR_TIME * 60 * 1000) {
                Teacher t = teacherService.login(teacher);
                if (t != null) {
                    /**
                     判断是否升学失败，学校是否禁用等情况
                     */
                    ResponseJson responseJson = appPermService.findSchoolExpireOrSchoolYear(t.getSchoolId());
                    if (!responseJson.getResult().isSuccess()){
                        return responseJson;
                    }

                    t.setPostList(teacherService.findTeacherPost(t.getId()));
                    teacherService.saveTeacherToRedis(t);
                    t.setPassword(null);
                    t.setSchool(null);
                    String token = JwtUtil.createJWT(t.getId(), "{}", null, -1);
                    teacherService.clearLoginErroInfoCache(key);
                    return new ResponseJson(token, t);
                } else {
                    errorInfo.setNum(1);
                    errorInfo.setWrongTime(null);
                    errorInfo.setOk(true);
                    String errorInfoStr = JSONUtil.toJsonStr(errorInfo);
                    teacherService.updateLoginErroInfoToRedis(errorInfoStr, key);
                }
            } else {
                return new ResponseJson(false, "密码错误超过5次" + String.valueOf(Constant.Redis.TAP_TEACHER_LOGIN_ERROR_TIME) + "分钟后重试");
            }
        }
        return new ResponseJson(false, "错误的用户名或密码");
    }

    @PostMapping("/findTeacherCountByCondition")
    @ApiOperation(value = "根据条件查找教师信息列表个数", notes = "返回教师信息总个数")
    public ResponseJson findTeacherCountByCondition(
            @ApiParam(value = "教师信息对象")
            @RequestBody Teacher teacher) {
        long count = teacherService.findTeacherCountByCondition(teacher);
        return new ResponseJson(count);
    }

    @GetMapping("/getIdentifyingCode/{tel}")
    @ApiOperation(value = "获取验证码：传入手机号", notes = "返回发送结果")
    public ResponseJson getIdentifyingCode(
            @ApiParam(value = "手机号")
            @Length(min = 11, max = 11, message = "手机号码长度必须为11位")
            @PathVariable String tel) {

        Teacher teacher = new Teacher();
        teacher.setTel(tel);
        long count = teacherService.findTeacherCountByCondition(teacher);
        if (count >= 1) {
            String code = teacherService.getIdentifyingCode(tel);
            if (code != "") {
                teacherService.clearCodeCache(tel);
                teacherService.saveCodeToRadis(tel, code);
                return new ResponseJson(true, "验证码发送成功");
            }
            return new ResponseJson(false, "验证码发送失败");

        } else {
            return new ResponseJson(false, "用户不存在");
        }
    }

    @PostMapping("/compareIdentifyingCode")
    @ApiOperation(value = "验证验证码：传入tel:手机号，code:验证码", notes = "返回验证码是否正确")
    public ResponseJson compareIdentifyingCode(
            @ApiParam(value = "校验验证码")
            @RequestBody LoginObj codeCompare
            ) {


        String code1 = codeCache.get(codeCompare.getTel());
        if (code1 == null) {
            return new ResponseJson(false, "验证码已过期");
        }
        if (codeCompare.getCode().equals(code1)) {
            Teacher teacher = new Teacher();
            teacher.setTel(codeCompare.getTel());
            List<Teacher> teachers = teacherService.findTeacherListByCondition(teacher);
            teacher=teachers.get(0);
            teacher.setSchool(schoolService.findSchoolById(teacher.getSchoolId()));

            /**
             判断是否升学失败，学校是否禁用等情况
             */
            ResponseJson responseJson = appPermService.findSchoolExpireOrSchoolYear(teacher.getSchoolId());
            if (!responseJson.getResult().isSuccess()){
                return responseJson;
            }
            teacher.setPostList(teacherService.findTeacherPost(teacher.getId()));
            teacherService.clearTeacherInRedis(teacher.getId());
            teacherService.saveTeacherToRedis(teacher);
            teacher.setPassword(null);
            teacher.setSchool(null);
            String token = JwtUtil.createJWT(teacher.getId(), "{}", null, -1);
            teacherService.clearCodeCache(codeCompare.getTel());
            return new ResponseJson(token, teacher);
        }
        return new ResponseJson(false, "验证码错误");
    }


    @PostMapping("/h5login")
    @ApiOperation(value = "教师登录：传入手机号(tel)，密码(password)", notes = "返回教师信息")
    public ResponseJson h5login(@RequestBody Teacher teacher) throws Exception {
        ResponseJson rj = this.login(teacher);
        if (rj.getResult().isSuccess()) {
            //登录成功 进行验证屏保权限
            Teacher t = (Teacher) rj.getData2();
            List<Perm> perms = teacherService.findDmssTeacherFuncPermsByTeacherId(t.getId());
            permCache.PUT(t.getId(), perms);
            if (perms.size() > 0) {
                return rj;
            } else {
                return new ResponseJson(false, "你无权限操作屏保");
            }
        }
        return rj;
    }

    /**
     * 小程序登录使用
     * 1、手机号和密码登录；登录上来并且将openId记录到当前的教师信息中
     * 2、openId登录 去校验取当前教师，并且验证设备编号
     * 3、手机号加验证码登录
     *
     * @param loginObj
     * @return
     * @throws Exception
     */
    @PostMapping("/wxLogin")
    @ApiOperation(value = "教师登录：传入手机号(tel)，密码(password)", notes = "返回教师信息")
    public ResponseJson wxLogin(@RequestBody LoginObj loginObj) throws Exception {
        loginObj.setAppPermType(1);
        ResponseJson rj = new ResponseJson(false, "无效登录");
        Teacher teacher = new Teacher();
        if (StringUtils.isNotEmpty(loginObj.getTel()) && StringUtils.isNotEmpty(loginObj.getPassword())) {
            //手机号和密码登录 登录成功 并且绑定openId
            teacher.setTel(loginObj.getTel());
            teacher.setPassword(loginObj.getPassword());
            teacher.setAppPermType(loginObj.getAppPermType());
            rj = this.login(teacher);
            if (rj.getResult().isSuccess() && StringUtils.isNotEmpty(loginObj.getOpenId())) {
                //登录成功 进行绑定openId
                teacher = (Teacher) rj.getData2();
                teacher.setOpenId(loginObj.getOpenId());
                rj.setData2(teacher);
                teacherService.bindOpenId2Teacher(teacher);
            }
            return rj;
        }
        if (StringUtils.isNotEmpty(loginObj.getTel()) && StringUtils.isNotEmpty(loginObj.getCode())) {
            //手机号和验证码登录 登录成功 并且绑定openId
            rj = this.compareIdentifyingCode(loginObj);
            if (rj.getResult().isSuccess() && StringUtils.isNotEmpty(loginObj.getOpenId())) {
                //登录成功 进行绑定openId
                teacher = (Teacher) rj.getData2();
                teacher.setOpenId(loginObj.getOpenId());
                teacherService.bindOpenId2Teacher(teacher);
                rj.setData2(teacher);
            }
            return rj;
        }
        if (StringUtils.isNotEmpty(loginObj.getOpenId()) && StringUtils.isEmpty(loginObj.getTel())) {
            //只有openId的时候进行 openId登录
            teacher.setOpenId(loginObj.getOpenId());
            List<Teacher> teacherList = teacherService.findTeacherListByCondition(teacher);
            if (teacherList != null && teacherList.size() > 0) {
                teacher = teacherList.get(0);
                teacher.setPermList(teacherService.findTeacherPerm4AppXq(teacher.getId()));
                teacher.setPostList(teacherService.findTeacherPost(teacher.getId()));
                teacherService.clearTeacherInRedis(teacher.getId());
                teacherService.saveTeacherToRedis(teacher);
                teacher.setPassword(null);
                teacher.setSchool(null);
                String token = JwtUtil.createJWT(teacher.getId(), "{}", null, -1);
                return new ResponseJson(token, teacher);
            }
        }
        return rj;
    }
    /**
     * 小程序登录使用
     * 获取openId
     *
     * @param jscode2session
     * @return
     * @throws Exception
     */
    @PostMapping("/jsCode2session")
    @ApiOperation(value = "小程序获取openId，并且默认会去校验登录，成功返回token和教师信息，失败返回code901，msg返回openId", notes = "对应信息")
    public ResponseJson jsCode2session(@RequestBody Jscode2session jscode2session)throws Exception{
        String openId = teacherService.jsCode2Session(jscode2session);
        LoginObj loginObj = new LoginObj();
        loginObj.setOpenId(openId);
        ResponseJson rj = this.wxLogin(loginObj);
        if(rj.getResult().isSuccess()){
            //登录成功 openId从data2中的教师信息获取
            return rj;
        }else {
            //微信小程序登录失败 返回openId
           return new ResponseJson(true,901,openId);
        }
    }


    @PostMapping("/loginByQRcode")
    @ApiOperation(value = "扫码登录", notes = "返回扫码结果")
    public ResponseJson loginByQRCode(@RequestParam("tel")String tel, @RequestParam("qrCode")String qrCode){
        return qrCodeService.loginByQRCode(tel,qrCode);
    }

    /**
     * 德育小程序登录
     * 1、手机号和密码登录；登录上来并且将openId记录到德育小程序教师表mes_applets_teacher中
     * 2、openId登录 去校验取当前教师，并且验证设备编号
     * 3、手机号加验证码登录
     */
    @PostMapping("/mesAppletsLogin")
    @ApiOperation(value = "教师登录：传入手机号(tel)，密码(password)", notes = "返回教师信息")
    public ResponseJson mesAppletsLogin(@RequestBody LoginObj loginObj) throws Exception {
        loginObj.setAppPermType(1);
        ResponseJson rj = new ResponseJson(false, "无效登录");
        Teacher teacher = new Teacher();
        if (StringUtils.isNotEmpty(loginObj.getTel()) && StringUtils.isNotEmpty(loginObj.getPassword())) {
            //手机号和密码登录 登录成功 并且绑定openId
            teacher.setTel(loginObj.getTel());
            teacher.setPassword(loginObj.getPassword());
            teacher.setAppPermType(loginObj.getAppPermType());      
            rj = this.login(teacher);
            return getResponseJson(rj, loginObj);
        }
        if (StringUtils.isNotEmpty(loginObj.getTel()) && StringUtils.isNotEmpty(loginObj.getCode())) {
            //手机号和验证码登录 登录成功 并且绑定openId
            rj = this.compareIdentifyingCode(loginObj);
            return getResponseJson(rj, loginObj);
        }
        if (StringUtils.isNotEmpty(loginObj.getOpenId()) && StringUtils.isEmpty(loginObj.getTel())) {
            //只有openId的时候进行 openId登录
            MesAppletsTeacher model = new MesAppletsTeacher();
            model.setOpenId(loginObj.getOpenId());
            model.setType(1);//教师端
            List<MesAppletsTeacher> appletsTeacherList = mesAppletsTeacherService.findMesAppletsTeacherListByCondition(model);
            if (CollectionUtil.isNotEmpty(appletsTeacherList)) {
                Teacher teacher1 = teacherService.findTeacherById(appletsTeacherList.get(0).getObjectId());
                if (teacher1 != null) {
                    teacher1.setOpenId(loginObj.getOpenId());
                    return getLoginResponseJson(teacher1);
                }
            }
        }
        return rj;
    }

    private ResponseJson getLoginResponseJson(Teacher teacher) {
        teacher.setPermList(teacherService.findTeacherPerm4AppXq(teacher.getId()));
        teacher.setPostList(teacherService.findTeacherPost(teacher.getId()));
        teacherService.clearTeacherInRedis(teacher.getId());
        teacherService.saveTeacherToRedis(teacher);
        teacher.setSchool(null);
        teacher.setPassword(null);
        String token = JwtUtil.createJWT(teacher.getId(), "{}", null, -1);
        return new ResponseJson(token, teacher, getTeacherPermission(teacher));
    }

    private ResponseJson getResponseJson(ResponseJson rj, LoginObj loginObj) {
        if (rj.getResult().isSuccess() && StringUtils.isNotEmpty(loginObj.getOpenId())) {
            //登录成功 进行绑定openId
            Teacher te = (Teacher) rj.getData2();
            te.setOpenId(loginObj.getOpenId());
            //将teacherId、openId 插入mes_applets_teacher
            MesAppletsTeacher mesAppletsTeacher = new MesAppletsTeacher();
            mesAppletsTeacher.setObjectId(te.getId());
            mesAppletsTeacher.setSchoolId(te.getSchoolId());
            mesAppletsTeacher.setType(1);//教师端
            List<MesAppletsTeacher> lists = mesAppletsTeacherService.findMesAppletsTeacherListByCondition(mesAppletsTeacher);
            if (CollectionUtil.isEmpty(lists)) {
                mesAppletsTeacher.setOpenId(te.getOpenId());
                mesAppletsTeacherService.saveMesAppletsTeacher(mesAppletsTeacher);
            }else {
                if (StringUtils.isEmpty(lists.get(0).getOpenId()) || !lists.get(0).getOpenId().equals(loginObj.getOpenId())){
                    lists.get(0).setOpenId(loginObj.getOpenId());
                    mesAppletsTeacherService.updateMesAppletsTeacher(lists.get(0));
                }
            }
            rj.setData2(te);
            rj.setData3(getTeacherPermission(te));
        }
        return rj;
    }

    /**
     * 通过teacherId获取教师的职务对应的功能权限
     *
     * @return map
     */
    private Map<String, Boolean> getTeacherPermission(Teacher teacher) {
        Map<String, Boolean> map = new HashMap<>(16);
        map.put("classManagement",false);//班级管理
        map.put("dormitoryInspectionEntry",false);//宿舍检查(录入+历史+通知)
        map.put("schoolCheckNotice",false);//学校检查(通知)
        map.put("schoolCheckEntry",false);//学校检查（录入+历史+通知）
        map.put("dormitoryInspectionNotice",false);//宿舍检查（通知）
        map.put("dormitoryInspectionHistory",false);//宿舍检查（录入+历史)
        map.put("schoolCheckHistory",false);//学校检查（录入+历史)
        if (teacher != null && CollectionUtil.isNotEmpty(teacher.getPostList())) {
            Set<Integer> permIds = mesAppletsPostPermService.findMesAppletsPostPermByPostId(teacher.getPostList());
            if (CollUtil.isNotEmpty(permIds)){
                setPermission(map,permIds);
            }
        }
        return map;
    }

    private void setPermission(Map<String, Boolean> map, Set<Integer> permIds) {
        if (permIds.contains(Constant.DY_PERMISSION.CLASS_MANAGEMENT)){
            map.put("classManagement",true);
        }
//        当前版本小程序没有做下面的
        /*if (permIds.contains(Constant.DY_PERMISSION.DORMITORY_INSPECTION_ENTRY)){
            map.put("dormitoryInspectionEntry",true);
            map.put("dormitoryInspectionNotice",true);
            map.put("dormitoryInspectionHistory",true);
        }
        if (permIds.contains(Constant.DY_PERMISSION.SCHOOL_CHECK_NOTICE)){
            map.put("schoolCheckNotice",true);
        }
        if (permIds.contains(Constant.DY_PERMISSION.SCHOOL_CHECK_ENTRY)){
            map.put("schoolCheckEntry",true);
            map.put("schoolCheckNotice",true);
            map.put("schoolCheckHistory",true);
        }
        if (permIds.contains(Constant.DY_PERMISSION.DORMITORY_INSPECTION_NOTICE)){
            map.put("dormitoryInspectionNotice",true);
        }
        if (permIds.contains(Constant.DY_PERMISSION.DORMITORY_INSPECTION_HISTORY)){
            map.put("dormitoryInspectionEntry",true);
            map.put("dormitoryInspectionHistory",true);
        }
        if (permIds.contains(Constant.DY_PERMISSION.SCHOOL_CHECK_HISTORY)){
            map.put("schoolCheckEntry",true);
            map.put("schoolCheckHistory",true);
        }*/
    }

    /**
     * 小程序登录使用
     * 获取openId
     *
     * @param jscode2session
     * @return
     * @throws Exception
     */
    @PostMapping("/code2session")
    @ApiOperation(value = "小程序获取openId，并且默认会去校验登录，成功返回token和教师信息，失败返回code901，msg返回openId", notes = "对应信息")
    public ResponseJson code2session(@RequestBody Jscode2session jscode2session) throws Exception {
        String openId = teacherService.jsCode2Session(jscode2session);
        LoginObj loginObj = new LoginObj();
        loginObj.setOpenId(openId);
        ResponseJson rj = this.mesAppletsLogin(loginObj);
        if (rj.getResult().isSuccess()) {
            //登录成功 openId从data2中的教师信息获取
            return rj;
        } else {
            //微信小程序登录失败 返回openId
            return new ResponseJson(true, 901, openId);
        }
    }

    /**
     * 退出账号
     */
    @PostMapping("/logout")
    @ApiOperation(value = "教师退出登录：传入openId")
    public ResponseJson logout(@RequestBody LoginObj loginObj) throws Exception {
        if (loginObj != null && StringUtils.isNotBlank(loginObj.getOpenId())) {
            MesAppletsTeacher mesAppletsTeacher = new MesAppletsTeacher();
            mesAppletsTeacher.setOpenId(loginObj.getOpenId());
            mesAppletsTeacher.setType(1);
            mesAppletsTeacherService.deleteMesAppletsTeacherByCondition(mesAppletsTeacher);
            return new ResponseJson();
        }
        return new ResponseJson(false, "缺少必要参数");
    }


}
