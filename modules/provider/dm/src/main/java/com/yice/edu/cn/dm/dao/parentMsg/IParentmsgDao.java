package com.yice.edu.cn.dm.dao.parentMsg;

import com.yice.edu.cn.common.pojo.dm.parentMsg.Parentmsg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IParentmsgDao {
    List<Parentmsg> findParentmsgListByCondition(Parentmsg parentmsg);

    long findParentmsgCountByCondition(Parentmsg parentmsg);

    Parentmsg findOneParentmsgByCondition(Parentmsg parentmsg);

    Parentmsg findParentmsgById(@Param("id") String id);

    void saveParentmsg(Parentmsg parentmsg);

    void updateParentmsg(Parentmsg parentmsg);

    void deleteParentmsg(@Param("id") String id);

    void deleteParentmsgByCondition(Parentmsg parentmsg);

    void deleteParentMsgBySchoolId(@Param("schoolId")String schoolId);

    void batchSaveParentmsg(List<Parentmsg> parentmsgs);

    void deleteParentmsgByState(Parentmsg parentmsg);

    void updateParentmsgByStuCardNo(Parentmsg parentmsg);

    void deleteParentMsgTwoDayBefore();

    void deleteParentmsgByClassIds(@Param("clazzIds") List<String> clazzIds);
}
