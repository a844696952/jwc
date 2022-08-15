package com.yice.edu.cn.jw.dao.parent;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.parent.Parent;
import com.yice.edu.cn.common.pojo.jw.parent.ParentStudent;
import com.yice.edu.cn.common.pojo.jw.parent.ParentStudentFile;
import com.yice.edu.cn.common.pojo.jw.student.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.RequestBody;

@Mapper
public interface IParentDao {
    List<Parent> findParentListByCondition(Parent parent);

    Parent findOneParentByCondition(Parent parent);

    long findParentCountByCondition(Parent parent);

    Parent findParentById(String id);

    void saveParent(Parent parent);

    void saveParentStudent(ParentStudent parentStudent);

    void updateParent(Parent parent);

    void updateParent1(Parent parent);

    void updatePassword(Parent parent);

    void deleteParent(String id);

    void deleteParentByCondition(Parent parent);

    void deleteParentStudentByCondition(ParentStudent parentStudent);

    void deleteParentStudentByParentId(ParentStudent parentStudent);

    void batchSaveParent(List<Parent> parent);

    List<ParentStudent> findParentStudentListByCondition(ParentStudent parentStudent);

    void updateParentStudent(ParentStudent parentStudent);

    void setStudentidToNull(Parent parent);

    List<Student> getBoundStudentList(ParentStudent parentStudent);

    List<ParentStudentFile> getBoundStudentListInCenter(ParentStudent parentStudent);

    @Update("UPDATE jw_parent  SET open_id = NULL WHERE open_id = #{openId}")
    void unbindOpenIdParent(@Param("openId") String openId);

    Parent findParentByStudentId(String id);

    ParentStudentFile findParentMsgByStudentId(String id);

    void deleteParentStudentByShiftpromotion(@RequestBody List<String> classId);

    /*List<String> findStudentIdByClassId(List<String> classId);*/

    List<ParentStudent> findSchoolByParentId(ParentStudent parentStudent);

    List<String> removeParentIdByClassList(@RequestBody List<String> classId);

}
