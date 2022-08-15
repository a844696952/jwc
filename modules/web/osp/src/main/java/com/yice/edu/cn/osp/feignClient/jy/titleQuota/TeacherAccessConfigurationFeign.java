package com.yice.edu.cn.osp.feignClient.jy.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.TeacherAccessConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="jy",contextId = "teacherAccessConfigurationFeign",path = "/teacherAccessConfiguration")
public interface TeacherAccessConfigurationFeign {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @GetMapping("/findTeacherAccessConfigurationById/{id}")
    TeacherAccessConfiguration findTeacherAccessConfigurationById(@PathVariable("id") String id);
    @PostMapping("/saveTeacherAccessConfiguration")
    TeacherAccessConfiguration saveTeacherAccessConfiguration(TeacherAccessConfiguration teacherAccessConfiguration);
    @PostMapping("/batchSaveTeacherAccessConfiguration")
    void batchSaveTeacherAccessConfiguration(List<TeacherAccessConfiguration> teacherAccessConfigurations);
    @PostMapping("/findTeacherAccessConfigurationListByCondition")
    List<TeacherAccessConfiguration> findTeacherAccessConfigurationListByCondition(TeacherAccessConfiguration teacherAccessConfiguration);
    @PostMapping("/findOneTeacherAccessConfigurationByCondition")
    TeacherAccessConfiguration findOneTeacherAccessConfigurationByCondition(TeacherAccessConfiguration teacherAccessConfiguration);
    @PostMapping("/findTeacherAccessConfigurationCountByCondition")
    long findTeacherAccessConfigurationCountByCondition(TeacherAccessConfiguration teacherAccessConfiguration);
    @PostMapping("/updateTeacherAccessConfiguration")
    void updateTeacherAccessConfiguration(TeacherAccessConfiguration teacherAccessConfiguration);
    @PostMapping("/updateTeacherAccessConfigurationForAll")
    void updateTeacherAccessConfigurationForAll(TeacherAccessConfiguration teacherAccessConfiguration);
    @GetMapping("/deleteTeacherAccessConfiguration/{id}")
    void deleteTeacherAccessConfiguration(@PathVariable("id") String id);
    @PostMapping("/deleteTeacherAccessConfigurationByCondition")
    void deleteTeacherAccessConfigurationByCondition(TeacherAccessConfiguration teacherAccessConfiguration);

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @PostMapping("/findTeacherAccessConfigurationListByCondition4Like")
    List<TeacherAccessConfiguration> findTeacherAccessConfigurationListByCondition4Like(TeacherAccessConfiguration teacherAccessConfiguration);
    @PostMapping("/saveOrUpdate")
    TeacherAccessConfiguration saveOrUpdate(TeacherAccessConfiguration teacherAccessConfiguration);
    @PostMapping("/findTeacherAccessConfigurationListByCondition4LikeCount")
    long findTeacherAccessConfigurationListByCondition4LikeCount(TeacherAccessConfiguration teacherAccessConfiguration);
    @PostMapping("/findTeacherAccessConfigurationsByConditioOne")
    TeacherAccessConfiguration findTeacherAccessConfigurationsByConditioOne(TeacherAccessConfiguration teacherAccessConfiguration);
}
