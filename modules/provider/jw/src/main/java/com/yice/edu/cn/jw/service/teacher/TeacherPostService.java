package com.yice.edu.cn.jw.service.teacher;

import cn.hutool.core.date.DateUtil;
import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.Pager;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import com.yice.edu.cn.jw.dao.student.IStudentDao;
import com.yice.edu.cn.jw.dao.teacher.ITeacherPostDao;
import com.yice.edu.cn.jw.service.dd.DdService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherPostService {
    @Autowired
    private ITeacherPostDao teacherPostDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IStudentDao studentDao;
    @Autowired
    private DdService ddService;


    @Transactional(readOnly = true)
    public TeacherPost findTeacherPostById(String id) {
        return teacherPostDao.findTeacherPostById(id);
    }

    @Transactional
    public void saveTeacherPost(TeacherPost teacherPost) {
        teacherPost.setId(sequenceId.nextId());
        teacherPostDao.saveTeacherPost(teacherPost);
    }

    @Transactional(readOnly = true)
    public List<TeacherPost> findTeacherPostListByCondition(TeacherPost teacherPost) {
        return teacherPostDao.findTeacherPostListByCondition(teacherPost);
    }

    @Transactional(readOnly = true)
    public long findTeacherPostCountByCondition(TeacherPost teacherPost) {
        return teacherPostDao.findTeacherPostCountByCondition(teacherPost);
    }

    @Transactional
    public void updateTeacherPost(TeacherPost teacherPost) {
        teacherPostDao.updateTeacherPost(teacherPost);
    }

    @Transactional
    public void deleteTeacherPost(String id) {
        teacherPostDao.deleteTeacherPost(id);
    }

    @Transactional
    public void deleteTeacherPostByCondition(TeacherPost teacherPost) {
        teacherPostDao.deleteTeacherPostByCondition(teacherPost);
    }

    @Transactional
    public int editTeacherPost(TeacherPost teacherPost) {
        //是否当前用户重复添加
        TeacherPost old = teacherPostDao.findOneTeacherPostByCondition(teacherPost);
        if(old!=null){
            return 1;//已经存在
        }
        switch (teacherPost.getDdId()){
            case Constant.TEACHER_POST.PRINCIPAL:
                //校长限制
            break;
            case Constant.TEACHER_POST.GRADE_LEADER:
                //年段长限制
                //1、判断该年段是否存在段长
                old = new TeacherPost();
                old.setSchoolId(teacherPost.getSchoolId());
                old.setDdId(teacherPost.getDdId());
                old.setGradeId(teacherPost.getGradeId());
                old = teacherPostDao.findOneTeacherPostByCondition(old);
                if(old!=null){
                    return 2;//该年段存在段长
                }
                //2、判断本身是否是段长
                old = new TeacherPost();
                old.setSchoolId(teacherPost.getSchoolId());
                old.setDdId(teacherPost.getDdId());
                old.setTeacherId(teacherPost.getTeacherId());
                old = teacherPostDao.findOneTeacherPostByCondition(old);
                if(old!=null){
                    return 3;//已经是其他年段段长
                }
            break;
            case Constant.TEACHER_POST.CLASS_TEACHER:
                //班主任限制
                //1、判断该班是否已经有班主任
                old = new TeacherPost();
                old.setSchoolId(teacherPost.getSchoolId());
                old.setDdId(teacherPost.getDdId());
                old.setGradeId(teacherPost.getGradeId());
                old.setClassId(teacherPost.getClassId());
                old.setEnrollYear(teacherPost.getEnrollYear());
                old = teacherPostDao.findOneTeacherPostByCondition(old);
                if(old!=null){
                    return 5;//该班级存在班主任
                }
                //2、判断是否已经是班主任
                old = new TeacherPost();
                old.setSchoolId(teacherPost.getSchoolId());
                old.setDdId(teacherPost.getDdId());
                //old.setGradeId(teacherPost.getGradeId());
                old.setTeacherId(teacherPost.getTeacherId());
                old = teacherPostDao.findOneTeacherPostByCondition(old);
                if(old!=null){
                    return 6;//该老师已经是班主任
                }
            break;
        }
        //添加记录
        this.saveTeacherPost(teacherPost);
        return 200;
    }
    @Transactional(readOnly = true)
    public TeacherPost findOneTeacherPostByCondition(TeacherPost teacherPost) {
        return teacherPostDao.findOneTeacherPostByCondition(teacherPost);
    }

    public List<Teacher> findTeachersByPost(TeacherPost teacherPost) {
        return teacherPostDao.findTeachersByPost(teacherPost);
    }

    public List<TeacherPost> findTeacherListByPost(TeacherPost teacherPost) {
        return teacherPostDao.findTeacherListByPost(teacherPost);
    }

    public Long findStudentsCountByPower(TeacherPost teacherPost) {
        TeacherPost teacherPost11 = new TeacherPost();
        teacherPost11.setTeacherId(teacherPost.getTeacherId());
        teacherPost11.setTeacherId(teacherPost.getTeacherId());
        teacherPost11.setPager(new Pager().setPaging(false).setIncludes("name","ddId","gradeId","gradeName","classId","className","sort").setSortField("sort"));
        List<TeacherPost> teacherPosts = findTeacherPostListByCondition(teacherPost11);
        teacherPosts = teacherPosts.stream().filter(teacherPost1 -> teacherPost1.getSort()!=null).collect(Collectors.toList());
        teacherPosts = teacherPosts.stream().sorted(Comparator.comparing(TeacherPost::getSort)).collect(Collectors.toList());
        if (teacherPosts.size()==0){
            return null;
        }
        String ddId = teacherPosts.get(0).getDdId();
        final TeacherPost teacherPost1 = teacherPosts.get(0);
        Student student = new Student();
        student.setNowStatus(Constant.STUDENT_NOW_STATUS_TYPE.NOT_INT_SCHOOL);
        if (ddId.equals(Constant.TEACHER_POST.GRADE_LEADER)) {//段长
            if (student.getGradeId() != null && student.getGradeId().equals(teacherPost1.getGradeId())) {
                //返回本年段
                student.setGradeId(teacherPost1.getGradeId());
            } else if (student.getGradeId() == null) {
                //返回本年段
                student.setGradeId(teacherPost1.getGradeId());
            } else if (student.getGradeId() != null && !student.getGradeId().equals(teacherPost1.getGradeId())) {
                //是否是任班主任年段
                List<TeacherPost> classHeader = teacherPosts.stream().filter(t -> t.getDdId().equals(Constant.TEACHER_POST.CLASS_TEACHER)).collect(Collectors.toList());
                if (classHeader.size() > 0) {
                    //是班主任，判断指定的年段是否是班主任所在年段
                    TeacherPost teacherPost2 = classHeader.get(0);
                    if (student.getGradeId().equals(teacherPost2.getGradeId())) {
                        student.setGradeId(teacherPost2.getGradeId());
                        student.setClassesId(teacherPost2.getClassId());
                    } else {
                        return null;
                    }
                }
            }
            student.setSchoolId(teacherPost.getSchoolId());
            // List<Student> data = studentService.findStudentListByCondition(student);
            long count = studentDao.findStudentCountByCondition(student);
            return  count;
        } else if (ddId.equals(Constant.TEACHER_POST.CLASS_TEACHER)) {//班主任
            //返回本年段
            student.setClassesId(teacherPost1.getClassId());
            student.setSchoolId(teacherPost.getSchoolId());
          //  List<Student> data = studentService.findStudentListByCondition(student);
            long count = studentDao.findStudentCountByCondition(student);
            return  count;
        } else if (ddId.equals(Constant.TEACHER_POST.PRINCIPAL)) {//校长
            //返回所有年段
            student.setSchoolId(teacherPost.getSchoolId());
           // List<Student> data = studentService.findStudentListByCondition(student);
            long count = studentDao.findStudentCountByCondition(student);
            return  count;
        }

        return null;
    }
    /**
     * 班级升班携带年段长
     * @param teacherPosts
     */
    @Transactional
    public void GradeLeaderPromotionCarry(String schoolId,List<TeacherPost> teacherPosts){
        //先删除原先所有的年段长
        TeacherPost teacherPost = new TeacherPost();
        teacherPost.setSchoolId(schoolId);
        teacherPost.setDdId(Constant.TEACHER_POST.GRADE_LEADER);
        this.deleteTeacherPostByCondition(teacherPost);
        if(teacherPosts!=null && !teacherPosts.isEmpty()){
            String nowTime = DateUtil.now();
            List<TeacherPost> posts = teacherPosts.stream().filter(tp->StringUtils.isNotEmpty(tp.getTeacherId()))
                    .map(tp->{
                        tp.setCreateTime(nowTime);
                        tp.setId(sequenceId.nextId());
                        tp.setUpdateTime(nowTime);
                        return tp;
                    }).collect(Collectors.toList());
            if(posts!=null&&!posts.isEmpty())
                teacherPostDao.batchSaveTeacherPost(posts);
        }
    }
    /**
     * 升班获取年段长 默认循环+1
     * @param schoolId,gradeList
     */
    public List<TeacherPost> findGradeTeacherBySchool(String schoolId, List<Dd> gradeList){
        TeacherPost teacherPost = new TeacherPost();
        teacherPost.setSchoolId(schoolId);
        teacherPost.setDdId(Constant.TEACHER_POST.GRADE_LEADER);
        List<TeacherPost> list = this.findTeacherPostListByCondition(teacherPost);
        final Dd d = ddService.findDdById(Constant.TEACHER_POST.GRADE_LEADER);
        final TeacherPost last = new TeacherPost();
        if(list!=null&&list.size()>0){
            TeacherPost t1 = list.stream().filter(tp->tp.getGradeId().equals(gradeList.get(gradeList.size()-1).getId())).findFirst().orElse(null);
            if(t1!=null){
                last.setTeacherId(t1.getTeacherId());
                last.setTeacherName(t1.getTeacherName());
            }
        }

        //没拍好顺序 找调用接口的人
        return gradeList.stream().map(grade->{
            TeacherPost tp = new TeacherPost();
            tp.setGradeId(grade.getId());
            tp.setGradeName(grade.getName());
            if(gradeList.indexOf(grade)==0){
                //第一个年级
                if(last.getTeacherId() != null){
                    tp.setTeacherId(last.getTeacherId());
                    tp.setTeacherName(last.getTeacherName());
                }
            }else{
                Integer ddInt = Integer.parseInt(grade.getId());
                TeacherPost theOne = list.stream().filter(t->String.valueOf(ddInt-1).equals(t.getGradeId())).findFirst().orElse(null);
                if(theOne != null){
                    tp.setTeacherId(theOne.getTeacherId());
                    tp.setTeacherName(theOne.getTeacherName());
                }
            }
            tp.setSchoolId(schoolId);
            tp.setSort(d.getSort());
            tp.setDdId(d.getId());
            tp.setName(d.getName());
            return tp;
        }).collect(Collectors.toList());
        //return list;
    }

    /**
     * 获取学校各个年段 年段长
     * @param schoolId
     * @return
     */
    public List<TeacherPost> findTeachers4Grade(String schoolId){
        return teacherPostDao.findTeachers4Grade(schoolId);
    }

    /**
     * 获取学校 各班班主任
     * @param teacherPost
     * @return
     */
    public List<TeacherPost> findTeachers4Class(TeacherPost teacherPost){
        return teacherPostDao.findTeachers4Class(teacherPost);
    }
}
