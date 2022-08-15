package com.yice.edu.cn.xw.service.zc.assetsInOutQuery;

import com.yice.edu.cn.common.pojo.jw.department.DepartmentTeacher;
import com.yice.edu.cn.common.pojo.xw.zc.assetsFile.AssetsFile;
import com.yice.edu.cn.common.pojo.xw.zc.assetsStockDetail.AssetsStockDetail;
import com.yice.edu.cn.xw.dao.zc.assetsInOutQuery.IAssetsInOutQueryDao;
import com.yice.edu.cn.xw.feignClient.jw.department.DepartmentTeacherFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssetsInOutQueryService {
    @Autowired
    private IAssetsInOutQueryDao assetsInOutQueryDao;

    @Autowired
    private DepartmentTeacherFeign departmentTeacherFeign;

    @Transactional
    public List<AssetsFile> findInOutQueryByCondition(AssetsFile assetsFile){
        return assetsInOutQueryDao.findInOutQueryByCondition(assetsFile);
    }

    @Transactional
    public long findInOutQueryCountByCondition(AssetsFile assetsFile){
        return assetsInOutQueryDao.findInOutQueryCountByCondition(assetsFile);
    }

    @Transactional
    public List<AssetsFile> findAssetUseDataByCondition(AssetsFile assetsFile){
        //return assetsInOutQueryDao.findAssetUseDataByCondition(assetsFile);
        List<AssetsFile>  fileList = assetsInOutQueryDao.findAssetUseDataByCondition(assetsFile);
        List<AssetsFile> newListDetail = fileList.stream().map(ss -> {
            if (null != ss.getDutyPersonId() && !"".equals(ss.getDutyPersonId())) {
                List<DepartmentTeacher> department = departmentTeacherFeign.findDepartmentByTeacherId(ss.getDutyPersonId());
                StringBuffer personDeptId = new StringBuffer();
                StringBuffer personDeptName = new StringBuffer();
                if(null != department && department.size()>0){
                    for(DepartmentTeacher d:department){
                        personDeptId.append(d.getDepartmentId());
                        personDeptId.append(",");
                        personDeptName.append(d.getDepartmentName());
                        personDeptName.append(",");
                    }


                    String subPersonDeptId = personDeptId.substring(0, personDeptId.lastIndexOf(","));
                    String subPersonDeptName = personDeptName.substring(0, personDeptName.lastIndexOf(","));
                    ss.setDutyPersonDept(subPersonDeptName);
                }
            }

            return ss;
        }).collect(Collectors.toList());
        return newListDetail;
    }

    @Transactional
    public long findAssetUseDataCountByCondition(AssetsFile assetsFile){
        return assetsInOutQueryDao.findAssetUseDataCountByCondition(assetsFile);
    }
}
