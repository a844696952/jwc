package com.yice.edu.cn.dy.dao.classManage.mesAppletsPostPerm;

import java.util.List;
import java.util.Set;

import com.yice.edu.cn.common.pojo.jw.teacher.TeacherPost;
import com.yice.edu.cn.common.pojo.mes.classManage.mesAppletsPostPerm.MesAppletsPostPerm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IMesAppletsPostPermDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<MesAppletsPostPerm> findMesAppletsPostPermListByCondition(MesAppletsPostPerm mesAppletsPostPerm);

    long findMesAppletsPostPermCountByCondition(MesAppletsPostPerm mesAppletsPostPerm);

    MesAppletsPostPerm findOneMesAppletsPostPermByCondition(MesAppletsPostPerm mesAppletsPostPerm);

    MesAppletsPostPerm findMesAppletsPostPermById(@Param("id") String id);

    void saveMesAppletsPostPerm(MesAppletsPostPerm mesAppletsPostPerm);

    void updateMesAppletsPostPerm(MesAppletsPostPerm mesAppletsPostPerm);

    void deleteMesAppletsPostPerm(@Param("id") String id);

    void deleteMesAppletsPostPermByCondition(MesAppletsPostPerm mesAppletsPostPerm);

    void batchSaveMesAppletsPostPerm(List<MesAppletsPostPerm> mesAppletsPostPerms);
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    Set<Integer> findMesAppletsPostPermByPostId(List<TeacherPost> teacherPosts);
}
