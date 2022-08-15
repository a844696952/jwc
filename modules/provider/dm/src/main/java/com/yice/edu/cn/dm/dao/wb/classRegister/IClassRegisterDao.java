package com.yice.edu.cn.dm.dao.wb.classRegister;

import com.yice.edu.cn.common.pojo.wb.classRegister.ClassRegister;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IClassRegisterDao {
    List<ClassRegister> findClassRegisterListByCondition(ClassRegister classRegister);

    long findClassRegisterCountByCondition(ClassRegister classRegister);

    ClassRegister findOneClassRegisterByCondition(ClassRegister classRegister);

    ClassRegister findClassRegisterById(@Param("id") String id);

    void saveClassRegister(ClassRegister classRegister);

    void updateClassRegister(ClassRegister classRegister);
    void updateClassRegisterStatus(ClassRegister classRegister);

    void deleteClassRegister(@Param("id") String id);

    void deleteClassRegisterByCondition(ClassRegister classRegister);

    void batchSaveClassRegister(List<ClassRegister> classRegisters);

    List<ClassRegister> findClassRegisterCountByCondition2(ClassRegister classRegister);
    List<ClassRegister> findClassRegisterListByCondition2(ClassRegister classRegister);
}
