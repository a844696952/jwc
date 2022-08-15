package com.yice.edu.cn.dm.dao.wb.groupManage;

import com.yice.edu.cn.common.pojo.wb.groupManage.StudentGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IStudentGroupDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<StudentGroup> findStudentGroupListByCondition(StudentGroup studentGroup);

    long findStudentGroupCountByCondition(StudentGroup studentGroup);

    StudentGroup findOneStudentGroupByCondition(StudentGroup studentGroup);

    StudentGroup findStudentGroupById(@Param("id") String id);

    void saveStudentGroup(StudentGroup studentGroup);

    void updateStudentGroup(StudentGroup studentGroup);

    void deleteStudentGroup(@Param("id") String id);

    void deleteStudentGroupByCondition(StudentGroup studentGroup);

    void batchSaveStudentGroup(List<StudentGroup> studentGroups);

    void deleteStudentGroupByGroupId(@Param("groupId") String groupId);

    void deleteStudentGroupByClassIds(List<String> classIdList);

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
