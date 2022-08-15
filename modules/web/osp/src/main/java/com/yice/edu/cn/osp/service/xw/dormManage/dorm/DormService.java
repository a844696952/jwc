package com.yice.edu.cn.osp.service.xw.dormManage.dorm;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.Dorm;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildVo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPerson;
import com.yice.edu.cn.osp.feignClient.jw.building.BuildingFeign;
import com.yice.edu.cn.osp.feignClient.xw.dormManage.dorm.DormFeign;
import com.yice.edu.cn.osp.feignClient.xw.dormManage.dorm.DormPersonFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class DormService {
    @Autowired
    private DormFeign dormFeign;

    public Dorm findDormById(String id) {
        return dormFeign.findDormById(id);
    }

    public Dorm saveDorm(Dorm dorm) {
        return dormFeign.saveDorm(dorm);
    }

    public List<Dorm> findDormListByCondition(Dorm dorm) {
        return dormFeign.findDormListByCondition(dorm);
    }

    public Dorm findOneDormByCondition(Dorm dorm) {
        return dormFeign.findOneDormByCondition(dorm);
    }

    public long findDormCountByCondition(Dorm dorm) {
        return dormFeign.findDormCountByCondition(dorm);
    }

    public void updateDorm(Dorm dorm) {
        dormFeign.updateDorm(dorm);
    }

    public void deleteDorm(String id) {
        dormFeign.deleteDorm(id);
    }

    public void deleteDormByCondition(Dorm dorm) {
        dormFeign.deleteDormByCondition(dorm);
    }

    /*------------------------------------------------------------------------------------------------------*/
    public List<Dorm> findDormBuildingTreeByCondition(DormBuildVo dormBuildVo) {
        List<Dorm> dormList = dormFeign.findDormBuildingTreeByCondition(dormBuildVo);
        List<Dorm> dormFloorList = dormFeign.findDormFloorNum(dormBuildVo);
        if(dormList!=null&&dormList.size()>0){
            dormList.forEach(one->{
                List<Dorm> twoList = one.getChildren();
                if(twoList!=null&&twoList.size()>0){
                    twoList.forEach(two->{
                        if(dormFloorList!=null&&dormFloorList.size()>0){
                            dormFloorList.forEach(f->{
                              if(f.getDormId().equals(two.getDormId())){
                                  //设置楼层容量与入住人数
                                    two.setFloorCapacity(f.getFloorCapacity());
                                    two.setFloorEmptyNum(f.getFloorEmptyNum());
                                    two.setFloorPersonNum(f.getFloorPersonNum());
                                }
                            });
                        }

                        List<Dorm> threeList = two.getChildren();
                        if (threeList!=null&&threeList.size()>0){
                            threeList.forEach(three->{
                                //设置宿舍状态
                                if (three.getPersonNum()>=three.getCapacity()){
                                    three.setDormStatus("1");
                                }else {
                                    three.setDormStatus("0");
                                }
                            });

                            //根据状态进行删选
                            if (dormBuildVo.getDormStatus()!=null){
                                if ("1".equals(dormBuildVo.getDormStatus())) {
                                    threeList = threeList.stream().filter(t -> t.getDormStatus().equals("1")).collect(Collectors.toList());
                                }
                                if ("0".equals(dormBuildVo.getDormStatus())) {
                                    threeList = threeList.stream().filter(t -> t.getDormStatus().equals("0")).collect(Collectors.toList());
                                }
                            }
                            two.setChildren(threeList);
                        }
                    });
                }

            });
        }

        return dormList;

    }

    public void batchSaveDormType(Dorm dorm) {
        dorm.setSchoolId(mySchoolId());
        dormFeign.batchSaveDormType(dorm);
    }

    public List<Building> findDormBuildingAndFloor(String schoolId){
        return dormFeign.findDormBuildingAndFloor(schoolId);
    }

    public List<Dorm> findDormByFloorId(Building building){
        return dormFeign.findDormByFloorId(building);
    }

}
