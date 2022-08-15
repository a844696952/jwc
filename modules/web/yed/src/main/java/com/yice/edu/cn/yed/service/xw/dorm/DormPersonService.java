package com.yice.edu.cn.yed.service.xw.dorm;

import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildVo;
import com.yice.edu.cn.yed.feignClient.xw.dorm.DormPersonFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DormPersonService {
    @Autowired
    private DormPersonFeign dormPersonFeign;

    //对接楼栋管理方法:根据宿舍ids查询宿舍下是否有人
    public boolean findDormIsPersonByDormId(List<String> dormIds){
        DormBuildVo dormBuildVo = new DormBuildVo();
        dormBuildVo.setDormIdList(dormIds);
        long count = dormPersonFeign.findDormMoveIntoPersonNumByCondition(dormBuildVo);
        if(count>0){
            return true;
        }else {
            return false;
        }
    }

    //对接楼栋管理方法:修改宿舍的容量
    public void updateDormCapacityByDormId(Building building){
        dormPersonFeign.updateDormCapacityByDormId(building);
    }

    //对接删除场地的方法
    public void deleteDormAndDormPerson(List<String> dormIdList){
        dormPersonFeign.deleteDormAndDormPerson(dormIdList);
    }

}
