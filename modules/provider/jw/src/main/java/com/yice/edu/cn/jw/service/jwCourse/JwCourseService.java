package com.yice.edu.cn.jw.service.jwCourse;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherClasses;
import com.yice.edu.cn.jw.dao.classSchedule.IClassScheduleDao;
import com.yice.edu.cn.jw.dao.jwCourse.JwCourseDao;
import com.yice.edu.cn.jw.dao.teacher.ITeacherClassesDao;
import com.yice.edu.cn.jw.service.dd.DdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwCourseService {
    @Autowired
    private JwCourseDao jwCourseDao;
    @Autowired
    private SequenceId sequenceId;

    @Autowired
    private ITeacherClassesDao teacherClassesDao;

    @Autowired
    private IClassScheduleDao classScheduleDao;
    @Autowired
    private DdService ddService;


    @Transactional(readOnly = true)
    public JwCourse findJwCourseById(String id) {
        return jwCourseDao.findJwCourseById(id);
    }
    @Transactional
    public void saveJwCourse(JwCourse jwCourse) {
        jwCourse.setId(sequenceId.nextId());
        jwCourseDao.saveJwCourse(jwCourse);
    }
    @Transactional(readOnly = true)
    public List<JwCourse> findJwCourseListByCondition(JwCourse jwCourse) {
        return jwCourseDao.findJwCourseListByCondition(jwCourse);
    }
    @Transactional(readOnly = true)
    public JwCourse findOneJwCourseByCondition(JwCourse jwCourse) {
        return jwCourseDao.findOneJwCourseByCondition(jwCourse);
    }
    @Transactional(readOnly = true)
    public long findJwCourseCountByCondition(JwCourse jwCourse) {
        return jwCourseDao.findJwCourseCountByCondition(jwCourse);
    }

    @Transactional
    public void updateJwCourse(JwCourse jwCourse) {
        jwCourseDao.updateJwCourse(jwCourse);
    }
    @Transactional
    public void updateJwCourseForAll(JwCourse jwCourse) {
        jwCourseDao.updateJwCourseForAll(jwCourse);
    }
    @Transactional
    public void deleteJwCourse(String id) {
        jwCourseDao.deleteJwCourse(id);
    }
    @Transactional
    public void deleteJwCourseByCondition(JwCourse jwCourse) {
        jwCourseDao.deleteJwCourseByCondition(jwCourse);
    }
    @Transactional
    public void batchSaveJwCourse(List<JwCourse> jwCourses){
        jwCourses.forEach(jwCourse -> jwCourse.setId(sequenceId.nextId()));
        jwCourseDao.batchSaveJwCourse(jwCourses);
    }



    @Transactional
    public List<Dd> queryAllByTypeIdGrade() {
        return jwCourseDao.queryAllByTypeIdGrade();
    }

    @Transactional
    public List<Dd> queryAllByTypeIdCourse() {

        return jwCourseDao.queryAllByTypeIdCourse();
    }

    @Transactional
    public List<Dd> queryAllByTypeIdType() {
        return jwCourseDao.queryAllByTypeIdType();
    }

    @Transactional
    public List<Dd> queryAllByTypeIdBuilding() {
        return jwCourseDao.queryAllByTypeIdBuilding();
    }

    @Transactional
    public Dd queryOneById(String id) {
        return jwCourseDao.queryOneById(id);
    }

    @Transactional
    public long distinctJwCourse(JwCourse jwCourse) {

        return jwCourseDao.distinctJwCourse(jwCourse);
    }



    @Transactional
    public void deleteTeacherClassesCourseByCondition(JwCourse jwCourse) {
        deleteJwCourseByCondition(jwCourse);
        TeacherClasses teacherClasses = new TeacherClasses();
        teacherClasses.setCourseId(jwCourse.getId());
        teacherClasses.setSchoolId(jwCourse.getSchoolId());
        teacherClassesDao.deleteTeacherClassesByCondition(teacherClasses);
    }

    @Transactional
    public void updateTeacherClassesCourseByCondition(@RequestBody JwCourse jwCourse){
        jwCourseDao.updateJwCourseSchoolId(jwCourse);
    }

    //一次性添加多个不同年级的相同课程
    @Transactional
    public long saveJwcourses(JwCourse jwCourse){
        List<JwCourse> jwCourseList = new ArrayList<>();
        //获取传过来的数组
        long c = 0;
        for (int i=0;i<jwCourse.getDdId().length;i++){
            JwCourse jwCourse1 = new JwCourse();
            jwCourse1.setNameId(jwCourse.getDdId()[i]);
            jwCourse1.setSchoolId(jwCourse.getSchoolId());
            jwCourse1.setAlias(jwCourse.getNames()[i]);
            jwCourse1.setName(jwCourse.getNames()[i]);
            jwCourse1.setExam(1);
            c = jwCourseDao.distinctJwCourse(jwCourse1);
            if (c != 0) {
                return c;
            }

            //唯一性约束(193,140,163为不同年段校本课程)
            if(!jwCourse1.getNameId().equals("193") && !jwCourse1.getNameId().equals("140")&& !jwCourse1.getNameId().equals("163")){
                jwCourse1.setId(jwCourse1.getNameId());
            }else{
                jwCourse1.setId(sequenceId.nextId());
            }
            jwCourseList.add(jwCourse1);

        }

        //没查找到则添加
        jwCourseDao.batchSaveJwCourse(jwCourseList);
        return c;
    }


    //原生成查询代码
    @Transactional(readOnly = true)
    public  List<JwCourse> findJwCourseListByConditionGai(JwCourse jwCourse){
        return jwCourseDao.findJwCourseListByConditionGai(jwCourse);
    }
    @Transactional(readOnly = true)
    public List<Teacher> findTeachersByNameId(String nameId, String schoolId) {
        return teacherClassesDao.findTeachersByNameId(nameId,schoolId);
    }

    @Transactional(readOnly = true)
    public List<JwCourse> findJwCourseListByConditionKong(JwCourse jwCourse){
        return jwCourseDao.findJwCourseListByConditionKong(jwCourse);
    }

    @Transactional(readOnly = true)
    public long findJwCourseCountByConditionKong(JwCourse jwCourse){
        return jwCourseDao.findJwCourseCountByConditionKong(jwCourse);
    }

    @Transactional(readOnly = true)
    public List<JwCourse> findSchoolEaxmCourseList(String schoolId){
        return jwCourseDao.findSchoolEaxmCourseList(schoolId);
    }

    @Transactional(readOnly = true)
    public List<Dd> findFiltrationJwCouserBySchoolId(Dd dd){
        String schoolId = dd.getSchoolId();
        dd.setSchoolId(null);
        List<Dd> data=ddService.findDdListByCondition(dd);
        JwCourse jwCourse = new JwCourse();
        jwCourse.setSchoolId(schoolId);
        List<JwCourse> jwCourse1 = findJwCourseListByCondition(jwCourse);
        for(int i = data.size()-1;i>=0;i--){
            for(int j =0;j<jwCourse1.size();j++){
                if(data.get(i).getId().equals(jwCourse1.get(j).getId())){
                    data.remove(i);
                    break;
                }
            }
        }
        return data;
    }
}
