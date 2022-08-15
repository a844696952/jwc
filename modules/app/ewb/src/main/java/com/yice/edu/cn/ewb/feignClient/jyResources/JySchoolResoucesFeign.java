package com.yice.edu.cn.ewb.feignClient.jyResources;

import com.yice.edu.cn.common.pojo.jy.resources.JySchoolResouces;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "jySchoolResoucesFeign",path = "/jySchoolResouces")
public interface JySchoolResoucesFeign {

    @PostMapping("/findJySchoolResoucesListByCondition")
    List<JySchoolResouces> findJySchoolResoucesListByCondition(JySchoolResouces jySchoolResouces);

    @PostMapping("/findJySchoolResoucesCountByCondition")
    long findJySchoolResoucesCountByCondition(JySchoolResouces jySchoolResouces);

    /**
     * 返回资源列表和文件夹列表
     * @param jySchoolResouces
     * @return
     */
    @PostMapping("/findJySchoolResoucesList")
    List<JySchoolResouces> findJySchoolResoucesList(JySchoolResouces jySchoolResouces);

    /**
     * 返回资源和文件夹记录数
     * @param jySchoolResouces
     * @return
     */
    @PostMapping("/findJySchoolResoucesCount")
    long findJySchoolResoucesCount(JySchoolResouces jySchoolResouces);


}
