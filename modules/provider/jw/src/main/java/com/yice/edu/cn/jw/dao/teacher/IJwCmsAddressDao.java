package com.yice.edu.cn.jw.dao.teacher;

import com.yice.edu.cn.common.pojo.jw.teacher.JwCmsAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IJwCmsAddressDao {
    /*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    List<JwCmsAddress> findJwCmsAddressListByCondition(JwCmsAddress jwCmsAddress);

    List<JwCmsAddress> getJwCmsAddress();

    long findJwCmsAddressCountByCondition(JwCmsAddress jwCmsAddress);

    JwCmsAddress findOneJwCmsAddressByCondition(JwCmsAddress jwCmsAddress);

    JwCmsAddress findJwCmsAddressById(@Param("id") String id);

    void saveJwCmsAddress(JwCmsAddress jwCmsAddress);

    void updateJwCmsAddress(JwCmsAddress jwCmsAddress);

    void deleteJwCmsAddress(@Param("id") String id);

    void deleteJwCmsAddressByCondition(JwCmsAddress jwCmsAddress);

    void batchSaveJwCmsAddress(List<JwCmsAddress> jwCmsAddresss);
    /*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
}
