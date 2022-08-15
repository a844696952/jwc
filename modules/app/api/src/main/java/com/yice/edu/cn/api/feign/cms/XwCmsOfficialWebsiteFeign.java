package com.yice.edu.cn.api.feign.cms;

import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsColumn;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsContent;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsHeaderNavigation;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsStyleConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value="xw",contextId = "xwCmsOfficialWebsiteFeign",path = "/xwCmsOfficialWebsite")
public interface XwCmsOfficialWebsiteFeign {

    @PostMapping("/findXwCmsColumnsByCondition")
    ResponseJson findXwCmsColumnsByCondition( @RequestBody XwCmsColumn xwCmsColumn);

    @PostMapping("/findXwCmsHeaderNavigationsByCondition")
    ResponseJson findXwCmsHeaderNavigationsByCondition( @RequestBody XwCmsHeaderNavigation xwCmsHeaderNavigation);

    @PostMapping("/findAllCmsHomeLayout/{schoolId}")
    ResponseJson findAllCmsHomeLayout(@PathVariable(value="schoolId") String schoolId);

    @PostMapping("/look/lookXwCmsContentById/{id}")
    ResponseJson lookXwCmsContentById(@PathVariable(value="id") String id);

    @PostMapping("/findXwCmsContentsByCondition")
    ResponseJson findXwCmsContentsByCondition(@RequestBody XwCmsContent xwCmsContent);

    @GetMapping("/findDomainStatus/{secondDomain}")
    ResponseJson findDomainStatus(@PathVariable(value = "secondDomain")String secondDomain);

    @GetMapping("/findXwCmsStyleConfigByDomain/{schoolId}")
    XwCmsStyleConfig findXwCmsStyleConfigByDomain(@PathVariable(value="schoolId")String schoolId );

    @GetMapping("findSchoolCompusCenterBySchoolId/{schoolId}")
    ResponseJson findSchoolCompusCenterBySchoolId(@PathVariable(value="schoolId")String schoolId);


    @GetMapping("/findSchoolIdBySecondDomain/{secondDomain}")
    String  findSchoolIdBySecondDomain(@PathVariable(value="secondDomain") String secondDomain);

}
