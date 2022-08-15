package com.yice.edu.cn.xw.dao.teacherattendance;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.teacherattendance.XwTeacherImageInput;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IXwTeacherImageInputDao {
    List<XwTeacherImageInput> findXwTeacherImageInputListByCondition(XwTeacherImageInput xwTeacherImageInput);

    long findXwTeacherImageInputCountByCondition(XwTeacherImageInput xwTeacherImageInput);

    XwTeacherImageInput findOneXwTeacherImageInputByCondition(XwTeacherImageInput xwTeacherImageInput);

    XwTeacherImageInput findXwTeacherImageInputById(@Param("id") String id);

    List<XwTeacherImageInput> findXwTeacherImageInputByTeacherId(
            @Param("teacherId") String teacherId,
            @Param("schoolId") String schoolId);

    void saveXwTeacherImageInput(XwTeacherImageInput xwTeacherImageInput);

    void updateXwTeacherImageInputTeahcer(XwTeacherImageInput xwTeacherImageInput);

    void deleteXwTeacherImageInput(@Param("id") String id);

    void deleteXwTeacherImageInputByCondition(XwTeacherImageInput xwTeacherImageInput);

    void batchSaveXwTeacherImageInput(List<XwTeacherImageInput> xwTeacherImageInputs);

    List<XwTeacherImageInput> findXwTeacherImageInputListAlls(XwTeacherImageInput xwTeacherImageInput);

    long findXwTeacherImageInputListAllCount(@Param("schoolId") String schoolId, @Param("status") int status);

    List<XwTeacherImageInput> findXwTeacherImageInputByTeacherName(@Param("teacherName") String teacherName
            , @Param("schoolId") String schoolId);

    long findXwTeacherImageInputByTeacherNameCount(@Param("teacherName") String teacherName
            , @Param("schoolId") String schoolId);

    List<String> findXwTeacherleft(@Param("schoolId") String schoolId);
}
