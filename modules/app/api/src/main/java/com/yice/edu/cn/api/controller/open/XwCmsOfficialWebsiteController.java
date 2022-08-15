package com.yice.edu.cn.api.controller.open;

import com.yice.edu.cn.api.service.cms.XwCmsOfficialWebsiteService;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsColumn;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsContent;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsHeaderNavigation;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsStyleConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.yice.edu.cn.api.interceptor.ApiInterceptor.currentSchoolId;


/**
 *@ClassName XwCmsOfficialWebsiteController
 *@Description cms项目学校官网
 *@Author administrator
 *@Date 2019/7/3 9:26
 *@Version 1.0
 */
@RequestMapping("/open/xwCmsOfficialWebsite")
@RestController
@Api(value = "/xwCmsOfficialWebsite",description = "CMS官网模块")
public class XwCmsOfficialWebsiteController {

    @Autowired
    private XwCmsOfficialWebsiteService xwCmsOfficialWebsiteService;

    @PostMapping ("/findXwCmsStyleConfigByDomain")
    @ApiOperation(value = "根据二级域名获取官网配置信息", notes = "返回响应对象")
    public ResponseJson findXwCmsStyleConfigByDomain() {
        XwCmsStyleConfig xwCmsStyleConfig = new XwCmsStyleConfig();
        xwCmsStyleConfig.setSchoolId(currentSchoolId());
        XwCmsStyleConfig data = xwCmsOfficialWebsiteService.findXwCmsStyleConfigByDomain(xwCmsStyleConfig);
        return new ResponseJson(data);
    }


    @PostMapping("/findXwCmsContentsByCondition")
    @ApiOperation(value = "根据条件查找内容信息表(给前台使用)", notes = "返回响应对象", response = XwCmsContent.class)
    public ResponseJson findXwCmsContentsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwCmsContent xwCmsContent) {
        return xwCmsOfficialWebsiteService.findXwCmsContentsByCondition(xwCmsContent);
    }

    @PostMapping("/look/lookXwCmsContentById/{id}")
    @ApiOperation(value = "通过id查找内容信息表(给前台使用)", notes = "返回响应对象", response = XwCmsContent.class)
    public ResponseJson lookXwCmsContentById(
            @ApiParam(value = "去查看页面,需要用到的id", required = true)
            @PathVariable String id) {
        return xwCmsOfficialWebsiteService.lookXwCmsContentById(id);
    }

   @GetMapping("findSchoolCompusCenterBySchoolId")
    @ApiOperation(value = "福州大数据接口(给前台使用)", notes = "返回响应对象")
    public ResponseJson findSchoolCompusCenterBySchoolId(){
      return   new ResponseJson(xwCmsOfficialWebsiteService.findSchoolCompusCenterBySchoolId(currentSchoolId())) ;
    }

    @PostMapping("/findXwCmsHeaderNavigationsByCondition")
    @ApiOperation(value = "根据条件查找校务CMS头部导航表", notes = "返回响应对象", response= XwCmsHeaderNavigation.class)
    public ResponseJson findXwCmsHeaderNavigationsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwCmsHeaderNavigation xwCmsHeaderNavigation){
        xwCmsHeaderNavigation.setSchoolId(currentSchoolId());
        return xwCmsOfficialWebsiteService.findXwCmsHeaderNavigationsByCondition(xwCmsHeaderNavigation);
    }


    @PostMapping("/findXwCmsColumnsByCondition")
    @ApiOperation(value = "根据条件查找校务CMS栏目表", notes = "返回响应对象", response= XwCmsColumn.class)
    public ResponseJson findXwCmsColumnsByCondition(
            @ApiParam(value = "属性不为空则作为条件查询")
            @Validated
            @RequestBody XwCmsColumn xwCmsColumn){
        xwCmsColumn.setSchoolId(currentSchoolId());
        return xwCmsOfficialWebsiteService.findXwCmsColumnsByCondition(xwCmsColumn);
    }


    @PostMapping("/findAllCmsHomeLayout")
    @ApiOperation(value = "查询首页布局", notes = "返回校务CMS首页布局列表")
    public ResponseJson findAllCmsHomeLayout(){
      return xwCmsOfficialWebsiteService.findAllCmsHomeLayout(currentSchoolId());
    }


    @GetMapping("/findDomainStatus/{secondDomain}")
    @ApiOperation(value = "查询二级域名状态", notes = "返回当前二级域名状态")
    public ResponseJson findDomainStatus(@PathVariable(value = "secondDomain")String secondDomain){
        return xwCmsOfficialWebsiteService.findDomainStatus(secondDomain);
    }



}
