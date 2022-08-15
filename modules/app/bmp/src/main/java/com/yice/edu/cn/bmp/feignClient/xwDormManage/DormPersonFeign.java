package com.yice.edu.cn.bmp.feignClient.xwDormManage;

import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildVo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildingPersonInfo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPerson;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(value="xw",contextId = "dormPersonFeign",path = "/dormPerson")
public interface DormPersonFeign {

    @PostMapping("/findDormPersonInfoWithStudent")
    List<DormBuildingPersonInfo> findDormPersonInfoWithStudent(DormBuildingPersonInfo dormBuildingPersonInfo);

    @PostMapping("/findDormPersonCountByCondition")
    long findDormPersonCountByCondition(DormPerson dormPerson);
}
