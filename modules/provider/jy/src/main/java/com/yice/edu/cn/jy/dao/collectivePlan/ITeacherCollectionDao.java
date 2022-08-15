package com.yice.edu.cn.jy.dao.collectivePlan;

import java.util.List;

import com.yice.edu.cn.common.pojo.jw.teacher.Teacher;
import com.yice.edu.cn.common.pojo.jy.collectivePlan.TeacherCollection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ITeacherCollectionDao {
    List<TeacherCollection> findTeacherCollectionListByCondition(TeacherCollection teacherCollection);

    TeacherCollection findOneTeacherCollectionByCondition(TeacherCollection teacherCollection);

    long findTeacherCollectionCountByCondition(TeacherCollection teacherCollection);

    TeacherCollection findTeacherCollectionById(@Param("id") String id);

    void saveTeacherCollection(TeacherCollection teacherCollection);

    void updateTeacherCollection(TeacherCollection teacherCollection);

    void deleteTeacherCollection(@Param("id") String id);

    void deleteTeacherCollectionByCondition(TeacherCollection teacherCollection);

    void batchSaveTeacherCollection(List<TeacherCollection> teacherCollections);

    //对集体备课教案的修改次数  进行增加
    void updateModifyCount(TeacherCollection teacherCollection);

    //对集体备课教案的 评论次数 进行增加
    void updateCommentCount(TeacherCollection teacherCollection);

    //评论次数减少
    void updateCommentCountReduce(TeacherCollection teacherCollection);
}
