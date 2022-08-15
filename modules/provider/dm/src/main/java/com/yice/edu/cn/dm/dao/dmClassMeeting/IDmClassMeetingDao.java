package com.yice.edu.cn.dm.dao.dmClassMeeting;

import com.yice.edu.cn.common.pojo.dm.dmClassMeeting.DmClassMeeting;
import com.yice.edu.cn.common.pojo.dm.schoolActive.DmAttachmentFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IDmClassMeetingDao {
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<DmClassMeeting> findDmClassMeetingListByCondition(DmClassMeeting dmClassMeeting);

    long findDmClassMeetingCountByCondition(DmClassMeeting dmClassMeeting);

    DmClassMeeting findOneDmClassMeetingByCondition(DmClassMeeting dmClassMeeting);

    DmClassMeeting findDmClassMeetingById(@Param("id") String id);

    void saveDmClassMeeting(DmClassMeeting dmClassMeeting);

    void updateDmClassMeeting(DmClassMeeting dmClassMeeting);

    void deleteDmClassMeeting(@Param("id") String id);

    void deleteDmClassMeetingByCondition(DmClassMeeting dmClassMeeting);

    void batchSaveDmClassMeeting(List<DmClassMeeting> dmClassMeetings);

    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    List<DmAttachmentFile> findDmClassMeetingImgsByclassId(String classId);
}
