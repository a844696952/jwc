package com.yice.edu.cn.dy.feign;

import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@FeignClient(value="jw",path = "/jwClasses")
public interface JwClassesFeign {
    @PostMapping("/findJwClassesListByCondition")
    List<JwClasses> findClassesByGradeId(JwClasses jwClasses);
}
