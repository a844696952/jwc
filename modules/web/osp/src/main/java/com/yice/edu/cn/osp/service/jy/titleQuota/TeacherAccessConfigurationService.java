package com.yice.edu.cn.osp.service.jy.titleQuota;

import com.yice.edu.cn.common.pojo.jy.titleQuota.TeacherAccessConfiguration;
import com.yice.edu.cn.osp.feignClient.jy.titleQuota.TeacherAccessConfigurationFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherAccessConfigurationService {
    @Autowired
    private TeacherAccessConfigurationFeign teacherAccessConfigurationFeign;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    public TeacherAccessConfiguration findTeacherAccessConfigurationById(String id) {
        return teacherAccessConfigurationFeign.findTeacherAccessConfigurationById(id);
    }

    public TeacherAccessConfiguration saveTeacherAccessConfiguration(TeacherAccessConfiguration teacherAccessConfiguration) {
        return teacherAccessConfigurationFeign.saveTeacherAccessConfiguration(teacherAccessConfiguration);
    }

    public void batchSaveTeacherAccessConfiguration(List<TeacherAccessConfiguration> teacherAccessConfigurations){
        teacherAccessConfigurationFeign.batchSaveTeacherAccessConfiguration(teacherAccessConfigurations);
    }

    public List<TeacherAccessConfiguration> findTeacherAccessConfigurationListByCondition(TeacherAccessConfiguration teacherAccessConfiguration) {
        return teacherAccessConfigurationFeign.findTeacherAccessConfigurationListByCondition(teacherAccessConfiguration);
    }

    public TeacherAccessConfiguration findOneTeacherAccessConfigurationByCondition(TeacherAccessConfiguration teacherAccessConfiguration) {
        return teacherAccessConfigurationFeign.findOneTeacherAccessConfigurationByCondition(teacherAccessConfiguration);
    }

    public long findTeacherAccessConfigurationCountByCondition(TeacherAccessConfiguration teacherAccessConfiguration) {
        return teacherAccessConfigurationFeign.findTeacherAccessConfigurationCountByCondition(teacherAccessConfiguration);
    }

    public void updateTeacherAccessConfiguration(TeacherAccessConfiguration teacherAccessConfiguration) {
        teacherAccessConfigurationFeign.updateTeacherAccessConfiguration(teacherAccessConfiguration);
    }

    public void updateTeacherAccessConfigurationForAll(TeacherAccessConfiguration teacherAccessConfiguration) {
        teacherAccessConfigurationFeign.updateTeacherAccessConfigurationForAll(teacherAccessConfiguration);
    }

    public void deleteTeacherAccessConfiguration(String id) {
        teacherAccessConfigurationFeign.deleteTeacherAccessConfiguration(id);
    }

    public void deleteTeacherAccessConfigurationByCondition(TeacherAccessConfiguration teacherAccessConfiguration) {
        teacherAccessConfigurationFeign.deleteTeacherAccessConfigurationByCondition(teacherAccessConfiguration);
    }


    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    public List<TeacherAccessConfiguration> findTeacherAccessConfigurationListByCondition4Like(TeacherAccessConfiguration teacherAccessConfiguration) {
        return teacherAccessConfigurationFeign.findTeacherAccessConfigurationListByCondition4Like(teacherAccessConfiguration);
    }

    public TeacherAccessConfiguration saveOrUpdate(TeacherAccessConfiguration teacherAccessConfiguration) {
        return teacherAccessConfigurationFeign.saveOrUpdate(teacherAccessConfiguration);
    }

    public long findTeacherAccessConfigurationListByCondition4LikeCount(TeacherAccessConfiguration teacherAccessConfiguration) {
        return teacherAccessConfigurationFeign.findTeacherAccessConfigurationListByCondition4LikeCount(teacherAccessConfiguration);
    }

    public TeacherAccessConfiguration findTeacherAccessConfigurationsByConditioOne(TeacherAccessConfiguration teacherAccessConfiguration) {
        return teacherAccessConfigurationFeign.findTeacherAccessConfigurationsByConditioOne(teacherAccessConfiguration);
    }
}
