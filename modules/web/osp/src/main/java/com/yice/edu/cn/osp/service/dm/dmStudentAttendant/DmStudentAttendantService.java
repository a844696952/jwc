package com.yice.edu.cn.osp.service.dm.dmStudentAttendant;

import com.yice.edu.cn.common.pojo.Constant;
import com.yice.edu.cn.common.pojo.ResponseJson;
import com.yice.edu.cn.common.pojo.dm.dmStudentAttendant.DmStudentAttendant;
import com.yice.edu.cn.common.pojo.general.dd.Dd;
import com.yice.edu.cn.common.pojo.wb.studentAccount.StudentAccount;
import com.yice.edu.cn.osp.feignClient.dd.DdFeign;
import com.yice.edu.cn.osp.feignClient.dm.dmStudentAttendant.DmStudentAttendantFeign;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DmStudentAttendantService {
    @Autowired
    private DmStudentAttendantFeign dmStudentAttendantFeign;
    @Autowired
    private DdFeign ddFeign;

    public DmStudentAttendant findDmStudentAttendantById(String id) {
        return dmStudentAttendantFeign.findDmStudentAttendantById(id);
    }

    public DmStudentAttendant saveDmStudentAttendant(DmStudentAttendant dmStudentAttendant) {
        return dmStudentAttendantFeign.saveDmStudentAttendant(dmStudentAttendant);
    }

    public List<DmStudentAttendant> findDmStudentAttendantListByCondition(DmStudentAttendant dmStudentAttendant) {
        List<DmStudentAttendant> list = dmStudentAttendantFeign.findDmStudentAttendantListByCondition(dmStudentAttendant);
        List<Dd> ddList = findAttendantType();
        if(list != null && list.size() >0){
            List<String> ddIds = list.stream().map(DmStudentAttendant::getDdId).collect(Collectors.toList());
            for (Dd dd : ddList) {
                if(!ddIds.contains(dd.getId())){
                    DmStudentAttendant attendant = copyData(dmStudentAttendant,dd);
                    list.add(attendant);
                }
            }
        }else{
            for (Dd dd : ddList) {
                DmStudentAttendant attendant = copyData(dmStudentAttendant,dd);
                list.add(attendant);
            }
        }
        Collections.sort(list, new Comparator<DmStudentAttendant>(){
            public int compare(DmStudentAttendant o1, DmStudentAttendant o2) {
                //按照CityModel的city_code字段进行降序排列
                if(Integer.valueOf(o1.getDdId()) < Integer.valueOf(o2.getDdId())){
                    return -1;
                }
                if(Integer.valueOf(o1.getDdId()) == Integer.valueOf(o2.getDdId())){
                    return 0;
                }
                return 1;
            }
        });
        return list;
    }

    public DmStudentAttendant findOneDmStudentAttendantByCondition(DmStudentAttendant dmStudentAttendant) {
        return dmStudentAttendantFeign.findOneDmStudentAttendantByCondition(dmStudentAttendant);
    }

    public long findDmStudentAttendantCountByCondition(DmStudentAttendant dmStudentAttendant) {
        return dmStudentAttendantFeign.findDmStudentAttendantCountByCondition(dmStudentAttendant);
    }

    public void updateDmStudentAttendant(DmStudentAttendant dmStudentAttendant) {
        dmStudentAttendantFeign.updateDmStudentAttendant(dmStudentAttendant);
    }

    public void updateDmStudentAttendantForNotNull(DmStudentAttendant dmStudentAttendant) {
        dmStudentAttendantFeign.updateDmStudentAttendantForNotNull(dmStudentAttendant);
    }

    public void deleteDmStudentAttendant(String id) {
        dmStudentAttendantFeign.deleteDmStudentAttendant(id);
    }

    public void deleteDmStudentAttendantByCondition(DmStudentAttendant dmStudentAttendant) {
        dmStudentAttendantFeign.deleteDmStudentAttendantByCondition(dmStudentAttendant);
    }

    public List<Dd> findAttendantType(){
        Dd dd = new Dd();
        dd.setTypeId(Constant.DD_TYPE.ATTENTDANTTYPE);
        return ddFeign.findDdListByCondition(dd);
    }

    private DmStudentAttendant copyData(DmStudentAttendant dmStudentAttendant,Dd dd){
        DmStudentAttendant studentAttendant = null;
        try {
            studentAttendant = (DmStudentAttendant) dmStudentAttendant.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        studentAttendant.setDdId(dd.getId());
        studentAttendant.setWeekName(dd.getName());
        return studentAttendant;
    }

}
