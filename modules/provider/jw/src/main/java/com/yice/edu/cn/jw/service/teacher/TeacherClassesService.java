package com.yice.edu.cn.jw.service.teacher;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.jw.JwCourse.JwCourse;
import com.yice.edu.cn.common.pojo.jw.classes.ClassesForQusSurvey;
import com.yice.edu.cn.common.pojo.jw.classes.JwClasses;
import com.yice.edu.cn.common.pojo.jw.teacher.*;
import com.yice.edu.cn.jw.dao.classes.IJwClassesDao;
import com.yice.edu.cn.jw.dao.jwCourse.JwCourseDao;
import com.yice.edu.cn.jw.dao.teacher.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeacherClassesService {
    @Autowired
    private ITeacherClassesDao teacherClassesDao;
    @Autowired
    private ITeacherPostDao teacherPostDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private ITeacherDao teacherDao;
    @Autowired
    private IJwClassesDao jwClassesDao;
    @Autowired
    private JwCourseDao jwCourseDao;

    @Transactional(readOnly = true)
    public TeacherClasses findTeacherClassesById(String id) {
        TeacherClasses teacherClasses = teacherClassesDao.findTeacherClassesById(id);
        return teacherClasses;
    }

    @Transactional
    public void saveTeacherClasses(TeacherClasses teacherClasses) {
        teacherClasses.setId(sequenceId.nextId());
        teacherClassesDao.saveTeacherClasses(teacherClasses);
    }

    @Transactional(readOnly = true)
    public List<TeacherClasses> findTeacherClassesListByCondition(TeacherClasses teacherClasses) {
        return teacherClassesDao.findTeacherClassesListByCondition(teacherClasses);
    }

    @Transactional(readOnly = true)
    public long findTeacherClassesCountByCondition(TeacherClasses teacherClasses) {
        return teacherClassesDao.findTeacherClassesCountByCondition(teacherClasses);
    }

    @Transactional
    public void updateTeacherClasses(TeacherClasses teacherClasses) {
        teacherClassesDao.updateTeacherClasses(teacherClasses);
    }

    @Transactional
    public void deleteTeacherClasses(String id) {
        teacherClassesDao.deleteTeacherClasses(id);
        //删除对应的职务信息
        //删除对应的教学课程信息
//        TeacherClassesCourse teacherClassesCourse = new TeacherClassesCourse();
//        teacherClassesCourse.setTeacherClassesId(id);
//        teacherClassesCourseDao.deleteTeacherClassesCourseByCondition(teacherClassesCourse);
    }

    @Transactional
    public void deleteTeacherClassesByCondition(TeacherClasses teacherClasses) {
        teacherClassesDao.deleteTeacherClassesByCondition(teacherClasses);
    }

    @Transactional
    public int editTeacherCourse(TeacherClasses teacherClasses) {
        teacherClasses.setUpdateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constant.DATE_FORMATTER)));
        List<TeacherClasses> teacherClassesList = new ArrayList<>();
        String[] classIds = teacherClasses.getClassesId().split(",");
        for (String classId : classIds) {
            if(teacherClasses.getTeacherClassesCourseList()!=null){
                teacherClassesList.addAll(teacherClasses.getTeacherClassesCourseList().stream().map(tcc->{
                    TeacherClasses tc = new TeacherClasses();
                    BeanUtil.copyProperties(teacherClasses,tc);
                    tc.setId(sequenceId.nextId());
                    tc.setClassesId(classId);
                    tc.setCourseId(tcc.getCourseId());
                    return tc;
                }).collect(Collectors.toList()));
            }
        }
        //删除原先的记录
        teacherClassesDao.deleteTeacherClassesByMutil(teacherClasses.getTeacherId(),classIds);
        //批量新增
        if(teacherClassesList.size()>0)
            teacherClassesDao.batchSaveTeacherClasses(teacherClassesList);
        return 200;
    }

    public List<Map<String, Object>> findTeacherClassPostCourseList(TeacherClasses teacherClasses) {
        return teacherClassesDao.findTeacherClassPostCourseList(teacherClasses);
    }


    /**
     * 查找班级班主任
     * @param teacherClasses
     * @return
     */
    public Teacher findHeadmasterByClasses(TeacherClasses teacherClasses) {
        return teacherClassesDao.findHeadmasterByClasses(teacherClasses);
    }

    public List<Teacher4Classes> findClassTeacherListByClasses(TeacherClasses teacherClasses) {
        return teacherClassesDao.findClassTeacherListByClasses(teacherClasses);
    }

    /**
     * 删除班级中教师和班级的关系
     * @param teacherClasses
     */
    @Transactional
    public void delTeacherClassesByClass(TeacherClasses teacherClasses) {
        //清理班主任？？？
        TeacherPost teacherPost = new TeacherPost();
        teacherPost.setClassId(teacherClasses.getClassesId());
        teacherPost.setDdId(Constant.TEACHER_POST.CLASS_TEACHER);
        teacherPostDao.deleteTeacherPostByCondition(teacherPost);
        //删除班级对应教师关系
        teacherClassesDao.deleteTeacherClassesByCondition(teacherClasses);
    }

    /**
     * 同步班级名称
     * @param teacherClasses
     * 学校id 和 年级id
     */
    public void syncClassesName(TeacherClasses teacherClasses){
        //teacherClassesDao.syncClassesName(teacherClasses);
    }
    /**
     * 通过班级查询班级的课程
     *
     * @param teacherClasses
     * @return
     */
    public List<Map<String, String>> findCourseTeacherByClass(TeacherClasses teacherClasses) {
        return teacherClassesDao.findCourseTeacherByClass(teacherClasses);
    }

    /**
     * 通过教师id查询教师所任职的班级
     * @param teacherId
     * @return
     */
    public List<TeacherClasses> findTeacherClassesByTeacher(String teacherId){
        return teacherClassesDao.findTeacherClassesByTeacher(teacherId);
    }

    /**
     * 通过教师id查询教师所任职的年级
     * @param teacherId
     * @return
     */
    public List<TeacherClasses> findGradeByTeacher(String teacherId){
        return teacherClassesDao.findGradeByTeacher(teacherId);
    }
    /**
     * 查询教师在年级中教的科目(线上)
     *
     */
    public List<TeacherClassesCourse> findCourseByTeacherGrade(TeacherClasses teacherClasses){
        return teacherClassesDao.findCourseByTeacherGrade(teacherClasses);
    }

    /**
     * 查询教师在年级中教的科目(线下)
     *
     */
    public List<TeacherClassesCourse> findCourseByTeacherGrade2(TeacherClasses teacherClasses){
        return teacherClassesDao.findCourseByTeacherGrade2(teacherClasses);
    }

    /**
     * 查询教师在年级中教的科目(线上)
     *
     */
    public List<TeacherClassesCourse> findCourseByTeacherGrade3(TeacherClasses teacherClasses){
        return teacherClassesDao.findCourseByTeacherGrade3(teacherClasses);
    }

    /**
     * 通过老师课程id获取对应所教的班级
     * @param teacherId
     * @param courseId
     * @return
     */
    public List<TeacherClasses> findClassesByTeacherCourse(String teacherId,String courseId,String gradeId){
        return teacherClassesDao.findClassesByTeacherCourse(teacherId,courseId,gradeId);
    }

    /**
     * 查找教师对应班级所交的课程
     * @param teacherClasses
     *  teacherId and classesId
     * @return
     */
    public List<TeacherClassesCourse> findCourse4TeacherClasses(TeacherClasses teacherClasses){
        List<TeacherClasses> teacherClassesList = teacherClassesDao.findTeacherClassesListByCondition(teacherClasses);
        if(teacherClassesList.isEmpty())
            return new ArrayList<>();
        return teacherClassesList.stream().map(tc->{
            TeacherClassesCourse teacherClassesCourse = new TeacherClassesCourse();
            teacherClassesCourse.setCourseName(tc.getCourseName());
            teacherClassesCourse.setSubjectName(tc.getSubjectName());
            teacherClassesCourse.setCourseId(tc.getCourseId());
            return teacherClassesCourse;
        }).collect(Collectors.toList());
    }

    /**
     * 通过教师id获取教师所有课程名称列表
     * @param teacherId
     * @return
     */
    public List<String> findCourseNameList4Teacher(String teacherId){
        return teacherClassesDao.findCourseNameList4Teacher(teacherId);
    }


    public List<TeacherClassesForQusSurvey> findClassesByTeacherInfo(TeacherClassesForQusSurvey teacher5Classes){
        List<TeacherClassesForQusSurvey> teacherClassesList= teacherClassesDao.findClassesByTeacherInfo(teacher5Classes);
        teacherClassesList.forEach(teacherClasses -> {
            String [] gradeId=teacherClasses.getGradeId().split(",");
            String [] classIds=teacherClasses.getClassesId().split(",");
            String [] gradeNames=teacherClasses.getGradeName().split(",");
            String [] classesNames=teacherClasses.getClassesName().split(",");
            String [] enrollYears=teacherClasses.getEnrollYear().split(",");
            ArrayList<ClassesForQusSurvey> classes1List=new ArrayList<>();
            if(classIds.length>0){
                for (int i = 0; i <classIds.length ; i++) {
                    ClassesForQusSurvey classes1=new ClassesForQusSurvey();
                    classes1.setClassesId(classIds[i]);
                    classes1.setClassesName(classesNames[i]);
                    classes1.setGradeName(gradeNames[i]);
                    classes1.setGradeId(gradeId[i]);
                    classes1.setEnrollYear(enrollYears[i]);
                    classes1List.add(classes1);
                }

            }
            classes1List.sort(Comparator.comparing(ClassesForQusSurvey::getGradeId));
            teacherClasses.setClassesId(null);
            teacherClasses.setClassesName(null);
            teacherClasses.setEnrollYear(null);
            teacherClasses.setGradeName(null);
            teacherClasses.setGradeId(null);
            teacherClasses.setClassList(classes1List);


        });

        return teacherClassesList;
    }


    /**
     * 学生评价获取学校班级及班主任
     * @param schoolId
     * @return
     */
    public List<TeacherClassesForStuEvaluate> findClassesHeadTeacherBySchoolId(String schoolId){
        return teacherClassesDao.findClassesHeadTeacherBySchoolId(schoolId);
    }

    /**
     * 作业分析   获取教师所教班级和课程
     */
    public List<Map<String,String>> findTeacherClassPostCourseListHomeworkAnalyse(String teacherId){
        return teacherClassesDao.findTeacherClassPostCourseListHomeworkAnalyse(teacherId);
    }

    public List<Map<String,String>> findTeacherClassesHomeworkAnalyseByTeacherId(String teacherId){
        return teacherClassesDao.findTeacherClassesHomeworkAnalyseByTeacherId(teacherId);
    }

    public List<TeacherClassesCourse> findTeacherClassCourseListHomeworkAnalyse(String teacherClassesId ){
        return null;
        //return teacherClassesDao.findTeacherClassCourseListHomeworkAnalyse(teacherClassesId);
    }
    /**
     * 作业分析
     */
    public   List<TeacherHomeworkAnalyseClasses> findClassTeacherListHomeworkAnalyseByClasses(String classesId){
        return teacherClassesDao.findClassTeacherListHomeworkAnalyseByClasses(classesId);
    }
    public   List<Map<String,Object>> findClassesCourseListHomeworkAnalyseByTeacherClassesIds(Map<String,String> map){
        return null;
        //return teacherClassesDao.findClassesCourseListHomeworkAnalyseByTeacherClassesIds(map);
    }


    /**
     * 通过教师id查询教师所教的班级信息（含年级）
     * @param teacherId
     * @return
     */
    public List<JwClasses> findTeacherClassByTeacherId(String teacherId){
        return teacherClassesDao.findTeacherClassByTeacherId(teacherId);
    }
    /**
     * 通过教师id和职务名称查询教师任职记录
     * （ps：年级、班级等信息）
     * @param teacherId
     * @param postName
     * @return
     */
    public TeacherClasses findTeacherClassByTeacherIdAndPost(String teacherId,String postName){
        //通过teacherPost进行转换
        TeacherPost teacherPost = new TeacherPost();
        teacherPost.setTeacherId(teacherId);
        teacherPost.setDdId(postName);
        teacherPost = teacherPostDao.findOneTeacherPostByCondition(teacherPost);
        if(teacherPost!=null){
            TeacherClasses teacherClasses = new TeacherClasses();
            teacherClasses.setTeacherId(teacherId);
            teacherClasses.setClassesName(teacherPost.getClassName());
            teacherClasses.setClassesId(teacherPost.getClassId());
            teacherClasses.setGradeId(teacherPost.getGradeId());
            teacherClasses.setGradeName(teacherPost.getGradeName());
            teacherClasses.setSchoolId(teacherPost.getSchoolId());
            teacherClasses.setEnrollYear(teacherPost.getEnrollYear());
            return teacherClasses;
        }else
            return null;
    }

    /**
     * 通过教师id查询科目 年级
     * @param teacherId
     * @return
     */
    public List<Map<String,String>> findTeacherClassCourseByTeacherId(String teacherId){
        return teacherClassesDao.findTeacherClassCourseByTeacherId(teacherId);
    }
    @Transactional(readOnly = true)
    public List<TeacherClasses> findTeacherClassesByTeacherId(String id) {
        List<TeacherClasses> teacherClasses = teacherClassesDao.findTeacherClassesByTeacherId(id);

        return teacherClasses;
    }

    /**
     * 通过班级id和课程id获取教师信息
     * @param classId
     * @param courseId
     * @return
     */
    public List<Teacher> findTeacherByClassAndCourse(String classId,String courseId){
        return teacherClassesDao.findTeacherByClassAndCourse(classId,courseId);
    }

    public List<TeacherClasses> findTeacherClasses(String id) {
        return teacherClassesDao.findTeacherClasses(id);
    }

    /**
     * 批量导入授课信息
     * @param schoolId
     * @param teachingInfos
     * @return
     */
    public Map<String, Object> batchSaveTeaching(String schoolId, List<TeachingInfo> teachingInfos) {
        Map<String, Object> result = new HashMap<>();
        List<String> errors = new ArrayList<>();
        String nt = DateUtil.now();
        //查询这个学校的所有老师
        Teacher tt = new Teacher();
        tt.setSchoolId(schoolId);
        tt.setStatus(Constant.STATUS.TEACHER_ON_LINE);
        tt.setPersonType(Constant.PERSON_TYPE.TEACHER);
        tt.setPager(new Pager().setPaging(false).setIncludes("id","workNumber","name"));
        List<Teacher> teachers = teacherDao.findTeacherListByCondition(tt);
        if(CollectionUtil.isEmpty(teachers)){
            result.put("code", "222");
            result.put("errors", errors.add("该学校没有老师！"));
            return result;
        }
        //查询这个学校所有班级 按年级分组
        JwClasses ct = new JwClasses();
        ct.setSchoolId(schoolId);
        ct.setPager(new Pager().setPaging(false).setIncludes("id","gradeName","gradeId","number"));
        List<JwClasses> jwClassesList = jwClassesDao.findJwClassesListByCondition(ct);
        if(CollectionUtil.isEmpty(jwClassesList)){
            result.put("code", "222");
            result.put("errors", errors.add("该学校没有班级！"));
            return result;
        }
        //查询这个学校的所有课程
        JwCourse cot = new JwCourse();
        cot.setSchoolId(schoolId);
        cot.setPager(new Pager().setPaging(false).setIncludes("id","alias"));
        List<JwCourse> courses = jwCourseDao.findJwCourseListByCondition(cot);
        if(CollectionUtil.isEmpty(courses)){
            result.put("code", "222");
            result.put("errors", errors.add("该学校没有课程！"));
            return result;
        }
        //进行循环校验
        List<String> teacherIds = new ArrayList<>(teachingInfos.size());
        List<TeacherClasses> teacherClassesList = new ArrayList<>(teachingInfos.size());
        int i = 0;
        for (TeachingInfo info: teachingInfos) {
            i++;
            StringBuffer error = new StringBuffer();
            if(teachingInfos.stream().filter(r->{
                boolean single = false;
                if(((r.getClassNumber()==null&&info.getClassNumber()==null)||(r.getClassNumber().equals(info.getClassNumber())))
                   && ((r.getGradeName()==null&&info.getGradeName()==null)||(r.getGradeName().equals(info.getGradeName())))
                   && ((r.getSubjectName()==null&&info.getSubjectName()==null)||(r.getSubjectName().equals(info.getSubjectName())))
                   && ((r.getTeacherName()==null&&info.getTeacherName()==null)||(r.getTeacherName().equals(info.getTeacherName())))
                   && ((r.getWorkNumber()==null&&info.getWorkNumber()==null)||(r.getWorkNumber().equals(info.getWorkNumber())))
                ){
                    single = true;
                }
                return single;
            }).count()>1){
                error.append("不能和其余数据重复");
                errors.add("第"+i+"行，"+error.toString());
            }else{
                //校验工号和名称 获取对应老师
                Teacher teacher = new Teacher();
                if(StringUtils.isEmpty(info.getWorkNumber())){
                    error.append("工号不能为空；");
                } else if(StringUtils.isEmpty(info.getTeacherName())){
                    error.append("教师名称不能为空；");
                } else {
                    teacher = teachers.stream().filter(t->t.getWorkNumber().equals(info.getWorkNumber().trim())&&t.getName().equals(info.getTeacherName().trim())).findFirst().orElse(null);
                    if(teacher == null){
                        error.append("教师工号和教师名称不匹配；");
                    } else {
                        teacherIds.add(teacher.getId());
                    }
                }
                if(StringUtils.isEmpty(info.getGradeName())&&StringUtils.isEmpty(info.getClassNumber())&&StringUtils.isEmpty(info.getSubjectName())){
                    //年级班级课程都为空 则只清空 教师授课信息
                    if(error.length()>0){
                        errors.add("第"+i+"行，"+error.toString());
                    }
                }else{
                    //校验年级和班级
                    JwClasses classes = new JwClasses();
                    if (StringUtils.isEmpty(info.getGradeName())) {
                        error.append("年级不能为空；");
                    } else if(StringUtils.isEmpty(info.getClassNumber())){
                        error.append("班号不能为空；");
                    } else {
                        classes = jwClassesList.stream().filter(c -> c.getGradeName().equals(info.getGradeName().trim())&&c.getNumber().equals(info.getClassNumber().trim())).findFirst().orElse(null);
                        if(classes==null){
                            error.append("学校年级或者班号不存在；");
                        }
                    }
                    //校验课程
                    JwCourse course = new JwCourse();
                    if(StringUtils.isEmpty(info.getSubjectName())){
                        error.append("课程不能为空；");
                    } else {
                        course = courses.stream().filter(c->c.getAlias().equals(info.getSubjectName().trim())).findFirst().orElse(null);
                        if(course==null){
                            error.append("学校课程不存在");
                        }
                    }
                    if(error.length()>0){
                        errors.add("第"+i+"行，"+error.toString());
                    }else{
                        teacherClassesList.add(new TeacherClasses().setUpdateTime(nt)
                                .setId(sequenceId.nextId()).setTeacherId(teacher.getId())
                                .setClassesId(classes.getId()).setCourseId(course.getId())
                                .setSchoolId(schoolId));
                    }
                }
            }
        }
        if(CollectionUtil.isNotEmpty(errors)){
            result.put("code","222");
            result.put("errors", errors);
            return result;
        }
        //删除原先的记录
        if(CollectionUtil.isNotEmpty(teacherIds))
            teacherClassesDao.deleteTeacherClassesByTeachers(teacherIds);
        //批量新增
        if(CollectionUtil.isNotEmpty(teacherClassesList))
            teacherClassesDao.batchSaveTeacherClasses(teacherClassesList);
        result.put("code","200");
        return result;
    }
}
