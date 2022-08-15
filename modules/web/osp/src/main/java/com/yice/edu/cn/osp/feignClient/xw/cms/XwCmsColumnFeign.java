package com.yice.edu.cn.osp.feignClient.xw.cms;

import com.yice.edu.cn.common.pojo.xw.cms.XwCmsColumn;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="xw",contextId = "xwCmsColumnFeign",path = "/xwCmsColumn")
public interface XwCmsColumnFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findXwCmsColumnById/{id}")
    XwCmsColumn findXwCmsColumnById(@PathVariable("id") String id);
    @PostMapping("/saveXwCmsColumn")
    Integer saveXwCmsColumn(XwCmsColumn xwCmsColumn);
    @PostMapping("/batchSaveXwCmsColumn")
    void batchSaveXwCmsColumn(List<XwCmsColumn> xwCmsColumns);
    @PostMapping("/findXwCmsColumnListByCondition")
    List<XwCmsColumn> findXwCmsColumnListByCondition(XwCmsColumn xwCmsColumn);
    @PostMapping("/findOneXwCmsColumnByCondition")
    XwCmsColumn findOneXwCmsColumnByCondition(XwCmsColumn xwCmsColumn);
    @PostMapping("/findXwCmsColumnCountByCondition")
    long findXwCmsColumnCountByCondition(XwCmsColumn xwCmsColumn);
    @PostMapping("/updateXwCmsColumn")
    Boolean updateXwCmsColumn(XwCmsColumn xwCmsColumn);
    @PostMapping("/updateXwCmsColumnForNotNull")
    void updateXwCmsColumnForAll(XwCmsColumn xwCmsColumn);
    @GetMapping("/deleteXwCmsColumn/{id}")
    void deleteXwCmsColumn(@PathVariable("id") String id);
    @PostMapping("/deleteXwCmsColumnByCondition")
    void deleteXwCmsColumnByCondition(XwCmsColumn xwCmsColumn);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/

    @PostMapping("/findXwCmsColumnList")
    List<XwCmsColumn> findXwCmsColumnList(XwCmsColumn xwCmsColumn);

    @PostMapping("/move")
    Boolean move(XwCmsColumn xwCmsColumn);

    @GetMapping("/selectBannerAndApp/{mySchoolId}")
    List<XwCmsColumn> selectBannerAndApp(@PathVariable("mySchoolId") String mySchoolId);
}
