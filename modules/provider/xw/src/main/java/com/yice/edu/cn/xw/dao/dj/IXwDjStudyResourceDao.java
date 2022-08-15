package com.yice.edu.cn.xw.dao.dj;

import java.util.List;

import com.yice.edu.cn.common.dto.xw.StudyTeacherDto;
import com.yice.edu.cn.common.pojo.xw.dj.XwDjStudyResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IXwDjStudyResourceDao {
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<XwDjStudyResource> findXwDjStudyResourceListByCondition(XwDjStudyResource xwDjStudyResource);

    long findXwDjStudyResourceCountByCondition(XwDjStudyResource xwDjStudyResource);

    XwDjStudyResource findOneXwDjStudyResourceByCondition(XwDjStudyResource xwDjStudyResource);

    XwDjStudyResource findXwDjStudyResourceById(@Param("id") String id);

    void saveXwDjStudyResource(XwDjStudyResource xwDjStudyResource);

    void updateXwDjStudyResource(XwDjStudyResource xwDjStudyResource);

    void deleteXwDjStudyResource(@Param("id") String id);

    void deleteXwDjStudyResourceByCondition(XwDjStudyResource xwDjStudyResource);

    void batchSaveXwDjStudyResource(List<XwDjStudyResource> xwDjStudyResources);

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    void saveXwDjStudyResourceDraft(XwDjStudyResource xwDjStudyResource);

    void updateXwDjStudyResourceDraft(XwDjStudyResource xwDjStudyResource);

}
