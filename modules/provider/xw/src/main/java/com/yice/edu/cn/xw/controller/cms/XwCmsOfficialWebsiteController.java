package com.yice.edu.cn.xw.controller.cms;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.jw.SchoolDateCenter.SchoolDateCenter;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsColumn;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsContent;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsHeaderNavigation;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsStyleConfig;
import com.yice.edu.cn.common.util.jmessage.utils.StringUtils;
import com.yice.edu.cn.xw.service.cms.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/xwCmsOfficialWebsite")
public class XwCmsOfficialWebsiteController {

    @Autowired
    private XwCmsHomeLayoutService cmsHomeLayoutService;
    @Autowired
    private XwCmsHeaderNavigationService xwCmsHeaderNavigationService;
    @Autowired
    private XwCmsContentService xwCmsContentService;
    @Autowired
    private XwCmsStyleConfigService xwCmsStyleConfigService;
    @Autowired
    private XwCmsColumnService xwCmsColumnService;

    @Autowired
    private XwCmsSchoolSpaceConfigService xwCmsSchoolSpaceConfigService;


    @PostMapping("/findAllCmsHomeLayout/{schoolId}")
    @ApiOperation(value = "查询首页布局", notes = "返回校务CMS首页布局列表")
    public ResponseJson findAllCmsHomeLayout(
            @ApiParam(value = "学校id", required = true)
            @PathVariable("schoolId") String schoolId
    ){
        List<Map<String,Object>> rows = cmsHomeLayoutService.findAllCmsHomeLayout(schoolId);
        return new ResponseJson(rows);
    }

    @PostMapping("/findXwCmsHeaderNavigationsByCondition")
    @ApiOperation(value = "根据条件查找校务CMS头部导航表", notes = "返回响应对象", response= XwCmsHeaderNavigation.class)
    public ResponseJson findXwCmsHeaderNavigationsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwCmsHeaderNavigation xwCmsHeaderNavigation){
        List<XwCmsHeaderNavigation> data=xwCmsHeaderNavigationService.findXwCmsHeaderNavigationListByCondition(xwCmsHeaderNavigation);
        long count=xwCmsHeaderNavigationService.findXwCmsHeaderNavigationCountByCondition(xwCmsHeaderNavigation);
        return new ResponseJson(data,count);
    }
    @PostMapping("/findXwCmsColumnsByCondition")
    @ApiOperation(value = "根据条件查找校务CMS栏目表", notes = "返回响应对象", response= XwCmsColumn.class)
    public ResponseJson findXwCmsColumnsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwCmsColumn xwCmsColumn){
        List<XwCmsColumn> data=xwCmsColumnService.findXwCmsColumnListByCondition(xwCmsColumn,true);
        long count=xwCmsColumnService.findXwCmsColumnCountByCondition(xwCmsColumn);
        return new ResponseJson(data,count);
    }

    @PostMapping("/findXwCmsContentsByCondition")
    @ApiOperation(value = "根据条件查找内容信息表(给前台使用)", notes = "返回响应对象", response = XwCmsContent.class)
    public ResponseJson findXwCmsContentsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwCmsContent xwCmsContent) {
        List<XwCmsContent> data = xwCmsContentService.findXwCmsContentListByCondition(xwCmsContent);
        long count = xwCmsContentService.findXwCmsContentCountByCondition(xwCmsContent);
        return new ResponseJson(data, count);
    }

    @PostMapping("/look/lookXwCmsContentById/{id}")
    @ApiOperation(value = "通过id查找内容信息表(给前台使用)", notes = "返回响应对象", response = XwCmsContent.class)
    public ResponseJson lookXwCmsContentById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        XwCmsContent xwCmsContent = xwCmsContentService.findXwCmsContentById(id);
        return new ResponseJson(xwCmsContent);
    }

    @GetMapping("/findXwCmsStyleConfigByDomain/{schoolId}")
    @ApiOperation(value = "根据二级域名获取官网配置信息", notes = "返回响应对象")
    public XwCmsStyleConfig findXwCmsStyleConfigByDomain(@PathVariable("schoolId")String schoolId ) {
        XwCmsStyleConfig xwCmsStyleConfig = new XwCmsStyleConfig();
        xwCmsStyleConfig.setSchoolId(schoolId);
        return xwCmsStyleConfigService.findXwCmsStyleConfigByDomain(xwCmsStyleConfig);
    }

    @GetMapping("/findDomainStatus/{secondDomain}")
    @ApiOperation(value = "查询二级域名状态", notes = "返回当前二级域名状态")
    public ResponseJson findDomainStatus(@PathVariable(value = "secondDomain")String secondDomain){
        String schoolIdBySecondDomain = xwCmsSchoolSpaceConfigService.findSchoolIdBySecondDomain(secondDomain);
        if(StringUtils.isNotEmpty(schoolIdBySecondDomain)){
            return new ResponseJson(true,200,"域名正常打开");
        }
        return new ResponseJson(false,404,"域名不存在或者被关闭");
    }


    @GetMapping("findSchoolCompusCenterBySchoolId/{schoolId}")
    @ApiOperation(value = "福州大数据接口(给前台使用)", notes = "返回响应对象")
    public ResponseJson findSchoolCompusCenterBySchoolId(@PathVariable("schoolId")String schoolId){
        SchoolDateCenter schoolCompusCenterBySchoolId = xwCmsSchoolSpaceConfigService.findSchoolCompusCenterBySchoolId(schoolId);
        return new ResponseJson(schoolCompusCenterBySchoolId);
    }

    @GetMapping("/findSchoolIdBySecondDomain/{secondDomain}")
    @ApiOperation(value = "根据域名查询学校ID(给前台使用)", notes = "返回响应对象")
    public String  findSchoolIdBySecondDomain(@PathVariable("secondDomain") String secondDomain){
       return xwCmsSchoolSpaceConfigService.findSchoolIdBySecondDomain(secondDomain);
    }

}
