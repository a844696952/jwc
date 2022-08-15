package com.yice.edu.cn.osp.feignClient.jw.jwCourse;

import com.yice.edu.cn.common.pojo.jy.subjectSourse.Material;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "jy",contextId = "materialFeigin",path = "/material")
public interface MaterialFeigin {

    @PostMapping("/findMaterialListByCondition")
    List<Material> findMaterialListByCondition(Material material);
}
