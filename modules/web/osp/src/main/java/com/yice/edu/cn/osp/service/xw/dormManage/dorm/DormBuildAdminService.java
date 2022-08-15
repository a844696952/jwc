package com.yice.edu.cn.osp.service.xw.dormManage.dorm;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildAdmin;
import com.yice.edu.cn.osp.feignClient.jw.building.BuildingFeign;
import com.yice.edu.cn.osp.feignClient.xw.dormManage.dorm.DormBuildAdminFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.osp.interceptor.LoginInterceptor.mySchoolId;

@Service
public class DormBuildAdminService {
    @Autowired
    private DormBuildAdminFeign dormBuildAdminFeign;
    @Autowired
    private BuildingFeign buildingFeign;

    public DormBuildAdmin findDormBuildAdminById(String id) {
        return dormBuildAdminFeign.findDormBuildAdminById(id);
    }

    public boolean saveDormBuildAdmin(DormBuildAdmin dormBuildAdmin) {
        if(dormBuildAdmin.getStaffType().equals(Constant.DORM_STAFF_TYPE.DORM_ADMIN)){
            String[] staffIds = dormBuildAdmin.getStaffIds();
            if (staffIds!=null&&staffIds.length>0){
                for (String staffId : staffIds) {
                    dormBuildAdmin.setStaffId(staffId);
                    dormBuildAdminFeign.saveDormBuildAdmin(dormBuildAdmin);
                }
            }
        }
        if(dormBuildAdmin.getStaffType().equals(Constant.DORM_STAFF_TYPE.DORM_TEACHER)){
            DormBuildAdmin admin = dormBuildAdminFeign.findOneDormBuildAdminByCondition(dormBuildAdmin);
            if(admin==null){
                dormBuildAdminFeign.saveDormBuildAdmin(dormBuildAdmin);
            }else {
                return false;
            }

        }

        return true;
    }

    public List<DormBuildAdmin> findDormBuildAdminListByCondition(DormBuildAdmin dormBuildAdmin) {
        return dormBuildAdminFeign.findDormBuildAdminListByCondition(dormBuildAdmin);
    }

    public DormBuildAdmin findOneDormBuildAdminByCondition(DormBuildAdmin dormBuildAdmin) {
        return dormBuildAdminFeign.findOneDormBuildAdminByCondition(dormBuildAdmin);
    }

    public long findDormBuildAdminCountByCondition(DormBuildAdmin dormBuildAdmin) {
        return dormBuildAdminFeign.findDormBuildAdminCountByCondition(dormBuildAdmin);
    }

    public void updateDormBuildAdmin(DormBuildAdmin dormBuildAdmin) {
        dormBuildAdminFeign.updateDormBuildAdmin(dormBuildAdmin);
    }

    public void deleteDormBuildAdmin(String id) {
        dormBuildAdminFeign.deleteDormBuildAdmin(id);
    }

    public void deleteDormBuildAdminByCondition(DormBuildAdmin dormBuildAdmin) {
        dormBuildAdminFeign.deleteDormBuildAdminByCondition(dormBuildAdmin);
    }
/*---------------------------------------------------------------------------------------------------*/
    public List<Building> getBuildingList(Building building) {
       return dormBuildAdminFeign.getBuildingList(building);
    }

    public List<DormBuildAdmin> findDormBuildAdminListByConditionConnect(DormBuildAdmin dormBuildAdmin) {
        return dormBuildAdminFeign.findDormBuildAdminListByConditionConnect(dormBuildAdmin);
    }

    public long findDormBuildAdminListCountConnect(DormBuildAdmin dormBuildAdmin) {
        return dormBuildAdminFeign.findDormBuildAdminListCountConnect(dormBuildAdmin);
    }

    public List<DormBuildAdmin> findDormBuildTeacherByConditionConnect(DormBuildAdmin dormBuildAdmin) {
        return dormBuildAdminFeign.findDormBuildTeacherByConditionConnect(dormBuildAdmin);
    }

    public List<Building> findCreateDormBuildingList(DormBuildAdmin dormBuildAdmin){
        return dormBuildAdminFeign.findCreateDormBuildingList(dormBuildAdmin);
    }


    //根据登录人查询所管宿舍楼
    public List<Building> findDormBuildingByLogin(){
        DormBuildAdmin dormBuildAdmin = new DormBuildAdmin();
        dormBuildAdmin.setSchoolId(mySchoolId());
        dormBuildAdmin.setStaffId(myId());
        dormBuildAdmin.setStaffType(Constant.DORM_STAFF_TYPE.DORM_TEACHER);
        DormBuildAdmin oneDormBuildAdmin = dormBuildAdminFeign.findOneDormBuildAdminByCondition(dormBuildAdmin);
        if (oneDormBuildAdmin!=null&&oneDormBuildAdmin.getStaffId().equals(myId())){
            //查询的为宿管老师,可以查看所有宿舍楼
            DormBuildAdmin dormTeacher = new DormBuildAdmin();
            dormTeacher.setSchoolId(mySchoolId());
           return dormBuildAdminFeign.findDormBuildListByCondition(dormTeacher);
        }else {
            //否则为宿舍管理员,只可以查看管理的楼栋
            DormBuildAdmin dormAdmin = new DormBuildAdmin();
            dormAdmin.setSchoolId(mySchoolId());
            dormAdmin.setStaffId(myId());
           return dormBuildAdminFeign.findCreateDormBuildingList(dormAdmin);
        }
    }

    public List<Building> findDormBuildListByCondition(){
        DormBuildAdmin dormBuildAdmin = new DormBuildAdmin();
        dormBuildAdmin.setSchoolId(mySchoolId());
       return dormBuildAdminFeign.findDormBuildListByCondition(dormBuildAdmin);
    }


}
