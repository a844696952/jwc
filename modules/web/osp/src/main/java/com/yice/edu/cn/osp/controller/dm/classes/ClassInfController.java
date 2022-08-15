package com.yice.edu.cn.osp.controller.dm.classes;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.classes.DmClassDesc;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.osp.interceptor.LoginInterceptor;
import com.yice.edu.cn.osp.service.dm.classes.DmClassDescService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.yice.edu.cn.osp.service.jw.classes.JwClassesService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.currentTeacher;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/classInf")
@Api(value = "/classInf", description = "班级信息管理")
public class ClassInfController {
    @Autowired
    private JwClassesService jwClassesService;
    @Autowired
    private DmClassDescService dmClassDescService;
    @PostMapping("/findJwClassessByCondition")
    @ApiOperation(value = "根据条件查找班级信息", notes = "返回响应对象")
    public ResponseJson findJwClassessByCondition(@ApiParam(value = "属性不为空则作为条件查询") @RequestBody JwClasses jwClasses) {
        jwClasses.setSchoolId(mySchoolId());

        Teacher teacher = currentTeacher();
        //管理员和非管理员查出的结果集不同
        //非管理员查出教师所管辖的班牌
        List<JwClasses> data = null;
        jwClasses.setSchoolId(LoginInterceptor.mySchoolId());
        long count = 0;
        if(Constant.STATUS.TEACHER_ADMIN.equals(teacher.getStatus())){//说明是管理员,直接过去
            //设置 HeadTeacher 为null. 会查询所有班级
            jwClasses.setHeadTeacher(null);
            data = dmClassDescService.findDmClassesListByCardCondition(jwClasses);
            count = dmClassDescService.findDmClassesCountByCardCondition(jwClasses);
        }else{
            //设置 HeadTeacher班主任. 会查询指定管理员管理的班级
            jwClasses.setHeadTeacher(teacher.getId());
            data = dmClassDescService.findDmClassesListByCardCondition(jwClasses);
            count = dmClassDescService.findDmClassesCountByCardCondition(jwClasses);
        }
        return new ResponseJson(data,count);
    }
}
