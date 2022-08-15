package com.yice.edu.cn.osp.service.dy.schoolManage.institution;

import com.yice.edu.cn.common.pojo.mes.schoolManage.institution.MesCustomTitle;
import com.yice.edu.cn.osp.feignClient.dy.schoolManage.institution.MesCustomTitleFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesCustomTitleService {
    @Autowired
    private MesCustomTitleFeign mesCustomTitleFeign;

    public MesCustomTitle findMesCustomTitleById(String id) {
        return mesCustomTitleFeign.findMesCustomTitleById(id);
    }

    public MesCustomTitle saveMesCustomTitle(MesCustomTitle mesCustomTitle) {
        return mesCustomTitleFeign.saveMesCustomTitle(mesCustomTitle);
    }

    public List<MesCustomTitle> findMesCustomTitleListByCondition(MesCustomTitle mesCustomTitle) {
        return mesCustomTitleFeign.findMesCustomTitleListByCondition(mesCustomTitle);
    }

    public MesCustomTitle findOneMesCustomTitleByCondition(MesCustomTitle mesCustomTitle) {
        return mesCustomTitleFeign.findOneMesCustomTitleByCondition(mesCustomTitle);
    }

    public long findMesCustomTitleCountByCondition(MesCustomTitle mesCustomTitle) {
        return mesCustomTitleFeign.findMesCustomTitleCountByCondition(mesCustomTitle);
    }

    public void updateMesCustomTitle(MesCustomTitle mesCustomTitle) {
        mesCustomTitleFeign.updateMesCustomTitle(mesCustomTitle);
    }

    public void deleteMesCustomTitle(String id) {
        mesCustomTitleFeign.deleteMesCustomTitle(id);
    }

    public void deleteMesCustomTitleByCondition(MesCustomTitle mesCustomTitle) {
        mesCustomTitleFeign.deleteMesCustomTitleByCondition(mesCustomTitle);
    }
}
