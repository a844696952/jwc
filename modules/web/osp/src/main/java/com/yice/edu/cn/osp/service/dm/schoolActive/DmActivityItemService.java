package com.yice.edu.cn.osp.service.dm.schoolActive;

import com.yice.edu.cn.common.pojo.dm.schoolActive.DmActivityItem;
import com.yice.edu.cn.osp.feignClient.dm.schoolActive.DmActivityItemFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DmActivityItemService {
    @Autowired
    private DmActivityItemFeign dmActivityItemFeign;

    public DmActivityItem findDmActivityItemById(String id) {
        return dmActivityItemFeign.findDmActivityItemById(id);
    }

    public DmActivityItem saveDmActivityItem(DmActivityItem dmActivityItem) {
        return dmActivityItemFeign.saveDmActivityItem(dmActivityItem);
    }

    public List<DmActivityItem> findDmActivityItemListByCondition(DmActivityItem dmActivityItem) {
        return dmActivityItemFeign.findDmActivityItemListByCondition(dmActivityItem);
    }

    public DmActivityItem findOneDmActivityItemByCondition(DmActivityItem dmActivityItem) {
        return dmActivityItemFeign.findOneDmActivityItemByCondition(dmActivityItem);
    }

    public long findDmActivityItemCountByCondition(DmActivityItem dmActivityItem) {
        return dmActivityItemFeign.findDmActivityItemCountByCondition(dmActivityItem);
    }

    public void updateDmActivityItem(DmActivityItem dmActivityItem) {
        dmActivityItemFeign.updateDmActivityItem(dmActivityItem);
    }

    public void deleteDmActivityItem(String id) {
        dmActivityItemFeign.deleteDmActivityItem(id);
    }

    public void deleteDmActivityItemByCondition(DmActivityItem dmActivityItem) {
        dmActivityItemFeign.deleteDmActivityItemByCondition(dmActivityItem);
    }
}
