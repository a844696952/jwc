package com.yice.edu.cn.osp.service.dy.schoolManage.institution;

import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesTimeStatus;
import com.yice.edu.cn.osp.feignClient.dy.schoolManage.institution.MesTimeStatusFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesTimeStatusService {
    @Autowired
    private MesTimeStatusFeign mesTimeStatusFeign;

    public MesTimeStatus findMesTimeStatusById(String id) {
        return mesTimeStatusFeign.findMesTimeStatusById(id);
    }

    public MesTimeStatus saveMesTimeStatus(MesTimeStatus mesTimeStatus) {
        return mesTimeStatusFeign.saveMesTimeStatus(mesTimeStatus);
    }

    public List<MesTimeStatus> findMesTimeStatusListByCondition(MesTimeStatus mesTimeStatus) {
        return mesTimeStatusFeign.findMesTimeStatusListByCondition(mesTimeStatus);
    }

    public MesTimeStatus findOneMesTimeStatusByCondition(MesTimeStatus mesTimeStatus) {
        return mesTimeStatusFeign.findOneMesTimeStatusByCondition(mesTimeStatus);
    }

    public long findMesTimeStatusCountByCondition(MesTimeStatus mesTimeStatus) {
        return mesTimeStatusFeign.findMesTimeStatusCountByCondition(mesTimeStatus);
    }

    public void updateMesTimeStatus(MesTimeStatus mesTimeStatus) {
        mesTimeStatusFeign.updateMesTimeStatus(mesTimeStatus);
    }

    public void deleteMesTimeStatus(String id) {
        mesTimeStatusFeign.deleteMesTimeStatus(id);
    }

    public void deleteMesTimeStatusByCondition(MesTimeStatus mesTimeStatus) {
        mesTimeStatusFeign.deleteMesTimeStatusByCondition(mesTimeStatus);
    }
}
