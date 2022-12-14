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
        //??????????????????????????????
        TeacherPost old = teacherPostDao.findOneTeacherPostByCondition(teacherPost);
        if(old!=null){
            return 1;//????????????
        }
        switch (teacherPost.getDdId()){
            case Constant.TEACHER_POST.PRINCIPAL:
                //????????????
            break;
            case Constant.TEACHER_POST.GRADE_LEADER:
                //???????????????
                //1????????????????????????????????????
                old = new TeacherPost();
                old.setSchoolId(teacherPost.getSchoolId());
                old.setDdId(teacherPost.getDdId());
                old.setGradeId(teacherPost.getGradeId());
                old = teacherPostDao.findOneTeacherPostByCondition(old);
                if(old!=null){
                    return 2;//?????????????????????
                }
                //2??????????????????????????????
                old = new TeacherPost();
                old.setSchoolId(teacherPost.getSchoolId());
                old.setDdId(teacherPost.getDdId());
                old.setTeacherId(teacherPost.getTeacherId());
                old = teacherPostDao.findOneTeacherPostByCondition(old);
                if(old!=null){
                    return 3;//???????????????????????????
                }
            break;
            case Constant.TEACHER_POST.CLASS_TEACHER:
                //???????????????
                //1???????????????????????????????????????
                old = new TeacherPost();
                old.setSchoolId(teacherPost.getSchoolId());
                old.setDdId(teacherPost.getDdId());
                old.setGradeId(teacherPost.getGradeId());
                old.setClassId(teacherPost.getClassId());
                old.setEnrollYear(teacherPost.getEnrollYear());
                old = teacherPostDao.findOneTeacherPostByCondition(old);
                if(old!=null){
                    return 5;//????????????????????????
                }
                //2?????????????????????????????????
                old = new TeacherPost();
                old.setSchoolId(teacherPost.getSchoolId());
                old.setDdId(teacherPost.getDdId());
                //old.setGradeId(teacherPost.getGradeId());
                old.setTeacherId(teacherPost.getTeacherId());
                old = teacherPostDao.findOneTeacherPostByCondition(old);
                if(old!=null){
                    return 6;//???????????????????????????
                }
            break;
        }
        //????????????
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
        if (ddId.equals(Constant.TEACHER_POST.GRADE_LEADER)) {//??????
            if (student.getGradeId() != null && student.getGradeId().equals(teacherPost1.getGradeId())) {
                //???????????????
                student.setGradeId(teacherPost1.getGradeId());
            } else if (student.getGradeId() == null) {
                //???????????????
                student.setGradeId(teacherPost1.getGradeId());
            } else if (student.getGradeId() != null && !student.getGradeId().equals(teacherPost1.getGradeId())) {
                //???????????????????????????
                List<TeacherPost> classHeader = teacherPosts.stream().filter(t -> t.getDdId().equals(Constant.TEACHER_POST.CLASS_TEACHER)).collect(Collectors.toList());
                if (classHeader.size() > 0) {
                    //??????????????????????????????????????????????????????????????????
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
        } else if (ddId.equals(Constant.TEACHER_POST.CLASS_TEACHER)) {//?????????
            //???????????????
            student.setClassesId(teacherPost1.getClassId());
            student.setSchoolId(teacherPost.getSchoolId());
          //  List<Student> data = studentService.findStudentListByCondition(student);
            long count = studentDao.findStudentCountByCondition(student);
            return  count;
        } else if (ddId.equals(Constant.TEACHER_POST.PRINCIPAL)) {//??????
            //??????????????????
            student.setSchoolId(teacherPost.getSchoolId());
           // List<Student> data = studentService.findStudentListByCondition(student);
            long count = studentDao.findStudentCountByCondition(student);
            return  count;
        }

        return null;
    }
    /**
     * ???????????????????????????
     * @param teacherPosts
     */
    @Transactional
    public void GradeLeaderPromotionCarry(String schoolId,List<TeacherPost> teacherPosts){
        //?????????????????????????????????
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
     * ????????????????????? ????????????+1
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

        //??????????????? ?????????????????????
        return gradeList.stream().map(grade->{
            TeacherPost tp = new TeacherPost();
            tp.setGradeId(grade.getId());
            tp.setGradeName(grade.getName());
            if(gradeList.indexOf(grade)==0){
                //???????????????
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
     * ???????????????????????? ?????????
     * @param schoolId
     * @return
     */
    public List<TeacherPost> findTeachers4Grade(String schoolId){
        return teacherPostDao.findTeachers4Grade(schoolId);
    }

    /**
     * ???????????? ???????????????
     * @param teacherPost
     * @return
     */
    public List<TeacherPost> findTeachers4Class(TeacherPost teacherPost){
        return teacherPostDao.findTeachers4Class(teacherPost);
    }
}
