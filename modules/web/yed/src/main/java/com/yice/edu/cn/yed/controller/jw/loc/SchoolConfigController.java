package com.yice.edu.cn.yed.controller.jw.loc;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.loc.SchoolConfig;
import com.yice.edu.cn.yed.service.jw.loc.SchoolConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

@RestController
@RequestMapping("/schoolConfig")
@Api(value = "/schoolConfig",description = "模块")
public class SchoolConfigController {
    @Autowired
    private SchoolConfigService schoolConfigService;
    @PostMapping("/saveSchoolConfig")
    @ApiOperation(value = "保存对象", notes = "返回保存好的对象", response=SchoolConfig.class)
    public ResponseJson saveSchoolConfig(
            @ApiParam(value = "对象", required = true)
            @RequestBody SchoolConfig schoolConfig){
        schoolConfigService.saveSchoolConfigKong(schoolConfig);
        return new ResponseJson();
    }

    @GetMapping("/findSchoolConfigById/{id}")
    @ApiOperation(value = "去更新页面,通过id查找", notes = "返回响应对象", response=SchoolConfig.class)
    public ResponseJson findSchoolConfigById(
            @ApiParam(value = "去更新页面,需要用到的id", required = true)
            @PathVariable String id){
        SchoolConfig schoolConfig=schoolConfigService.findSchoolConfigById(id);
        return new ResponseJson(schoolConfig);
    }

    @PostMapping("/updateSchoolConfig")
    @ApiOperation(value = "修改对象非空字段", notes = "返回响应对象")
    public ResponseJson updateSchoolConfig(
            @ApiParam(value = "被修改的对象,对象属性不为空则修改", required = true)
            @RequestBody SchoolConfig schoolConfig){
        schoolConfigService.updateSchoolConfig(schoolConfig);
        return new ResponseJson();
    }

    @PostMapping("/updateSchoolConfigForAll")
    @ApiOperation(value = "修改对象所有字段", notes = "返回响应对象")
    public ResponseJson updateSchoolConfigForAll(
            @ApiParam(value = "被修改的对象", required = true)
            @RequestBody SchoolConfig schoolConfig){
        schoolConfigService.updateSchoolConfigForAll(schoolConfig);
        return new ResponseJson();
    }


    @PostMapping("/findSchoolConfigsByCondition")
    @ApiOperation(value = "根据条件查找", notes = "返回响应对象", response=SchoolConfig.class)
    public ResponseJson findSchoolConfigsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolConfig schoolConfig){
        List<SchoolConfig> data=schoolConfigService.findSchoolConfigListByCondition(schoolConfig);
        long count=schoolConfigService.findSchoolConfigCountByCondition(schoolConfig);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findOneSchoolConfigByCondition")
    @ApiOperation(value = "根据条件查找单个,结果必须为单条数据", notes = "没有时返回空", response=SchoolConfig.class)
    public ResponseJson findOneSchoolConfigByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @RequestBody SchoolConfig schoolConfig){
        SchoolConfig one=schoolConfigService.findOneSchoolConfigByCondition(schoolConfig);
        return new ResponseJson(one);
    }
    @GetMapping("/deleteSchoolConfig/{id}")
    @ApiOperation(value = "根据id删除", notes = "返回响应对象")
    public ResponseJson deleteSchoolConfig(
            @ApiParam(value = "被删除记录的id", required = true)
            @PathVariable String id){
        schoolConfigService.deleteSchoolConfig(id);
        return new ResponseJson();
    }


    @PostMapping("/findSchoolConfigListByCondition")
    @ApiOperation(value = "根据条件查找列表", notes = "返回响应对象,不包含总条数", response=SchoolConfig.class)
    public ResponseJson findSchoolConfigListByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody SchoolConfig schoolConfig){
        List<SchoolConfig> data=schoolConfigService.findSchoolConfigListByCondition(schoolConfig);
        return new ResponseJson(data);
    }


    @GetMapping("/getProperties/{id}")
    public void getProperties(@PathVariable("id")String id, HttpServletResponse response){
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=application-dev.properties");
        OutputStream outputStream = null;
        try{
            SchoolConfig schoolConfig = schoolConfigService.findSchoolConfigById(id);
            Map<String,Object> map = new HashMap<>();
            if (schoolConfig==null){
                map.put("提示","请先配置学校信息");
            }else {
                map.put("spring.redis.host",schoolConfig.getRedisHost());
                map.put("spring.redis.password",schoolConfig.getRedisPassword());
                map.put("spring.redis.port",schoolConfig.getRedisPort());
                map.put("spring.redis.database",schoolConfig.getRedisDatabase());
                map.put("spring.datasource.url",Constant.SCHOOL_CONFIG.MYSQL_PRE+schoolConfig.getMysqlUrl()+Constant.SCHOOL_CONFIG.MSQL_SUFFIX);
                map.put("spring.datasource.username",schoolConfig.getMysqlUserName());
                map.put("spring.datasource.password",schoolConfig.getMysqlPassWord());
                map.put("spring.datasource.driver-class-name", Constant.SCHOOL_CONFIG.MYSQL_CLASS_NAME);
                map.put("spring.data.mongodb.uri",schoolConfig.getMongodbUrl());
                map.put("api.loginUrl",schoolConfig.getApiLoginUrl());
                map.put("api.schoolUrl",schoolConfig.getApiSchoolUrl());
                map.put("logging.pattern.file",Constant.SCHOOL_CONFIG.LOGGING_PATTERN_FILE);
                map.put("logging.level.cn.ycjd.loc.dao",Constant.SCHOOL_CONFIG.LOGGING_LEVEL_CN_YCJD_LOCDAO);
                map.put("logging.level.org.springframework.web",Constant.SCHOOL_CONFIG.LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_WEB);
                map.put("res.path",schoolConfig.getResPath());
                map.put("school.id",schoolConfig.getId());
                map.put("wiris.server",schoolConfig.getWirisServer());
                map.put("application.host",schoolConfig.getApplicationHost());
                map.put("jetcache.remote.default.uri","redis://:"+schoolConfig.getRedisPassword()+"@"+schoolConfig.getRedisHost()+":"+schoolConfig.getRedisPort()+"/"+schoolConfig.getRedisDatabase());
                map.put("api.updateSchoolUrl",schoolConfig.getApiUpdateSchoolUrl());
            }
            outputStream = response.getOutputStream();
            Set<String> strings = map.keySet();
            byte[] bytes = null;
            for (String s:strings){
                bytes = (s+"="+map.get(s)).getBytes();
                ((ServletOutputStream) outputStream).println();
                outputStream.write(bytes,0,bytes.length);
            }
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try {
                if(outputStream!=null){
                    outputStream.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }


    }

}
