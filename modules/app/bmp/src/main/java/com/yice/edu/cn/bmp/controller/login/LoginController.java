package com.yice.edu.cn.bmp.controller.login;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CreateCache;
import com.yice.edu.cn.bmp.service.appPerm.AppPermService;
import com.yice.edu.cn.bmp.service.dy.MesAppletsTeacherService;
import com.yice.edu.cn.bmp.service.parent.ParentService;
import com.yice.edu.cn.bmp.service.perm.PermService;
import com.yice.edu.cn.bmp.service.student.StudentService;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.parent.LoginInfo;
import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import com.yice.edu.cn.common.pojo.jw.parent.ParentStudent;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.LoginObj;
import com.yice.edu.cn.common.pojo.jw.weixin.Jscode2session;
import com.yice.edu.cn.common.pojo.mes.classManage.mesAppletsTeacher.MesAppletsTeacher;
import com.yice.edu.cn.common.pojo.yedAdmin.AppPerm;
import com.yice.edu.cn.common.util.jwt.JwtUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private ParentService parentService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StudentService studentService;
    @Autowired
    private AppPermService appPermService;
    @Autowired
    private PermService permService;
    @Autowired
    private MesAppletsTeacherService mesAppletsTeacherService;

    @CreateCache(name=Constant.Redis.BMP_PARENT_KEY_LOGIN_ERROR,expire = 300)
    private Cache<String, String> loginErrorInfo;
    @CreateCache(name = Constant.Redis.BMP_VERIFICATION_CODE, expire = 180)
    private Cache<String, String> codeCache;

    @PostMapping("/login") //????????????
    public ResponseJson login(@ApiParam(value = "??????__:{tel:'*****',password:'*****'}")@RequestBody Parent parent)throws Exception{
        String tel=parent.getTel();
        String key=tel+Constant.Redis.BMP_PARENT_KEY_LOGIN_ERROR;
        String value=loginErrorInfo.get(key);
        LoginInfo loginInfo=new LoginInfo();
        if(value!=null){
            loginInfo= JSONUtil.toBean(value,LoginInfo.class);
        }
        if(loginInfo.isLogin()){
            Parent p=parentService.login(parent);
            if(p!=null){
                if(value!=null){
                    loginErrorInfo.remove(key);
                }
               return PrLogin(p);
            }
        }
        int num=loginInfo.getErrorNum();
        if(num<5){
            loginInfo.setErrorNum(num+1);
            if(num+1==5){
                loginInfo.setLogin(false);
                loginInfo.setErrorTime(DateUtil.date());
            }
            String errorInfoStr=JSONUtil.toJsonStr(loginInfo);
            loginErrorInfo.put(key,errorInfoStr);
        }else {
            Date errordate=loginInfo.getErrorTime();
            Date nowdate=DateUtil.date();
            long differ=nowdate.getTime()-errordate.getTime();
            if(differ>Constant.Redis.BMP_PARENT_LOGIN_ERROR_TIME*60*1000){
                Parent p=parentService.login(parent);
                if(p!=null){
                    if(value!=null){
                        loginErrorInfo.remove(key);
                    }
                   return PrLogin(p);
                }else {
                    loginInfo.setErrorNum(1);
                    loginInfo.setErrorTime(null);
                    loginInfo.setLogin(true);
                    String errorInfoStr=JSONUtil.toJsonStr(loginInfo);
                    loginErrorInfo.put(key,errorInfoStr);
                }
            }else {
                return  new ResponseJson(false,"????????????????????????"+String.valueOf(Constant.Redis.BMP_PARENT_LOGIN_ERROR_TIME)+"???????????????");
            }
        }
        return new ResponseJson(false,"???????????????????????????");
    }
    @GetMapping("/getCheckTel/{tel}") //?????????????????????????????????????????????
    @ApiOperation(value = "???????????????????????????????????????", notes = "????????????")
    public ResponseJson getCheckTel(@ApiParam(value = "?????????")@Length(min=11,max=11,message = "???????????????????????????11???") @PathVariable String tel){
        Parent parent=new Parent();
        parent.setTel(tel);
        List<Parent>list=parentService.findParentListByCondition(parent);
        if(list.size()==0){
            return new ResponseJson(false,"??????????????????????????????");
        }else {
            String code=parentService.getVerificationCode(tel);
            if(!code.equals("")){
                return  new ResponseJson(true,"?????????????????????");
            }else{
                return  new ResponseJson(true,"?????????????????????");
            }
        }

    }

   @GetMapping("/validationTel/{tel}/{code}")  //???????????????????????????????????????
    @ApiOperation(value = "????????????????????????", notes = "????????????")
    public ResponseJson validationTel(@ApiParam(value = "?????????") @Length(min=11,max=11,message = "???????????????????????????11???") @PathVariable String tel,  @ApiParam(value = "?????????")
     @Length(min=6,max=6,message = "????????????6???") @PathVariable String code){
            boolean verifyFlag = parentService.compareIdentifyingCode(tel,code);
            if(verifyFlag){
                Parent parent=new Parent();
                parent.setTel(tel);

                List<Parent>list=parentService.findParentListByCondition(parent);
                if(list.get(0).getStudentId()!=null) {
                    Student student = studentService.findStudentById(list.get(0).getStudentId());
                    if(student==null){
                        return new ResponseJson(false,"??????????????????");
                    }
                }
                String token = JwtUtil.createJWT(list.get(0).getId(), "{}", null, -1);
                parentService.saveParenToCache(list.get(0));
                parentService.saveTokenToCache(list.get(0).getId() + Constant.Redis.BMP_TOKEN_SUFFIX, token);
                codeCache.remove(tel);
                return new ResponseJson(token);
            }else{
                return  new ResponseJson(false,"???????????????");
            }
    }

    @PostMapping("/validationTelForLogin")  //???????????????(????????????)
    @ApiOperation(value = "????????????????????????", notes = "????????????")
    public ResponseJson validationTelForLogin(@ApiParam(value = "????????????") @RequestBody Parent parent1){
        boolean verifyFlag = parentService.compareIdentifyingCode(parent1.getTel(),parent1.getCode());
        if(verifyFlag){
            codeCache.remove(parent1.getTel());
            Parent parent=new Parent();
            parent.setTel(parent1.getTel());

            List<Parent>list=parentService.findParentListByCondition(parent);
            Parent p=list.get(0);
            return PrLogin(p);
        }else{
            return  new ResponseJson(false,"???????????????");
        }
    }



    /**
     * ?????????????????????
     * 1???????????????????????????????????????????????????openId?????????????????????????????????
     * 2???openId?????? ???????????????????????????????????????????????????
     *
     * @param loginObj
     * @return
     * @throws Exception
     */
    @PostMapping("/wxLogin")
    @ApiOperation(value = "??????????????????????????????(tel)?????????(password)", notes = "??????????????????")
    public ResponseJson wxLogin(@RequestBody LoginObj loginObj)throws Exception{
        ResponseJson result=new ResponseJson(false,"??????????????????");
        Parent parent=new Parent();
        if(StringUtils.isNotEmpty(loginObj.getTel())&&StringUtils.isNotEmpty(loginObj.getPassword())){
            parent.setTel(loginObj.getTel());
            parent.setPassword(loginObj.getPassword());
            parent.setAppPermType(loginObj.getAppPermType());
            result=this.login(parent);
            if(result.getResult().isSuccess()){
                if (StringUtils.isNotEmpty(loginObj.getOpenId())) {
                    parent= (Parent) result.getData2();
                    parent.setOpenId(loginObj.getOpenId());
                    parentService.bindOpenIdParent(parent);
                }
            }
            return result;
        }
        if (StringUtils.isNotEmpty(loginObj.getTel()) && StringUtils.isNotEmpty(loginObj.getCode())) {
            //??????????????????????????? ???????????? ????????????openId
            Parent parent1=new Parent();
            parent1.setTel(loginObj.getTel());
            parent1.setCode(loginObj.getCode());
            parent1.setAppPermType(loginObj.getAppPermType());
            result = this.validationTelForLogin(parent1);
            if (result.getResult().isSuccess()) {
                //???????????? ????????????openId
                if (StringUtils.isNotEmpty(loginObj.getOpenId())) {
                    parent= (Parent) result.getData2();
                    parent.setOpenId(loginObj.getOpenId());
                    parentService.bindOpenIdParent(parent);
                }
            }
            return result;
        }
        if(StringUtils.isNotEmpty(loginObj.getOpenId())&&StringUtils.isEmpty(loginObj.getTel())){
               parent.setOpenId(loginObj.getOpenId());
               List<Parent>parentList=parentService.findParentListByCondition(parent);
               if(parentList !=null&&parentList.size()>0){
                   parent=parentList.get(0);
                   if(parent.getSchoolId()!=null){
                       parentService.saveParenToCache(parent);
                       String token = JwtUtil.createJWT(parent.getId(), "{}", null, -1);
                       parentService.saveTokenToCache(parent.getId() + Constant.Redis.BMP_TOKEN_SUFFIX, token);
                       parent.setPassword(null);
                       return new ResponseJson(token, parent);
                   }else {
                       return new ResponseJson(true,901,parent.getOpenId());
                   }

               }
        }
        return result;
    }


    /**
     * ?????????????????????
     * ??????openId
     *
     * @param jscode2session
     * @return
     * @throws Exception
     */
    @PostMapping("/jsCode2session")
    @ApiOperation(value = "???????????????openId????????????????????????????????????????????????token??????????????????????????????code901???msg??????openId", notes = "????????????")
    public ResponseJson jsCode2session(@RequestBody Jscode2session jscode2session)throws Exception{
        String openId = parentService.jsCode2Session(jscode2session);
        LoginObj loginObj = new LoginObj();
        loginObj.setOpenId(openId);
        ResponseJson rj = this.wxLogin(loginObj);
        if(rj.getResult().isSuccess()){
            //???????????? openId???data2????????????????????????
            return rj;
        }else {
            //??????????????????????????? ??????openId
            return new ResponseJson(true,901,openId);
        }
    }

    @PostMapping("/getAppPerm")
    @ApiOperation(value = "??????????????????????????????", notes = "??????APP????????????")
    public ResponseJson getAppPerm(@RequestBody LoginObj loginObj){
        AppPerm appPerm=new AppPerm();
        appPerm.setSchoolId(loginObj.getSchoolId());
        appPerm.setWhatApp(loginObj.getAppPermType());
        List<AppPerm> appPermList = appPermService.findAppPermListTreeByClass(appPerm);
        return new ResponseJson(appPermList);
    }

    private ResponseJson PrLogin(Parent p){
        Parent parent=new Parent();
        ParentStudent parentStudent1=new ParentStudent();
        parentStudent1.setParentId(p.getId());
        List<ParentStudent> parentStudentList1 = parentService.findSchoolByParentId(parentStudent1);
        ResponseJson responseJson1=new ResponseJson();
        boolean flag=false;
        if(p.getStudentId()!=null){
            Student studentById = studentService.findStudentById(p.getStudentId());
            if(studentById==null){
                if(parentStudentList1.size()>0){
                    for (ParentStudent s : parentStudentList1) {
                        //?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                        responseJson1 = appPermService.findSchoolExpireOrSchoolYear(s.getSchoolId());
                        if (responseJson1.getResult().isSuccess()) {
                            parent.setId(s.getParentId());
                            parent.setStudentId(s.getStudentId());
                            parent.setSchoolId(s.getSchoolId());
                            parentService.updateParent(parent);
                            flag=false;
                            break;
                        }else {
                            flag=true;
                        }
                    }
                    if(flag){
                        return responseJson1;
                    }
                }else{
                    parent.setId(p.getId());
                    parent.setStudentId(null);
                    parent.setSchoolId(null);
                    parentService.updateParent(parent);
                }
            }
        }
        Parent parentById = parentService.findParentById(p.getId());
        p=parentById;
        if(p.getStudentId()!=null) {
            Student student = studentService.findStudentById(p.getStudentId());
            if(student!=null){
                p.setRegisterStatus(student.getRegisterStatus());
            }
        }
        parentService.saveParenToCache(p);
        String token = JwtUtil.createJWT(p.getId(), "{}", null, -1);
        parentService.saveTokenToCache(p.getId() + Constant.Redis.BMP_TOKEN_SUFFIX, token);
        p.setPassword(null);
        return new ResponseJson(token,p);
    }

    /**
     * ?????????????????????????????????
     * 1???????????????????????????????????????????????????openId????????????????????????mes_applets_teacher???
     * 2???openId?????? ???????????????????????????????????????????????????
     *
     * @param loginObj
     * @return
     * @throws Exception
     */
    @PostMapping("/mesAppletsWxLogin")
    @ApiOperation(value = "??????????????????????????????(tel)?????????(password)", notes = "??????????????????")
    public ResponseJson mesAppletsWxLogin(@RequestBody LoginObj loginObj) throws Exception {
        ResponseJson result = new ResponseJson(false, "??????????????????");
        Parent parent = new Parent();
        if (StringUtils.isNotEmpty(loginObj.getTel()) && StringUtils.isNotEmpty(loginObj.getPassword())) {
            parent.setPassword(loginObj.getPassword());
            parent.setTel(loginObj.getTel());
            parent.setAppPermType(loginObj.getAppPermType());
            result = this.login(parent);
            return getResponseJson(result, loginObj);
        }
        if (StringUtils.isNotEmpty(loginObj.getTel()) && StringUtils.isNotEmpty(loginObj.getCode())) {
            //??????????????????????????? ????????????
            Parent parent1 = new Parent();
            parent1.setCode(loginObj.getCode());
            parent1.setTel(loginObj.getTel());
            parent1.setAppPermType(loginObj.getAppPermType());
            result = this.validationTelForLogin(parent1);
            return getResponseJson(result, loginObj);
        }
        if (StringUtils.isNotEmpty(loginObj.getOpenId()) && StringUtils.isEmpty(loginObj.getTel())) {
            JSONObject jsonObject = loginObj.getJscode2sessionObject();
            String sessionKey = jsonObject.get("session_key").toString();//????????????????????????jsCode2Session???????????????????????????????????????????????????????????????
            //??????openId??????????????? openId??????
            MesAppletsTeacher model = new MesAppletsTeacher();
            model.setOpenId(loginObj.getOpenId());
            model.setType(2);//?????????
            List<MesAppletsTeacher> appletsTeacherList = mesAppletsTeacherService.findMesAppletsTeacherListByCondition(model);
            if (CollectionUtil.isNotEmpty(appletsTeacherList)) {
                Parent p1 = parentService.findParentById(appletsTeacherList.get(0).getObjectId());
                if (p1 != null) {
                    if (p1.getSchoolId() != null) {
                        String token = parentService.findTokenByKey(p1.getId() + Constant.Redis.BMP_TOKEN_SUFFIX);
                        parentService.saveParenToCache(p1);
                        if (StringUtils.isEmpty(token)){
                            token = JwtUtil.createJWT(p1.getId(), "{}", null, -1);
                            //parentService.deleteTokenToCache(p1.getId() + Constant.Redis.BMP_TOKEN_SUFFIX);//??????????????????????????????????????????
                            parentService.saveTokenToCache(p1.getId() + Constant.Redis.BMP_TOKEN_SUFFIX, token);
                        }
                        parent.setPassword(null);
                        return new ResponseJson(token, p1);
                    } else {
                        return new ResponseJson(true, 901, p1.getOpenId());
                    }
                }
            }
        }
        return result;
    }

    private ResponseJson getResponseJson(ResponseJson result, LoginObj loginObj) {
        if (result.getResult().isSuccess() && StringUtils.isNotEmpty(loginObj.getOpenId())) {
            Parent p = (Parent) result.getData2();
            p.setOpenId(loginObj.getOpenId());
            //???parentId???openId ??????mes_applets_teacher
            MesAppletsTeacher mesAppletsTeacher = new MesAppletsTeacher();
            mesAppletsTeacher.setObjectId(p.getId());
            mesAppletsTeacher.setType(2);//?????????
            mesAppletsTeacher.setSchoolId(p.getSchoolId());
            List<MesAppletsTeacher> lists = mesAppletsTeacherService.findMesAppletsTeacherListByCondition(mesAppletsTeacher);
            if (CollectionUtil.isEmpty(lists)) {
                mesAppletsTeacher.setOpenId(p.getOpenId());
                mesAppletsTeacherService.saveMesAppletsTeacher(mesAppletsTeacher);
            }else {
                if (StringUtils.isEmpty(lists.get(0).getOpenId()) || !lists.get(0).getOpenId().equals(loginObj.getOpenId())){
                    lists.get(0).setOpenId(loginObj.getOpenId());
                    mesAppletsTeacherService.updateMesAppletsTeacher(lists.get(0));
                }
            }
        }
        return result;
    }

    /**
     * ?????????????????????
     * ??????openId
     *
     * @param jscode2session
     * @return
     * @throws Exception
     */
    @PostMapping("/code2session")
    @ApiOperation(value = "???????????????openId????????????????????????????????????????????????token??????????????????????????????code901???msg??????openId", notes = "????????????")
    public ResponseJson code2session(@RequestBody Jscode2session jscode2session) throws Exception {
        jscode2session.setNeedAll(true);
        String content = parentService.jsCode2Session(jscode2session);
        JSONObject jsonObject = JSONUtil.parseObj(content);
        String openId = jsonObject.get("openid").toString();
        LoginObj loginObj = new LoginObj();
        loginObj.setOpenId(openId);
        loginObj.setJscode2sessionObject(jsonObject);
        ResponseJson rj = this.mesAppletsWxLogin(loginObj);
        if (rj.getResult().isSuccess()) {
            if (rj.getResult()!=null &&rj.getResult().getCode()!=null && rj.getResult().getCode()==901){
                rj.getResult().setMsg(openId);
            }else {
                Parent par=(Parent)rj.getData2();
                par.setOpenId(openId);
            }
            return rj;
        } else {
            //??????????????????????????? ??????openId
            return new ResponseJson(true, 901, openId);
        }
    }

    /**
     * ????????????
     */
    @PostMapping("/logout")
    @ApiOperation(value = "???????????????????????????openId")
    public ResponseJson logout(@RequestBody LoginObj loginObj) throws Exception {
        if (loginObj != null && StringUtils.isNotBlank(loginObj.getOpenId())) {
            MesAppletsTeacher mesAppletsTeacher = new MesAppletsTeacher();
            mesAppletsTeacher.setOpenId(loginObj.getOpenId());
            mesAppletsTeacher.setType(2); //?????????
            mesAppletsTeacherService.deleteMesAppletsTeacherByCondition(mesAppletsTeacher);
            return new ResponseJson();
        }
        return new ResponseJson(false, "??????????????????");
    }

}
