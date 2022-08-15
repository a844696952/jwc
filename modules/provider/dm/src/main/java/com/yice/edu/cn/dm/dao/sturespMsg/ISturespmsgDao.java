package com.yice.edu.cn.dm.dao.sturespMsg;

import java.util.List;

import com.yice.edu.cn.common.pojo.dm.sturespMsg.Sturespmsg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ISturespmsgDao {
    List<Sturespmsg> findSturespmsgListByCondition(Sturespmsg sturespmsg);

    long findSturespmsgCountByCondition(Sturespmsg sturespmsg);

    Sturespmsg findOneSturespmsgByCondition(Sturespmsg sturespmsg);

    Sturespmsg findSturespmsgById(@Param("id") String id);

    void saveSturespmsg(Sturespmsg sturespmsg);

    void updateSturespmsg(Sturespmsg sturespmsg);

    void deleteSturespmsg(@Param("id") String id);

    void deleteSturespmsgByCondition(Sturespmsg sturespmsg);

    void batchSaveSturespmsg(List<Sturespmsg> sturespmsgs);

    void deleteSturespmsgBeforeTwoDay();
}
