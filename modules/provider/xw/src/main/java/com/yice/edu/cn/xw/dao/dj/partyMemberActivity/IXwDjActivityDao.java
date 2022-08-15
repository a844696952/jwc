package com.yice.edu.cn.xw.dao.dj.partyMemberActivity;

import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivity;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivityAndUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IXwDjActivityDao {

    /**@description:查询活动列表
     * @param xwDjActivity 参数实体对象
     * @return List<XwDjActivity>
     */
    List<XwDjActivity> findXwDjActivityListByCondition(XwDjActivity xwDjActivity);

    List<XwDjActivity> findXwDjActivityTeacherListByCondition(XwDjActivity xwDjActivity);

    List<XwDjActivity> findXwDjActivityPartyMemberListByCondition(XwDjActivity xwDjActivity);

    long findXwDjActivityPartyMemberCountByCondition(XwDjActivity xwDjActivity);

    long findXwDjActivityCountByCondition(XwDjActivity xwDjActivity);

    long findXwDjActivityTeacherCountByCondition(XwDjActivity xwDjActivity);

    XwDjActivity findOneXwDjActivityByCondition(XwDjActivity xwDjActivity);

    XwDjActivity findXwDjActivityById(@Param("id") String id);

    long findXwDjActivityCountByColumnId(@Param("id") String id);

    long findXwDjActivityCountByActivityCreatorId(@Param("id") String id);

    XwDjActivityAndUserVo findTeacherXwDjActivityById(XwDjActivity xwDjActivityAndUserVo);

    void saveXwDjActivity(XwDjActivity xwDjActivity);

    void updateXwDjActivity(XwDjActivity xwDjActivity);

    void updateXwDjActivityStatus(XwDjActivity xwDjActivity);

    void deleteXwDjActivity(@Param("id") String id);

    void deleteXwDjActivityByCondition(XwDjActivity xwDjActivity);

    void batchSaveXwDjActivity(List<XwDjActivity> xwDjActivitys);

    void closeXwDjActivity(XwDjActivity xwDjActivity);

}
