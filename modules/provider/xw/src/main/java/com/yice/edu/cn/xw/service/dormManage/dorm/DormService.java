package com.yice.edu.cn.xw.service.dormManage.dorm;

import com.yice.edu.cn.common.dbSupport.mysqlId.SequenceId;
import com.yice.edu.cn.common.pojo.jw.building.Building;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.Dorm;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildVo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormBuildingPersonInfo;
import com.yice.edu.cn.common.pojo.xw.dormManage.dorm.DormPerson;
import com.yice.edu.cn.xw.dao.dormManage.dorm.IDormDao;
import com.yice.edu.cn.xw.dao.dormManage.dorm.IDormPersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DormService {
    @Autowired
    private IDormDao dormDao;
    @Autowired
    private SequenceId sequenceId;
    @Autowired
    private IDormPersonDao dormPersonDao;
/*-------------------------------------------------generated code start,do not update-----------------------------------------------------------*/
    @Transactional(readOnly = true)
    public Dorm findDormById(String id) {
        return dormDao.findDormById(id);
    }
    @Transactional
    public Dorm saveDorm(Dorm dorm) {
        Dorm findDorm = new Dorm();
        findDorm.setDormId(dorm.getDormId());
        findDorm.setSchoolId(dorm.getSchoolId());
        findDorm.setPersonNum(null);
        Dorm oneDorm = dormDao.findOneDormByCondition(findDorm);
        //如果有值为修改
        if (oneDorm!=null&&oneDorm.getDormCategory()!=null&&oneDorm.getDormType()!=null){
            //修改操作
            dorm.setId(oneDorm.getId());
            dorm.setPersonNum(null);
            dormDao.updateDorm(dorm);
            return null;
        }else {
            //添加操作
            dorm.setId(sequenceId.nextId());
            dormDao.saveDorm(dorm);
            //为宿舍添加床位
            Building building = dormDao.findBuildingById(dorm.getDormId());
            if (building!=null){
                this.addDormPerson(building);
            }
            return dorm;
        }
    }
    @Transactional(readOnly = true)
    public List<Dorm> findDormListByCondition(Dorm dorm) {
        return dormDao.findDormListByCondition(dorm);
    }
    @Transactional(readOnly = true)
    public Dorm findOneDormByCondition(Dorm dorm) {
        return dormDao.findOneDormByCondition(dorm);
    }
    @Transactional(readOnly = true)
    public long findDormCountByCondition(Dorm dorm) {
        return dormDao.findDormCountByCondition(dorm);
    }
    @Transactional
    public void updateDorm(Dorm dorm) {
        dormDao.updateDorm(dorm);
    }
    @Transactional
    public void deleteDorm(String id) {
        dormDao.deleteDorm(id);
    }
    @Transactional
    public void deleteDormByCondition(Dorm dorm) {
        dormDao.deleteDormByCondition(dorm);
    }
    @Transactional
    public void batchSaveDorm(List<Dorm> dorms){
        dorms.forEach(dorm -> dorm.setId(sequenceId.nextId()));
        dormDao.batchSaveDorm(dorms);
    }
/*-------------------------------------------------generated code end,do not update-----------------------------------------------------------*/
    @Transactional
    public List<Dorm> findDormBuildingTreeByCondition(DormBuildVo dormBuildVo){
        return dormDao.findDormBuildingTreeByCondition(dormBuildVo);
    }
    @Transactional
    public List<Dorm> findDormFloorNum(DormBuildVo dormBuildVo) {
        return dormDao.findDormFloorNum(dormBuildVo);
    }

    @Transactional
    public void updateDormByDormId(Dorm dorm){
        dormDao.updateDormByDormId(dorm);
    }
    @Transactional
    public List<Dorm> findDormBuildingTreeByConditionTap(DormBuildVo dormBuildVo){
        return dormDao.findDormBuildingTreeByConditionTap(dormBuildVo);
    }
    @Transactional
    public List<DormBuildingPersonInfo> findDormListByTypeAndCategory(Dorm dorm){
        return dormDao.findDormListByTypeAndCategory(dorm);
    }

    @Transactional
    public void
    batchSaveDormType(Dorm dorm) {
        List<String> dormIds = dorm.getDormIds();
        for (String dormId : dormIds) {
            Dorm findDorm = new Dorm();
            findDorm.setDormId(dormId);
            findDorm.setSchoolId(dorm.getSchoolId());
            findDorm.setPersonNum(null);
            Dorm oneDorm = dormDao.findOneDormByCondition(findDorm);
            //如果有值为修改
            if (oneDorm!=null&&oneDorm.getDormCategory()!=null&&oneDorm.getDormType()!=null){
                dorm.setId(oneDorm.getId());
                dorm.setPersonNum(null);
                dormDao.updateDorm(dorm);
            }else {
                //如果没值为保存
                dorm.setId(sequenceId.nextId());
                dorm.setDormId(dormId);
                dorm.setPersonNum(0);
                dormDao.saveDorm(dorm);
                //为宿舍添加床位
                Building building = dormDao.findBuildingById(dorm.getDormId());
                if (building!=null){
                    this.addDormPerson(building);
                }

            }

        }
    }

    private void addDormPerson(Building building){
        String capacity = building.getCapacity();
        for (int i = 1; i <= Integer.parseInt(capacity); i++) {
            DormPerson dormPerson = new DormPerson();
            dormPerson.setId(sequenceId.nextId());
            dormPerson.setDormId(building.getId());
            dormPerson.setBunkName(i+"号床");
            dormPerson.setBunkSort(i);
            dormPerson.setSchoolId(building.getSchoolId());
            dormPersonDao.saveDormPerson(dormPerson);
        }
    }

    @Transactional
    public List<Building> findDormBuildingAndFloor(String schoolId){
       return dormDao.findDormBuildingAndFloor(schoolId);
    }

    @Transactional
    public List<Dorm> findDormByFloorId(Building building){
        return dormDao.findDormByFloorId(building);
    }

}
