package com.yice.edu.cn.jy.service.titleQuota;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jy.titleQuota.TeacherAccessConfiguration;
import com.yice.edu.cn.jy.dao.titleQuota.ITeacherAccessConfigurationDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherAccessConfigurationService {
    @Autowired
    private ITeacherAccessConfigurationDao teacherAccessConfigurationDao;
    @Autowired
    private SequenceId sequenceId;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public TeacherAccessConfiguration findTeacherAccessConfigurationById(String id) {
        return teacherAccessConfigurationDao.findTeacherAccessConfigurationById(id);
    }
    @Transactional
    public void saveTeacherAccessConfiguration(TeacherAccessConfiguration teacherAccessConfiguration) {
        teacherAccessConfiguration.setId(sequenceId.nextId());
        teacherAccessConfigurationDao.saveTeacherAccessConfiguration(teacherAccessConfiguration);
    }
    @Transactional(readOnly = true)
    public List<TeacherAccessConfiguration> findTeacherAccessConfigurationListByCondition(TeacherAccessConfiguration teacherAccessConfiguration) {
        return teacherAccessConfigurationDao.findTeacherAccessConfigurationListByCondition(teacherAccessConfiguration);
    }
    @Transactional(readOnly = true)
    public TeacherAccessConfiguration findOneTeacherAccessConfigurationByCondition(TeacherAccessConfiguration teacherAccessConfiguration) {
        return teacherAccessConfigurationDao.findOneTeacherAccessConfigurationByCondition(teacherAccessConfiguration);
    }
    @Transactional(readOnly = true)
    public long findTeacherAccessConfigurationCountByCondition(TeacherAccessConfiguration teacherAccessConfiguration) {
        return teacherAccessConfigurationDao.findTeacherAccessConfigurationCountByCondition(teacherAccessConfiguration);
    }

    @Transactional
    public void deleteTeacherAccessConfiguration(String id) {
        teacherAccessConfigurationDao.deleteTeacherAccessConfiguration(id);
    }
    @Transactional
    public void deleteTeacherAccessConfigurationByCondition(TeacherAccessConfiguration teacherAccessConfiguration) {
        teacherAccessConfigurationDao.deleteTeacherAccessConfigurationByCondition(teacherAccessConfiguration);
    }
    @Transactional
    public void batchSaveTeacherAccessConfiguration(List<TeacherAccessConfiguration> teacherAccessConfigurations){
        teacherAccessConfigurations.forEach(teacherAccessConfiguration -> teacherAccessConfiguration.setId(sequenceId.nextId()));
        teacherAccessConfigurationDao.batchSaveTeacherAccessConfiguration(teacherAccessConfigurations);
    }


    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public List<TeacherAccessConfiguration> findTeacherAccessConfigurationListByCondition4Like(TeacherAccessConfiguration teacherAccessConfiguration) {
        return  teacherAccessConfigurationDao.findTeacherAccessConfigurationListByCondition4Like(teacherAccessConfiguration);
    }

    @Transactional
    public void updateTeacherAccessConfiguration(TeacherAccessConfiguration teacherAccessConfiguration) {//批量修改
        Integer dailyVisits = teacherAccessConfiguration.getDailyVisits();

        List<TeacherAccessConfiguration> list = new ArrayList<>();
        TeacherAccessConfiguration one = new TeacherAccessConfiguration();
        for(int i=0;i<teacherAccessConfiguration.getIds().length;i++){
            one.setTeacherId(teacherAccessConfiguration.getIds()[i]);
            deleteTeacherAccessConfigurationByCondition(one);
        }
        TeacherAccessConfiguration two;
        for(int i=0;i<teacherAccessConfiguration.getIds().length;i++){
            two = new TeacherAccessConfiguration();
            two.setId(sequenceId.nextId());
            two.setDailyVisits(dailyVisits);
            two.setTeacherId(teacherAccessConfiguration.getIds()[i]);
            two.setTeacherName(teacherAccessConfiguration.getTeacherName());
            two.setSchoolId(teacherAccessConfiguration.getSchoolId());
            list.add(two);
        }
        batchSaveTeacherAccessConfiguration(list);
    }

    @Transactional
    public void updateTeacherAccessConfigurationForAll(TeacherAccessConfiguration teacherAccessConfiguration) {
        TeacherAccessConfiguration temp = new TeacherAccessConfiguration();
        temp.setTeacherId(teacherAccessConfiguration.getTeacherId());
        TeacherAccessConfiguration one = teacherAccessConfigurationDao.findOneTeacherAccessConfigurationByCondition(temp);
        teacherAccessConfiguration.setId(one.getId());
        teacherAccessConfiguration.setCreateTime(one.getCreateTime());
        teacherAccessConfigurationDao.updateTeacherAccessConfigurationForAll4Like(teacherAccessConfiguration);
    }

    @Transactional
    public void saveOrUpdate(TeacherAccessConfiguration teacherAccessConfiguration) {
        List<TeacherAccessConfiguration> list = new ArrayList<>();
        String[] ids = teacherAccessConfiguration.getIds();
        for(int i=0;i<ids.length;i++){
            String teacherId = ids[i];
            TeacherAccessConfiguration temp1 = new TeacherAccessConfiguration();
            temp1.setTeacherId(teacherId);
            teacherAccessConfigurationDao.deleteTeacherAccessConfigurationByCondition(temp1);
            if(teacherAccessConfiguration.getDailyVisits()==null){
              temp1.setDailyVisits(0);
            }else {
              temp1.setDailyVisits(teacherAccessConfiguration.getDailyVisits());
            }
            temp1.setSchoolId(teacherAccessConfiguration.getSchoolId());
            list.add(temp1);
        }
        batchSaveTeacherAccessConfiguration(list);
    }

    public long findTeacherAccessConfigurationListByCondition4LikeCount(TeacherAccessConfiguration teacherAccessConfiguration) {
        return teacherAccessConfigurationDao.findTeacherAccessConfigurationListByCondition4LikeCount(teacherAccessConfiguration);
    }

    public TeacherAccessConfiguration findTeacherAccessConfigurationsByConditioOne(TeacherAccessConfiguration teacherAccessConfiguration) {
        return teacherAccessConfigurationDao.findTeacherAccessConfigurationsByConditioOne(teacherAccessConfiguration);
    }
}
