package com.yice.edu.cn.osp.feignClient.xw.cms;

import com.yice.edu.cn.common.pojo.xw.cms.XwCmsContentFile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "xw", contextId = "xwCmsContentFileFeign", path = "/xwCmsContentFile")
public interface XwCmsContentFileFeign {
    @GetMapping("/findXwCmsContentFileById/{id}")
    XwCmsContentFile findXwCmsContentFileById(@PathVariable("id") String id);

    @PostMapping("/saveXwCmsContentFile")
    XwCmsContentFile saveXwCmsContentFile(XwCmsContentFile xwCmsContentFile);

    @PostMapping("/findXwCmsContentFileListByCondition")
    List<XwCmsContentFile> findXwCmsContentFileListByCondition(XwCmsContentFile xwCmsContentFile);

    @PostMapping("/findOneXwCmsContentFileByCondition")
    XwCmsContentFile findOneXwCmsContentFileByCondition(XwCmsContentFile xwCmsContentFile);

    @PostMapping("/findXwCmsContentFileCountByCondition")
    long findXwCmsContentFileCountByCondition(XwCmsContentFile xwCmsContentFile);

    @PostMapping("/updateXwCmsContentFile")
    void updateXwCmsContentFile(XwCmsContentFile xwCmsContentFile);

    @GetMapping("/deleteXwCmsContentFile/{id}")
    void deleteXwCmsContentFile(@PathVariable("id") String id);

    @PostMapping("/deleteXwCmsContentFileByCondition")
    void deleteXwCmsContentFileByCondition(XwCmsContentFile xwCmsContentFile);
}
