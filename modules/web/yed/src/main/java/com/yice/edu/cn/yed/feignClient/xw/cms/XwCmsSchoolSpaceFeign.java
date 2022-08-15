package com.yice.edu.cn.yed.feignClient.xw.cms;

import com.yice.edu.cn.common.pojo.xw.cms.XwCmsSchoolSpace;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "xwCmsSchoolSpaceFeign",path = "/xwCmsSchoolSpace")
public interface XwCmsSchoolSpaceFeign {
    @GetMapping("/findXwCmsSchoolSpaceById/{id}")
    XwCmsSchoolSpace findXwCmsSchoolSpaceById(@PathVariable("id") String id);
    @PostMapping("/saveXwCmsSchoolSpace")
    XwCmsSchoolSpace saveXwCmsSchoolSpace(XwCmsSchoolSpace xwCmsSchoolSpace);
    @PostMapping("/findXwCmsSchoolSpaceListByCondition")
    List<XwCmsSchoolSpace> findXwCmsSchoolSpaceListByCondition(XwCmsSchoolSpace xwCmsSchoolSpace);
    @PostMapping("/findOneXwCmsSchoolSpaceByCondition")
    XwCmsSchoolSpace findOneXwCmsSchoolSpaceByCondition(XwCmsSchoolSpace xwCmsSchoolSpace);

    @PostMapping("/findXwCmsSchoolSpaceByDomain/{secondDomain}")
    XwCmsSchoolSpace findXwCmsSchoolSpaceByDomain(@PathVariable("secondDomain")String secondDomain);

    @PostMapping("/findXwCmsSchoolSpaceCountByCondition")
    long findXwCmsSchoolSpaceCountByCondition(XwCmsSchoolSpace xwCmsSchoolSpace);
    @PostMapping("/updateXwCmsSchoolSpace")
    void updateXwCmsSchoolSpace(XwCmsSchoolSpace xwCmsSchoolSpace);
    @GetMapping("/deleteXwCmsSchoolSpace/{id}")
    void deleteXwCmsSchoolSpace(@PathVariable("id") String id);
    @PostMapping("/deleteXwCmsSchoolSpaceByCondition")
    void deleteXwCmsSchoolSpaceByCondition(XwCmsSchoolSpace xwCmsSchoolSpace);
    @PostMapping("/findProvinceList")
    List<XwCmsSchoolSpace> findProvinceList();
    @PostMapping("/updateXwCmsSchoolSpaceBySchoolId")
    void updateXwCmsSchoolSpaceBySchoolId(XwCmsSchoolSpace xwCmsSchoolSpace);
}
