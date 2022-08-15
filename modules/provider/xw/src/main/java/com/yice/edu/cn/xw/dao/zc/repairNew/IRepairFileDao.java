package com.yice.edu.cn.xw.dao.zc.repairNew;

import java.util.List;

import com.yice.edu.cn.common.pojo.xw.zc.repairNew.RepairFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IRepairFileDao {
    List<RepairFile> findRepairFileListByCondition(RepairFile repairFile);

    long findRepairFileCountByCondition(RepairFile repairFile);

    RepairFile findOneRepairFileByCondition(RepairFile repairFile);

    RepairFile findRepairFileById(@Param("id") String id);

    void saveRepairFile(RepairFile repairFile);

    void updateRepairFile(RepairFile repairFile);

    void deleteRepairFile(@Param("id") String id);

    void deleteRepairFileByCondition(RepairFile repairFile);

    void batchSaveRepairFile(List<RepairFile> repairFiles);
}
