package com.yice.edu.cn.xw.dao.dj.partyMemberActivity;

import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivityAndUserVo;
import com.yice.edu.cn.common.pojo.xw.dj.partyMemberActivity.XwDjActivityUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IXwDjActivityUserDao {

    /**
     * @description:c查询发送方活动列表
     * @param xwDjActivityUser
     * @return
     */
    List<XwDjActivityAndUserVo> findXwDjActivityUserListByCondition(XwDjActivityUser xwDjActivityUser);

    List<XwDjActivityUser> findXwDjActivityUserListCondition(XwDjActivityUser xwDjActivityUser);

    List<XwDjActivityUser> findXwDjActivityUserListIsSignUpLeader(XwDjActivityUser xwDjActivityUser);

    List<XwDjActivityUser> findUserListByCondition(XwDjActivityUser xwDjActivityUser);

    List<XwDjActivityUser> findIsAbsenceList(XwDjActivityUser xwDjActivityUser);

    long findUserListCountByCondition(XwDjActivityUser xwDjActivityUser);

    long findIsAbsenceCount(XwDjActivityUser xwDjActivityUser);

    long findXwDjActivityUserCountByCondition(XwDjActivityUser xwDjActivityUser);

    XwDjActivityUser findOneXwDjActivityUserByCondition(XwDjActivityUser xwDjActivityUser);

    XwDjActivityUser findXwDjActivityUserById(@Param("id") String id);

    long findXwDjActivityUserCountByActivityUserId(@Param("id") String id);

    void saveXwDjActivityUser(XwDjActivityUser xwDjActivityUser);

    void updateXwDjActivityUser(XwDjActivityUser xwDjActivityUser);

    void askForLeave(XwDjActivityUser xwDjActivityUser);

    void signUp(XwDjActivityUser xwDjActivityUser);

    void autoSignIn(XwDjActivityUser xwDjActivityUser);

    void signIn(XwDjActivityUser xwDjActivityUser);

    void deleteXwDjActivityUser(@Param("id") String id);

    void deleteXwDjActivityUserByCondition(XwDjActivityUser xwDjActivityUser);

    void deleteXwDjActivityUserByActivityId(@Param("activityId") String activityId);

    void saveBatchXwDjActivityUser(List<XwDjActivityUser> xwDjActivityUsers);

}
