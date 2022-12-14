package com.yice.edu.cn.ewb.controller.login;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import com.yice.edu.cn.common.pojo.jw.student.StuParentVo;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.LoginErrorInfo;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.common.pojo.wb.studentAccount.StudentAccount;
import com.yice.edu.cn.common.util.crypto.SimpleCryptoKit;
import com.yice.edu.cn.common.util.jwt.JwtUtil;
import com.yice.edu.cn.common.util.qrcode.QRCodeUtil;
import com.yice.edu.cn.ewb.service.parent.ParentService;
import com.yice.edu.cn.ewb.service.school.SchoolService;
import com.yice.edu.cn.ewb.service.student.StudentService;
import com.yice.edu.cn.ewb.service.teacher.TeacherClassesService;
import com.yice.edu.cn.ewb.service.teacher.TeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/login")
@Validated
//@RefreshScope
public class LoginController {
    @Autowired
    TeacherClassesService teacherClassesService;
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ParentService parentService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private SequenceId sequenceId;



    @CreateCache(name=Constant.Redis.EWB_TEACHER_IDENTIFYING_CODE,expire = 300)
    private Cache<String,String> codeCache;
    @CreateCache(name=Constant.Redis.EWB_TEACHER_LOGIN_ERROR,expire = 1,timeUnit=TimeUnit.DAYS)
    private Cache<String,String> LoginErrorInfoCache;
    @CreateCache(name=Constant.Redis.EWB_STUDENT_LOGIN_ERROR,expire = 1,timeUnit=TimeUnit.DAYS)
    private Cache<String,String> LoginStudentErrorInfoCache;
    @CreateCache(name=Constant.Redis.EWB_QR_CODE_INFO,expire = 1,timeUnit =TimeUnit.MINUTES)
    private Cache<String,String> qrCodeInfo;



    @PostMapping("/login")
    @ApiOperation(value = "??????????????????????????????(tel)?????????(password)", notes = "??????????????????")
    public ResponseJson login(@RequestBody Teacher teacher) throws Exception {
        String tel =teacher.getTel();
        String key=tel+Constant.Redis.EWB_TEACHER_LOGIN_ERROR;
        String value=LoginErrorInfoCache.get(key);
       LoginErrorInfo errorInfo =null;
       if(value!=null){
           errorInfo =JSONUtil.toBean(value,LoginErrorInfo.class);//?????????bean??????
       }
       if(errorInfo==null){
           errorInfo=new LoginErrorInfo();
           errorInfo.setNum(0);
           errorInfo.setOk(true);
           String errorInfoStr=JSONUtil.toJsonStr(errorInfo);
           teacherService.saveLoginErroInfoToRedis(errorInfoStr,key);
       }
       if(errorInfo.isOk()){
           Teacher t=teacherService.login(teacher);
           if(t!=null){
            //????????????????????????
               ResponseJson responseJson = schoolService.findSchoolExpireOrSchoolYear(t.getSchoolId());
               if(!responseJson.getResult().isSuccess()){
                   return responseJson;
               }
               String token = setTeacherInfo(t, t);

               if(value!=null){
                   teacherService.clearLoginErroInfoCache(key);
               }
               //???????????????????????????
               List<TeacherClasses> ts = teacherClassesService.findTeacherClassesByTeacherId(t.getId());
               t.setTeacherClasses(ts);
               TeacherClasses teacherClasses = new TeacherClasses();
               teacherClasses.setPager(new Pager("update_time",Pager.ASC));
               teacherClasses.setTeacherId(t.getId());
               teacherClasses.setSchoolId(t.getSchoolId());
               List<Map<String, Object>> maps = teacherClassesService.findTeacherClassPostCourseList(teacherClasses);
               return new ResponseJson(token,t,maps);
           }
       }

       int num=errorInfo.getNum();
       if(num<5){
           errorInfo.setNum(num+1);
           if(num+1==5){
               errorInfo.setOk(false);
               errorInfo.setWrongTime(DateUtil.date());
           }
           String errorInfoStr=JSONUtil.toJsonStr(errorInfo);
           teacherService.updateLoginErroInfoToRedis(errorInfoStr,key);
       }else {
           Date wrongDate=errorInfo.getWrongTime();
           Date nowDate=DateUtil.date();

           long diff=nowDate.getTime()-wrongDate.getTime();

           if(diff>Constant.Redis.EWB_TEACHER_LOGIN_ERROR_TIME*60*1000){
               Teacher t=teacherService.login(teacher);

               if(t!=null){
                   String token = setTeacherInfo(t, t);
                   teacherService.clearLoginErroInfoCache(key);
                   TeacherClasses teacherClasses = new TeacherClasses();
                   teacherClasses.setPager(new Pager("update_time",Pager.ASC));
                   teacherClasses.setTeacherId(t.getId());
                   teacherClasses.setSchoolId(t.getSchoolId());
                   List<Map<String, Object>> maps = teacherClassesService.findTeacherClassPostCourseList(teacherClasses);
                   //???????????????????????????
                   List<TeacherClasses> ts = teacherClassesService.findTeacherClassesByTeacherId(t.getId());
                   t.setTeacherClasses(ts);
                   return new ResponseJson(token,t,maps);
               }else {
                   errorInfo.setNum(1);
                   errorInfo.setWrongTime(null);
                   errorInfo.setOk(true);
                   String errorInfoStr=JSONUtil.toJsonStr(errorInfo);
                   teacherService.updateLoginErroInfoToRedis(errorInfoStr,key);
               }

           }else {
               return new ResponseJson(false,"??????????????????5???"+String.valueOf(Constant.Redis.EWB_TEACHER_LOGIN_ERROR_TIME)+"???????????????");
           }

       }


        return new ResponseJson(false,"???????????????????????????");
    }
    @GetMapping("/getIdentifyingCode/{tel}")
    @ApiOperation(value = "?????????????????????????????????", notes = "??????????????????")

    public ResponseJson getIdentifyingCode(
            @ApiParam(value = "?????????")
/*            @Length(min=11,max=11,message = "???????????????????????????11???")*/
            @PathVariable String tel){
            if(tel.length()!=11){
                return  new ResponseJson(false,"???????????????????????????11???");
            }
            Teacher teacher=new Teacher();
            teacher.setTel(tel);
            long count= teacherService.findTeacherCountByCondition(teacher);
            if(count>=1){
                String code= teacherService.getIdentifyingCode(tel);
                if (code!=""){
                    teacherService.clearCodeCache(tel);
                    teacherService.saveCodeToRadis(tel,code);
                    return  new ResponseJson(true,"?????????????????????");
                }
                return  new ResponseJson(false,"?????????????????????");

            }else {
                return  new ResponseJson(false,"???????????????");

            }

    }

    @GetMapping("/compareIdentifyingCode/{tel}/{code}/{newPassWord}")
    @ApiOperation(value = "?????????????????????????????????????????????", notes = "???????????????????????????")

    public ResponseJson compareIdentifyingCode(
            @ApiParam(value = "?????????")
/*            @Length(min=11,max=11,message = "???????????????????????????11???")*/
            @PathVariable String tel,
            @ApiParam(value = "?????????")
            @PathVariable String newPassWord,
            @ApiParam(value = "?????????")
/*            @Length(min=6,max=6,message = "????????????6???")*/
            @PathVariable String code){

        if(tel.length()!=11){
            return  new ResponseJson(false,"???????????????????????????11???");
        }
        if(code.length()!=6){
            return  new ResponseJson(false,"??????????????????6???");
        }
        String code1 = codeCache.get(tel);

        if(code1==null){
            return  new ResponseJson(false,"??????????????????");
        }

        if (code.equals(code1)){
            Teacher teacher=new Teacher();
            teacher.setTel(tel);

            //?????????????????????????????????????????????
            List<Teacher> teachers=teacherService.findTeacherListByCondition(teacher);
            if (Objects.isNull(teachers) || teachers.isEmpty()) {
                return  new ResponseJson(false,"??????????????????");
            }
            if(StringUtils.isNotBlank(newPassWord)){
                Teacher t = teacherService.findTeacherById(teachers.get(0).getId());
                String newPassword= DigestUtil.sha1Hex(newPassWord);
                t.setPassword(newPassword);
                teacherService.updateTeacherAdmin(t);
            }
            String token = setTeacherInfo(teacher, teachers.get(0));
            teacherService.clearCodeCache(tel);
            return  new ResponseJson(token,teachers.get(0));
        }
        return  new ResponseJson(false,"???????????????");
    }

    @PostMapping("/studentLogin")
    @ApiOperation(value = "??????????????????????????????????????????(padAccount)?????????(padPassword)", notes = "??????????????????")
    public ResponseJson studentLogin(@RequestParam("padAccount")String padAccount,@RequestParam("padPassword")String padPassword) throws Exception {
        String key=padAccount+Constant.Redis.EWB_STUDENT_LOGIN_ERROR;
        String value=LoginStudentErrorInfoCache.get(key);
        LoginErrorInfo errorInfo =null;
        StudentAccount sa = new StudentAccount();
        sa.setPadAccount(padAccount);
        sa.setPadPassword(padPassword);
        List<Student> students = studentService.findStudentLogin(sa);
        if(value!=null){
            errorInfo =JSONUtil.toBean(value,LoginErrorInfo.class);//?????????bean??????
        }
        if(errorInfo==null){
            errorInfo=new LoginErrorInfo();
            errorInfo.setNum(0);
            errorInfo.setOk(true);
            String errorInfoStr=JSONUtil.toJsonStr(errorInfo);
            studentService.saveLoginErroInfoToRedis(errorInfoStr,key);
        }
        if(errorInfo.isOk()){
            if(Objects.nonNull(students) && !students.isEmpty()){
                    String studentId = students.get(0).getId();
                    studentService.saveStudentToRedis(students.get(0));
                    String token = JwtUtil.createJWT(students.get(0).getId(), "{\"name\":\"student\"}", null, -1);
                    if(value!=null){
                        studentService.clearLoginErroInfoCache(key);
                    }
                    return new ResponseJson(token,studentService.getStudentLoginInfo(studentId));

            }
        }

        int num=errorInfo.getNum();
        if(num<5){
            errorInfo.setNum(num+1);
            if(num+1==5){
                errorInfo.setOk(false);
                errorInfo.setWrongTime(DateUtil.date());
            }
            String errorInfoStr=JSONUtil.toJsonStr(errorInfo);
            studentService.updateLoginErroInfoToRedis(errorInfoStr,key);
        }else {
            Date wrongDate=errorInfo.getWrongTime();
            Date nowDate=DateUtil.date();
            long diff=nowDate.getTime()-wrongDate.getTime();
            if(diff>Constant.Redis.EWB_STUDENT_LOGIN_ERROR_TIME*60*1000){
                if( students.size()>0){
                    studentService.saveStudentToRedis(students.get(0));
                    String token = JwtUtil.createJWT(students.get(0).getId(), "{\"name\":\"student\"}", null, -1);
                    if(value!=null){
                        teacherService.clearLoginErroInfoCache(key);
                    }
                    return new ResponseJson(token,studentService.getStudentLoginInfo(students.get(0).getId()));
                }else {
                    errorInfo.setNum(1);
                    errorInfo.setWrongTime(null);
                    errorInfo.setOk(true);
                    String errorInfoStr=JSONUtil.toJsonStr(errorInfo);
                    studentService.updateLoginErroInfoToRedis(errorInfoStr,key);
                }

            }else {
                return new ResponseJson(false,"??????????????????5???"+String.valueOf(Constant.Redis.EWB_STUDENT_LOGIN_ERROR_TIME)+"???????????????");
            }

        }


        return new ResponseJson(false,"???????????????????????????");
    }

    /**
     * ???????????????
     * @return
     */
    @PostMapping("/createQRCode")
    @ApiOperation(value = "???????????????", notes = "???????????????????????????")
    public ResponseJson createQRCode(){
        String qrCode=sequenceId.nextId();
        String encrypt = SimpleCryptoKit.encrypt(qrCode);
        qrCodeInfo.put(qrCode,qrCode);
        String qrCodeUrl=String.format("/login/loginByQRCode?qrCode=%1$s",encrypt);
        String encodeQrUrl=StringUtils.EMPTY;
        try {
            encodeQrUrl = QRCodeUtil.encodeNoLogoAndTitle(qrCodeUrl);
        } catch (Exception e) {
            Console.error("?????????????????????,{}",e.getMessage());
        }
        return  new ResponseJson(encodeQrUrl,encrypt,60);
    }

    /**
     * ????????????
     * @param tel
     * @param qrCode
     * @return
     */
    @PostMapping("/loginByQRcode")
    @ApiOperation(value = "????????????", notes = "??????????????????")
    public ResponseJson loginByQRCode(@RequestParam("tel")String tel, @RequestParam("qrCode")String qrCode){
        if(StringUtils.isNoneBlank(qrCode)){
          qrCode=SimpleCryptoKit.decrypt(qrCode);
        }
        if(StringUtils.isBlank(qrCodeInfo.get(qrCode))){
            return new ResponseJson(false,202,"???????????????");
        }
        if(StringUtils.isBlank(tel)){
            return new ResponseJson(false,203,"???????????????");
        }
        Teacher teacher = teacherService.findTeacherByTel(tel);
        if(Objects.nonNull(teacher)){
            String token = setTeacherInfo(teacher, teacher);
            //???????????????????????????
            TeacherClasses teacherClasses = new TeacherClasses();
            teacherClasses.setPager(new Pager("update_time",Pager.ASC));
            teacherClasses.setTeacherId(teacher.getId());
            teacherClasses.setSchoolId(teacher.getSchoolId());
            List<Map<String, Object>> maps = teacherClassesService.findTeacherClassPostCourseList(teacherClasses);
            teacher.setMaps(maps);
            teacher.setToken(token);
            qrCodeInfo.put(qrCode,JSONUtil.toJsonStr(teacher));
            return new ResponseJson(true,200,"????????????");
        }
        return new ResponseJson(false,204,"??????????????????");
    }

    /**
     * ???????????????????????????????????????????????????
     * @param qrCode
     * @return
     */
    @PostMapping("/loginPulling/{qrCode}")
    @ApiOperation(value = "????????????", notes = "??????????????????")
    public ResponseJson loginPulling(@PathVariable("qrCode")String qrCode){
        if(StringUtils.isNotBlank(qrCode)){
            qrCode=SimpleCryptoKit.decrypt(qrCode);
            String teacherInfo = qrCodeInfo.get(qrCode);
            if(StringUtils.isNotBlank(teacherInfo)){
                try{
                    Teacher teacher = JSONUtil.toBean(teacherInfo, Teacher.class);
                    if(Objects.nonNull(teacher)){
                        qrCodeInfo.remove(qrCode);
                        return new ResponseJson(teacher.getToken(),teacher,teacher.getMaps());
                    }else{
                        return new ResponseJson(false,201,"????????????");
                    }
                }catch (Exception ex){
                    return new ResponseJson(false,204,"?????????");
                }
            }
            return new ResponseJson(false,202,"???????????????");
        }
        return new ResponseJson(false,203,"????????????");
    }

    /**
     * ????????????
     * @param qrCode
     * @return
     */
    @PostMapping("/cancelQRLogin/{qrCode}")
    @ApiOperation(value = "????????????", notes = "??????????????????")
    public ResponseJson cancelQRLogin(@PathVariable("qrCode")String qrCode){
        if(StringUtils.isNotBlank(qrCode)){
            qrCodeInfo.remove(qrCode);
        }
        return new ResponseJson(true,"????????????");
    }





    private String setTeacherInfo(Teacher teacher, Teacher teacherCache) {
        teacher.setPermList(teacherService.findTeacherPerm4AppXq(teacherCache.getId()));
        teacherService.saveTeacherToRedis(teacherCache);
        teacher.setPassword(null);
        teacher.setSchool(null);
        return JwtUtil.createJWT(teacherCache.getId(), "{}", null, -1);
    }


}
