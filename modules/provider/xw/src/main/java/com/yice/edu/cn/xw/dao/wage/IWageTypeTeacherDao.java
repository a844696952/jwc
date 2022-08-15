package com.yice.edu.cn.xw.dao.wage;

import java.util.List;
import java.util.Map;

import com.yice.edu.cn.common.pojo.xw.wage.WageType;
import com.yice.edu.cn.common.pojo.xw.wage.WageTypeTeacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IWageTypeTeacherDao {
    List<WageTypeTeacher> findWageTypeTeacherListByCondition(WageTypeTeacher wageTypeTeacher);

    List<WageTypeTeacher> findWageTypeTeacherListByWorkNum(WageTypeTeacher wageTypeTeacher);

    WageTypeTeacher findOneWageTypeTeacherByCondition(WageTypeTeacher wageTypeTeacher);

    long findWageTypeTeacherCountByCondition(WageTypeTeacher wageTypeTeacher);

    WageTypeTeacher findWageTypeTeacherById(@Param("id") String id);

    void saveWageTypeTeacher(WageTypeTeacher wageTypeTeacher);

    void updateWageTypeTeacher(WageTypeTeacher wageTypeTeacher);

    void deleteWageTypeTeacher(@Param("id") String id);

    void deleteWageTypeTeacherByCondition(WageTypeTeacher wageTypeTeacher);

    void batchSaveWageTypeTeacher(List<WageTypeTeacher> wageTypeTeachers);

    List<Map<String, String>> findWageValueByTypeId(Map<String,Object> wageTypeTeacherMap);

    List<WageTypeTeacher> findWageAttributeListByRecordId(WageTypeTeacher wageTypeTeacher);

    List<WageTypeTeacher> findWageAttributeListByTeacherId(WageTypeTeacher wageTypeTeacher);

    List<WageTypeTeacher> findWageAttributeNameByTeacherId(WageTypeTeacher wageTypeTeacher);

    List<Map<String, Object>> findWageValueByRecordId(Map<String,Object> wageTypeRecordMap);

    List<Map<String, Object>> findWageValueByTeacherId(Map<String,Object> wageTypeTeacherMap);

    long findWageValueByTypeIdCount(WageTypeTeacher wageTypeTeacher);

    WageTypeTeacher findWageTeacherIdByRecordId(WageTypeTeacher wageTypeTeacher);
}
