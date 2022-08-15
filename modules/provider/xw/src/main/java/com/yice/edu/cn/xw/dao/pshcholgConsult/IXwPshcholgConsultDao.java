package com.yice.edu.cn.xw.dao.pshcholgConsult;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.psycholgConsult.XwPshcholgConsult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IXwPshcholgConsultDao {
    List<XwPshcholgConsult> findXwPshcholgConsultListByCondition(XwPshcholgConsult xwPshcholgConsult);

    XwPshcholgConsult findOneXwPshcholgConsultByCondition(XwPshcholgConsult xwPshcholgConsult);

    long findXwPshcholgConsultCountByCondition(XwPshcholgConsult xwPshcholgConsult);

    XwPshcholgConsult findXwPshcholgConsultById(@Param("id") String id);

    void saveXwPshcholgConsult(XwPshcholgConsult xwPshcholgConsult);

    void updateXwPshcholgConsult(XwPshcholgConsult xwPshcholgConsult);

    void deleteXwPshcholgConsult(@Param("id") String id);

    void deleteXwPshcholgConsultByCondition(XwPshcholgConsult xwPshcholgConsult);

    void batchSaveXwPshcholgConsult(List<XwPshcholgConsult> xwPshcholgConsults);

    List<XwPshcholgConsult> findXwPshcholgConsultByCondition2(XwPshcholgConsult xwPshcholgConsult);

    Long findXwPshcholgConsultCountByCondition2(XwPshcholgConsult xwPshcholgConsult);
}
