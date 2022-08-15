package com.yice.edu.cn.osp.controller.xw.visitorManage;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.school.School;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.visitorManage.Visitor;
import com.yice.edu.cn.common.util.crypto.SimpleCryptoKit;
import com.yice.edu.cn.common.util.qrcode.QRCodeUtil;
import com.yice.edu.cn.osp.service.dm.school.SchoolService;
import com.yice.edu.cn.osp.service.jw.teacher.TeacherService;
import com.yice.edu.cn.osp.service.xw.visitorManage.VisitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.codec.digest.Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.security.CryptoPrimitive;
import java.util.List;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@RestController
@RequestMapping("/visitor")
@Api(value = "/visitor", description = "模块")
public class VisitorController {
    @Autowired
    private VisitorService visitorService;
    @Autowired
    private SchoolService schoolService;

    @Value("${qrcode.url}")
    private String url;

    @PostMapping("/update/creatQRCode")
    @ApiOperation(value = "二维码生成 schoolId加密")
    public ResponseJson creatQRCode(
            @ApiParam(value = "对象", required = true)
            @RequestBody Visitor visitor) {
        School school = schoolService.findSchoolById(mySchoolId());
        String name1 = "访客二维码";
        String name2 = school.getName();
        String schoolId=SimpleCryptoKit.encrypt(mySchoolId());
        byte[] encode = new byte[0];
        if (visitor.getVisitorWay().equals("0")) {
            String text = url + "/visitorApplication/1/" + schoolId;
            try {
                encode = QRCodeUtil.visitorEncode(text, null, name1, name2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (visitor.getVisitorWay().equals("1")) {
            String text = url + "/visitorApplication/2/" + schoolId;
            try {
                encode = QRCodeUtil.visitorEncode(text, null, name1, name2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ResponseJson(encode);
    }

    @PostMapping("/saveVisitor")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response = Visitor.class)
    public ResponseJson saveVisitor(
            @ApiParam(value = "对象", required = true)
            @RequestBody Visitor visitor) {
        visitor.setSchoolId(mySchoolId());
        Visitor s = visitorService.saveVisitor(visitor);
        return new ResponseJson(s);
    }

    @GetMapping("/update/findVisitorById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response = Visitor.class)
    public ResponseJson findVisitorById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id) {
        Visitor visitor = visitorService.findVisitorById(id);
        return new ResponseJson(visitor);
    }

    @PostMapping("/update/updateVisitor")
    @ApiOperation(value = "修改对象", notes = "返回响应对象")
    public ResponseJson updateVisitor(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody Visitor visitor) {
        visitorService.updateVisitor(visitor);
        return new ResponseJson();
    }

    @GetMapping("/look/lookVisitorById/{id}")
    @ApiOperation(value = "去查看页面,通过id查找", notes = "返回响应对象", response = Visitor.class)
    public ResponseJson lookVisitorById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        Visitor visitor = visitorService.findVisitorById(id);
        return new ResponseJson(visitor);
    }

    @PostMapping("/findVisitorsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response = Visitor.class)
    public ResponseJson findVisitorsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Visitor visitor) {
        visitor.setSchoolId(mySchoolId());
        List<Visitor> data = visitorService.findVisitorListByCondition(visitor);
        long count = visitorService.findVisitorCountByCondition(visitor);
        return new ResponseJson(data, count);
    }

    @PostMapping("/findOneVisitorByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response = Visitor.class)
    public ResponseJson findOneVisitorByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Visitor visitor) {
        Visitor one = visitorService.findOneVisitorByCondition(visitor);
        return new ResponseJson(one);
    }

    @GetMapping("/deleteVisitor/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteVisitor(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id) {
        visitorService.deleteVisitor(id);
        return new ResponseJson();
    }


    @PostMapping("/findVisitorListByCondition")
    @ApiOperation(value = "根据条件查找中移刷脸列表", notes = "返回响应对象,不包含总条数", response = Visitor.class)
    public ResponseJson findVisitorListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Visitor visitor) {
        Visitor vv = new Visitor();
        vv.setSchoolId(mySchoolId());
        vv.setVisitorWay("0");
        List<Visitor> data = visitorService.findVisitorListByCondition(vv);
        if (data.size() > 0) {
            for (Visitor v : data) {
                if (DateUtil.parse(DateUtil.now()).isAfterOrEquals(DateUtil.parse(v.getEndTime())) && v.getApplyStatus().equals("2")) {
                    v.setApplyStatus("3");
                    visitorService.updateVisitor(v);
                }
            }
        }
        visitor.getPager().setLike("auditor");
        visitor.getPager().setSortField("createTime");
        visitor.getPager().setSortOrder(Pager.DESC);
        visitor.setSchoolId(mySchoolId());
        visitor.setVisitorWay("0");
        List<Visitor> list = visitorService.findVisitorListByCondition(visitor);
        long count = visitorService.findVisitorCountByCondition(visitor);
        return new ResponseJson(list, count);
    }

    @PostMapping("/findVisitorListByCondition4")
    @ApiOperation(value = "根据条件查找中移人证列表", notes = "返回响应对象,不包含总条数", response = Visitor.class)
    public ResponseJson findVisitorListByCondition4(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody Visitor visitor) {
        Visitor vv=new Visitor();
        vv.setSchoolId(mySchoolId());
        vv.setVisitorWay("1");
        List<Visitor> data = visitorService.findVisitorListByCondition(vv);
        if (data.size() > 0) {
            for (Visitor v : data) {
                if (DateUtil.parse(DateUtil.now()).isAfterOrEquals(DateUtil.parse(v.getEndTime())) && v.getApplyStatus().equals("2")) {
                    v.setApplyStatus("3");
                    visitorService.updateVisitor(v);
                }
            }
        }
        visitor.getPager().setLike("auditor");
        visitor.getPager().setSortField("createTime");
        visitor.getPager().setSortOrder(Pager.DESC);
        visitor.setSchoolId(mySchoolId());
        visitor.setVisitorWay("1");
        List<Visitor> list = visitorService.findVisitorListByCondition(visitor);
        long count = visitorService.findVisitorCountByCondition(visitor);
        return new ResponseJson(list, count);
    }

}
