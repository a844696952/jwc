package com.yice.edu.cn.tap.controller.dy.schoolManage;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.tap.interceptor.LoginInterceptor;
import com.yice.edu.cn.tap.service.dy.schoolManage.DyIndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author xiezhi
 */
@RestController
@RequestMapping("/dyIndex")
@Api(value ="/dyIndex",description = "常规检查进入主页")
public class DyIndexController {


    private final DyIndexService dyIndexService;

    @Autowired
    public DyIndexController(DyIndexService dyIndexService) {
        this.dyIndexService = dyIndexService;
    }


    @GetMapping("/entrance")
    @ApiOperation(value = "进入icon方法，判断相应权限", notes = "根据不同角色处理返回不同内容")
    public ResponseJson entrance(){
        Teacher teacher = LoginInterceptor.currentTeacher();
        if(Objects.isNull(teacher)){
            return new ResponseJson(false,"登录用户出错");
        }
        return dyIndexService.entrance(teacher);
    }
}
