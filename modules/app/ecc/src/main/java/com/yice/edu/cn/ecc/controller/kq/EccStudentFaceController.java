package com.yice.edu.cn.ecc.controller.kq;

import cn.hutool.core.collection.CollUtil;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.classCard.DmClassCard;
import com.yice.edu.cn.common.pojo.dm.kq.EccStudentFace;
import com.yice.edu.cn.common.pojo.dm.kq.ProtectedStudent;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import com.yice.edu.cn.common.util.oss.QiniuUtil;
import com.yice.edu.cn.ecc.service.kq.EccStudentFaceService;
import com.yice.edu.cn.ecc.service.student.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static com.yice.edu.cn.ecc.interceptor.LoginInterceptor.currentDmClassCard;

/**
 * @author dengfengfeng
 */
@RequestMapping("/kqEccStudentFace")
@RestController
@Api(value = "/云班牌-人脸操作",description = "云班牌-人脸操作")
public class EccStudentFaceController {

    @Autowired
    private EccStudentFaceService eccStudentFaceService;

    @Autowired
    private StudentService studentService;


    @ApiOperation(value="获取学生人脸录入数量统计", response= EccStudentFaceState.class)
    @GetMapping("/findEccStudentFaceState")
    public ResponseJson findEccStudentFaceState(){
        final DmClassCard dmClassCard = currentDmClassCard();
        Student student = new Student();
        student.setClassesId(dmClassCard.getClassId());
        student.setSchoolId(dmClassCard.getSchoolId());
        final List<Student> students = studentService.findStudentListByCondition(student);

        EccStudentFace eccStudentFace = new EccStudentFace();
        eccStudentFace.setClassesId(dmClassCard.getClassId());
        eccStudentFace.setSchoolId(dmClassCard.getSchoolId());
        eccStudentFace.setGradeId(dmClassCard.getGradeId());
        List<EccStudentFace> faceList = eccStudentFaceService.findEccStudentFaceListByCondition(eccStudentFace);
        Map<String, List<EccStudentFace>> studentFaceMap = faceList.stream().collect(Collectors.groupingBy(EccStudentFace::getStudentId));
        AtomicLong haveFaceCount = new AtomicLong();
        final List<ProtectedStudent> protectedStudents= students.stream()
                .map(s->{
                    ProtectedStudent protectedStudent = new ProtectedStudent(s);
                    List<EccStudentFace> eccStudentFaces = studentFaceMap.get(s.getId());
                    EccStudentFace studentFace=null;
                    if(CollUtil.isNotEmpty(eccStudentFaces)){
                        List<EccStudentFace> collect = eccStudentFaces.stream().filter(x -> StringUtils.isNoneBlank(x.getGradeId())).collect(Collectors.toList());
                        if(CollUtil.isNotEmpty(collect)){
                            studentFace=collect.get(0);
                        }else{
                            studentFace=eccStudentFaces.get(0);
                        }
                    }
                    if(studentFace!=null){
                        protectedStudent.setHead(studentFace.getFaceImg());
                        protectedStudent.setFaceId(studentFace.getId());
                        haveFaceCount.incrementAndGet();
                    }
                    return protectedStudent;
                })
                .sorted(Comparator.comparing(ProtectedStudent::getStudentId))
                .collect(Collectors.toList());
        return new ResponseJson(new EccStudentFaceState(haveFaceCount.get(),protectedStudents));
    }

    /**
     * 保存云班牌录入的人脸
     * @param studentId
     * @param file
     * @return
     */
    @ApiOperation(value="保存云班牌录入的人脸", response= EccStudentFace.class)
    @PostMapping("/saveEccStudentFace/{studentId}")
    public ResponseJson saveEccStudentFace(@PathVariable("studentId")String studentId, MultipartFile file) {
        EccStudentFace param = new EccStudentFace();
        final DmClassCard dmClassCard = currentDmClassCard();
        param.setClassesId(dmClassCard.getClassId());
        param.setStudentId(studentId);
        param.setGradeId(dmClassCard.getGradeId());
        param.setSchoolId(dmClassCard.getSchoolId());
        long count = eccStudentFaceService.findEccStudentFaceCountByCondition(param);
        if(count>0){
            return new ResponseJson(false,"该学生已录入过人脸");
        }
        EccStudentFace eccStudentFace = getEccStudentFace(studentId,dmClassCard.getGradeId(), file);
        EccStudentFace result = eccStudentFaceService.saveEccStudentFace(eccStudentFace);
        return new ResponseJson(result);
    }


    /**
     * 修改云班牌录入的人脸
     * @param faceId
     * @param file
     * @return
     */
    @ApiOperation(value="修改云班牌录入的人脸", response= String.class)
    @PostMapping("/updateEccStudentFace/{faceId}")
    public ResponseJson updateEccStudentFace(@PathVariable("faceId")String faceId, MultipartFile file) {
        EccStudentFace eccStudentFace = eccStudentFaceService.findEccStudentFaceById(faceId);
        if(eccStudentFace == null){
            return new ResponseJson(false,"人脸不存在");
        }
        eccStudentFace.setFaceImg(QiniuUtil.uploadImage(file, "eccStudentImg"));
        eccStudentFaceService.updateEccStudentFace(eccStudentFace);
        return new ResponseJson("修改成功");
    }

    @ApiOperation(value="根据人脸id删除云班牌录入的人脸", response= String.class)
    @DeleteMapping("/delete/{faceId}")
    public ResponseJson deleteFace(@PathVariable("faceId")String faceId) {
        eccStudentFaceService.deleteFace(faceId);
        return new ResponseJson("删除成功");
    }



    private EccStudentFace getEccStudentFace(String studentId,String gradeId, MultipartFile file) {
        final Student student = studentService.findStudentById(studentId);
        EccStudentFace eccStudentFace = new EccStudentFace();
        eccStudentFace.setStudentId(studentId);
        eccStudentFace.setStudent(student);
        eccStudentFace.setClassesId(student.getClassesId());
        eccStudentFace.setSchoolId(student.getSchoolId());
        eccStudentFace.setGradeId(gradeId);
        final String eccStudentImg = QiniuUtil.uploadImage(file, "eccStudentImg");
        eccStudentFace.setFaceImg(eccStudentImg);
        return eccStudentFace;
    }

    private EccStudentFace getEccStudentFace(EccStudentFace studentFace) {
        final Student student = studentService.findStudentById(studentFace.getId());
        EccStudentFace eccStudentFace = new EccStudentFace();
        eccStudentFace.setStudentId(studentFace.getId());
        eccStudentFace.setStudent(student);
        eccStudentFace.setClassesId(student.getClassesId());
        eccStudentFace.setSchoolId(student.getSchoolId());
        return eccStudentFace;
    }

    private class EccStudentFaceState {

        private long all;
        private long havaFace;
        private long noFace;
        private List<ProtectedStudent> students;

        public EccStudentFaceState(long havaFace,List<ProtectedStudent> students) {
            this.all = students.size();
            this.havaFace = havaFace;
            this.noFace = all - havaFace;
            this.students = students;
        }

        public long getAll() {
            return all;
        }

        public void setAll(long all) {
            this.all = all;
        }

        public long getHavaFace() {
            return havaFace;
        }

        public void setHavaFace(long havaFace) {
            this.havaFace = havaFace;
        }

        public long getNoFace() {
            return noFace;
        }

        public void setNoFace(long noFace) {
            this.noFace = noFace;
        }

        public List<ProtectedStudent> getStudents() {
            return students;
        }

        public void setStudents(List<ProtectedStudent> students) {
            this.students = students;
        }
    }

}