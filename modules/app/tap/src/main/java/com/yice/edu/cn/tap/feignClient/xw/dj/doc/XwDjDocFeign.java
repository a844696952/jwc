package com.yice.edu.cn.tap.feignClient.xw.dj.doc;

import com.yice.edu.cn.common.pojo.xw.document.Doc;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@FeignClient(value="xw",contextId = "xwDjDocFeign",path = "/xwDjDoc")
public interface XwDjDocFeign {

    @GetMapping("/findDocById/{id}")
    Doc findDocById(@PathVariable("id") String id);
    @PostMapping("/saveDoc")
    Doc saveDoc(Doc doc);
    @PostMapping("/findDocListByCondition")
    List<Doc> findDocListByCondition(Doc doc);
    @PostMapping("/findOneDocByCondition")
    Doc findOneDocByCondition(Doc doc);
    @PostMapping("/findDocCountByCondition")
    long findDocCountByCondition(Doc doc);
    @PostMapping("/updateDoc")
    void updateDoc(Doc doc);
    @GetMapping("/deleteDoc/{id}")
    void deleteDoc(@PathVariable("id") String id);
    @PostMapping("/deleteDocByCondition")
    void deleteDocByCondition(Doc doc);

}
