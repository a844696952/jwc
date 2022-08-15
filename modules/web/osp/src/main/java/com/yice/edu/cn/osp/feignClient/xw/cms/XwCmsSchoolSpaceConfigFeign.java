package com.yice.edu.cn.osp.feignClient.xw.cms;

import com.yice.edu.cn.common.pojo.xw.cms.XwCmsSchoolSpaceConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "xwCmsSchoolSpaceConfigFeign",path = "/xwCmsSchoolSpaceConfig")
public interface XwCmsSchoolSpaceConfigFeign {
    @GetMapping("/findXwCmsSchoolSpaceConfigById/{id}")
    XwCmsSchoolSpaceConfig findXwCmsSchoolSpaceConfigById(@PathVariable("id") String id);
    @PostMapping("/saveXwCmsSchoolSpaceConfig")
    XwCmsSchoolSpaceConfig saveXwCmsSchoolSpaceConfig(XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig);
    @PostMapping("/findXwCmsSchoolSpaceConfigListByCondition")
    List<XwCmsSchoolSpaceConfig> findXwCmsSchoolSpaceConfigListByCondition(XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig);
    @PostMapping("/findOneXwCmsSchoolSpaceConfigByCondition")
    XwCmsSchoolSpaceConfig findOneXwCmsSchoolSpaceConfigByCondition(XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig);
    @PostMapping("/findXwCmsSchoolSpaceConfigCountByCondition")
    long findXwCmsSchoolSpaceConfigCountByCondition(XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig);
    @PostMapping("/updateXwCmsSchoolSpaceConfig")
    void updateXwCmsSchoolSpaceConfig(XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig);
    @GetMapping("/deleteXwCmsSchoolSpaceConfig/{id}")
    void deleteXwCmsSchoolSpaceConfig(@PathVariable("id") String id);
    @PostMapping("/deleteXwCmsSchoolSpaceConfigByCondition")
    void deleteXwCmsSchoolSpaceConfigByCondition(XwCmsSchoolSpaceConfig xwCmsSchoolSpaceConfig);
    @GetMapping("/findSchoolIdBySecondDomain/{secondDomain}")
    String findSchoolIdBySecondDomain(@PathVariable("secondDomain")String secondDomain);

}
