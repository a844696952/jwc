package com.yice.edu.cn.osp.feignClient.xw.cms;

import com.yice.edu.cn.common.pojo.xw.cms.XwCmsColumn;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsHomeLayout;
import com.yice.edu.cn.common.pojo.xw.cms.XwCmsLayoutCondition;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value="xw",contextId = "xwCmsHomeLayoutFeign",path = "/xwCmsHomeLayout")
public interface XwCmsHomeLayoutFeign {
    @PostMapping("/saveCmsHomeLayout")
    Boolean saveCmsHomeLayout(List<Map<String,Object>> rows, @RequestParam("schoolId") String schoolId);
    @GetMapping("/findAllCmsHomeLayout/{schoolId}")
    List<Map<String,Object>> findAllCmsHomeLayout(@PathVariable("schoolId") String schoolId);
    @PostMapping("/initCmsHomeLayout/{schoolId}")
    Boolean initCmsHomeLayout(@PathVariable("schoolId") String schoolId);
    @GetMapping("/findLiftHomeLayoutList/{schoolId}")
    List<XwCmsColumn> findLiftHomeLayoutList(@PathVariable("schoolId") String schoolId);
    @GetMapping("/findRigntHomeLayoutList/{schoolId}")
    List<XwCmsColumn> findRigntHomeLayoutList(@PathVariable("schoolId") String schoolId);
    @PostMapping("/updateXwCmsHomeLayout")
    void updateXwCmsHomeLayout(XwCmsHomeLayout xwCmsHomeLayout);
    @PostMapping("/addOrDeleteXwCmsHomeLayoutRow")
    Boolean addOrDeleteXwCmsHomeLayoutRow(XwCmsLayoutCondition xwCmsLayoutCondition);
    @GetMapping("/checkXwCmsHomeLayout/{columnId}")
    Boolean findCmsHomeLayoutByCid(@PathVariable("columnId") String columnId);
    @PostMapping("/checkCmsHomeLayouTopRow")
    Boolean checkCmsHomeLayouTopRow(XwCmsHomeLayout xwCmsHomeLayout);
}
