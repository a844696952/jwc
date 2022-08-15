package com.yice.edu.cn.tap.service.xwDormManage.dorm;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildAdmin;
import com.yice.edu.cn.tap.feignClient.xwDormManage.dorm.DormBuildAdminFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.myId;
import static com.yice.edu.cn.tap.interceptor.LoginInterceptor.mySchoolId;

@Service
public class DormBuildAdminService {
    @Autowired
    private DormBuildAdminFeign dormBuildAdminFeign;


    public List<DormBuildAdmin> findDormBuildAdminListByCondition(DormBuildAdmin dormBuildAdmin) {
        return dormBuildAdminFeign.findDormBuildAdminListByCondition(dormBuildAdmin);
    }

    public List<Building> findCreateDormBuildingList(DormBuildAdmin dormBuildAdmin){
        return dormBuildAdminFeign.findCreateDormBuildingList(dormBuildAdmin);
    }

    public long findDormBuildAdminListCountConnect(DormBuildAdmin dormBuildAdmin) {
        return dormBuildAdminFeign.findDormBuildAdminListCountConnect(dormBuildAdmin);
    }

    public List<DormBuildAdmin> findDormBuildTeacherByConditionConnect(DormBuildAdmin dormBuildAdmin) {
        return dormBuildAdminFeign.findDormBuildTeacherByConditionConnect(dormBuildAdmin);
    }

    public long findDormBuildAdminCountByCondition(DormBuildAdmin dormBuildAdmin) {
        return dormBuildAdminFeign.findDormBuildAdminCountByCondition(dormBuildAdmin);
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


}
